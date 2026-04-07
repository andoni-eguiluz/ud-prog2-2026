package tema3.basicos;

/** Ejemplo de uso de enum y explicación del concepto
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class ConceptoEnums {

	public static void main(String[] args) {
		
		// Imaginemos que quiero usar colores de 4 tipos: rojo, verde, azul, blanco

		// Me lo podría currar con strings... pero es muy fácil cometer errores
		String color = "rojo";
		color = "verde";
		color = "berde";
		System.out.println( "Color string: " + color );
		
		// Podría codificarlo con enteros... pero ¿quién es quién? ¿Por qué 0 es rojo y no verde?		
		int color2 = 0;  // 0 rojo
		color2 = 1; // 1 verde  ¿o era azul?
		System.out.println( "Color entero: " + color2 );
		
		// En informática nos hemos pasado décadas codificando valores con enteros, normalmente con constantes:.
		final int COLOR_ROJO = 0;
		final int COLOR_VERDE = 1;
		System.out.println( "Color entero en constante: " + COLOR_VERDE );
		// De hecho Java todavía lo sigue usando mucho
		// Por ejemplo: https://docs.oracle.com/javase/8/docs/api/javax/swing/WindowConstants.html#DISPOSE_ON_CLOSE
        // Ahí puedes ver tres posibilidades (cuando trabajemos ventanas) HIDE / DISPOSE / EXIT que son los enteros		
		
		// El problema de las constantes es que no hay relación entre el valor y su significado. 
		// Por ejemplo si escribimos a consola el rojo
		System.out.println( COLOR_ROJO );  // ¿Qué es 0 para el usuario?
		// Por ejemplo si le pedimos a un usuario que introduzca un color 0-1-2-3  ¿qué color es cada uno?
		
		// Solución de muchos lenguajes modernos: ENUMERACIONES
		// Ventajas: no cabe error léxico, no hay que aprender códigos innaturales, pero se mantiene la relación con número si la queremos
		// En java palabra clave enum usar enums.  (ver abajo: "MiColor")
		// Se pueden asignar directamente con información "simbólica" (identificadores)
		MiColor color3 = MiColor.ROJO;
		color3 = MiColor.VERDE;
		// Se pueden convertir desde strings (.valueOf)
		String colorEnString = "AZUL";
		color3 = MiColor.valueOf( colorEnString );  // Se convierte el string "AZUL" en el valor MiColor.AZUL
		// Se pueden convertir directamente a strings (toString por defecto de cualquier enum):
		System.out.println( color3 );
		// Se pueden recorrer muy fácil con un for each sobre el método .values():
		for (MiColor colorI : MiColor.values()) {
			System.out.println( colorI );
			System.out.println( colorI.ordinal() );   // .ordinal() saca el orden 0 a n-1 del enum en la lista
		}
		// Se pueden comparar
		System.out.println( "comparación blanco y rojo? " + MiColor.BLANCO.compareTo( MiColor.ROJO ) + " (>0 indica que blanco es mayor (posterior) que rojo)");
		// Y son clases normales, se les pueden añadir atributos y métodos.
		// Ver este otro ejemplo:
		System.out.println( Mes.ENE.getNumeroDeDias() );
		for (Mes mes : Mes.values()) {
			mes.getNumeroDeDias();
		}
	}

}

// Ejemplo ENUM simple:
enum MiColor {
	ROJO, VERDE, AZUL, BLANCO
	// Java internamente hace algo así (no es exactamente eso, pero es la idea: un objeto nuevo con cada valor de enum, asociado al índice entero 0 a n-1):
	// public static final MiColor ROJO = new MiColor( 0 );
	// public static final MiColor VERDE = new MiColor( 1 );
	// public static final MiColor AZUL = new MiColor( 2 );
	// public static final MiColor BLANCO = new MiColor( 3 );
	// values() --> [ ROJO, VERDE, AZUL, BLANCO ]
}
// Características de un enum:
// Solo valen los valores que se indican (se crea un objeto por valor)
// Cada valor se asocia a una constante con el mismo nombre indicado:  MiColor.ROJO, MiColor.VERDE, etc.  (son public static final, aunque no se indica)
// Tienen ORDEN y se pueden comparar: ROJO < VERDE < AZUL < BLANCO   (con el método compareTo) (O sea, un enum implementa el interfaz Comparable)
// El método estático values() devuelve un array de todas las constantes definidas para ese enum
// El método ordinal() devuelve el índice del enum (empezando en 0)


// Ejemplo ENUM con atributos/métodos:
enum Mes { ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC;
		// El punto y coma se pone cuando hay más cosas que solo las constantes de enum

	// atributos adicionales (es static pero también puede haber atributos no static, 
	// es como cualquier otra clase - SIN CONSTRUCTOR)
	private static int[] numDias = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	// métodos adicionales
	public int getNumeroDeDias() {
		return numDias[ this.ordinal() ];
	}
}
