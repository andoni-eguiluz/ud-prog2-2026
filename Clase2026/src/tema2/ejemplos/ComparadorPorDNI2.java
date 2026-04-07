package tema2.ejemplos;

import java.util.Comparator;

/** Compara dnis por número, ignorando la letra
 */
public class ComparadorPorDNI2 implements Comparator<Persona> {
	@Override
	public int compare(Persona o1, Persona o2) {
		int dni1 = calculaNumeroDNI( o1.getDni() );
		int dni2 = calculaNumeroDNI( o2.getDni() );
		return dni1 - dni2;
	}
	
	public static int calculaNumeroDNI( String dni ) {
		// Coger último carácter
		String ultimoCar = dni.substring( dni.length()-1 );
		// Comprobar si es una letra
		boolean esLetra = Character.isLetter( ultimoCar.charAt(0));
		// Convertir string a entero (quitando la letra si acaba en letra)
		int valorDNI = 0;
		if (esLetra) {
			valorDNI = Integer.parseInt( dni.substring( 0, dni.length()-1 ));
		} else {
			valorDNI = Integer.parseInt( dni );
		}
		return valorDNI;
	}

}
