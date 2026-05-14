package tema5.junit.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FraccionTest {

	// Tolerancia para comparaciones de doubles
	private static final double DELTA = 1e-9;
	
	@Test
	void testFraccion_Basico() {
		Fraccion f = new Fraccion(2, 5);
		// assertEquals( new Fraccion(2, 5), f );  // Incompleto
		
		// Comprobar num y den: completo
		assertEquals( 2, f.num );  // Anotacion: ¿estaría bien tener getNum()?
		assertEquals( 5, f.den );  // Anotación: posible getDen()
		
		// Comprobar valor real: incompleto
		// assertEquals( 0.4, f.calcular() );
		
		// Comprobar con toString
		assertEquals( "2/5", f.toString() );
	}
	
	@Test
	void testFraccion_Negativo() {
		Fraccion f = new Fraccion( -2, 5 );
		assertEquals( "-2/5", f.toString() );
	}
	
	@Test
	void testFraccion_NegativoDenominador() {
		Fraccion f = new Fraccion( 2, -5 );
		assertEquals( "2/-5", f.toString() );
		// Podríamos hacerlo solo en un paso:
		// assertEquals( "2/-5", (new Fraccion(2,-5)).toString() );
	}

	@Test
	void testFraccion_CeroNumerador() {
		Fraccion f = new Fraccion( 0, 5 );
		assertEquals( "0/5", f.toString() );
	}
	
	// OJO: Error de diseño que corregimos lanzando excepción
	@Test
	void testFraccion_CeroDenominador() {
		try {
			Fraccion f = new Fraccion( 5, 0 );
			// 1 no exc
			fail();
		} catch (ArithmeticException e) {
			// 2 hay exc
		}
		//assertEquals( "5/0", f.toString() );
	}
	
	
	// =====================================================================
	// TESTS PARA: calcular()
	//
	// Javadoc: "Calcula el número double que corresponde a la fracción"
	// (El Javadoc del @return está vacío, falta documentación)
	// =====================================================================
 
	@Test
	void testCalcular_FraccionPositiva() {
		assertEquals( 0.75, new Fraccion(3, 4).calcular(), DELTA );
	}
 
	@Test
	void testCalcular_FraccionNegativa() {
		assertEquals( -0.5, new Fraccion(-1, 2).calcular(), DELTA );
	}
 
	@Test
	void testCalcular_FraccionEntera() {
		// 6/3 = 2.0
		assertEquals( 2.0, new Fraccion(6, 3).calcular(), DELTA );
	}
 
	@Test
	void testCalcular_NumeradorCero() {
		// 0/7 = 0.0
		assertEquals( 0.0, new Fraccion(0, 7).calcular(), DELTA );
	}
 
	@Test
	void testCalcular_FraccionMayorQueUno() {
		assertEquals( 2.5, new Fraccion(5, 2).calcular(), DELTA );
	}
 
	// =====================================================================
	// TESTS PARA: simplifica()
	//
	// Javadoc:
	//   - Divide num y den por su MCD
	//   - Deja el signo (si lo hubiera) en el numerador
	//   - Devuelve this (modifica la fracción)
	// =====================================================================
 
	@Test
	void testSimplifica_FraccionSimplificable() {
		Fraccion f = new Fraccion(4, 8);
		Fraccion f2 = f.simplifica();
		assertEquals( "1/2", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	@Test
	void testSimplifica_FraccionYaSimplificada() {
		Fraccion f = new Fraccion(3, 7);
		Fraccion f2 = f.simplifica();
		assertEquals( "3/7", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	@Test
	void testSimplifica_NegativoNegativo() {
		Fraccion f = new Fraccion( -2, -5 );
		Fraccion f2 = f.simplifica();
		assertEquals( "2/5", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
	
	@Test
	void testSimplifica_AmbasNegativas() {
		// -3/-4 debe simplificarse a 3/4 (los dos signos se cancelan)
		Fraccion f = new Fraccion(-3, -4);
		Fraccion f2 = f.simplifica();
		assertEquals( "3/4", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	@Test
	void testSimplifica_NumeradorCero() {
		// 0/5 simplificada debería ser 0/1 (o al menos 0/algo)
		// mcd(0,5): Math.min(0,5)=0 → el bucle while usa mcd=0 → a%0 = ¡ArithmeticException!
		//
		// ESTE TEST REVELA UN BUG: mcd(0, n) falla con ArithmeticException interna
		// porque intenta hacer módulo por 0.
		//
		// ACCIÓN NECESARIA: el método mcd debe manejar el caso de que uno de los
		// números sea 0 (el MCD de 0 y n es n).
		Fraccion f = new Fraccion(0, 5);
		Fraccion f2 = f.simplifica(); // debería funcionar sin excepción
		assertEquals( 0.0, f.calcular(), DELTA );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	@Test
	void testSimplifica_MCD_Grande() {
		// 100/250 = 2/5 (MCD = 50)
		Fraccion f = new Fraccion(100, 250);
		Fraccion f2 = f.simplifica();
		assertEquals( "2/5", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	@Test
	void testSimplifica_FraccionEntera() {
		// 12/4 = 3/1
		Fraccion f = new Fraccion(12, 4);
		Fraccion f2 = f.simplifica();
		assertEquals( "3/1", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	@Test
	void testSimplifica_NumeradorNegativoSimplificable() {
		// -6/9 → -2/3
		Fraccion f = new Fraccion(-6, 9);
		Fraccion f2 = f.simplifica();
		assertEquals( "-2/3", f.toString() );
		assertSame( f, f2, "simplifica() debe devolver this, no un objeto nuevo" );
	}
 
	// =====================================================================
	// TESTS PARA: suma(Fraccion f1, Fraccion f2)
	//
	// Javadoc: "Suma dos fracciones y devuelve nueva fracción resultado"
	// =====================================================================
 
	@Test
	void testSuma_Basica() {
		// 1/2 + 1/3 = 5/6
		Fraccion resultado = Fraccion.suma( new Fraccion(1,2), new Fraccion(1,3) );
		resultado.simplifica();
		assertEquals( "5/6", resultado.toString() );
	}
 
	@Test
	void testSuma_MismoDenominador() {
		// 1/4 + 2/4 = 12/16 → simplificada 3/4
		Fraccion resultado = Fraccion.suma( new Fraccion(1,4), new Fraccion(2,4) );
		resultado.simplifica();
		assertEquals( "3/4", resultado.toString() );
	}
 
	@Test
	void testSuma_ConCero() {
		// 3/5 + 0/1 = 3/5
		Fraccion resultado = Fraccion.suma( new Fraccion(3,5), new Fraccion(0,1) );
		resultado.simplifica();
		assertEquals( "3/5", resultado.toString() );
	}
 
	@Test
	void testSuma_ConNegativo() {
		// 1/2 + (-1/3) = 1/6
		Fraccion resultado = Fraccion.suma( new Fraccion(1,2), new Fraccion(-1,3) );
		resultado.simplifica();
		assertEquals( "1/6", resultado.toString() );
	}
 
	@Test
	void testSuma_ResultadoCero() {
		// 1/3 + (-1/3) = 0
		Fraccion resultado = Fraccion.suma( new Fraccion(1,3), new Fraccion(-1,3) );
		assertEquals( 0.0, resultado.calcular(), DELTA );
	}
 
	@Test
	void testSuma_NoModificaOperandos() {
		// La suma debe crear una NUEVA fracción, sin modificar las originales
		Fraccion f1 = new Fraccion(1, 2);
		Fraccion f2 = new Fraccion(1, 3);
		Fraccion.suma(f1, f2);
		assertEquals( "1/2", f1.toString(), "f1 no debería haberse modificado" );
		assertEquals( "1/3", f2.toString(), "f2 no debería haberse modificado" );
	}
 
	@Test
	void testSuma_OperandoNull_DeberiaLanzarExcepcion() {
		// El Javadoc no menciona nulls.
		// PREGUNTA DE DISEÑO: ¿debería documentarse y lanzar una excepción explícita?
		// Actualmente lanza NullPointerException (no controlada).
		try {
			Fraccion.suma( null, new Fraccion(1,2) );
			fail( "Debería lanzarse excepción con operando null" );
		} catch (NullPointerException e) {
			// Correcto (aunque debería documentarse)
		}
		// Alternativa: assertThrows( NullPointerException.class,
		//                () -> Fraccion.suma(null, new Fraccion(1,2)) );
	}
 
	// =====================================================================
	// TESTS PARA: resta(Fraccion f1, Fraccion f2)
	// =====================================================================
 
	@Test
	void testResta_Basica() {
		// 3/4 - 1/4 = 8/16 → 1/2
		Fraccion resultado = Fraccion.resta( new Fraccion(3,4), new Fraccion(1,4) );
		resultado.simplifica();
		assertEquals( "1/2", resultado.toString() );
	}
 
	@Test
	void testResta_ResultadoNegativo() {
		// 1/4 - 3/4 = -8/16 → -1/2
		Fraccion resultado = Fraccion.resta( new Fraccion(1,4), new Fraccion(3,4) );
		resultado.simplifica();
		assertEquals( "-1/2", resultado.toString() );
	}
 
	@Test
	void testResta_Iguales_ResultadoCero() {
		// 2/3 - 2/3 = 0
		Fraccion resultado = Fraccion.resta( new Fraccion(2,3), new Fraccion(2,3) );
		assertEquals( 0.0, resultado.calcular(), DELTA );
	}
 
	@Test
	void testResta_OperandoNull_DeberiaLanzarExcepcion() {
		// El Javadoc no menciona nulls.
		// Actualmente lanza NullPointerException (no controlada).
		try {
			Fraccion.suma( null, new Fraccion(1,2) );
			fail( "Debería lanzarse excepción con operando null" );
		} catch (NullPointerException e) {
			// Correcto (aunque debería documentarse)
		}
	}
 
	// =====================================================================
	// TESTS PARA: multiplica(Fraccion f1, Fraccion f2)
	// =====================================================================
 
	@Test
	void testMultiplica_Basica() {
		// 2/3 * 3/4 = 6/12 → 1/2
		Fraccion resultado = Fraccion.multiplica( new Fraccion(2,3), new Fraccion(3,4) );
		resultado.simplifica();
		assertEquals( "1/2", resultado.toString() );
	}
 
	@Test
	void testMultiplica_PorUno() {
		// 5/7 * 1/1 = 5/7
		Fraccion resultado = Fraccion.multiplica( new Fraccion(5,7), new Fraccion(1,1) );
		resultado.simplifica();
		assertEquals( "5/7", resultado.toString() );
	}
 
	@Test
	void testMultiplica_PorCero() {
		// 5/7 * 0/1 = 0/7 → valor 0
		Fraccion resultado = Fraccion.multiplica( new Fraccion(5,7), new Fraccion(0,1) );
		assertEquals( 0.0, resultado.calcular(), DELTA );
	}
 
	@Test
	void testMultiplica_NegativoPorNegativo() {
		// (-1/2) * (-1/3) = 1/6
		Fraccion resultado = Fraccion.multiplica( new Fraccion(-1,2), new Fraccion(-1,3) );
		resultado.simplifica();
		assertEquals( "1/6", resultado.toString() );
	}
 
	@Test
	void testMultiplica_OperandoNull_DeberiaLanzarExcepcion() {
		// El Javadoc no menciona nulls.
		// Actualmente lanza NullPointerException (no controlada).
		try {
			Fraccion.suma( null, new Fraccion(1,2) );
			fail( "Debería lanzarse excepción con operando null" );
		} catch (NullPointerException e) {
			// Correcto (aunque debería documentarse)
		}
	}
 
	// =====================================================================
	// TESTS PARA: divide(Fraccion f1, Fraccion f2)
	//
	// Javadoc: "Divide dos fracciones y devuelve nueva fracción resultado"
	// =====================================================================
 
	@Test
	void testDivide_Basica() {
		// (1/2) / (3/4) = (1/2)*(4/3) = 4/6 → 2/3
		Fraccion resultado = Fraccion.divide( new Fraccion(1,2), new Fraccion(3,4) );
		resultado.simplifica();
		assertEquals( "2/3", resultado.toString() );
	}
 
	@Test
	void testDivide_PorUno() {
		// (5/7) / (1/1) = 5/7
		Fraccion resultado = Fraccion.divide( new Fraccion(5,7), new Fraccion(1,1) );
		resultado.simplifica();
		assertEquals( "5/7", resultado.toString() );
	}
 
	@Test
	void testDivide_PorSiMisma() {
		// (3/4) / (3/4) = 1/1 → 1
		Fraccion resultado = Fraccion.divide( new Fraccion(3,4), new Fraccion(3,4) );
		resultado.simplifica();
		assertEquals( "1/1", resultado.toString() );
	}
 
	@Test
	void testDivide_EntreFraccionCero_DeberiaLanzarExcepcion() {
		// Dividir entre 0/5 equivale a dividir entre cero.
		// El resultado tendría denominador 0 (f1.den*f2.num = 2*0 = 0)
		// → NO es un número real.
		// PROPUESTA DE MEJORA: divide() debería lanzar ArithmeticException
		// cuando f2 vale cero (f2.num == 0).
		try {
			Fraccion.divide( new Fraccion(1,2), new Fraccion(0,5) );
			fail( "Debería lanzarse ArithmeticException al dividir entre fracción cero" );
		} catch (ArithmeticException e) {
			// Correcto: no se puede dividir entre cero
		}
		// Alternativa con assertThrows:
		// assertThrows( ArithmeticException.class,
		//     () -> Fraccion.divide( new Fraccion(1,2), new Fraccion(0,5) ) );
	}
 
	@Test
	void testDivide_OperandoNull_DeberiaLanzarExcepcion() {
		try {
			Fraccion.divide( new Fraccion(1,2), null );
			fail( "Debería lanzarse excepción con operando null" );
		} catch (NullPointerException e) {
			// Correcto (aunque debería documentarse)
		}
		// Alternativa: assertThrows( NullPointerException.class,
		//     () -> Fraccion.divide(new Fraccion(1,2), null) );
	}
 
	// =====================================================================
	// TESTS PARA: equals(Object obj)
	//
	// Javadoc:
	//   - Devuelve true si son iguales o equivalentes
	//   - "las simplificadas son iguales a las no simplificadas"
	// =====================================================================
 
	@Test
	void testEquals_FraccionesIguales() {
		Fraccion f1 = new Fraccion(1, 2);
		Fraccion f2 = new Fraccion(1, 2);
		assertTrue( f1.equals(f2) );
	}
 
	@Test
	void testEquals_FraccionesEquivalentes() {
		// 2/4 y 1/2 son equivalentes
		Fraccion f1 = new Fraccion(2, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertTrue( f1.equals(f2) );
	}
 
	@Test
	void testEquals_FraccionesDistintas() {
		Fraccion f1 = new Fraccion(1, 2);
		Fraccion f2 = new Fraccion(1, 3);
		assertFalse( f1.equals(f2) );
	}
 
	@Test
	void testEquals_ConNull() {
		Fraccion f = new Fraccion(1, 2);
		assertFalse( f.equals(null) );
	}
 
	@Test
	void testEquals_ConOtroTipo() {
		Fraccion f = new Fraccion(1, 2);
		assertFalse( f.equals("1/2") );
	}
 
	@Test
	void testEquals_Simetria() {
		// Si f1.equals(f2), entonces f2.equals(f1)
		Fraccion f1 = new Fraccion(2, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertEquals( f1.equals(f2), f2.equals(f1),
			"equals() debe ser simétrico" );
	}
 
	// TODO - Arreglar este error en Fraccion
	
	@Test
	void testEquals_EFECTO_LATERAL_Simplifica() {
		// BUG GRAVE: equals() llama a simplifica(), que MODIFICA this y el argumento.
		// Tras comparar 2/4 con 1/2, la fracción 2/4 queda convertida en 1/2.
		//
		// Esto viola el principio de que equals() no debería tener efectos laterales.
		//
		// ESTE TEST FALLA: demuestra que equals() modifica las fracciones.
		// ACCIÓN NECESARIA: equals() no debería mutar las fracciones originales.
		//   Debería comparar copias simplificadas o usar otro criterio (productos cruzados).
		Fraccion f1 = new Fraccion(2, 4);
		Fraccion f2 = new Fraccion(3, 9);
		f1.equals(f2); // Comparamos... pero ¡modifica f1 y f2!
		assertEquals( "2/4", f1.toString(),
			"equals() NO debería haber modificado f1" );
		assertEquals( "3/9", f2.toString(),
			"equals() NO debería haber modificado f2" );
	}
 
	// =====================================================================
	// TESTS PARA: hashCode()
	//
	// Contrato Java: si a.equals(b), entonces a.hashCode() == b.hashCode()
	// =====================================================================
 
	@Test
	void testHashCode_ConsistenciaConEquals() {
		// BUG: hashCode() devuelve den+num (sin simplificar).
		// Para 2/4: hashCode = 6.  Para 1/2: hashCode = 3.
		// Pero 2/4.equals(1/2) es true → ¡violan el contrato de hashCode!
		//
		// ESTE TEST FALLARÁ: demuestra la inconsistencia.
		// ACCIÓN NECESARIA: hashCode() debería calcular sobre la fracción simplificada.
		//   Por ejemplo: simplificar una copia y devolver 31*num_s + den_s.
		Fraccion f1 = new Fraccion(2, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertTrue( f1.equals(f2), "Premisa: 2/4 equals 1/2" );
		assertEquals( f1.hashCode(), f2.hashCode(),
			"Si equals() es true, hashCode() DEBE ser igual (contrato Java)" );
	}
 
	@Test
	void testHashCode_MismaFraccion() {
		// Llamadas repetidas al hashCode de la misma fracción deben dar igual
		Fraccion f = new Fraccion(3, 5);
		assertEquals( f.hashCode(), f.hashCode() );
	}
 
	// =====================================================================
	// TESTS PARA: compareTo(Fraccion o)
	//
	// Contrato Comparable: devuelve <0, 0 o >0 según orden
	// =====================================================================
 
	@Test
	void testCompareTo_MenorQue() {
		// 1/4 < 1/2
		Fraccion f1 = new Fraccion(1, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertTrue( f1.compareTo(f2) < 0 );
	}
 
	@Test
	void testCompareTo_MayorQue() {
		// 3/4 > 1/2
		Fraccion f1 = new Fraccion(3, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertTrue( f1.compareTo(f2) > 0 );
	}
 
	@Test
	void testCompareTo_Iguales() {
		// 2/4 y 1/2 valen lo mismo
		Fraccion f1 = new Fraccion(2, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertEquals( 0, f1.compareTo(f2) );
	}
 
	@Test
	void testCompareTo_Negativos() {
		// -1/2 < 1/2
		Fraccion f1 = new Fraccion(-1, 2);
		Fraccion f2 = new Fraccion(1, 2);
		assertTrue( f1.compareTo(f2) < 0 );
	}
 
	@Test
	void testCompareTo_ConsistenciaConEquals() {
		// Si compareTo devuelve 0, equals debería devolver true (recomendado por Java)
		Fraccion f1 = new Fraccion(2, 4);
		Fraccion f2 = new Fraccion(1, 2);
		assertEquals( 0, f1.compareTo(f2) );
		assertTrue( f1.equals(f2),
			"Recomendación Java: compareTo==0 debería ser consistente con equals" );
	}
 
	@Test
	void testCompareTo_ComparacionDouble_PosibleErrorPrecision() {
		// La implementación compara doubles con ==, lo que puede fallar por precisión.
		// Ejemplo: 1/3 y 2/6 deberían ser iguales, pero sus cálculos double
		// podrían diferir en el último bit.
		//
		// PREGUNTA DE DISEÑO: ¿es mejor comparar con productos cruzados (enteros)
		// para evitar errores de punto flotante?
		// Es decir: a/b vs c/d → comparar a*d vs c*b (todo en enteros)
		Fraccion f1 = new Fraccion(1, 3);
		Fraccion f2 = new Fraccion(2, 6);
		assertEquals( 0, f1.compareTo(f2),
			"1/3 y 2/6 son iguales, pero la comparación con == en doubles podría fallar" );
	}
 
	@Test
	void testCompareTo_Null_DeberiaLanzarExcepcion() {
		// El contrato de Comparable dice que compareTo(null) debe lanzar
		// NullPointerException
		Fraccion f = new Fraccion(1, 2);
		try {
			f.compareTo(null);
			fail( "Debería haber lanzado NullPointerException al comparar con null" );
		} catch (NullPointerException e) {
			// Correcto según el contrato de Comparable
		}
		// Alternativa: assertThrows( NullPointerException.class,
		//     () -> new Fraccion(1,2).compareTo(null) );
	}
 
	// =====================================================================
	// TESTS PARA: toString() y toStringConValor()
	// =====================================================================
 
	@Test
	void testToString_Formato() {
		assertEquals( "3/4", new Fraccion(3, 4).toString() );
	}
 
	@Test
	void testToString_ConNegativos() {
		assertEquals( "-3/4", new Fraccion(-3, 4).toString() );
	}
 
	@Test
	void testToStringConValor_Formato() {
		assertEquals( "1/2 = 0.5", new Fraccion(1, 2).toStringConValor() );
	}
 
	// =====================================================================
	// TESTS DE INTEGRACIÓN: operaciones encadenadas
	// =====================================================================
 
	@Test
	void testIntegracion_SumaYSimplifica() {
		// 1/6 + 1/6 = 2/6 → simplificada 1/3
		Fraccion resultado = Fraccion.suma( new Fraccion(1,6), new Fraccion(1,6) );
		resultado.simplifica();
		assertEquals( "1/3", resultado.toString() );
	}
 
	@Test
	void testIntegracion_OperacionesComplejas() {
		// (1/2 + 1/3) * (1/4) = (5/6) * (1/4) = 5/24
		Fraccion suma = Fraccion.suma( new Fraccion(1,2), new Fraccion(1,3) );
		Fraccion resultado = Fraccion.multiplica( suma, new Fraccion(1,4) );
		resultado.simplifica();
		assertEquals( "5/24", resultado.toString() );
	}
 
	@Test
	void testIntegracion_RestaInversa() {
		// f - f = 0  para cualquier fracción f
		Fraccion f = new Fraccion(7, 13);
		Fraccion resultado = Fraccion.resta(f, f);
		assertEquals( 0.0, resultado.calcular(), DELTA );
	}
 
	@Test
	void testIntegracion_MultiplicaPorInversa() {
		// f * (1/f) = 1  (para f != 0)
		Fraccion f = new Fraccion(3, 5);
		Fraccion inversa = new Fraccion(5, 3);
		Fraccion resultado = Fraccion.multiplica(f, inversa);
		resultado.simplifica();
		assertEquals( "1/1", resultado.toString() );
	}
 
	@Test
	void testIntegracion_DividePorSiMisma() {
		// f / f = 1  (para f != 0)
		Fraccion f = new Fraccion(7, 11);
		Fraccion resultado = Fraccion.divide(f, f);
		resultado.simplifica();
		assertEquals( "1/1", resultado.toString() );
	}
	
	
}
