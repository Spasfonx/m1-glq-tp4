package outils;

/**
 * Exception lancée lorsqu'un cabine est arrêtée.
 * @author Christophe
 */
public class ExceptionCabineArretee extends Exception {
	
	/**
	 * Serial pour la serialisation de l'objet.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur avec message d'exception.
	 * @param message - Message de l'exception
	 */
	public ExceptionCabineArretee(String message) {
		super(message);
	}
	
	/**
	 * Constructeur sans paramètre.
	 */
	public ExceptionCabineArretee() {
		super();
	}

}
