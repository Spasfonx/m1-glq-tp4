package controller;

import iug.IIUG;
import outils.Demande;
import outils.Sens;
import cabine.ICabine;

import commande.ListeTrieeCirculaireDeDemandes;

public class Controller implements IController {

	private int nombreEtages;
	private int position;
	private Sens sens;
	private Demande demande;
	private ListeTrieeCirculaireDeDemandes stockDemandes;
	
	private IIUG iug;
	private ICabine cabine;
	
	private EnumEtatController etat;
	
	public Controller(int nombreEtages, int position, Sens sens, Sens sensPrecedent) {
		this.nombreEtages = nombreEtages;
		this.position = position;
		this.sens = sens;
		
		this.stockDemandes = new ListeTrieeCirculaireDeDemandes(nombreEtages);
		this.etat = EnumEtatController.ATTENTE_DEMANDE;
	}
	
	@Override
	public void demander(Demande pDemande) {
		if (EnumEtatController.ATTENTE_DEMANDE.equals(etat)) {
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
			
			this.stocker(pDemande);
			this.iug.allumerBouton(pDemande);	
			MAJSens();
		} else if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
			if (pDemande.etage() != this.position || pDemande.sens() != this.sens) {
				this.stocker(pDemande);
				this.iug.allumerBouton(pDemande);
			}
		} else {
			stocker(pDemande);
			this.iug.allumerBouton(pDemande);
		}
	}

	@Override
	public void signalerChangementDEtage() {
		MAJPosition();
		System.out.println(String.format("Signalement de changement d'etage (cabine en %s)", this.position));
		
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
			this.iug.eteindreBouton(demandeSuivante);
		}
		
		if (EnumEtatController.ARRET_ETAGE.equals(etat)) {
						
			if (demandeSuivante != null && !stockDemandes.estVide()) {
				int delta = demandeSuivante.etage() - this.position;
				
				/* Dans le cas où la demande suivante est l'étage directement supérieur/inférieur */
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
				} 
				
				/* Dans le cas normal */
				else {
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
	
	@Override
	public void arretDUrgence() {
		this.eteindreTousBoutons();
		this.viderStock();
		
		if (EnumEtatController.ATTENTE_DEMANDE.equals(etat)
				|| EnumEtatController.ARRET_ETAGE.equals(etat)
				|| EnumEtatController.ARRET_IMMINENT.equals(etat)) {
			this.etat = EnumEtatController.ARRET_IMMEDIAT;
		} else if (EnumEtatController.MONTEE.equals(etat)
				|| EnumEtatController.DESCENTE.equals(etat)){
			this.etat = EnumEtatController.ARRET_IMMEDIAT;
			this.cabine.arreter();
			MAJSens();
		} else {
			this.etat = EnumEtatController.ATTENTE_DEMANDE;
		}
	}
	
	private Demande interrogerStock() {
		return stockDemandes.suivantDe(
				new Demande(this.position, sens)
			);
	}
	
	private void stocker(Demande pDemande) {
		/* Transformation du sens d'une demande si elle est INDEFINI */
		if (pDemande.estIndefini()) {
			/* On test les cas bornés */
			if (pDemande.etage() == 0) {
				pDemande.changeSens(Sens.MONTEE);
			} else if (pDemande.etage() == this.nombreEtages - 1) {
				pDemande.changeSens(Sens.DESCENTE);
			} 
			
			/* On test les cas normaux */
			else {
				if (pDemande.etage() > position)
					pDemande.changeSens(Sens.MONTEE);
				else 
					pDemande.changeSens(Sens.DESCENTE);
			}
		}
		
		this.stockDemandes.inserer(pDemande);
			
	}
	
	private void enleverDuStock(Demande pDemande) {
		this.stockDemandes.supprimer(pDemande);
	}
	
	private void viderStock() {
		this.stockDemandes.vider();
	}
	
	private void MAJPosition() {
		if (Sens.MONTEE.equals(this.sens)) {
			this.position++;
		} else if (Sens.DESCENTE.equals(this.sens)){
			this.position--;
		}
	}
	
	private void MAJSens() {
		if (EnumEtatController.MONTEE.equals(this.etat))
			this.sens = Sens.MONTEE;
		else if (EnumEtatController.DESCENTE.equals(this.etat))
			this.sens = Sens.DESCENTE;
		else
			this.sens = Sens.INDEFINI;
	}
	
	private void eteindreTousBoutons() {
		
	}

	@Override
	public Sens getSens() {
		return null;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

}
