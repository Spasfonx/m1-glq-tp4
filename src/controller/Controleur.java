package controller;

import operative.ICabine;
import operative.IIUG;
import outils.Demande;
import outils.Message;
import outils.Sens;

import commande.IControleur;
import commande.IListeTrieeCirculaire;
import commande.ListeTrieeCirculaireDeDemandes;

/**
 * Classe Controller permettant de gÃ©rer les demandes entre l'iug et la cabine. 
 * @author Christophe
 */
public class Controleur implements IControleur {

	/**
	 * Nombre d'Ã©tage maximal que gÃ¨re le controlleur.
	 */
	private int nombreEtages;
	
	/**
	 * Position Ã  l'instant actuel de la cabine.
	 */
	private int position;
	
	/**
	 * Sens courant de la cabine.
	 */
	private Sens sens;
	
	/**
	 * Liste des demandes en attente.
	 */
	private IListeTrieeCirculaire<Demande> stockDemandes;
	
	/**
	 * RefÃ©rence Ã  l'objet reprÃ©sentant l'iug.
	 */
	private IIUG iug;
	
	/**
	 * RefÃ©rence Ã  l'objet reprÃ©sentant la cabine.
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
	 * @param pNombreEtages - Nombre d'Ã©tage maximal que gÃ¨re le controlleur
	 * @param pPosition - Position actuelle
	 * @param pSens - Sens actuel
	 */
	public Controleur(final ICabine pCabine, final IIUG pIUG,
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
	 * Constructeur de la classe. 
	 * @param pCabine - Lien vers la cabine
	 * @param pIUG - Lien vers l'iug
	 * @param pNombreEtages - Nombre d'Ã©tage maximal que gÃ¨re le controlleur
	 * @param pPosition - Position actuelle
	 * @param pSens - Sens actuel
	 */
	public Controleur(final int pNombreEtages, final IIUG pIUG, 
			final ICabine pCabine, IListeTrieeCirculaire<Demande> stock) {
		this.nombreEtages = pNombreEtages;
		this.position = 0;
		this.sens = Sens.INDEFINI;
		
		this.cabine = pCabine;
		this.iug = pIUG;

		this.stockDemandes = stock;
		this.etat = EnumEtatController.ATTENTE_DEMANDE;
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
			
			/* On initialise le sens de dÃ©part de la cabine */
			int d = demandeActuelle.etage() - position;
			
			/* Si c'est directement l'Ã©tage suivant */
			if (Math.abs(d) == 1) {
				if (d == 1) {
					this.iug.ajouterMessage("Monter");
					this.cabine.monter();
					this.etat = EnumEtatController.MONTEE;
				} else {
					this.iug.ajouterMessage("Descendre");
					this.cabine.descendre();
					this.etat = EnumEtatController.DESCENTE;
				}

				MAJSens();
				
				this.eteindreBouton(demandeActuelle);
				
				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_ETAGE;
				
				this.iug.ajouterMessage(Message.ARRET_PROCHAIN.getMessage());
			/* Sinon */
			} else if (demandeActuelle.etage() > position) {
				this.iug.ajouterMessage("Monter");
				this.cabine.monter();
				this.etat = EnumEtatController.MONTEE;
			} else if (demandeActuelle.etage() < position) {
				this.iug.ajouterMessage("Descendre");
				this.cabine.descendre();
				this.etat = EnumEtatController.DESCENTE;
			} 
			
			/* Demande au mÃªme palier */
			if (this.position == demandeActuelle.etage() && (demandeActuelle.sens().equals((this.sens))
					|| Sens.INDEFINI.equals(this.sens))) {
				this.eteindreBouton(demandeActuelle);
			}
			
			if (!EnumEtatController.ARRET_IMMINENT.equals(this.etat)
					&& !EnumEtatController.ARRET_ETAGE.equals(this.etat)) {
				MAJSens();
			}
		} else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
			/* On vÃ©rifie que la demande ne soit pas l'Ã©tage 
			 * sur lequel on va dÃ©jÃ  s'arrÃªtÃ© */
			if (Sens.MONTEE.equals(this.sens) 
					&& (demandeActuelle.etage() != this.position + 1 
						|| !demandeActuelle.sens().equals(this.sens))
				|| Sens.DESCENTE.equals(this.sens) 
					&& (demandeActuelle.etage() != this.position - 1 
						|| !demandeActuelle.sens().equals(this.sens))) {
				this.iug.allumerBouton(demandeActuelle);
				stocker(demandeActuelle);
			}
			
			/* On vÃ©rifie que la demande ne soit pas l'Ã©tage
			 * sur lequel on est dÃ©jÃ  arrÃªtÃ©*/
			if (demandeActuelle.etage() != this.position 
					|| demandeActuelle.sens() != this.sens) {
				this.iug.allumerBouton(demandeActuelle);
				this.stocker(demandeActuelle);
			}
		} else if (EnumEtatController.ARRET_IMMINENT.equals(etat)) {
			/* On vÃ©rifie que la demande ne soit pas l'Ã©tage 
			 * sur lequel on va dÃ©jÃ  s'arrÃªtÃ© */
			if (Sens.MONTEE.equals(this.sens) 
					&& (demandeActuelle.etage() != this.position + 1 
						|| !demandeActuelle.sens().equals(this.sens))
				|| Sens.DESCENTE.equals(this.sens) 
					&& (demandeActuelle.etage() != this.position - 1 
						|| !demandeActuelle.sens().equals(this.sens))) {
				this.iug.allumerBouton(demandeActuelle);
				stocker(demandeActuelle);
			}
		} else {
			this.iug.allumerBouton(demandeActuelle);
			stocker(demandeActuelle);
		}
		
		System.out.println("\n");
		System.out.println(demandeActuelle);
		System.out.println(this.stockDemandes);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final synchronized void signalerChangementDEtage() {
		MAJPosition();
		
		this.iug.ajouterMessage(String.format(
				Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), 
				this.position)
			);
		
		Demande demandeSuivante = interrogerStock();
		
		if ((EnumEtatController.MONTEE.equals(etat) 
				|| EnumEtatController.DESCENTE.equals(etat))
				&& demandeSuivante != null) {
			// On doit s'arrÃªter aux prochains etage (cas normal)
			if ((demandeSuivante.etage() == this.position + 1
					&& Sens.MONTEE.equals(demandeSuivante.sens()))
				|| (demandeSuivante.etage() == this.position - 1
						&& Sens.DESCENTE.equals(demandeSuivante.sens()))) {
				this.cabine.arreterProchainNiveau();
				this.etat = EnumEtatController.ARRET_IMMINENT;
			} 
			
			// On doit s'arrÃªter au prochain Ã©tage, cas extrÃªmes
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
			
			this.iug.ajouterMessage(Message.ARRET_PROCHAIN.getMessage());
		}
		
		else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
					
			if (demandeSuivante != null && !stockDemandes.estVide()) {
				int delta = demandeSuivante.etage() - this.position;
				
				/*
				 * Dans le cas oÃ¹ la demande suivante est l'Ã©tage directement
				 * supÃ©rieur/infÃ©rieur
				 */
				if (Math.abs(delta) == 1) {
					if (delta == 1) {
						this.iug.ajouterMessage("Monter");
						this.cabine.monter();
						this.etat = EnumEtatController.MONTEE;
					} else {
						this.iug.ajouterMessage("Descendre");
						this.cabine.descendre();
						this.etat = EnumEtatController.DESCENTE;
					}
				
					MAJSens();
					
					this.cabine.arreterProchainNiveau();
					this.etat = EnumEtatController.ARRET_ETAGE;
					
					this.eteindreBouton(demandeSuivante);
					
					this.iug.ajouterMessage(Message.ARRET_PROCHAIN.getMessage());
				
				/* Dans le cas normal */
				} else {
					if (demandeSuivante.etage() > this.position) {
						this.iug.ajouterMessage("Monter");
						this.cabine.monter();
						this.etat = EnumEtatController.MONTEE;
					} else if (demandeSuivante.etage() < this.position) {
						this.iug.ajouterMessage("Descendre");
						this.cabine.descendre();
						this.etat = EnumEtatController.DESCENTE;
					} else {
						this.eteindreBouton(demandeSuivante);
						this.etat = EnumEtatController.ATTENTE_DEMANDE;
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
	public final void arretUrgence() {
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
		} else if (EnumEtatController.ARRET_IMMEDIAT.equals(etat)){
			this.etat = EnumEtatController.ATTENTE_DEMANDE;
		}
	}
	
	/**
	 * Renvoie la demande suivante dans le stock par rapport Ã  la position 
	 * et au sens actuel du controller.
	 * @return Un objet reprÃ©sentant la demande suivante dans le stock,
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
	 * caractÃ©ristiques de la demande et le sens courant.
	 * 
	 * @param pDemande
	 *            - Demande Ã  stocker
	 */
	private synchronized void stocker(final Demande pDemande) {
		/* Transformation du sens d'une demande si elle est INDEFINI */
		if (pDemande.estIndefini()) {
			/* On test les cas bornÃ©s */
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
	 * Retire la demande passÃ©e en paramÃ¨tre du stock de demandes.
	 * @param pDemande - Demande Ã  retirer
	 */
	private synchronized void enleverDuStock(final Demande pDemande) {
		this.stockDemandes.supprimer(pDemande);
	}
	
	/**
	 * Vide le stock de demandes.
	 */
	private void viderStock() {
		this.stockDemandes.vider();
	}
	
	/**
	 * Met Ã  jour la position de la cabine enregistrÃ© dans le controlleur en
	 * fonction du sens de dÃ©placement de la cabine.
	 */
	private synchronized final void MAJPosition() {
		if (Sens.MONTEE.equals(this.sens)) {
			this.position++;
		} else if (Sens.DESCENTE.equals(this.sens)) {
			this.position--;
		}
		
		this.iug.changerPosition(this.position);
	}
	
	/**
	 * Met Ã  jour le sens de la cabine enregistrÃ© dans le controlleur en
	 * fonction de l'Ã©tat actuel de celui-ci.
	 */
	private synchronized final void MAJSens() {
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
	private synchronized void eteindreTousBoutons() {
		for (int i = 0; i < this.nombreEtages; i++) {
			this.iug.eteindreBouton(new Demande(i, Sens.INDEFINI));
			
			if (i != 0) {
				this.iug.eteindreBouton(new Demande(i, Sens.DESCENTE));
			}
			
			if (i != this.nombreEtages - 1) {
				this.iug.eteindreBouton(new Demande(i, Sens.MONTEE));
			}
		}
	}
	
	private synchronized void eteindreBouton(Demande d) {
		this.enleverDuStock(d);
		
		/*
		 * On peux Ã©teindre Ã  chaque fois la demande satisfaite en plus
		 * d'eteindre la mÃªme demande au Sens INDEFINI puisqu'on sait que si
		 * par exemple l'Ã©tage 4 en montÃ©e est satisfait, alors on peux
		 * eteindre et le bouton 4M et le bouton 4 dans la cabine puisque
		 * c'est les mÃªmes demandes dans notre systÃ¨me
		 */
		this.iug.eteindreBouton(d);
		this.iug.eteindreBouton(new Demande(d.etage(),
				Sens.INDEFINI));
	}

	@Override
	public void exit() {
		
	}

}
