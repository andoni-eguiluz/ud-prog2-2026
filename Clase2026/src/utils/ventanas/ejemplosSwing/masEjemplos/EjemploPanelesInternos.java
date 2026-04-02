package utils.ventanas.ejemplosSwing.masEjemplos;

import javax.swing.*;

/** Ejemplo de JInternalFrames
 * @author andoni.eguiluz at deusto.es
 */
public class EjemploPanelesInternos {
	
	/** Método principal
	 * @param s	No utilizado
	 */
	public static void main( String[] s ) {
		pruebaJInternalFrames();
	}
	
	private static void pruebaJInternalFrames() {
		JFrame miJFramePrincipal = new JFrame();
		miJFramePrincipal.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		miJFramePrincipal.setTitle ( "Ventana con internal frames" );
		miJFramePrincipal.setSize( 640, 400 );
		JDesktopPane fondo = new JDesktopPane();  // Componente principal donde van los JInternalFrames
		miJFramePrincipal.setContentPane( fondo );
		
		JInternalFrame miFrameInterno1 = new JInternalFrame();
		miFrameInterno1.setTitle( "JFinterno 1 con iconos" );
		miFrameInterno1.setIconifiable( true );
		miFrameInterno1.setMaximizable( true );
		miFrameInterno1.setClosable( true );
		miFrameInterno1.setBounds( 10, 10, 280, 160);  // setBounds = setSize + setLocation
		JInternalFrame miFrameInterno2 = new JInternalFrame();
		miFrameInterno2.setTitle( "JFinterno 2 sin decoración (por defecto)" );
		// miFrameInterno2.setIconifiable( false );
		// miFrameInterno2.setMaximizable( false );
		// miFrameInterno2.setClosable( true );
		miFrameInterno2.setBounds( 300, 50, 280, 160);  // setBounds = setSize + setLocation
		
		// Añadimos los JInternalFrames al desktop
		fondo.add( miFrameInterno1 );
		fondo.add( miFrameInterno2 );
		
		miFrameInterno1.setVisible( true );
		miFrameInterno2.setVisible( true );
		
		miJFramePrincipal.setVisible( true );
	}
	
}


