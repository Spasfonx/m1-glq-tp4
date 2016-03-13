package controller;

import outils.Demande;

/**
 * Interface d'un controlleur d'ascenceur.
 * @author Christophe
 */
public interface IController {
	
	/**
	 * Gère la demandes faite par l'utilisateur. Cela inclut le stockage des demande et
	 * l'allumage des boutons de la cabine.
	 * @param pDemande - Demande de l'utilisateur.
	 */
	public void demander(Demande pDemande);
	
	/**
	 * Gère l'arrêt d'urgence de l'ascenceur.
	 */
	public void arretDUrgence();
	
	/**
	 * Gère le signalement de changement d'étage de l'ascenceur.
	 */
	public void signalerChangementDEtage();
	
}
