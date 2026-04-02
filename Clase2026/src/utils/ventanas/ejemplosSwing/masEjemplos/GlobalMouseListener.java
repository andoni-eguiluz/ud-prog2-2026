package utils.ventanas.ejemplosSwing.masEjemplos;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** Ejemplo de escuchar al ratón de forma "local" o "global"
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class GlobalMouseListener {
	public static void main(String[] args) {
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setSize( 600, 400 );
		f.add( new JScrollPane( new JTextArea(10,20) ), BorderLayout.EAST );
		// Esto coge los eventos de la ventana pero no de un componente de dentro (la textarea)
		f.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println( "(Local) Released en: " + e.getPoint() );
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println( "(Local) Pressed en: " + e.getPoint() );
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println( "(Local) Clicked en: " + e.getPoint() );
			}
		});
		f.setVisible( true );
		// Escuchador global de swing: coge TODOS los eventos (que se indicen en la máscara)
		Toolkit tk = Toolkit.getDefaultToolkit();
	    long eventMask = AWTEvent.MOUSE_EVENT_MASK; // + AWTEvent.MOUSE_MOTION_EVENT_MASK; // + AWTEvent.KEY_EVENT_MASK;
        tk.addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent e) {
                System.out.println( e );
            }
        }, eventMask);
	}
}
