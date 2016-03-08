package outils;

public enum Message {
	
	ALLUMER_BOUTON("Allumer bouton %s"),
	ETEINDRE_BOUTON("Éteindre bouton %s"),
	ARRET_PROCHAIN("Arrêter prochain étage"),
	DESCENDRE("Descendre"),
	MONTER("Monter"),
	SIGNALER_CHANGEMENT_ETAGE("Signal de franchissement de palier %s");
	
	private String message;
	
	
	private Message(String pMessage) {
		this.message = pMessage;
	}
}
