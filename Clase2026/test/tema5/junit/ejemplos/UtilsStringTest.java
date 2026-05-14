package tema5.junit.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilsStringTest {


	// =====================================================================
	// TESTS PARA: quitarTabsYSaltosLinea(String s)
	// Contrato según Javadoc:
	//   - Sustituye \n por # y \t por |
	//   - Devuelve null si s es null
	// =====================================================================
 
	// --- Casos básicos: funcionamiento normal ---
 
	@Test
	void testQuitar_SaltoLineaSencillo() {
		// Un solo \n debe convertirse en #
		assertEquals( "a#b", UtilsString.quitarTabsYSaltosLinea("a\nb") );
	}
 
	@Test
	void testQuitar_TabuladorSencillo() {
		// Un solo \t debe convertirse en |
		assertEquals( "a|b", UtilsString.quitarTabsYSaltosLinea("a\tb") );
	}
 
	@Test
	void testQuitar_SaltosYTabuladoresCombinados() {
		// Caso combinado (el ejemplo original del main)
		String entrada = "Hola\nEsto es un string con tres líneas\ny\tvarios\ttabuladores.";
		String esperado = "Hola#Esto es un string con tres líneas#y|varios|tabuladores.";
		assertEquals( esperado, UtilsString.quitarTabsYSaltosLinea(entrada) );
	}
 
	@Test
	void testQuitar_VariosSaltosConsecutivos() {
		// Varios \n seguidos: todos deben sustituirse
		assertEquals( "a##b", UtilsString.quitarTabsYSaltosLinea("a\n\nb") );
	}
 
	@Test
	void testQuitar_VariosTabuladoresConsecutivos() {
		// Varios \t seguidos: todos deben sustituirse
		assertEquals( "a||b", UtilsString.quitarTabsYSaltosLinea("a\t\tb") );
	}
 
	// --- Casos frontera ---
 
	@Test
	void testQuitar_StringSinSaltosNiTabs() {
		// Si no hay nada que sustituir, debe devolver el mismo string
		assertEquals( "texto normal", UtilsString.quitarTabsYSaltosLinea("texto normal") );
	}
 
	@Test
	void testQuitar_StringVacio() {
		// String vacío: caso frontera habitual, debe devolver ""
		assertEquals( "", UtilsString.quitarTabsYSaltosLinea("") );
	}
 
	@Test
	void testQuitar_SoloSaltoDeLinea() {
		// String que ES un salto de línea
		assertEquals( "#", UtilsString.quitarTabsYSaltosLinea("\n") );
	}
 
	@Test
	void testQuitar_SoloTabulador() {
		// String que ES un tabulador
		assertEquals( "|", UtilsString.quitarTabsYSaltosLinea("\t") );
	}
 
	// --- Caso especial: ¿y el retorno de carro \r? ---
 
	@Test
	void testQuitar_RetornoDeCarro() {
		// PREGUNTA ABIERTA: El Javadoc dice "saltos de línea".
		// En Windows un salto de línea es \r\n. ¿Debería \r también sustituirse?
		// El Javadoc solo menciona \n explícitamente, así que \r debería quedarse.
		// Si este test falla, la implementación va más allá de lo documentado.
		assertEquals( "\r#", UtilsString.quitarTabsYSaltosLinea("\r\n") );
	}
 
	// --- Caso que REVELARÁ UN BUG: null ---
 
	@Test
	void testQuitar_StringNull() {
		// El Javadoc PROMETE: "Devuelve null si s es null"
		// ESTE TEST FALLARÁ: la implementación hace s.replaceAll() directamente,
		// lo que lanza NullPointerException en lugar de devolver null.
		// 
		// ACCIÓN NECESARIA: o se corrige el código para que compruebe null,
		// o se corrige la documentación para indicar que lanza NullPointerException.
		assertNull( UtilsString.quitarTabsYSaltosLinea(null) );
	}
 
	// --- Caso de idempotencia ---
 
	@Test
	void testQuitar_StringConAlmohadillasYBarras() {
		// Si el string original ya contiene # y |, no deben modificarse.
		// La sustitución solo afecta a \n y \t, no a sus reemplazos.
		assertEquals( "a#b|c", UtilsString.quitarTabsYSaltosLinea("a#b|c") );
	}

	
	
	// =====================================================================
	// TESTS PARA: wrapString(String s, int largo)
	//
	// Contrato según Javadoc:
	//   - Si s tiene más de 'largo' caracteres: recorta a 'largo' + "..."
	//   - Si s tiene 'largo' o menos caracteres: devuelve el mismo string
	//   - Si s es null: devuelve null
	//   - Si largo es negativo: lanza IndexOutOfBoundsException
	// =====================================================================
 
	// --- Caso normal: truncamiento ---
 
	@Test
	void testWrap_StringMasLargoQueLimite() {
		// "abcdef" tiene 6 caracteres, truncar a 3 → "abc..."
		assertEquals( "abc...", UtilsString.wrapString("abcdef", 3) );
	}
 
	@Test
	void testWrap_StringMuchoMasLargoQueLimite() {
		// Caso con diferencia grande entre tamaño y límite
		assertEquals( "Ho...", UtilsString.wrapString("Hola esto es muy largo", 2) );
	}
 
	// --- Caso normal: sin truncamiento ---
 
	@Test
	void testWrap_StringMasCortoQueLimite() {
		// "abc" tiene 3 caracteres, largo=10: no debería truncar
		// Según el Javadoc: "mismo string en caso contrario"
		//
		// ESTE TEST FALLARÁ: la implementación SIEMPRE trunca y añade "...",
		// incluso cuando el string es más corto que 'largo'.
		// En este caso lanzará StringIndexOutOfBoundsException porque
		// substring(0, 10) falla en un string de 3 caracteres.
		//
		// ACCIÓN NECESARIA: el código necesita un if que compruebe la longitud.
		assertEquals( "abc", UtilsString.wrapString("abc", 10) );
	}
 
	@Test
	void testWrap_StringExactamenteDeLargoIgualAlLimite() {
		// Caso frontera clave: longitud == largo
		// Según el Javadoc: "si ocupaba más" → recorta. Si no → mismo string.
		// Con longitud == largo NO ocupa MÁS, así que debe devolver el mismo string.
		//
		// ESTE TEST FALLARÁ por la misma razón: la implementación siempre trunca.
		assertEquals( "abc", UtilsString.wrapString("abc", 3) );
	}
 
	// --- Casos frontera con largo ---
 
	@Test
	void testWrap_LargoCero() {
		// largo=0: el string siempre será "más largo" (salvo vacío).
		// Debe devolver los 0 primeros caracteres + "..." = "..."
		assertEquals( "...", UtilsString.wrapString("hola", 0) );
	}
 
	@Test
	void testWrap_LargoUno() {
		// largo=1
		assertEquals( "h...", UtilsString.wrapString("hola", 1) );
	}
 
	// --- Caso frontera: string vacío ---
 
	@Test
	void testWrap_StringVacioConLargoCero() {
		// "" tiene 0 caracteres, largo=0: no ocupa MÁS → mismo string
		// ESTE TEST FALLARÁ: la implementación añade "..." siempre.
		assertEquals( "", UtilsString.wrapString("", 0) );
	}
 
	@Test
	void testWrap_StringVacioConLargoPositivo() {
		// "" tiene 0 caracteres, largo=5: no ocupa más → mismo string
		// ESTE TEST FALLARÁ: substring(0,5) en "" lanza excepción.
		assertEquals( "", UtilsString.wrapString("", 5) );
	}
 
	// --- Caso null ---
 
	@Test
	void testWrap_StringNull() {
		// El Javadoc PROMETE: "null si s es null"
		// ESTE TEST FALLARÁ: s.substring() lanza NullPointerException.
		//
		// ACCIÓN NECESARIA: añadir comprobación de null al inicio del método.
		assertNull( UtilsString.wrapString(null, 5) );
	}
 
	// --- Caso de excepción documentada ---
 
	@Test
	void testWrap_LargoNegativo() {
		// El Javadoc PROMETE: lanza IndexOutOfBoundsException si largo es negativo.
		// (Realmente lo lanzará substring, pero debería documentarse mejor
		// si es un efecto buscado o simplemente heredado de substring.)
		assertThrows( IndexOutOfBoundsException.class, () -> {
			UtilsString.wrapString("hola", -1);
		});
		// (Manera alternativa de probar excepciones además de con try / catch)
	}
 
	@Test
	void testWrap_LargoNegativoConNull() {
		// ¿Qué ocurre si AMBOS parámetros son problemáticos?
		// s es null Y largo es negativo. ¿Qué tiene prioridad?
		// El Javadoc no lo aclara. Si se comprueba null primero → null.
		// Si se hace substring primero → NullPointerException (no IndexOutOfBounds).
		// 
		// PREGUNTA DE DISEÑO: ¿qué debería devolver?
		// Posibilidad razonable: null (primero se comprueba el parámetro s)
		// Posibilidad alternativa: la excepción (el largo negativo es un error de programación)
		//
		// Este test documenta una ambigüedad real del Javadoc.
		// Elegimos que debería devolver null (null "gana" sobre el error de largo):
		assertNull( UtilsString.wrapString(null, -1) );
	}
 
	// --- Pregunta sobre la semántica del resultado ---
 
	@Test
	void testWrap_LongitudDelResultadoTruncado() {
		// PREGUNTA DE DISEÑO: cuando se trunca a largo=5, ¿el resultado tiene
		// 5 caracteres o 5+3=8 (los 5 más los "...")?
		// El Javadoc dice "recortado si ocupaba más de largo caracteres".
		// Esto es ambiguo: ¿"largo" mide el resultado final o el punto de corte?
		//
		// La implementación actual devuelve largo+3 caracteres (corta en 'largo'
		// y luego AÑADE "..."), así que "abcdefghij" con largo=5 → "abcde..."  (8 chars)
		//
		// Aceptamos la interpretación de la implementación:
		String resultado = UtilsString.wrapString("abcdefghij", 5);
		assertEquals( "abcde...", resultado );
		assertEquals( 8, resultado.length() ); // 5 + 3 puntos
	}
 
	// =====================================================================
	// TESTS PARA: convierteOrd(String s)
	//
	// Contrato según Javadoc:
	//   - Convierte un string para comparación correcta en castellano
	//   - Considera: orden alfabético, mayúsculas, tildes, eñes, diéresis
	//   - No menciona comportamiento con null
	// =====================================================================
 
	// --- Caso básico: minúsculas sin acentos no cambian ---
 
	@Test
	void testConvierte_MinusculasSinAcentos() {
		assertEquals( "hola", UtilsString.convierteOrd("hola") );
	}
 
	// --- Mayúsculas a minúsculas ---
 
	@Test
	void testConvierte_MayusculasAMinusculas() {
		assertEquals( "hola mundo", UtilsString.convierteOrd("HOLA MUNDO") );
	}
 
	@Test
	void testConvierte_MayusculasMezcladas() {
		assertEquals( "hola", UtilsString.convierteOrd("HoLa") );
	}
 
	// --- Tildes (vocales acentuadas) ---
 
	@Test
	void testConvierte_VocalesConTilde() {
		assertEquals( "aeiou", UtilsString.convierteOrd("áéíóú") );
	}
 
	@Test
	void testConvierte_VocalesConTildeMayusculas() {
		// Á → (toLowerCase) → á → a
		assertEquals( "aeiou", UtilsString.convierteOrd("ÁÉÍÓÚ") );
	}
 
	@Test
	void testConvierte_PalabraConTilde() {
		assertEquals( "camion", UtilsString.convierteOrd("camión") );
	}
 
	// --- Diéresis ---
 
	@Test
	void testConvierte_Dieresis() {
		assertEquals( "pinguino", UtilsString.convierteOrd("pingüino") );
	}
 
	@Test
	void testConvierte_DieresisMayuscula() {
		assertEquals( "pinguino", UtilsString.convierteOrd("PINGÜINO") );
	}
 
	// --- Eñe ---
 
	@Test
	void testConvierte_EneMinuscula() {
		// ñ → nzz (para que ordene después de "nz" pero antes de "o")
		assertEquals( "anzzo", UtilsString.convierteOrd("año") );
	}
 
	@Test
	void testConvierte_EneMayuscula() {
		// Ñ → (toLowerCase) → ñ → nzz
		assertEquals( "anzzo", UtilsString.convierteOrd("AÑO") );
	}
 
	@Test
	void testConvierte_EneEnMedioYAlFinal() {
		// Verificar que ñ se sustituye en cualquier posición
		assertEquals( "nzzunzz", UtilsString.convierteOrd("ñuñ") );
	}
 
	// --- Verificación del ORDEN: el propósito real del método ---
 
	@Test
	void testConvierte_OrdenNuevaAntesDePalabra() {
		// En castellano: "nueva" < "ñu" < "obra"
		// Comprobemos que la conversión respeta esto
		String nueva = UtilsString.convierteOrd("nueva");
		String enu   = UtilsString.convierteOrd("ñu");
		String obra  = UtilsString.convierteOrd("obra");
		assertTrue( nueva.compareTo(enu) < 0,
			"'nueva' debería ordenar antes que 'ñu': " + nueva + " vs " + enu );
		assertTrue( enu.compareTo(obra) < 0,
			"'ñu' debería ordenar antes que 'obra': " + enu + " vs " + obra );
	}
 
	@Test
	void testConvierte_OrdenConTildesEquivalente() {
		// "camion" y "camión" deberían producir el mismo resultado de ordenación
		assertEquals( UtilsString.convierteOrd("camion"),
		              UtilsString.convierteOrd("camión") );
	}
 
	@Test
	void testConvierte_OrdenMayusculasEquivalente() {
		// "Bilbao" y "bilbao" deberían producir el mismo resultado
		assertEquals( UtilsString.convierteOrd("Bilbao"),
		              UtilsString.convierteOrd("bilbao") );
	}
 
	// --- Casos frontera ---
 
	@Test
	void testConvierte_StringVacio() {
		assertEquals( "", UtilsString.convierteOrd("") );
	}
 
	@Test
	void testConvierte_SoloEspacios() {
		assertEquals( "   ", UtilsString.convierteOrd("   ") );
	}
 
	@Test
	void testConvierte_Numeros() {
		// Los números no deberían verse afectados
		assertEquals( "abc123", UtilsString.convierteOrd("ABC123") );
	}
 
	@Test
	void testConvierte_CaracteresEspeciales() {
		// Caracteres como puntuación no deberían verse afectados
		// (solo se pasan a minúsculas los que apliquen)
		assertEquals( "¡hola!", UtilsString.convierteOrd("¡Hola!") );
	}
 
	// --- Caso null: NO documentado ---
 
	@Test
	void testConvierte_StringNull() {
		// El Javadoc de convierteOrd NO dice qué pasa con null.
		// A diferencia de los otros dos métodos, aquí no se menciona.
		//
		// PREGUNTA DE DISEÑO:
		// ¿Debería devolver null? ¿Debería lanzar NullPointerException?
		// ¿Debería documentarse?
		//
		// La implementación actual lanzará NullPointerException (s.toLowerCase())
		// Este test comprueba que al menos lance una excepción predecible:
		assertThrows( NullPointerException.class, () -> {
			UtilsString.convierteOrd(null);
		});
		// ACCIÓN RECOMENDADA: documentar el comportamiento con null en el Javadoc,
		// y decidir si debería devolver null (como los otros métodos) o lanzar excepción.
	}
 
	// --- Caso sutil: doble ñ y combinaciones ---
 
	@Test
	void testConvierte_DobleEne() {
		// "ññ" → "nzznzz"
		assertEquals( "nzznzz", UtilsString.convierteOrd("ññ") );
	}
 
	@Test
	void testConvierte_EneSeguidaDeZeta() {
		// Caso potencialmente problemático para el orden:
		// "ñ" se convierte en "nzz". ¿Y "nz"? Se queda como "nz".
		// Entonces "nz" < "nzz" (que es "ñ") ✓ correcto.
		// Pero ¿y "nzz"? Se quedaría como "nzz" → igual que "ñ" → ¡colisión!
		//
		// ESTE TEST DEMUESTRA UN FALLO DE DISEÑO:
		// "nzz" (literal) y "ñ" producen el MISMO resultado,
		// lo que haría que se considerasen "iguales" en ordenación.
		// En la práctica "nzz" es rarísimo en castellano, pero es un defecto teórico.
		String nzz_literal = UtilsString.convierteOrd("nzz");
		String ene         = UtilsString.convierteOrd("ñ");
		// Ambos producen "nzz" → son indistinguibles
		assertEquals( nzz_literal, ene,
			"DEFECTO CONOCIDO: 'nzz' literal y 'ñ' producen el mismo resultado" );
	}
 
	// --- Caso de combinación completa ---
 
	@Test
	void testConvierte_FraseCompleta() {
		// Frase con mayúsculas, tildes, eñe y diéresis
		assertEquals( "el nzzonzzo espanzzol tiene linguistica",
		              UtilsString.convierteOrd("El Ñoño Español tiene Lingüística") );
		// Nota: "Ñoño" → "nzzonzzo", "Español" → "espanzzol", "Lingüística" → "linguistica"
	}

}
