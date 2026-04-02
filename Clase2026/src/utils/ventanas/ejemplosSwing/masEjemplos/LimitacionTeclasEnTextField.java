package utils.ventanas.ejemplosSwing.masEjemplos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Ejemplo limitación de teclas en JTextField
 * @author andoni.eguiluz at ingenieria.deusto.es
 *
 */
public class LimitacionTeclasEnTextField {
	
	/** Método principal
	 * @param s	No utilizado
	 */
	public static void main( String[] s ) {
		// Prueba de text field con entrada limitada
		pruebaLimitarJTextField();
	}
	
	private static JTextField tfTexto;
	private static void pruebaLimitarJTextField() {
		JFrame f = new JFrame( "Prueba cuadro de texto limitado" );
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setSize(300,80);
		JLabel lTest = new JLabel( "Introduce texto limitado (a 3 cars.):" );
		f.add( lTest, BorderLayout.NORTH );
		tfTexto = new JTextField( "", 10 );
		f.add( tfTexto, BorderLayout.SOUTH );
		f.setVisible( true );
		tfTexto.addKeyListener( new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {
				if (tfTexto.getText().length() >= 3)
					// Aquí se controla que la longitud no sea mayor que 3
					// Se podría controlar cualquier otra cosa
					ev.consume(); // Si se consume el evento, no llega
						// al proceso estándar del cuadro de texto, y
						// por tanto no llega a introducirse en el cuadro
			}
		});
	}
	
}


