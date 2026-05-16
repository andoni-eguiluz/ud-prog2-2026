package tema5.junit.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculadoraNotasTest {

	private static double DELTA = 0.00000001;
	
	@Test
	void testMedia_Sencilla() {
		assertEquals( 7.0, CalculadoraNotas.media( 6, 8), DELTA );
	}
	
	@Test
	void testMedia_CasosBorde() {
		assertEquals( 0.0, CalculadoraNotas.media( 0, 0 ), DELTA );   // Mínimo posible
		assertEquals( 10.0, CalculadoraNotas.media( 10, 10 ), DELTA ); // Máximo posible
		assertEquals( 5.0, CalculadoraNotas.media( 0, 10 ), DELTA );   // Extremos combinados
	}
	
	@Test
	void testMedia_Negativa() {
		try {
			CalculadoraNotas.media( 6, -1 ); 
			fail( "Negativo no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
		try {
			CalculadoraNotas.media( -1, 6 ); 
			fail( "Negativo no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
		try {
			CalculadoraNotas.media( -1, -3 ); 
			fail( "Negativo no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}
	
	@Test
	void testMedia_EnRango() {
		try {
			CalculadoraNotas.media(11, 7); // Mayor que 10
			fail( "Nota mayor que 10 permitida" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
		try {
			CalculadoraNotas.media(7, 85322); // Mayor que 10
			fail( "Nota mayor que 10 permitida" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}


	//////////  FASE 2: media(int...) - varargs, reimplementación

	@Test
	void testMedia_MasDeDosNumeros() {
		assertEquals( 7.0, CalculadoraNotas.media(6, 8, 8, 6), DELTA );
		// notas de más números → reimplementar el método con varargs
		// Los tests de la FASE 1 tienen que seguir funcionando
	}

	@Test
	void testMedia_NumerosIguales() {
		assertEquals( 6.0, CalculadoraNotas.media(6, 6, 6, 6, 6), DELTA );
	}

	@Test
	void testMedia_NegativosEnMasDeDos() {
		try {
			CalculadoraNotas.media(-1, 7, 9, 3);
			fail( "Negativo no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}

	@Test
	void testMedia_FueraDeRangoEnMasDeDos() {
		try {
			CalculadoraNotas.media(5, 7, 12, 3); // 12 > 10
			fail( "Nota mayor que 10 permitida" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}

	@Test
	void testMedia_UnSoloNumero() {
		// ¿Tiene sentido una media de una sola nota? Sí: es ella misma
		assertEquals( 8.0, CalculadoraNotas.media(8), DELTA );
	}

	@Test
	void testMedia_SinNumeros() {
		// Sin notas no se puede calcular una media
		try {
			CalculadoraNotas.media();
			fail( "Media sin notas no permitida" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}


	//////////  FASE 3: notaLetra(double) - nueva funcionalidad
	//  Devuelve "Suspenso", "Aprobado", "Notable", "Sobresaliente" o "Matrícula de Honor"
	//  según los rangos habituales: [0,5) [5,7) [7,9) [9,10) [10]

	@Test
	void testNotaLetra_Suspenso() {
		assertEquals( "Suspenso", CalculadoraNotas.notaLetra(0) );
		assertEquals( "Suspenso", CalculadoraNotas.notaLetra(4.99) );
	}

	@Test
	void testNotaLetra_Aprobado() {
		assertEquals( "Aprobado", CalculadoraNotas.notaLetra(5) );
		assertEquals( "Aprobado", CalculadoraNotas.notaLetra(6.99) );
	}

	@Test
	void testNotaLetra_Notable() {
		assertEquals( "Notable", CalculadoraNotas.notaLetra(7) );
		assertEquals( "Notable", CalculadoraNotas.notaLetra(8.99) );
	}

	@Test
	void testNotaLetra_Sobresaliente() {
		assertEquals( "Sobresaliente", CalculadoraNotas.notaLetra(9) );
		assertEquals( "Sobresaliente", CalculadoraNotas.notaLetra(9.99) );
	}

	@Test
	void testNotaLetra_MH() {
		assertEquals( "Matrícula de Honor", CalculadoraNotas.notaLetra(10) );
	}

	@Test
	void testNotaLetra_FueraDeRango() {
		try {
			CalculadoraNotas.notaLetra(-1);
			fail( "Nota negativa no permitida" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
		try {
			CalculadoraNotas.notaLetra(10.5);
			fail( "Nota mayor que 10 no permitida" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}


	//////////  FASE 4: mejorNota(double...) - devuelve la nota más alta

	@Test
	void testMejorNota_Basico() {
		assertEquals( 9, CalculadoraNotas.mejorNota(5, 9, 3, 7) );
	}

	@Test
	void testMejorNota_TodasIguales() {
		assertEquals( 6, CalculadoraNotas.mejorNota(6, 6, 6) );
	}

	@Test
	void testMejorNota_UnaSola() {
		assertEquals( 8, CalculadoraNotas.mejorNota(8) );
	}

	@Test
	void testMejorNota_SinNotas() {
		try {
			CalculadoraNotas.mejorNota();
			fail( "Sin notas no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}

	@Test
	void testMejorNota_FueraDeRango() {
		try {
			CalculadoraNotas.mejorNota(5, -2, 8);
			fail( "Negativo no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
		try {
			CalculadoraNotas.mejorNota(5, 2, 18);
			fail( "> 10 no permitido" );
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}

}
