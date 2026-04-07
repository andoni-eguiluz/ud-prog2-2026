package tema3.basicos;

import java.util.*;

// Esta es una clase externa
public class ConceptoClasesInternas {
	static String staticC = "C";
	// Atributos de clase externa
	String datoC = "c";  

	public static void main(String[] args) {
		claseInternaNoStatic();
		claseInternaStatic();
		claseAnonima();
	}
	
	public static void claseInternaNoStatic() {
		// Objeto de clase externa
		ConceptoClasesInternas c = new ConceptoClasesInternas();  
		System.out.println( "Atributo de clase externa: " + c.datoC );
		
		// Objeto de clase interna - necesita un objeto de clase externa para poderse crear
		// Su nombre de clase es la concatenación del nombre de clase externa y clase interna
		//  (aunque en el ámbito de la clase interna se puede abreviar solo con su nombre de interna)
		// La sintaxis del new es especial: funciona sobre un objeto de la clase externa
		ConceptoClasesInternas.Interna i = c.new Interna();  
		// Y se asocia a ese objeto. Por eso puede acceder a información de ese objeto de la clase externa
		System.out.println( "Objeto de clase interna: " + i.info() );
	}
	
	public static void claseInternaStatic() {
		// Objeto de clase interna static
		// No necesita un objeto de la clase externa
		// Su nombre de clase es la concatenación del nombre de clase externa y clase interna
		ConceptoClasesInternas.InternaStatic is = new InternaStatic();
		// Y tiene acceso a atributos de la clase externa, pero solo static 
		System.out.println( "Objeto de clase externa: " + is.info() );
		ArrayList<String> l = new ArrayList<>( Arrays.asList( "A", "E", "I", "O", "U" ) );
		l.sort( new ComparadorInverso() );
		System.out.print( "Comparator inverso con clase interna static: " );
		System.out.println( l );
	}
	
	public static void claseAnonima() {
		// Clase anónima: una clase interna sin nombre. Se crea con una sintaxis especial
		// Ejemplo: comparador
		Comparator<String> comparadorInverso = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return -o1.compareTo(o2);
			}
		};
		ArrayList<String> l = new ArrayList<>( Arrays.asList( "A", "E", "I", "O", "U" ) );
		l.sort( comparadorInverso );
		System.out.print( "Comparator inverso con clase anónima: " );
		System.out.println( l );
		// Si se crea en un método static, es una clase interna static (como en este caso)
		// Si se crea en un método no static, es una clase interna no static -con acceso a atributos de objeto externo-
	}

	// Clase interna no static (observa que puede ser privada - solo las clases internas pueden ser privadas)
	private class Interna {
		String datoI = "i";
		public String info() {
			return "Mi dato: " + datoI + " - el dato de mi objeto externo asociado: " + datoC;
		}
	}
	
	// Clase interna static
	private static class InternaStatic {
		static String staticI = "I";
		String datoIS = "is";
		public String info() {
			return "mi dato: " + datoIS + " - acceso a static mío " + staticI + " y a static externa " + staticC;
		}
	}
	
	// Ejemplo uso clase interna static
	private static class ComparadorInverso implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return -o1.compareTo(o2);
		}
		
	}
}
