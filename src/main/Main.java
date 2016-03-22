package main;

import operative.Cabine;
import operative.ICabine;
import operative.IIUG;
import operative.IUG;
import outils.Demande;

import commande.IControleur;
import commande.IListeTrieeCirculaire;
import commande.ListeTrieeCirculaireDeDemandes;

import controller.Controleur;

public class Main {
    public static void main(String[] args) {
    	try { 
	    	int nbEtages = 7; // nombre d'étages
	        int hauteurEtage = 3; // hauteur d'un étage en nombre de pas
	        IIUG iug = new IUG( nbEtages);
	        ICabine cabine = new Cabine( 
	        		500, // délai d'un pas
	        		hauteurEtage, // hauteur d'un étage en nombre de pas
	        		20); // délai de l'arrêt cabine
	        IListeTrieeCirculaire<Demande> stock = new ListeTrieeCirculaireDeDemandes( nbEtages);
	        IControleur controleur = new Controleur( nbEtages, iug, cabine, stock);
	        cabine.assignerControleur( controleur);
	        iug.assignerControleur( controleur);
	    } catch (Exception e) { 
	    	System.out.println(e.toString()); 
	    }
    }
}
