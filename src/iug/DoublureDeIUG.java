package iug;

import outils.Demande;
import outils.Logger;
import outils.Message;
import outils.Sens;

public class DoublureDeIUG implements IIUG {

	private boolean[] tabBtnMonter;
	private boolean[] tabBtnDescente;
	private boolean[] tabBtnController;

	public DoublureDeIUG(int nbEtage) {
		tabBtnController = new boolean[nbEtage];
		tabBtnMonter = new boolean[nbEtage];
		tabBtnDescente = new boolean[nbEtage];

	}

	public void allumerBouton(Demande pDemande) {

		if (pDemande.sens().equals(Sens.DESCENTE)) {
			if (!tabBtnDescente[pDemande.etage()]) {
				tabBtnDescente[pDemande.etage()] = true;
				Logger.writeLog(String.format(Message.ALLUMER_BOUTON.getMessage(), pDemande));
			}
		} else if (pDemande.sens().equals(Sens.MONTEE)) {
			if (!tabBtnMonter[pDemande.etage()]) {
				tabBtnMonter[pDemande.etage()] = true;
				Logger.writeLog(String.format(Message.ALLUMER_BOUTON.getMessage(), pDemande));
			}
		} else {
			if (!tabBtnController[pDemande.etage()]) {
				tabBtnController[pDemande.etage()] = true;
				Logger.writeLog(String.format(Message.ALLUMER_BOUTON.getMessage(), pDemande));
			}
		}

	}

	public void eteindreBouton(Demande pDemande) {
		if (pDemande.sens().equals(Sens.DESCENTE)) {
			if (tabBtnDescente[pDemande.etage()]) {
				tabBtnDescente[pDemande.etage()] = false;
				Logger.writeLog(String.format(Message.ETEINDRE_BOUTON.getMessage(), pDemande));
			}
		} else if (pDemande.sens().equals(Sens.MONTEE)) {
			if (tabBtnMonter[pDemande.etage()]) {
				tabBtnMonter[pDemande.etage()] = false;
				Logger.writeLog(String.format(Message.ETEINDRE_BOUTON.getMessage(), pDemande));
			}
		} else {
			if (tabBtnController[pDemande.etage()]) {
				tabBtnController[pDemande.etage()] = false;
				Logger.writeLog(String.format(Message.ETEINDRE_BOUTON.getMessage(), pDemande));
			}
		}
		//Logger.writeLog(String.format(Message.ETEINDRE_BOUTON.getMessage(), pDemande));

	}

	public void ajouterMessage(String pString) {
		// TODO Auto-generated method stub

	}

	public void changerPosition(int pInt) {
		// TODO Auto-generated method stub

	}

	public void demander(Demande pDemande) {
		// TODO Auto-generated method stub

	}

	public void arretDUrgence() {
		// TODO Auto-generated method stub

	}

}
