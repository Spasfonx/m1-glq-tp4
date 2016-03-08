package outils;

import java.util.ArrayList;
import java.util.List;

public class Logger {

	private static List<String> loggers = new ArrayList<String>();
	private static int nbLog=-1;
	
	
	/**
	 * Ajoute une ligne dans le logger
	 * @param message le message a ajouter
	 */
	public static void writeLog(String message){
		loggers.add(message);
		nbLog++;
	}
	
	
	/**
	 * Retourne la dernière ligne entrée dans les logs
	 * @return la dernière ligne
	 */
	public static String getLast(){
		return loggers.get(nbLog);
	}
	
	/**
	 * Retourne la ligne n-before ou n est le nombre d'entré 
	 * @param before le nombre de ligne précédente a la dernière a afficher
	 * @return la ligne correspondante
	 */
	public static String getBeforeLast(int before){
		return loggers.get(nbLog-before);
	}

}
