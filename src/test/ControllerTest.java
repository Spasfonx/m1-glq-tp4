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
import outils.Sens;

public class ControllerTest {

	private IController dc;
	ByteArrayOutputStream baos;
	PrintStream ps;
	Scanner s;
	@Before
	public void setUp() {
		dc = new DoublureController();
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		
		System.setOut(ps);
		
	}

}
