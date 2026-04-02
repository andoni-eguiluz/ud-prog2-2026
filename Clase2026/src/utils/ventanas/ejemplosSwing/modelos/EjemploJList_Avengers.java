package utils.ventanas.ejemplosSwing.modelos;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** Ejemplo de uso de JList con modelo de datos por defecto
 * (ejemplo también de etiqueta en glasspane - panel cristal)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
@SuppressWarnings("serial")
public class EjemploJList_Avengers extends JFrame {
	private JList<Avenger> lAvengers;
	private DefaultListModel<Avenger> mAvengers;
	private JLabel lMensaje;
	private JLabel lHora;
	
	boolean sigue = true;
	
	public EjemploJList_Avengers() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle( "Avengers!" );
		setSize( 800, 600 );
		lAvengers = new JList<>();
		mAvengers = new DefaultListModel<Avenger>();
		mAvengers.addElement( new Avenger( "Black Widow", 100 ) );
		mAvengers.addElement( new Avenger( "Hawkeye", 100 ) );
		mAvengers.addElement( new Avenger( "Captain America", 1000 ) );
		mAvengers.addElement( new Avenger( "Thor", 5000 ) );
		mAvengers.addElement( new Avenger( "Hulk", 5000 ) );
		mAvengers.addElement( new Avenger( "Iron Man", 3000 ) );
		lAvengers.setModel( mAvengers );
		lAvengers.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		JScrollPane sp = new JScrollPane( lAvengers );
		getContentPane().add( sp, BorderLayout.CENTER );
		lMensaje = new JLabel( " " );
		getContentPane().add( lMensaje, BorderLayout.NORTH );
		JPanel pBotones = new JPanel();
		JButton bAnyadir = new JButton( "Añadir vengador aleatorio" );
		JButton bBorrar = new JButton( "Borra vengador seleccionado" );
		bAnyadir.setToolTipText( "<html><p><font color=\"#000\" " +
        "size=\"25\" face=\"Arial\">Este botón permite añadir<br />un coche con datos aleatorios<br />" +
        "</font></p></html>" );  // Ejemplo de añadir un tooltip maquetado en html
		pBotones.add( bAnyadir );
		pBotones.add( bBorrar );
		lHora = new JLabel();
		pBotones.add( lHora );
		getContentPane().add( pBotones, BorderLayout.SOUTH );
		// Ejemplo de uso de panel cristal
		JPanel panelCristal = new JPanel();
		setGlassPane( panelCristal );
		panelCristal.setVisible( true );  // Por defecto el panel cristal está oculto, hay que hacer esto
		panelCristal.setOpaque( false );  // Si fuera opaco taparía toda la ventana
		panelCristal.setLayout( null );   // El layout puede ser el que queramos. El panel funciona como un panel normal
		JLabel lOver = new JLabel( "Por encima!" );
		lOver.setOpaque( true );
		lOver.setForeground( Color.red );
		lOver.setBackground( Color.green );
		lOver.setHorizontalAlignment( JLabel.CENTER );
		lOver.setBounds( 200, 100, 150, 60 );
		panelCristal.add( lOver );
		// Eventos
		bAnyadir.addActionListener( new ActionListener() {
			Random r = new Random();
			@Override
			public void actionPerformed(ActionEvent e) {
				mAvengers.addElement( new Avenger( ""+((char) (r.nextInt(26)+65)), r.nextInt(100)));
			}
		});
		bBorrar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mAvengers.remove( lAvengers.getSelectedIndex() );
				lAvengers.setSelectedIndex( -1);
				lAvengers.repaint();
			}
		});
		lAvengers.addMouseMotionListener( new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println( "Moviendo en " + lAvengers.locationToIndex(e.getPoint()) );
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println( "Arrastando en " + e.getPoint() );
			}
		});
		lAvengers.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && lAvengers.getSelectedValue()!=null) {
					lMensaje.setText( "Vengador seleccionado: " + lAvengers.getSelectedValue().nombre );
				}
			}
		});
		lAvengers.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()>1 && lAvengers.getSelectedValue()!=null) {
					lMensaje.setText( "Doble click en: " + lAvengers.getSelectedValue().nombre );
				}
			}
		});
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sigue = false;
			}
		});
	}
	
	public static void main(String[] args) {
		EjemploJList_Avengers v = new EjemploJList_Avengers();
		v.setVisible( true );
	}
	
	

}

/** Ejemplo de clase para meter "cualquier cosa" en un JList
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
class Avenger {
	String nombre;
	int potencia;
	public Avenger(String nombre, int potencia) {
		super();
		this.nombre = nombre;
		this.potencia = potencia;
	}
	@Override
	public String toString() {
		return nombre + " " + potencia;
	}
	
}
