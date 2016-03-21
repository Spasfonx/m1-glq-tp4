package cabine;

/**
 * Interface de la cabine de l'ascenseur
 * @author Valentin
 *
 */
public interface ICabine {
	
	
	/**
	 * Fait monter la cabine.
	 */
	public void monter();
	
	/**
	 * Fait descendre la cabine.
	 */
	public void descendre();
	
	/**
	 * Demande a la cabine de 
	 * s'arreter au prochain niveau.
	 */
	public void arreterProchainNiveau();
	
	/**
	 * Arrete la cabine.
	 */
	public void arreter();
	
	
}
