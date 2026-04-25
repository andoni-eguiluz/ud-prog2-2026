package tema4.ejemplos;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ConceptoExcepciones {

	public static void main(String[] args) {
		// implicitas();	
		explicitasGuardarFic();
	}
	
	private static void explicitasGuardarFic() {
		// Leer números y guardar a fichero
		ArrayList<Integer> l = new ArrayList<>();
		JOptionPane.showMessageDialog( null, "Introduce números hasta cualquier cosa que no sea entera" );
		try {
			while (true) {
				int n = pedirNumero( "Introduce número:" );
				l.add( n );
			}
		} catch (NumberFormatException e) {
			// Nada
			e.printStackTrace();
		}
		guardarFic( l, "numeros.txt" );
	}
	
	private static void guardarFic( ArrayList<Integer> l, String fic ) {
		try {
			PrintStream ps = new PrintStream( fic );
			for (int num : l) {
				ps.println( num );
			}
			ps.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog( null, "Fichero no se ha podido crear" );
		}
	}
	
	private static void implicitas() {
		// Pedir dos números al usuario y sacar las operaciones básicas
		// Hasta -1 acabar
		while (true) {
			try {
				int n1 = pedirNumero( "Introduce número 1:" );
				if (n1==-1) {
					break;
				}
				pedirSegundo( n1 );
			//} catch (NumberFormatException | ArithmeticException e) {
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog( null, "Número no válido", "Error", JOptionPane.ERROR_MESSAGE );
				e.printStackTrace();
			} catch (ArithmeticException e) {
				JOptionPane.showMessageDialog( null, "No se puede dividir por cero", "Error", JOptionPane.ERROR_MESSAGE );
			} catch (RuntimeException e) { // Solo puede ir al final de las específicas
				// TODO
			}
		}
	}
	
	private static void pedirSegundo( int primerNumero ) {
		int n2 = pedirNumero( "Introduce número 2:" );
		calcular( primerNumero, n2 );
	}
	
	private static void calcular( int n1, int n2 ) {
		JOptionPane.showMessageDialog( null, 
				n1 + " + " + n2 + " = " + (n1+n2) + "\n" +
				n1 + " - " + n2 + " = " + (n1-n2) + "\n" +
				n1 + " * " + n2 + " = " + (n1*n2) + "\n" +
				n1 + " / " + n2 + " = " + (n1/n2) + "\n"
			);
	}
	
	
	private static int pedirNumero( String mens ) {
		String resp = JOptionPane.showInputDialog( mens );
		return Integer.parseInt( resp );
	}
	
}
