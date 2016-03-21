package controller;

/**
 * Enum contenant les types d'états du controller.
 * @author Christophe
 */
public enum EnumEtatController {
	
	/**
	 * Attente d'une demande.
	 */
	ATTENTE_DEMANDE,
	
	/**
	 * En montée.
	 */
	MONTEE,
	
	/**
	 * En descente.
	 */
	DESCENTE,
	
	/**
	 * Arrêt imminent.
	 */
	ARRET_IMMINENT,
	
	/**
	 * En cours d'arret à l'étage.
	 */
	ARRET_ETAGE,
	
	/**
	 * Arret immediat.
	 */
	ARRET_IMMEDIAT

}
