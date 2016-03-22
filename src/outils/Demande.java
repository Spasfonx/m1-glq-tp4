package outils;

import java.util.Objects;

/**
 * Classe qui représente une demande d'ascenceur.
 * @author Christophe
 */
public class Demande {
	
	/**
	 * Etage de la demande
	 */
	private int etage;
	
	/**
	 * {@link Sens} de la demande
	 */
	private Sens sens;
	
	/**
	 * Instancie une demande avec un etage et un sens 
	 * @param etage - Etage
	 * @param sens - {@link Sens}
	 */
	public Demande(int etage, Sens sens) {
		this.etage = etage;
		this.sens = sens;
	}
	
	/**
	 * Instancie une demande à l'étage 0 avec un sens INDEFINI
	 */
	public Demande() {
		this(0, Sens.INDEFINI);
	}
	
	/**
	 * Renvoi le numéro de l'étage de la demande
	 * @return
	 */
	public Integer etage() {
		return this.etage;
	}

	/**
	 * Renvois le {@link Sens} actuel de la demande.
	 * @return {@link Sens} actuel de la demande
	 */
	public Sens sens() {
		return this.sens;
	}
	
	/**
	 * Permet de savoir si le le sens de la demande est indéfini
	 * @return Vrai si le sens de la demande est indéfini
	 */
	public boolean estIndefini() {
		return Sens.INDEFINI.equals(this.sens);
	}
	
	/**
	 * Permet de savoir si le le sens de la demande est en montée
	 * @return Vrai si le sens de la demande est en montée
	 */
	public boolean enMontee() {
		return Sens.MONTEE.equals(this.sens);
	}
	
	/**
	 * Permet de savoir si le le sens de la demande est en descente.
	 * @return Vrai si le sens de la demande est en descente
	 */
	public boolean enDescente() {
		return Sens.DESCENTE.equals(this.sens);
	}
	
	/**
	 * Passe à l'étage suivant en fonction du sens de la demande.
	 * @throws ExceptionCabineArretee - Si le sens de la cabine est indéfini
	 */
	public void passeEtageSuivant() throws ExceptionCabineArretee {
		if (estIndefini())
			throw new ExceptionCabineArretee();
			
		if (enMontee())
			this.etage++;
		else
			this.etage--;
	}
	
	/**
	 * Change le sens actuel de la demande.
	 * @param sens - Sens final de la demande
	 */
	public void changeSens(Sens sens) {
		this.sens = sens;
	}
	
	/**
	 * Renvois une chaine de caracère qui représente textuellement l'objet Demande.
	 * Elle se présente de la forme : 
	 * - 3v
	 * - 5-
	 * - 6d
	 * - etc...
	 * @return Chaine de caractère représentant l'objet Demande
	 */
	@Override
	public String toString() {
		String strSens;
		
		if (Sens.MONTEE.equals(this.sens)) {
			strSens = "^";
		} else if (Sens.DESCENTE.equals(this.sens)) {
			strSens = "v";
		} else {
			strSens = "-";
		}
		
		return this.etage() + strSens; 
	}
	
	/**
	 * Teste l'egalité entre la Demande actuel et l'objet passé en paramètre.s
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null 
				&& obj instanceof Demande
				&& ((Demande) obj).etage() == this.etage
				&& ((Demande) obj).sens.equals(this.sens);
	}
	
	/**
	 * Redéfinition du hashCode.
	 * @return hashCode en fonction des champs etage et sens de la demande
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.etage, this.sens);
	}

}
