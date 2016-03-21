package iug;


import outils.Demande;
import outils.Logger;
import outils.Message;

public class DoublureDeIUG implements IIUG{

	public void allumerBouton(Demande pDemande) {
		Logger.writeLog(String.format(Message.ALLUMER_BOUTON.toString(), pDemande));
		
	}

	public void eteindreBouton(Demande pDemande) {
		Logger.writeLog(String.format(Message.ETEINDRE_BOUTON.toString(), pDemande));
		
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
