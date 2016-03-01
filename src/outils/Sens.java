package outils;

/**
 * Sens de déplacement d'un objet.
 * @author Christophe
 */
public enum Sens {
	MONTEE("^"),
	DESCENTE("v"),
	INDEFINI("-");
	
	/**
	 * Représentation textuelle du sens.
	 */
	private String sens;
	
	/**
	 * Constructeur avec la représentation textuelle du sens.
	 * @param sens - Représentation textuelle du sens
	 */
	private Sens(String sens) {
		this.sens = sens;
	}
	
	/**
	 * Renvois une chaine de caracère qui représente textuellement l'enum Sens.
	 * @return Chaine de caractère représentant l'énumération actuelle
	 */
	@Override
	public String toString() {
		return this.sens;
	}
}
