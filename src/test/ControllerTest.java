package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import controller.Controller;
import controller.DoublureController;
import controller.IController;
import outils.Demande;
import outils.Message;
import outils.Sens;
import outils.Logger;

public class ControllerTest {

	private IController dc;

	@Before
	public void setUp() {

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
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void etageMoins1_1_1() {
		Demande d;
		dc = new DoublureController(7, 2, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();

		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.ARRET_PROCHAIN.toString();
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());

		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void descendre1_2() {
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();

		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
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
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.MONTEE);
		dc.demander(d);
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void etagePlusUn2_1_1() {
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.MONTEE);
		dc.demander(d);
		dc.signalerChangementDEtage();

		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());

		message += Message.ARRET_PROCHAIN.toString();
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");

		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
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
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		dc.demander(d);
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void descendre3_2() {
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(3, Sens.DESCENTE);
		dc.demander(d);
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	@Test
	public void testAppelApresArretProlonger() {
		directionOppose4_1();
	}

	private void directionOppose4_1() {
		Demande d;
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		dc.signalerChangementDEtage();
		dc.arretDUrgence();
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
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
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(new Demande(4, Sens.MONTEE));
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ALLUMER_BOUTON.toString(), new Demande(4, Sens.MONTEE).toString());
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), new Demande(4, Sens.MONTEE).toString());
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");

		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void descend5_2() {
		Demande d;
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(new Demande(4, Sens.DESCENTE));
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += String.format(Message.ALLUMER_BOUTON.toString(), new Demande(4, Sens.DESCENTE).toString());
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), new Demande(4, Sens.DESCENTE).toString());
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d.toString());
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
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
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
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2);
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3);
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "7");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
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
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2);
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}

	private void avecChangementSens7_2() {
		Demande d;
		Demande d2 = new Demande(7, Sens.MONTEE);
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "7");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();

	}

	private void avec2ChangementSens7_3() {
		Demande d = new Demande(4, Sens.INDEFINI);
		;
		Demande d2 = new Demande(7, Sens.MONTEE);
		Demande d3 = new Demande(2, Sens.DESCENTE);
		Demande d4 = new Demande(1, Sens.INDEFINI);
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
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
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d3);
		message += String.format(Message.ALLUMER_BOUTON.toString(), d4.toString());
		message += Message.DESCENDRE.toString();
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d4.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "6");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "7");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2.toString());
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
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.DESCENDRE.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3.toString());
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2.toString());
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "5");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += Message.DESCENDRE.toString();
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	@Test
	public void deuxAppelMemeEtage(){
		memeSens9_1();
		sensDifferent9_2();
		
	}
	
	private void memeSens9_1(){
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.MONTEE);
		dc = new DoublureController(7, 0, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	private void sensDifferent9_2(){
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new DoublureController(7, 0, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d3);
		message += Message.DESCENDRE.toString();
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	@Test
	public void appelEtageEnCoursService(){
		appelExInt10_1();
		appelInterieur10_2();
		appelExtMemeDirection10_3();
		appelExtDifDirection10_4();
	}
	
	private void appelExInt10_1(){
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	private void appelInterieur10_2(){
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.INDEFINI);
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	
		
	}
	
	private void appelExtMemeDirection10_3(){
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.MONTEE);
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	private void appelExtDifDirection10_4(){
		Demande d = new Demande(3, Sens.MONTEE);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.signalerChangementDEtage();
		dc.demander(d2);
		dc.signalerChangementDEtage();
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message = String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	@Test
	public void arretUrgence(){
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new DoublureController(7, 0, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d3);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
	}
	
	@Test
	public void repriseArretUrgence(){
		Demande d = new Demande(3, Sens.INDEFINI);
		Demande d2 = new Demande(3, Sens.DESCENTE);
		Demande d3 = new Demande(4, Sens.INDEFINI);
		dc = new DoublureController(7, 0, Sens.INDEFINI, Sens.INDEFINI);
		dc.demander(d);
		dc.demander(d2);
		dc.demander(d3);
		String message = String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3.toString());
		message += Message.MONTER.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "1");
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "2");
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d2);
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d3);
		
		
		
		 d = new Demande(3, Sens.MONTEE);
		 d2 = new Demande(3, Sens.DESCENTE);
		 d3 = new Demande(4, Sens.INDEFINI);
	//	dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		message += String.format(Message.ALLUMER_BOUTON.toString(), d.toString());
		message += String.format(Message.ALLUMER_BOUTON.toString(), d2.toString());
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		message += String.format(Message.ALLUMER_BOUTON.toString(), d3.toString());
		message += Message.MONTER.toString();
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "4");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d3);
		message += Message.DESCENDRE.toString();
		message += Message.ARRET_PROCHAIN.toString();
		message += String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), "3");
		message += String.format(Message.ETEINDRE_BOUTON.toString(), d);
		assertEquals(message, Logger.getLog());
		Logger.clearLog();
		
	}
	
	

}
