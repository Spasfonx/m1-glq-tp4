package controller;

import outils.Demande;

import outils.Sens;

public interface IController {

	public void demander(Demande pDemande);
	
	public void arretDUrgence();
	
	public void signalerChangementDEtage();
	
	public Sens getSens();
	
	public int getPosition();
	
}
