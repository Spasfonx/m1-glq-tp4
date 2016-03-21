package controller;

import iug.IIUG;
import outils.Demande;
import outils.Logger;
import outils.Message;
import outils.Sens;
import cabine.ICabine;
import commande.ListeTrieeCirculaireDeDemandes;

/**
 * Classe Controller permettant de gérer les demandes entre l'iug et la cabine. 
 * @author Christophe
 */
public class Controller implements IController {

	/**
	 * Nombre d'étage maximal que gère le controlleur.
	 */
	private int nombreEtages;
	
	/**
	 * Position à l'instant actuel de la cabine.
	 */
	private int position;
	
	/**
	 * Sens courant de la cabine.
	 */
	private Sens sens;
	
	/**
	 * Liste des demandes en attente.
	 */
	private ListeTrieeCirculaireDeDemandes stockDemandes;
	
	/**
	 * Reférence à l'objet représentant l'iug.
	 */
	private IIUG iug;
	
	/**
	 * Reférence à l'objet représentant la cabine.
	 */
	private ICabine cabine;
	
	/**
	 * Etat dans lequel se trouve la cabine.
	 * @see controller.EnumEtatController
	 */
	private EnumEtatController etat;
	
	/**
	 * Constructeur de la classe. 
	 * @param pCabine - Lien vers la cabine
	 * @param pIUG - Lien vers l'iug
	 * @param pNombreEtages - Nombre d'étage maximal que gère le controlleur
	 * @param pPosition - Position actuelle
	 * @param pSens - Sens actuel
	 */
	public Controller(final ICabine pCabine, final IIUG pIUG,
			final int pNombreEtages, final int pPosition, final Sens pSens) {
		this.nombreEtages = pNombreEtages;
		this.position = pPosition;
		this.sens = pSens;
		
		this.cabine = pCabine;
		this.iug = pIUG;

		this.stockDemandes = new ListeTrieeCirculaireDeDemandes(pNombreEtages);
		this.etat = EnumEtatController.ATTENTE_DEMANDE;
	}
	
	/**
	 * Constructeur de la classe. Initialise la position à 0 
	 * et le sens à INDEFINI.
	 * @param pCabine - Lien vers la cabine
	 * @param pIUG - Lien vers l'iug
	 * @param pNombreEtages - Nombre d'étage maximal que 
	 * gère le controlleur
	 */
	public Controller(final ICabine pCabine, final IIUG pIUG,
			final int pNombreEtages) {
		this(pCabine, pIUG, pNombreEtages, 0, Sens.INDEFINI);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void demander(final Demande pDemande) {
		Demande demandeActuelle = new Demande(pDemande.etage(), pDemande.sens());
		
		if (EnumEtatController.ATTENTE_DEMANDE.equals(etat)) {
			this.iug.allumerBouton(demandeActuelle);
			this.stocker(demandeActuelle);
			
			/* On initialise le sens de départ de la cabine */
			int d = demandeActuelle.etage() - position;
			
			/* Si c'est directement l'étage suivant */
			if (Math.abs(d) == 1) {
				if (d == 1) {
					this.cabine.monter();
					this.etat = EnumEtatController.MONTEE;
				} else {
					this.cabine.descendre();
					this.etat = EnumEtatController.DESCENTE;
				}

				MAJSens();
				
				this.eteindreBouton(demandeActuelle);
				
				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_ETAGE;
				
				Logger.writeLog(Message.ARRET_PROCHAIN.getMessage());
			/* Sinon */
			} else if (demandeActuelle.etage() > position) {
				this.cabine.monter();
				this.etat = EnumEtatController.MONTEE;
			} else if (demandeActuelle.etage() < position) {
				this.cabine.descendre();
				this.etat = EnumEtatController.DESCENTE;
			} 
			
			/* Demande au même palier */ //TODO: VERIFIER TOUS LES CAS !!!!!
			if (this.position == demandeActuelle.etage() && (demandeActuelle.sens().equals((this.sens))
					|| Sens.INDEFINI.equals(this.sens))) {
				this.eteindreBouton(demandeActuelle);
			}
			
			if (!EnumEtatController.ARRET_IMMINENT.equals(this.etat)
					&& !EnumEtatController.ARRET_ETAGE.equals(this.etat)) {
				MAJSens();
			}
		} else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
			/* On vérifie que la demande ne soit pas l'étage 
			 * sur lequel on va déjà s'arrêté */
			if (Sens.MONTEE.equals(this.sens) 
					&& (demandeActuelle.etage() != this.position + 1 
						|| !demandeActuelle.sens().equals(this.sens))
				|| Sens.DESCENTE.equals(this.sens) 
					&& (demandeActuelle.etage() != this.position - 1 
						|| !demandeActuelle.sens().equals(this.sens))) {
				this.iug.allumerBouton(demandeActuelle);
				stocker(demandeActuelle);
			}
		} /*else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
			/* On vérifie que la demande ne soit pas l'étage
			 * sur lequel on est déjà arrêté
			if (demandeActuelle.etage() != this.position 
					|| demandeActuelle.sens() != this.sens) {
				this.iug.allumerBouton(demandeActuelle);
				this.stocker(demandeActuelle);
			}
		} */else {
			this.iug.allumerBouton(demandeActuelle);
			stocker(demandeActuelle);
		}
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void signalerChangementDEtage() {
		MAJPosition();
		
		Logger.writeLog(String.format(
				Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), 
				this.position)
			);
		
		Demande demandeSuivante = interrogerStock();
		
		if ((EnumEtatController.MONTEE.equals(etat) 
				|| EnumEtatController.DESCENTE.equals(etat))
				&& demandeSuivante != null) {
			// On doit s'arrêter aux prochains etage (cas normal)
			if ((demandeSuivante.etage() == this.position + 1
					&& Sens.MONTEE.equals(demandeSuivante.sens()))
				|| (demandeSuivante.etage() == this.position - 1
						&& Sens.DESCENTE.equals(demandeSuivante.sens()))) {
				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_IMMINENT;
			} 
			
			// On doit s'arrêter au prochain étage, cas extrêmes
			else if ((demandeSuivante.etage() == this.position + 1
						&& (demandeSuivante.etage() == this.nombreEtages - 1 || stockDemandes.taille() <= 1)
						&& Sens.DESCENTE.equals(demandeSuivante.sens()))
					|| (demandeSuivante.etage() == this.position - 1
						&& (demandeSuivante.etage() == 0 || stockDemandes.taille() <= 1) 
						&& Sens.MONTEE.equals(demandeSuivante.sens()))) {
				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_IMMINENT;
			}
		}
		
		if (EnumEtatController.ARRET_IMMINENT.equals(etat)) {
			etat = EnumEtatController.ARRET_ETAGE;

			this.eteindreBouton(demandeSuivante);
			
			Logger.writeLog(Message.ARRET_PROCHAIN.getMessage());
		}
		
		else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
					
			if (demandeSuivante != null && !stockDemandes.estVide()) {
				int delta = demandeSuivante.etage() - this.position;
				
				/*
				 * Dans le cas où la demande suivante est l'étage directement
				 * supérieur/inférieur
				 */
				if (Math.abs(delta) == 1) {
					if (delta == 1) {
						this.cabine.monter();
						this.etat = EnumEtatController.MONTEE;
					} else {
						this.cabine.descendre();
						this.etat = EnumEtatController.DESCENTE;
					}
				
					MAJSens();
					
					this.cabine.arreterProchainNiveau();
					this.etat = EnumEtatController.ARRET_ETAGE;
					
					this.eteindreBouton(demandeSuivante);
					
					Logger.writeLog(Message.ARRET_PROCHAIN.getMessage());
				
				/* Dans le cas normal */
				} else {
					if (demandeSuivante.etage() > this.position) {
						this.cabine.monter();
						this.etat = EnumEtatController.MONTEE;
					} else if (demandeSuivante.etage() < this.position) {
						this.cabine.descendre();
						this.etat = EnumEtatController.DESCENTE;
					} else {
						this.eteindreBouton(demandeSuivante);
					}
				}
				
			} else {
				etat = EnumEtatController.ATTENTE_DEMANDE;
			}
			
			if (!EnumEtatController.ARRET_IMMINENT.equals(this.etat)
					&& !EnumEtatController.ARRET_ETAGE.equals(this.etat)) {
				MAJSens();
			}
			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void arretDUrgence() {
		this.eteindreTousBoutons();
		this.viderStock();
		
		if (EnumEtatController.ATTENTE_DEMANDE.equals(etat)
				|| EnumEtatController.ARRET_ETAGE.equals(etat)
				|| EnumEtatController.ARRET_IMMINENT.equals(etat)) {
			this.etat = EnumEtatController.ARRET_IMMEDIAT;
		} else if (EnumEtatController.MONTEE.equals(etat)
				|| EnumEtatController.DESCENTE.equals(etat)) {
			this.etat = EnumEtatController.ARRET_IMMEDIAT;
			this.cabine.arreter();
			MAJSens();
		} 
		
		if (EnumEtatController.ARRET_IMMEDIAT.equals(etat)){
			this.etat = EnumEtatController.ATTENTE_DEMANDE;
		}
	}
	
	/**
	 * Renvoie la demande suivante dans le stock par rapport à la position 
	 * et au sens actuel du controller.
	 * @return Un objet représentant la demande suivante dans le stock,
	 * null s'il n'y a pas d'autre demandes.
	 */
	private Demande interrogerStock() {
		return stockDemandes.suivantDe(
				new Demande(this.position, sens)
			);
	}
	
	/**
	 * Stock une demande dans le stock de demande. Si le sens de la demande est
	 * INDEFINI, on la transforme alors en MONTEE ou en DESCENTE selon les
	 * caractéristiques de la demande et le sens courant.
	 * 
	 * @param pDemande
	 *            - Demande à stocker
	 */
	private void stocker(final Demande pDemande) {
		/* Transformation du sens d'une demande si elle est INDEFINI */
		if (pDemande.estIndefini()) {
			/* On test les cas bornés */
			if (pDemande.etage() == 0) {
				pDemande.changeSens(Sens.MONTEE);
			} else if (pDemande.etage() == this.nombreEtages - 1) {
				pDemande.changeSens(Sens.DESCENTE);
			
			/* On test les cas normaux */
			} else {
				if (pDemande.etage() > position) {
					pDemande.changeSens(Sens.MONTEE);
				} else { 
					pDemande.changeSens(Sens.DESCENTE);
				}
			}
		}
		
		this.stockDemandes.inserer(pDemande);
			
	}
	
	/**
	 * Retire la demande passée en paramètre du stock de demandes.
	 * @param pDemande - Demande à retirer
	 */
	private void enleverDuStock(final Demande pDemande) {
		this.stockDemandes.supprimer(pDemande);
	}
	
	/**
	 * Vide le stock de demandes.
	 */
	private void viderStock() {
		this.stockDemandes.vider();
	}
	
	/**
	 * Met à jour la position de la cabine enregistré dans le controlleur en
	 * fonction du sens de déplacement de la cabine.
	 */
	private final void MAJPosition() {
		if (Sens.MONTEE.equals(this.sens)) {
			this.position++;
		} else if (Sens.DESCENTE.equals(this.sens)) {
			this.position--;
		}
	}
	
	/**
	 * Met à jour le sens de la cabine enregistré dans le controlleur en
	 * fonction de l'état actuel de celui-ci.
	 */
	private final void MAJSens() {
		if (EnumEtatController.MONTEE.equals(this.etat)) {
			this.sens = Sens.MONTEE;
		} else if (EnumEtatController.DESCENTE.equals(this.etat)) {
			this.sens = Sens.DESCENTE;
		} else {
			this.sens = Sens.INDEFINI;
		}
	}
	
	/**
	 * Eteint tous les boutons dans et en dehors de la cabine.
	 */
	private void eteindreTousBoutons() {
		for (int i = 0; i < this.nombreEtages - 1; i++) {
			this.iug.eteindreBouton(new Demande(i, Sens.MONTEE));
			this.iug.eteindreBouton(new Demande(i, Sens.DESCENTE));
			this.iug.eteindreBouton(new Demande(i, Sens.INDEFINI));
		}
	}
	
	private void eteindreBouton(Demande d) {
		this.enleverDuStock(d);
		
		/*
		 * On peux éteindre à chaque fois la demande satisfaite en plus
		 * d'eteindre la même demande au Sens INDEFINI puisqu'on sait que si
		 * par exemple l'étage 4 en montée est satisfait, alors on peux
		 * eteindre et le bouton 4M et le bouton 4 dans la cabine puisque
		 * c'est les mêmes demandes dans notre système
		 */
		this.iug.eteindreBouton(d);
		this.iug.eteindreBouton(new Demande(d.etage(),
				Sens.INDEFINI));
	}

}
