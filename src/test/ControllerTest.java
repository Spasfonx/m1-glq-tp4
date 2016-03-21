package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import cabine.DoublureDeCabine;
import cabine.ICabine;

import static org.junit.Assert.*;

import controller.Controller;
import controller.Controller;
import controller.IController;
import iug.DoublureDeIUG;
import iug.IIUG;
import outils.Demande;
import outils.Message;
import outils.Sens;
import outils.Logger;

public class ControllerTest {

	private IController dc;
	private ICabine cabine;
	private IIUG iug;

	@Before
	public void setUp() {
		cabine = new DoublureDeCabine();
		iug = new DoublureDeIUG();
	}

	@Test
	public void testAppelPlusHautUtilisateur() {
		// 1.1 Monter
		monter1_1();
		etageMoins1_1_1();
		descendre1_2();

	}

	private void monter1_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void etageMoins1_1_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 2, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();

		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);

		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void descendre1_2() {
		Demande d;
		dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	@Test
	public void testAppelPlusBas() {
		monter2_1();
		etagePlusUn2_1_1();
	}

	private void monter2_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		d = new Demande(5, Sens.MONTEE);
		dc.demander(d);
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void etagePlusUn2_1_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		d = new Demande(4, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();

		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);

		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");

		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void testAppelMemePalier() {
		monter3_1();
		descendre3_2();
	}

	private void monter3_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		dc.demander(d);
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void descendre3_2() {
		Demande d;
		dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		d = new Demande(3, Sens.DESCENTE);
		dc.demander(d);
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void testAppelApresArretProlonger() {
		directionOppose4_1();
	}

	private void directionOppose4_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		dc.signalerChangementDEtage();
		dc.arretDUrgence();
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	@Test
	public void testAppelDeMemeSensCabine() {
		monte5_1();
		descend5_2();
	}

	private void monte5_1() {
		Demande d;
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		d = new Demande(5, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(new Demande(4, Sens.MONTEE));
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), new Demande(4, Sens.MONTEE));
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), new Demande(4, Sens.MONTEE));
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");

		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void descend5_2() {
		Demande d;
		dc = new Controller(cabine,iug,8, 7, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(new Demande(4, Sens.DESCENTE));
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), new Demande(4, Sens.DESCENTE));
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), new Demande(4, Sens.DESCENTE));
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	@Test
	public void testAppelSensInverse() {
		monte6_1();
	}

	private void monte6_1() {
		Demande d;
		Demande d2 = new Demande(4, Sens.MONTEE);
		Demande d3 = new Demande(3, Sens.DESCENTE);
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
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
		
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "7");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void appelAscenseurChangementSens() {
		sansChangementSens7_1();
		avecChangementSens7_2();
		avec2ChangementSens7_3();
	}

	private void sansChangementSens7_1() {
		Demande d;
		Demande d2 = new Demande(4, Sens.MONTEE);
		dc = new Controller(cabine,iug,8, 7, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void avecChangementSens7_2() {
		Demande d;
		Demande d2 = new Demande(7, Sens.MONTEE);
		dc = new Controller(cabine,iug,8, 7, Sens.INDEFINI);
		d = new Demande(4, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "7");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void avec2ChangementSens7_3() {
		Demande d = new Demande(4, Sens.INDEFINI);
		;
		Demande d2 = new Demande(7, Sens.MONTEE);
		Demande d3 = new Demande(2, Sens.DESCENTE);
		Demande d4 = new Demande(1, Sens.INDEFINI);
		dc = new Controller(cabine,iug,8, 7, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.demander(d3);
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
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d4);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d4);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "6");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "7");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void deuxAppelMemePalier() {
		ascenseurMonte8_1();
	}

	private void ascenseurMonte8_1() {
		Demande d = new Demande(5, Sens.INDEFINI);
		Demande d2 = new Demande(4, Sens.MONTEE);
		Demande d3 = new Demande(4, Sens.DESCENTE);
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "5");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void deuxAppelMemeEtage() {
		memeSens9_1();
		sensDifferent9_2();

	}

	private void memeSens9_1() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.MONTEE);
		dc = new Controller(cabine,iug,8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void sensDifferent9_2() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new Controller(cabine,iug,8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void appelEtageEnCoursService() {
		appelExInt10_1();
		appelInterieur10_2();
		appelExtMemeDirection10_3();
		appelExtDifDirection10_4();
	}

	private void appelExInt10_1() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void appelInterieur10_2() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.INDEFINI);
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void appelExtMemeDirection10_3() {
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.MONTEE);
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void appelExtDifDirection10_4() {
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		dc = new Controller(cabine,iug,8, 1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void arretUrgence() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new Controller(cabine,iug,8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void repriseArretUrgence() {
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new Controller(cabine,iug,8, 0, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		String message = "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "1");
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "2");
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);

		d = new Demande(3, Sens.MONTEE);
		d2 = new Demande(3, Sens.DESCENTE);
		d3 = new Demande(4, Sens.INDEFINI);
		// dc = new Controller(cabine,iug,8, 3, Sens.INDEFINI);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d2);
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		message += "\n" + String.format(Message.ALLUMER_BOUTON.getMessage(), d3);
		message += "\n" + Message.MONTER.getMessage();
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "4");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d3);
		message += "\n" + Message.DESCENDRE.getMessage();
		message += "\n" + Message.ARRET_PROCHAIN.getMessage();
		message += "\n" + String.format(Message.SIGNALER_CHANGEMENT_ETAGE.getMessage(), "3");
		message += "\n" + String.format(Message.ETEINDRE_BOUTON.getMessage(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

}
