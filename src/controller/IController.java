package controller;

import outils.Demande;

public interface IController {

	public void demander(Demande pDemande);
	
	public void arretDUrgence();
	
	public void signalerChangementDEtage();
	
}
