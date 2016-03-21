
package outils;

import java.util.ArrayList;
import java.util.List;

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
	 * Retourne la dernière ligne entrée dans les logs
	 * @return la dernière ligne
	 */
	public static String getLog(){
		return log;
	}
	
	/**
	 * Retourne la ligne n-before ou n est le nombre d'entré 
	 * @param before le nombre de ligne précédente a la dernière a afficher
	 * @return la ligne correspondante
	 */
	public static void clearLog(){
		log="";
	}

}
