package iug;

import outils.Demande;

/**
 * Interface de l'iug d'un ascenseur
 * 
 * @author Valentin
 *
 */
public interface IIUG {

	/**
	 * Allume le bouton dans l'iug 
	 * correspondant a la demande.
	 * 
	 * @param pDemande - la demande pour laquelle de bouton s'allumera
	 */
	public void allumerBouton(Demande pDemande);

	/**
	 * Eteins le bouton dans l'iug
	 * correspondant a la demande.
	 * @param pDemande - la demande pour laquelle de bouton s'allumera
	 */
	public void eteindreBouton(Demande pDemande);

	/**
	 * Ajoute un message a l'iug
	 * @param pString - le message
	 */
	public void ajouterMessage(String pString);

	
	/**
	 * Change la position de l'iug
	 * @param pInt - la position de l'iug
	 */
	public void changerPosition(int pInt);

}
