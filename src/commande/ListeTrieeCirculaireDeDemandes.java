package commande;

import outils.Demande;
import outils.Sens;

/**
 * Une IListeCirculaire represente une liste circulaire triee de {@link Demande}.
 * @author Christophe
 */
public class ListeTrieeCirculaireDeDemandes implements IListeTrieeCirculaire<Demande> {
	
	/**
	 * Nombre de paliers géré par la liste 
	 */
	private int nbPaliers;
	
	/**
	 * Nombre d'éléments effectivements présents dans la liste
	 */
	private int taille;
	
	/**
	 * Tableau de booléen, l'indice représentant l'étage de la demande,
	 * et la valeur à l'indice représentant si la demande est présente ou non.
	 * Ce tableau ne contient que les demandes en montée.
	 */
	private boolean[] listeMontee;
	
	/**
	 * Tableau de booléen, l'indice représentant l'étage de la demande,
	 * et la valeur à l'indice représentant si la demande est présente ou non.
	 * Ce tableau ne contient que les demandes en descente.
	 */
	private boolean[] listeDescente;
	
	/**
	 * Constructeur avec un nombre de paliers spécifié
	 * @param nbPaliers - Nombre de paliers
	 */
	public ListeTrieeCirculaireDeDemandes(int nbPaliers) {
		this.taille = 0;
		this.nbPaliers = nbPaliers;
		this.listeMontee = new boolean[nbPaliers];
		this.listeDescente = new boolean[nbPaliers];
		
		// Permet d'initialiser toutes valeurs des tableaux à false
		this.vider();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int taille() {
		return this.taille;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean estVide() {
		return this.taille == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vider() {
		for (int i = 0; i < listeMontee.length - 1; i++) {
			this.listeMontee[i] = false;
			this.listeDescente[i] = false;
		}
		
		this.taille = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contient(Demande e) {
		if (e.estIndefini())
			throw new IllegalArgumentException("Le sens de la demande ne peut être indéfinis");
		
		if (e.enMontee()) {
			return this.listeMontee[e.etage()];
		} else {
			return this.listeDescente[e.etage()];
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void inserer(Demande e) {
		if (isOutOfBound(e))
			throw new IllegalArgumentException("Cette demande est hors des limites du système");
		
		if (!contient(e)) {
			if (e.enMontee()) {
				this.listeMontee[e.etage()] = true;
			} else {
				this.listeDescente[e.etage()] = true;
			}
			
			this.taille++;
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimer(Demande e) {
		if (!contient(e))
			throw new IllegalArgumentException("La demande n'existe pas dans la liste");
		
		if (e.enMontee()) {
			this.listeMontee[e.etage()] = false;
		} else {
			this.listeDescente[e.etage()] = false;
		}
		
		this.taille--;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Demande suivantDe(Demande courant) {
		if (estVide())
			return null;
		
		if (courant.enMontee()) {
			/* On part dans le sens de la demande courante */
			for (int i = courant.etage(); i < listeMontee.length; i++) {
				if (listeMontee[i])
					return new Demande(i, Sens.MONTEE);
			}
			
			/* Si on a pas encore touvé, on part dans l'autre sens */
			for (int i = listeDescente.length - 1; i > 0; i--) {
				if (listeDescente[i])
					return new Demande(i, Sens.DESCENTE);
			}
			
			/* Si on a toujours pas trouvé, on parcours le reste du premier tableau */
			for (int i = 0; i < courant.etage(); i++) {
				if (listeMontee[i])
					return new Demande(i, Sens.MONTEE);
			}
		} else {
			/* On part dans le sens de la demande courante */
			for (int i = courant.etage(); i > 0; i--) {
				if (listeDescente[i])
					return new Demande(i, Sens.DESCENTE);
			}
			
			/* Si on a pas encore touvé, on part dans l'autre sens */
			for (int i = 0; i < listeMontee.length; i++) {
				if (listeMontee[i])
					return new Demande(i, Sens.MONTEE);
			}
			
			/* Si on a toujours pas trouvé, on parcours le reste du premier tableau */
			for (int i = listeDescente.length - 1; i > courant.etage(); i--) {
				if (listeDescente[i])
					return new Demande(i, Sens.DESCENTE);
			}
		}
		
		return null;
	}
	
	/**
	 * Retourne une représentation textuelle de la liste triée.
	 * @return Chaine de caractères réprésentant la représentation textuelle de la liste 
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		boolean first = true;
		
		for (int i = 0; i < listeMontee.length; i++) {
			if (this.listeMontee[i]) {
				if (first)
					first = false;
				else
					str.append(",");
				
				str.append(i);
				str.append(Sens.MONTEE.toString());
			}
		}
		
		for (int i = listeDescente.length - 1; i > 0; i--) {
			if (this.listeDescente[i]) {
				if (first)
					first = false;
				else
					str.append(",");
				
				str.append(i);
				str.append(Sens.DESCENTE.toString());
			}
		}
		
		str.append("]");
		return str.toString();
	}
	
	/**
	 * Vérifie que la demande ne soit pas hors des limites du système.
	 * C'est à dire un étage compris dans le nombre de paliers donnés
	 * et une direction cohérente en fonction de l'étage.
	 * <i>Exemple : Demande(0, Sens.DESCENTE) -> impossible</pre> 
	 * @param e - Demande à vérifier
	 * @return Vrai si la demande est hors des limites du système
	 */
	private boolean isOutOfBound(Demande e) {
		return e.etage() < 0
				|| e.etage() >= nbPaliers
			 	|| (e.enDescente() && e.etage() <= 0)
				|| (e.enMontee() && e.etage() >= nbPaliers - 1);
	}

}
