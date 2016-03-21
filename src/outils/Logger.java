
package outils;

import java.util.ArrayList;
import java.util.List;

/**
 * Logger servant aux test JUNIT
 * @author Valentin
 *
 */
public class Logger {

	private static String log="";
	
	/**
	 * Ajoute une ligne dans le logger
	 * @param message le message a ajouter
	 */
	public static void writeLog(String message){
		log+="\n"+message;
	}
	
	
	/**
	 * Retourne le logger
	 * @return - le logger
	 */
	public static String getLog(){
		return log;
	}
	
	/**
	 * Efface les logs
	 */
	public static void clearLog(){
		log="";
	}

}
