package cabine;

import outils.Logger;
import outils.Message;

public class DoublureDeCabine implements ICabine {

	public void signalerChangementDEtage() {
		// TODO Auto-generated method stub
		
	}

	public void monter() {
		Logger.writeLog(Message.MONTER.toString());
		
	}

	public void descendre() {
		Logger.writeLog(Message.DESCENDRE.toString());
		
	}

	public void arreterProchainNiveau() {
		// TODO Auto-generated method stub
		
	}

	public void arreter() {
		// TODO Auto-generated method stub
		
	}

}
