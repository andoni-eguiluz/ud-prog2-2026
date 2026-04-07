package tema3.basicos;

import java.awt.Point;
import java.util.Date;

/** Ejemplo de parámetros variables en Java
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class ConceptoVarArgs {
	
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		parsVars();
	}
	
	private static void parsVars() {
		// Ejemplo para parámetros variables. Supongamos que queremos calcular una media de números reales
		// (1) Podríamos partir de una media de dos enteros:
		System.out.println( media( 2, 5 ) );
		// (2) Pero ¿y si necesitamos números reales? Java permite sobrecargar los métodos, con lo que podemos definir otro método que se llame igual
		System.out.println( media( 2.0, 5.0 ) );
		// (3) Y ahora necesitamos la media de tres números... pues sobrecargamos otro método más:
		System.out.println( media( 2.0, 5.0, 7.0 ) );
		// (4) Obviamente esto no se puede mantener indefinidamente. Y para eso Java incorporó la notación de parámetros variables.
		// (Ver el método en su lugar de definición)
		// Ahora se puede calcular la media de cualquier número de valores, separados por comas:
		System.out.println( media( 2.0, 5.0, 7.0, 11.0, 28.5, 43.23 ) );
		// Alternativamente, se puede llamar pasando un array y funciona de la misma forma:
		System.out.println( media( new double[] { 2.0, 5.0, 7.0, 11.0, 28.5, 43.23 } ) );
		
		// Ejemplo con objetos
		System.out.println( concatenar( "Concatenación de objetos", ',', "\"número 1\"", new Point(2,2), new Date(), new Double( 4.0 ) ) );
	}
	
	// Método (1)
	private static double media( int v1, int v2 ) {
		return( (v1+v2)/2.0 );
	}
	
	// Método (2)
	private static double media( double d1, double d2 ) {
		return (d1+d2)/2;
	}
	
	// Método (3) 
	private static double media( double d1, double d2, double d3) {
		return (d1+d2+d3) / 3.0;
	}
	
	// Método (4) - varargs!  
	// Un parámetro de la lista de parámetros puede tener los TRES PUNTOS (...)
	// Eso significa que puede llegar cualquier número (de 0 a n) de parámetros.
	// Todos deben ser del MISMO tipo (el indicado antes de los tres puntos). Puede ser primitivo o clase, o sea cualquiera.
	// Solo puede haber un tipo variable (no se pueden hacer dos o más en la misma lista), y DEBE ser siempre el último de los parámetros.
	private static double media( double... nums ) {
		// Internamente se trata como se fuera un array. O sea, EXACTAMENTE IGUAL que si definiéramos double[] ad
		double suma = 0.0;
		for (double d : nums) {  // Como es un array se puede recorrer con un for each
			suma += d;
		}
		return suma / nums.length;
	}
	
	// Ejemplo con objetos y con un parámetro antes del varargs 
	// (puede haber cualquier número de parámetros mientras solo uno sea vararg y sea el último)
	private static String concatenar( String mensajeInicial, char separador, Object... datos ) {  // Como recibimos Object es polimórfico: array de cualquier objeto
		String ret = mensajeInicial + ": ";
		for (Object o : datos) {
			ret += o.toString();  // Cualquier objeto tiene toString()   (método polimórfico)
			ret += separador;
		}
		return ret.substring( 0, ret.length()-1 );  // Quitamos el último carácter (el último separador)
	}
	
}
