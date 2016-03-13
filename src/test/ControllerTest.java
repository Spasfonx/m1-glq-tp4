package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
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
		dc = new DoublureController();

	}

	@Test
	public void testAppelPlusHautUtilisateur() {
		// 1.1 Monter
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

		// 1.1.1 étage -1
		dc = new DoublureController(7, 2, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

		// 1.2 Descendre
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

		// 1.2.1 étage -1
		dc = new DoublureController(7, 2, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());
	}

	@Test
	public void testAppelPlusBasUtilisateur() {
		// 2.1 Monter
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.MONTEE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

		// 2.1.1 étage +1
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.MONTEE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

		// 2.2 Descendre
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

		// 2.2.1 étage +1
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), dc.getPosition()),
				Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

	}

	private void monter3_1() {
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(3, Sens.MONTEE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());

	}

	private void descendre3_2() {
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(3, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()), Logger.getLast());
	}

	@Test
	public void testAppelAscenseurMemePalierUtilisateur() {

		monter3_1();

		descendre3_2();
	}

	private void monter4_1() {
		Demande d;
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 2), Logger.getBeforeLast(0));

		dc.arretDUrgence();
		d = new Demande(3, Sens.MONTEE);
		dc.demander(d);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 3), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 4), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());
	}

	private void descente4_2() {
		Demande d;
		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 2), Logger.getBeforeLast(0));

		dc.arretDUrgence();

		d = new Demande(0, Sens.MONTEE);
		dc.demander(d);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 3), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 4), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());
	}

	@Test
	public void testAppelAscenseurApresArretUrgence() {
		monter4_1();

		descente4_2();
	}

	private void monte5_1() {
		Demande d;
		Demande d2;

		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.INDEFINI);
		dc.demander(d);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 2), Logger.getBeforeLast(0));

		d2 = new Demande(4, Sens.MONTEE);
		dc.demander(d2);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d2.toString()), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 3), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 4), Logger.getBeforeLast(2));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 5), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast());

	}

	private void descend5_2() {
		Demande d;
		Demande d2;

		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());

		// Cabine en 6
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 6), Logger.getBeforeLast(0));

		d2 = new Demande(4, Sens.DESCENTE);
		dc.demander(d2);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d2.toString()), Logger.getLast());
		// Cabine en 5
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 5), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		// Cabine en 4
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 4), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 3), Logger.getBeforeLast(0));

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 2), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 1), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast());

	}

	@Test
	public void testAppelAscenseurMemeSens(){
		monte5_1();
		
		descend5_2();
	}
	private void monte6_1() {
		Demande d;
		Demande d2;
		Demande d3;

		dc = new DoublureController(7, 1, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(7, Sens.INDEFINI);
		dc.demander(d);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());

		// Cabine en 2
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 2), Logger.getBeforeLast(0));

		d2 = new Demande(4, Sens.MONTEE);
		d3 = new Demande(3, Sens.DESCENTE);
		dc.demander(d2);
		dc.demander(d3);

		// Cabine en 3
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 3), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		// Cabine en 4
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 4), Logger.getBeforeLast(0));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 5), Logger.getBeforeLast(0));

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 6), Logger.getBeforeLast(0));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), 7), Logger.getBeforeLast(0));

		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());

	}

	@Test
	public void testAppelAscenseurSensInverseCabine(){
		monte6_1();
	}
	
	private void sansChangementSens7_1() {
		Demande d;
		Demande d2;

		int i = 7;
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.INDEFINI);
		dc.demander(d);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));

		d2 = new Demande(4, Sens.DESCENTE);
		dc.demander(d2);

		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d2.toString()), Logger.getBeforeLast(0));

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());
		
		
	}
	
	private void avecChangementSens7_2(){
		Demande d;
		Demande d2;

		int i = 7;
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.INDEFINI);
		dc.demander(d);
		
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));

		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());
		
		d2 = new Demande(7, Sens.INDEFINI);
		dc.demander(d2);
		
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d2.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i++), Logger.getBeforeLast(0));
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i++), Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast());
		
		
	}
	
	private void avec2ChangementSens7_3(){
		Demande d;
		Demande d2;

		int i = 7;
		dc = new DoublureController(7, 7, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.INDEFINI);
		dc.demander(d);
		
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(1));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());
	
		d = new Demande(7, Sens.INDEFINI);
		dc.demander(d);
		
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i++), Logger.getBeforeLast(0));
		
		d2=new Demande(2,Sens.MONTEE);
		
		assertEquals(String.format(Message.ALLUMER_BOUTON.toString(), d.toString()), Logger.getBeforeLast(0));
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i++), Logger.getBeforeLast(0));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i++), Logger.getBeforeLast(0));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d.toString(), Logger.getLast());
		assertEquals(Message.DESCENDRE.toString(), d.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
	
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
	
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		assertEquals(Message.ARRET_PROCHAIN.toString(), Logger.getLast());
		
		dc.signalerChangementDEtage();

		assertEquals(String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(), i--), Logger.getBeforeLast(0));
		assertEquals(Message.ETEINDRE_BOUTON.toString(), d2.toString(), Logger.getLast()); 
		
		
	}
	
	
	

}
