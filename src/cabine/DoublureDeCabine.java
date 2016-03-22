package cabine;

import commande.IControleur;

import operative.ICabine;
import outils.Logger;
import outils.Message;

/**
 * Doublure de la classe Cabine
 * @author Valentin
 */
public class DoublureDeCabine implements ICabine {

	private boolean monte;
	private boolean descend;

	public DoublureDeCabine() {
		monte = false;
		descend = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void monter() {
		if (!monte) {
			Logger.writeLog(Message.MONTER.getMessage());
			monte = true;
			descend = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void descendre() {
		if (!descend) {
			Logger.writeLog(Message.DESCENDRE.getMessage());
			descend = true;
			monte = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void arreterProchainNiveau() {}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void arreter() {}

	@Override
	public void assignerControleur(IControleur arg0) {
		// TODO Auto-generated method stub
		
	}

}
