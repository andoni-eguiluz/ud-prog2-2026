package tema5.junit.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilsStringTest {

	@Test
	void testQuitarTabsYSaltosDeLinea() {
		String prueba = "Hola\nEsto es un string con tres líneas\ny\tvarios\ttabuladores.";
		String prueba2 = "Hola#Esto es un string con tres líneas#y|varios|tabuladores.";
		assertEquals( "a#b", UtilsString.quitarTabsYSaltosLinea("a\nb") );
		assertEquals( "a|b", UtilsString.quitarTabsYSaltosLinea("a\tb") );
		assertEquals( prueba2, UtilsString.quitarTabsYSaltosLinea(prueba) );
//		if (prueba2.equals(UtilsString.quitarTabsYSaltosLinea(prueba))) {
//			System.out.println( "OK" );
//		} else {
//			System.out.println( "FAIL" );
//		}		
	}
	
	@Test
	void testQuitarTabsYSaltosDeLinea2() {
		assertEquals( "ab", UtilsString.quitarTabsYSaltosLinea("ab") );
	}

}
