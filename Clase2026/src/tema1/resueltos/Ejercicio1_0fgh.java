package tema1.resueltos;

//Ejercicios 1.0.f. / 1.0.g. / 1.0.h.

public class Ejercicio1_0fgh {

	// int cálculo = 5; // Nota de Java: se pueden usar tildes o ñs pero mejor no en identificadores para evitar problemas de charset

	public static void main(String[] args) {
		inicializar();
		visualizar();
		sacarMenor();
		ordenarNombres();
	}
	
	static void inicializar() {
		listaNombres = new String[] { "Andoni", "Luis", "María", "Marta", "Xabi", "Ana" };  // Inicialización aquí necesita new String[]
		listaNombres[1] = "Lucas";
		// listaNombres[7] = "Amaia";  IndexOutOfBounds
	}

	// Aunque se recomienda atributos al principio y métodos después, el orden en Java puede ser cualquiera
	static String[] listaNombres; //  = { "Andoni", "Luis", "María", "Marta", "Xabi", "Ana" };  // Aquí se podría hacer directo

	static void visualizar() {
		for (String s : listaNombres) {
			System.out.print( s + " " );
		}
		System.out.println();  // Para sacar salto de línea al final
	}
	
	static void sacarMenor() {   // TODO mejorar naming?
		String menor = "zzz";
		for (int i=0; i<6; i++) {   // TODO ¿mejorar el 6?
			if (listaNombres[i].compareTo( menor ) < 0) {   // TODO OBSERVAD LA DIFERENCIA SINTAXIS EN OBJETOS!!!
				menor = listaNombres[i];
			}
		}
		System.out.println( "Nombre menor alfabético: " + menor );
		String nombreMayor = "A";
		for (String nombre : listaNombres) {
			if (nombre.compareTo(nombreMayor)>0) {
				nombreMayor = nombre;
			}
		}
		System.out.println( "Nombre mayor alfabético: " + nombreMayor );
	}

	static void ordenarNombres() {
		ordenarNombres( listaNombres );
		System.out.print( "Lista ordenada: ");
		for (String s : listaNombres) {
			System.out.print( s + " " );
		}
		System.out.println();
	}

	// Algoritmo de ordenación bubble sort
	/** Ordena los strings por orden alfabético
	 * @param nombres	Array de strings. Lo modifica para dejar los mismos elementos pero ordenados
	 */
	public static void ordenarNombres( String[] nombres ) {
		for (int j=1; j<nombres.length; j++) {
			for (int i=0; i<nombres.length-j; i++) {
				if (nombres[i].compareTo(nombres[i+1])>0) {
					String temp = nombres[i];
					nombres[i] = nombres[i+1];
					nombres[i+1] = temp;
				}
			}
		}
	}
	
}
