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
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());

		// 1.1.1 étage -1
		dc = new DoublureController(7, 2, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.MONTEE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());

		// 1.2 Descendre
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());

		// 1.2.1 étage -1
		dc = new DoublureController(7, 2, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(1, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getBeforeLast(1));
		assertEquals(Message.DESCENDRE.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());
	}

	@Test
	public void testAppelPlusBasUtilisateur() {
		// 2.1 Monter
		Demande d;
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.MONTEE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());
		
		//2.1.1 étage +1
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.MONTEE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());
		
		// 2.2 Descendre
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(5, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());
		
		// 2.2.1 étage +1
		dc = new DoublureController(7, 3, Sens.INDEFINI, Sens.INDEFINI);
		d = new Demande(4, Sens.DESCENTE);
		dc.demander(d);
		assertEquals(
				String.format(Message.ALLUMER_BOUTON.toString(), d.toString()),
				Logger.getBeforeLast(2));
		assertEquals(Message.ARRET_PROCHAIN.toString(), d.toString(),
				Logger.getBeforeLast(1));
		assertEquals(Message.MONTER.toString(), d.toString(),
				Logger.getLast());
		dc.signalerChangementDEtage();
		assertEquals(
				String.format(Message.SIGNALER_CHANGEMENT_ETAGE.toString(),
						dc.getPosition()), Logger.getBeforeLast(1));
		assertEquals(
				String.format(Message.ETEINDRE_BOUTON.toString(), d.toString()),
				Logger.getLast());
		
		
	}
}
