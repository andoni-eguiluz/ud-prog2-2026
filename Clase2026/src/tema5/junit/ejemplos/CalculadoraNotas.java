package tema5.junit.ejemplos;

// PEQUEÑO EJERCICIO DE TDD

public class CalculadoraNotas {

	/** Calcula y devuelve la media de dos notas
	 * @param nota1
	 * @param nota2
	 * @return	Media aritmética de las dos
	 * @throws IllegalArgumentException	Si alguna no está en el rango 0-10
	 */
	public static double media( int nota1, int nota2 ) throws IllegalArgumentException {
		// TODO Definir los tests. Luego implementar y reimplementar hasta que pase los tests
		validarNota( nota1 );
		validarNota( nota2 );
		return (nota1+nota2)/2.0;
	}
	
	private static void validarNota( int nota ) {
		if (nota < 0 || nota > 10) {
			throw new IllegalArgumentException( "Nota fuera de rango 0-10: " + nota );
		}
	}
	
	/** Calcula y devuelve la media de una serie de notas
	 * @param notas	Array de notas de partida
	 * @return	Media aritmética de esas notas
	 * @throws IllegalArgumentException	Cuando alguna no está en el rango 0-10
	 */
	public static double media( int... notas ) throws IllegalArgumentException {
		// TODO Definir los tests. Luego implementar y reimplementar hasta que pase los tests
		// TODO Quitar el método anterior y dejar solo este
		if (notas.length==0) {
			throw new IllegalArgumentException( "Media de 0 valores" );
		}
		double suma = 0.0;
		for (int nota : notas) {
			validarNota(nota);
			suma += nota;
		}
		return suma / notas.length;
	}
	
	public static String notaLetra( double nota ) {
		// TODO Definir los tests. Luego implementar y reimplementar hasta que pase los tests
		return null;
	}
	
	public static int mejorNota( int... notas ) {
		// TODO Definir los tests. Luego implementar y reimplementar hasta que pase los tests
		return -1;
	}
	
}
