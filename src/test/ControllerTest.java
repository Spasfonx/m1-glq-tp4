package test;

import static org.junit.Assert.assertEquals;
import iug.DoublureDeIUG;
import iug.IIUG;

import org.junit.Before;
import org.junit.Test;

import outils.Demande;
import outils.Logger;
import outils.Message;
import outils.Sens;
import cabine.DoublureDeCabine;
import cabine.ICabine;
import controller.Controller;
import controller.IController;

/**
 * Classe de test du controller.
 * @author Valentin
 *
 */
public class ControllerTest {

	private IController dc;
	private ICabine cabine;
	private IIUG iug;

	@Before
	public void setUp() {
		cabine = new DoublureDeCabine();
		iug = new DoublureDeIUG(8);
		Logger.clearLog();

	}

	/**
	 * Reinitialise l'état de la cabine.
	 * (apellé entre chaque test)
	 */
	private void resetCabine() {
		this.cabine = new DoublureDeCabine();
	}
	
	/**
	 * Reinitialise l'état de l'iug.
	 * (apellé entre chaque test)
	 */
	private void resetIUG() {
		this.iug = new DoublureDeIUG(8);
	}

	/**
	 * Test d'un appel d'ascenseur plus haut
	 * que l'utilisateur
	 */
	@Test
	public void testAppelPlusHautUtilisateur() {
		// 1.1 Monter
		monter1_1();
		resetIUG();
		resetCabine();
		Logger.clearLog();
		etageMoins1_1_1();
		resetCabine();
		resetIUG();
		Logger.clearLog();
		descendre1_2();

	}

	private void monter1_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
		
		
		assertEquals(message, Logger.getLog());

	}

	private void etageMoins1_1_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 2, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();

		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
		

		assertEquals(message, Logger.getLog());

	}

	private void descendre1_2() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
	

		assertEquals(message, Logger.getLog());

	}

	
	/**
	 * Test d'un appel d'ascenseur
	 *  plus bas que l'utilisateur.
	 */
	@Test
	public void testAppelPlusBas() {
		monter2_1();
		Logger.clearLog();
		resetIUG();
		this.resetCabine();
		etagePlusUn2_1_1();
	}

	private void monter2_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		d = new Demande(5, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		assertEquals(message, Logger.getLog());

	}

	private void etagePlusUn2_1_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		d = new Demande(4, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();

		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);

		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		assertEquals(message, Logger.getLog());

	}

	
	/**
	 * Test d'un appel d'ascenseur
	 * au même palier.
	 */
	@Test
	public void testAppelMemePalier() {
		monter3_1();
		resetIUG();
		Logger.clearLog();
		descendre3_2();
	}

	private void monter3_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		dc.demander(d);
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());

	}

	private void descendre3_2() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		d = new Demande(3, Sens.DESCENTE);
		dc.demander(d);
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());

	}

	// TODO appel meme palier quand ascenseur monte ou descend

	/**
	 * Appel d’un ascenseur après un 
	 * arrêt d’urgence suivi par un arrêt prolongé.
	 */
	@Test
	public void testAppelApresArretProlonger() {
		directionOppose4_1();
	}

	private void directionOppose4_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 0, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.arretDUrgence();
		
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test appel de l'ascenseur dans le même 
	 * sens que celui de la cabine en cours de déplacement
	 */
	@Test
	public void testAppelDeMemeSensCabine() {
		monte5_1();
		resetIUG();
		Logger.clearLog();
		descend5_2();
	}

	private void monte5_1() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		d = new Demande(5, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(new Demande(4, Sens.MONTEE));
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(),
						new Demande(4, Sens.MONTEE));
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(),
						new Demande(4, Sens.MONTEE));
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");

		assertEquals(message, Logger.getLog());

	}

	private void descend5_2() {
		Demande d;
		dc = new Controller(cabine, iug, 8, 7, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(new Demande(4, Sens.DESCENTE));
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(),
						new Demande(4, Sens.DESCENTE));
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(),
						new Demande(4, Sens.DESCENTE));
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");

		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");

		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test appel de l'ascenseur dans le sens 
	 * inverse que celui de la cabine en cours de déplacement.
	 */
	@Test
	public void testAppelSensInverse() {
		monte6_1();
	}

	private void monte6_1() {
		Demande d;
		Demande d2 = new Demande(4, Sens.MONTEE);
		Demande d3 = new Demande(3, Sens.DESCENTE);
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		d = new Demande(7, Sens.INDEFINI);

		dc.demander(d);

		dc.signalerChangementDEtage();

		dc.demander(d2);
		dc.demander(d3);

		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"7");
		message += "\n" + Message.DESCENDRE.getMessage();
		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test d'appel de l'ascenseur qui sont satisfaits sans changement de sens,
	 *  ou avec un changement de sens, 
	 *  ou avec deux changements de sens de la cabine
	 */
	@Test
	public void appelAscenseurChangementSens() {
		sansChangementSens7_1();
		Logger.clearLog();
		resetIUG();
		resetCabine();
		avecChangementSens7_2();
		resetCabine();
		Logger.clearLog();
		resetIUG();
		avec2ChangementSens7_3();
	}

	private void sansChangementSens7_1() {
		Demande d;
		Demande d2 = new Demande(4, Sens.DESCENTE);
		dc = new Controller(cabine, iug, 8, 7, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
		
		assertEquals(message, Logger.getLog());

	}

	private void avecChangementSens7_2() {
		Demande d;
		Demande d2 = new Demande(7, Sens.DESCENTE);
		dc = new Controller(cabine, iug, 8, 7, Sens.INDEFINI);
		d = new Demande(4, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"7");
	
		assertEquals(message, Logger.getLog());

	}

	private void avec2ChangementSens7_3() {
		Demande d = new Demande(4, Sens.INDEFINI);
		
		Demande d2 = new Demande(7, Sens.DESCENTE);
		Demande d3 = new Demande(2, Sens.DESCENTE);
		Demande d4 = new Demande(1, Sens.INDEFINI);
		dc = new Controller(cabine, iug, 8, 7, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.demander(d4);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
	
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d4);
	
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
	
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"7");
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"6");
	
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d4);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
	
		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test de deux appels à partir du même palier.
	 */
	@Test
	public void deuxAppelMemePalier() {
		ascenseurMonte8_1();
	}

	private void ascenseurMonte8_1() {
		Demande d = new Demande(5, Sens.INDEFINI);
		Demande d2 = new Demande(4, Sens.MONTEE);
		Demande d3 = new Demande(4, Sens.DESCENTE);
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"5");
	
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");

		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test de deux appel pour le même étage.
	 */
	@Test
	public void deuxAppelMemeEtage() {
		memeSens9_1();
		resetCabine();
		resetIUG();
		Logger.clearLog();
		sensDifferent9_2();

	}

	private void memeSens9_1() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.MONTEE);
		dc = new Controller(cabine, iug, 8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
	
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);

		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		
		assertEquals(message, Logger.getLog());

	}

	private void sensDifferent9_2() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new Controller(cabine, iug, 8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"4");
	
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		
		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test d'un appel pour un étage en cours de service.
	 */
	@Test
	public void appelEtageEnCoursService() {
		appelExInt10_1();
		resetIUG();
		Logger.clearLog();
		this.resetCabine();
		appelInterieur10_2();
		resetIUG();
		Logger.clearLog();
		this.resetCabine();
		appelExtMemeDirection10_3();
		resetIUG();
		Logger.clearLog();
		this.resetCabine();
		appelExtDifDirection10_4();
	}


	private void appelExInt10_1() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");

		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);

		message += "\n" + Message.ARRET_PROCHAIN.getMessage();

		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);

		assertEquals(message, Logger.getLog());

	}

	private void appelInterieur10_2() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.INDEFINI);
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");

		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);

		message += "\n" + Message.ARRET_PROCHAIN.getMessage();

		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());

	}

	private void appelExtMemeDirection10_3() {
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.MONTEE);
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		assertEquals(message, Logger.getLog());

	}

	private void appelExtDifDirection10_4() {
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		dc = new Controller(cabine, iug, 8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"3");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		
		assertEquals(message, Logger.getLog());

	}

	/**
	 * Test d'un arret d'urgence.
	 */
	@Test
	public void arretUrgence() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new Controller(cabine, iug, 8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.arretDUrgence();
		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
	
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"1");
		message += "\n"
				+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),
						"2");
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
	
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n"
				+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		assertEquals(message, Logger.getLog());

	}
	
	/**
	 * Test d'une reprise après arret d'urgence.
	 */
	@Test
	public void repriseArretUrgence() {
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new Controller(cabine, iug, 8, 3, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();

		String message = "\n"
				+ String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n"+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n"+ String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n"+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n"+ String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
	
		message += "\n" + Message.MONTER.getMessage();
		message += "\n"+ String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n"+ String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(),"4");

		assertEquals(message, Logger.getLog());

	}

}
