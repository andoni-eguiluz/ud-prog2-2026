package tema1.ejemplos;

import java.util.Scanner;

public class EjemploPastillero {

	/** Método de prueba de pastillero - pastillas
	 * @param args
	 */
	public static void main(String[] args) {
		Pastilla pas1 = new Pastilla( "Termalgin", "Paracetamol", "mgr", 500 );
		Pastilla pas2 = new Pastilla( "Dalsy", "Ibuprofeno", "ml", 20 );
		System.out.println( "Pastilla 1 = " + pas1 );
		System.out.println( "Pastilla 2 = " + pas2 );
		Pastillero p = new Pastillero();
		p.anyadir( pas1 );
		p.anyadir( pas2 );
		System.out.println( "  Pastillero: " + p );
		
		// Cómo meter desde teclado:
		Scanner scanner = new Scanner( System.in );
		System.out.print( "Introduce nueva pastilla. Nombre comercial del medicamento: ");
		String medic = scanner.nextLine();
		System.out.print( "Nombre compuesto activo: ");
		String compuesto = scanner.nextLine();
		String unidad = "";
		do {
			System.out.print( "Unidad (mgr, ud o ml): ");
			unidad = scanner.nextLine();
		} while (!unidad.equals("mgr") && !unidad.equals("ud") && !unidad.equals("ml"));
		System.out.print( "Cantidad de " + unidad + " en la pastilla: ");
		int cantidad = scanner.nextInt();
		Pastilla pastNueva = new Pastilla( medic, compuesto, unidad, cantidad );
		p.anyadir( pastNueva );
		System.out.println( "Tras meter... " + pastNueva );
		System.out.println( "  Pastillero: " + p );
		
		System.out.println();
		System.out.println( "Y ahora vaciamos el pastillero:" );
		// Vaciar pastillero
		while (p.getNumPastillas()>0) {
			pas1 = p.coger(); System.out.println( "Cojo " + pas1 );
			System.out.println( "  Pastillero: " + p );
		}
		
		scanner.close();  // Cierre de la entrada
	}
	
}
