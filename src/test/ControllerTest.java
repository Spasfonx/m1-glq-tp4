package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import controller.DoublureController;
import controller.IController;
import outils.Demande;
import outils.Sens;
public class ControllerTest {

	private IController dc;
	
	
	@Before
	public void setUp(){
		dc =new  DoublureController();
	}
	
	@Test
	public void testApresArretProlonge(){
		//Monter
		dc =new DoublureController(7,3,Sens.INDEFINI,Sens.INDEFINI);
		dc.demander(new Demande(1, Sens.MONTEE));
		assertEquals(Sens.DESCENTE, dc.getSens());
		assertSame(1, dc.getPosition());
		
		//étage-1
		//dc=new
	}


	
	
	
	
	
}
