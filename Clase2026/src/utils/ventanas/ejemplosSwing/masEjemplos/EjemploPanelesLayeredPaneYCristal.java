package utils.ventanas.ejemplosSwing.masEjemplos;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*; 
import java.awt.event.*;

/** Ejemplo de los paneles de una ventana swing LayeredPane y CristalPane
 * @author andoni
 */
public class EjemploPanelesLayeredPaneYCristal {

	private static JLabel grafico;
	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				crearYMostrarGUI();
			} 
		} );
		try {Thread.sleep( 2000 );} catch (Exception e) {}
		for (int x=0; x<400; x++) {
			try {Thread.sleep( 50 );} catch (Exception e) {}
			grafico.setLocation( x, 300 );
		}
    }
	
    static JFrame miVentana;
    static JPanel p1;
    static JPanel p2;
    static JPanel p3;
    static JPanel p4;
    static JPanel p5;
    static JPanel p6;
    static JPanel p7;
    static JLayeredPane lp;
    
    private static void crearYMostrarGUI() {
    	// Crear ventana inicial
        miVentana = new JFrame("Prueba de paneles de Swing");        
        // Acabar de crear y hacer visible ventana
        miVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        p1 = new JPanel();
        	p1.setName( "p1" );
			p1.setBorder( BorderFactory.createMatteBorder( 2, 5, 2, 5, Color.green ) );
			p1.setBounds( 10, 10, 400, 150 );
        p2 = new JPanel();
        	p2.setName( "p2" );
    		Border b2 = BorderFactory.createBevelBorder( BevelBorder.RAISED );
    		p2.setBorder( b2 );
			p2.setBounds( 30, 30, 400, 150 );
        p3 = new JPanel();
        	p3.setName( "p3" );
    		Border b3 = BorderFactory.createEtchedBorder();
    		p3.setBorder( b3 );
			p3.setBounds( 50, 50, 400, 150 );
		p4 = new JPanel();
			p4.setName( "p4" );
    		p4.setBorder( BorderFactory.createLineBorder( Color.red ) );
			p4.setBounds( 70, 70, 400, 150 );
		p5 = new JPanel();
			p5.setName( "p5" );
    		p5.setBorder( BorderFactory.createLoweredBevelBorder() );
			p5.setBounds( 90, 90, 400, 150 );
		p6 = new JPanel();
    		p6.setName( "p6" );
    		p6.setBorder( BorderFactory.createRaisedBevelBorder() );
			p6.setBounds( 110, 110, 400, 150 );
		p7 = new JPanel();
			p7.setName( "p7" );
    		Border b7 = BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Nombre de panel"),
                    BorderFactory.createEmptyBorder(5,5,5,5));
        		p7.setBorder( b7 );
    		p7.setBounds( 130, 130, 400, 150 );
    			
		p1.add( new JLabel("[CRISTAL] Ejemplo de panel con borde matte (verde)") );
		p1.setOpaque( false );
		p2.add( new JLabel("[2] Ejemplo de panel con borde Bevel") );
		p3.add( new JLabel("[3] Ejemplo de panel con borde etched") );
		p4.add( new JLabel("[4] Ejemplo de panel con borde line (rojo)") );
		p5.add( new JLabel("[5] Ejemplo de panel con borde lowered") );
		p6.add( new JLabel("[6] Ejemplo de panel con borde raised") );
		p7.add( new JLabel("[7] Ejemplo de panel TRANSPARENTE con borde compuesto") );

		lp = new JLayeredPane();
		lp.setOpaque( false );
		miVentana.setLayeredPane( lp );
		p7.setOpaque( false );
        			
		lp.add( p7, new Integer(7) );
		lp.add( p6, new Integer(6) );
		lp.add( p5, new Integer(5) );
		lp.add( p4, new Integer(4) );
		lp.add( p3, new Integer(3) );
		lp.add( p2, new Integer(2) );
		
		p6.setToolTipText( "Ayuda panel 6");

		JPanel cristal = new JPanel();
		cristal.setLayout( null );
		miVentana.setGlassPane( cristal );
		cristal.add( p1 );
		grafico = new JLabel();
		grafico.setIcon( new ImageIcon( "bin/img/bicho.png" ));
		grafico.setBounds( 0, 300, 400, 150 );
		cristal.add( grafico, 0 );
		cristal.setOpaque( false );
		cristal.setVisible( true );
		
        miVentana.setSize( 600, 500 );
        miVentana.setLocationRelativeTo(null);  // Centrar en pantalla
        miVentana.setVisible(true);
        
        // Mover paneles
        MouseListener esc = new MouseAdapter() {
        	Point inicio = null;
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount()>1) { // Doble click - trae panel al frente
        			if (e.getSource()!=p1) {  // Excepto con el panel cristal, traer el panel clicado al frente cambiando el número de "capa"
						System.out.println( "Doble click en panel " + ((Container)(e.getSource())).getName() );
	        			lp.setLayer(  (Container)(e.getSource()), lp.highestLayer()+1 );
        			}
        		}
        	}
			@Override
			public void mousePressed(MouseEvent arg0) {
				inicio = arg0.getPoint();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (inicio != null) {
					if (!inicio.equals(arg0.getPoint())) {
						System.out.println( "Drag en panel " + ((Container)(arg0.getSource())).getName() );
						Container ventAMover = ((Container)(arg0.getSource()));
						Point origen = ventAMover.getLocation();
						origen.translate( 
							(int)(arg0.getX()-inicio.getX()),
							(int)(arg0.getY()-inicio.getY()) );
						ventAMover.setLocation( origen );
						inicio = null;
					}
				}
			}
		};
		// p1.addMouseListener( esc );
		// ...
		// p7.addMouseListener( esc );
		for (Container p : new Container[] { p1,p2,p3,p4,p5,p6,p7,miVentana.getContentPane() }) {
			p.addMouseListener( esc );
		}
    }
    
}
