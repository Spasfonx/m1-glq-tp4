package cabine;

import outils.Logger;
import outils.Message;

public class DoublureDeCabine implements ICabine {

	public void signalerChangementDEtage() {
		// TODO Auto-generated method stub
		
	}

	public void monter() {
		Logger.writeLog(Message.MONTER.getMessage());
		
	}

	public void descendre() {
		Logger.writeLog(Message.DESCENDRE.getMessage());
		
	}

	public void arreterProchainNiveau() {
		// TODO Auto-generated method stub
		
	}

	public void arreter() {
		// TODO Auto-generated method stub
		
	}

}
