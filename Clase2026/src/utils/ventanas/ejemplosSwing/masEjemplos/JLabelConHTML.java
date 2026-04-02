package utils.ventanas.ejemplosSwing.masEjemplos;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

/** Ejemplo de cómo JLabel permite un pequeño subset de HTML 
 * que permite cosas como saltos de línea (que con texto normal no se permiten)
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class JLabelConHTML {

	public static void main(String[] args) {
		JFrame v = new JFrame( "Ejemplo de JLabel en HTML" );
		v.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		v.setSize( 800, 300 );
		Font tipoLetra = new Font( "Arial", Font.PLAIN, 24 ); 
		JLabel lNormal = new JLabel( "Texto normal\n Los saltos de línea no ocurren" );
		lNormal.setFont( tipoLetra );
		JLabel lHtml = new JLabel( "<html>Texto html<br/>Puede haber saltos de línea<br/>Y otras cosas como <b>negritas</b> o <i>itálicas</i></html>" );  // salto de línea = <br/>
		lHtml.setFont( tipoLetra );
		v.add( lNormal, BorderLayout.NORTH );
		v.add( lHtml, BorderLayout.SOUTH );
		v.setVisible( true );
	}

}
