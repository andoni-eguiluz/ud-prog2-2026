package utils.ventanas.ventanaConsola;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

public class PruebaConsolaVentana {

	/** Main de prueba
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
			Object resp = JOptionPane.showInputDialog( null, "Selecciona prueba", "Inicio",
					JOptionPane.QUESTION_MESSAGE, null, 
					new Object[] { "Ficheros", "Salida out+err", "Salida solo out" }, "Ficheros" );
			System.out.println( resp );
			if ("Ficheros".equals(resp)) {
				ConsolaVentana.lanzarConsolaEnVentana( new PrintStream( "test_out.txt" ), new PrintStream( "test_err.txt" ) );
				System.out.println( "Prueba consola salida" );
				for (int i=0; i<1000; i++)
					System.out.println( "Prueba números consola salida " + i );
				System.err.println( "Prueba consola error" );
				System.err.println( "Prueba consola error 2" );
			} else if ("Salida out+err".equals(resp)) {
				ConsolaVentana.lanzarConsolaEnVentana();
				System.out.println( "Prueba tilde: áéíóúñÑÁÉÍÓÚ" );
				System.err.println( "Prueba consola error" );
			} else if ("Salida solo out".equals(resp)) {
				ConsolaVentana.lanzarConsolaEnVentana( null, null );
				System.out.println( "El tipo de letra es Hack (cargado dinámicamente desde Java)" );
				System.out.println( "Prueba de tildes: áéíóúñÑÁÉÍÓÚ" );
				System.err.println( "Prueba consola error" );
			}
	}
	
	
}
