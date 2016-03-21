package cabine;

import outils.Logger;
import outils.Message;

public class DoublureDeCabine implements ICabine {

	private boolean monte;
	private boolean descend;

	public DoublureDeCabine() {
		monte = false;
		descend = false;
	}

	public void signalerChangementDEtage() {
		// TODO Auto-generated method stub

	}

	public void monter() {
		if (!monte) {
			Logger.writeLog(Message.MONTER.getMessage());
			monte = true;
			descend = false;
		}

	}

	public void descendre() {
		if (!descend) {
			Logger.writeLog(Message.DESCENDRE.getMessage());
			descend = true;
			monte = false;
		}

	}

	public void arreterProchainNiveau() {
		// TODO Auto-generated method stub

	}

	public void arreter() {
		// TODO Auto-generated method stub

	}

}
