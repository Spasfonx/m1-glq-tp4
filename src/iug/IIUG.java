package iug;

import outils.Demande;

public interface IIUG {

	public void allumerBouton(Demande pDemande);
	
	public void eteindreBouton(Demande pDemande);
	
	public void ajouterMessage(String pString);
	
	public void changerPosition(int pInt);
	
	public void demander(Demande pDemande);
	
	public void arretDUrgence();
	
}
