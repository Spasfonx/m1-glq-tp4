package outils;

/**
 * Enum contenant les message de LOG à afficher. 
 * @author Valentin
 */
public enum Message {
	
	ALLUMER_BOUTON("Allumer bouton %s"),
	ETEINDRE_BOUTON("Éteindre bouton %s"),
	ARRET_PROCHAIN("Arrêter prochain étage"),
	DESCENDRE("Descendre"),
	MONTER("Monter"),
	SIGNALER_CHANGEMENT_ETAGE("Signal de franchissement de palier (cabine en %s)");
	
	/**
	 * Contenu du message.
	 */
	private String message;
	
	/**
	 * Constructeur d'un message avec son contenu.
	 * @param pMessage - Le contenu du message
	 */
	private Message(String pMessage) {
		this.message = pMessage;
	}
	
	/**
	 * Retourne le contenu du message
	 * @return le contenu du message
	 */
	public String getMessage() {
		return this.message;
	}

}
