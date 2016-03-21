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
		if (EnumEtatController.ATTENTE_DEMANDE.equals(etat)) {
			this.stocker(pDemande);
			this.iug.allumerBouton(pDemande);
			
			/* On initialise le sens de départ de la cabine */
			int d = pDemande.etage() - position;
			
			if (pDemande.etage() > position) {
				this.cabine.monter();
				this.etat = EnumEtatController.MONTEE;
			} else if (pDemande.etage() < position) {
				this.cabine.descendre();
				this.etat = EnumEtatController.DESCENTE;
			} else if (Math.abs(d) == 1) {
				if (d == 1) {
					this.cabine.monter();
					this.etat = EnumEtatController.MONTEE;
				} else {
					this.cabine.descendre();
					this.etat = EnumEtatController.DESCENTE;
				}

				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_IMMINENT;
			}
			
			MAJSens();
		} else if (EnumEtatController.ARRET_IMMINENT.equals(etat)) {
			/* On vérifie que la demande ne soit pas l'étage 
			 * sur lequel on va déjà s'arrêté */
			if (Sens.MONTEE.equals(this.sens) 
					&& (pDemande.etage() != this.position + 1 
						|| !pDemande.sens().equals(this.sens))
				|| Sens.DESCENTE.equals(this.sens) 
					&& (pDemande.etage() != this.position - 1 
						|| !pDemande.sens().equals(this.sens))) {
				stocker(pDemande);
				this.iug.allumerBouton(pDemande);
			}
		} else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
			/* On vérifie que la demande ne soit pas l'étage
			 * sur lequel on est déjà arrêté */
			if (pDemande.etage() != this.position 
					|| pDemande.sens() != this.sens) {
				this.stocker(pDemande);
				this.iug.allumerBouton(pDemande);
			}
		} else {
			stocker(pDemande);
			this.iug.allumerBouton(pDemande);
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
			if ((demandeSuivante.etage() == this.position + 1
					&& Sens.MONTEE.equals(demandeSuivante.sens()))
				|| (demandeSuivante.etage() == this.position - 1
						&& Sens.DESCENTE.equals(demandeSuivante.sens()))) {
				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_IMMINENT;
			}
		}
		
		if (EnumEtatController.ARRET_IMMINENT.equals(etat)) {
			etat = EnumEtatController.ARRET_ETAGE;
			this.enleverDuStock(demandeSuivante);
			
			/*
			 * On peux éteindre à chaque fois la demande satisfaite en plus
			 * d'eteindre la même demande au Sens INDEFINI puisqu'on sait que si
			 * par exemple l'étage 4 en montée est satisfait, alors on peux
			 * eteindre et le bouton 4M et le bouton 4 dans la cabine puisque
			 * c'est les mêmes demandes dans notre système
			 */
			this.iug.eteindreBouton(demandeSuivante);
			this.iug.eteindreBouton(new Demande(demandeSuivante.etage(),
					Sens.INDEFINI));
			
			Logger.writeLog(Message.ARRET_PROCHAIN.getMessage());
		}
		
		if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
						
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
					
					this.cabine.arreterProchainNiveau();
					this.etat = EnumEtatController.ARRET_IMMINENT; 
				
				/* Dans le cas normal */
				} else {
					if (demandeSuivante.etage() > this.position) {
						this.cabine.monter();
						this.etat = EnumEtatController.MONTEE;
					} else if (demandeSuivante.etage() < this.position) {
						this.cabine.descendre();
						this.etat = EnumEtatController.DESCENTE;
					}
				}
				
			} else {
				etat = EnumEtatController.ATTENTE_DEMANDE;
			}
			
			MAJSens();
			
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
		} else {
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

}
