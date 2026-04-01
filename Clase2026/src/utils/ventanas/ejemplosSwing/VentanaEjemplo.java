package utils.ventanas.ejemplosSwing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VentanaEjemplo extends JFrame {
	
	private JTextField tfCuadro = new JTextField( "<Introduce aquí>", 20 );
	private JButton bOk = new JButton( "Ok" );
	private JButton bCancel = new JButton( "Cancelar" );
	private JTextArea taEntrada = new JTextArea( 10, 50 );
	private JSlider slider = new JSlider( 0, 10, 2 );
	private JProgressBar pbProgreso = new JProgressBar( 0, 100 );
	private JRadioButton rb1 = new JRadioButton( "Opción 1" );
	private JRadioButton rb2 = new JRadioButton( "Opción 2" );
	private Vector<String> datos = new Vector<>( Arrays.asList( "Hola", "Qué tal", "Bye" ) );
	private JComboBox<String> cbTextos = new JComboBox<>( datos );
	
	public VentanaEjemplo() {
		// 1. Configurar la ventana
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.setSize( 800, 600 );
		this.setLocation( 2000, 100 );
		this.setTitle( "Ventana de ejemplo" );
		
		// 2. Crear componentes y asociarlos a la ventana en contenedores
		this.getContentPane().add( new JLabel( "Etiqueta" ) );
		this.getContentPane().add( tfCuadro );
		
		// LAYOUT -- formato - por defecto BorderLayout
		// componente en el centro solo puede haber uno
		// Cada contenedor tiene un solo layout
		getContentPane().setLayout( new FlowLayout() );
		
		// Más componentes!
		getContentPane().add( bOk );
		getContentPane().add( bCancel );
		getContentPane().add( slider );
		getContentPane().add( pbProgreso );
		getContentPane().add( rb1 );
		getContentPane().add( rb2 );
		getContentPane().add( cbTextos );
		getContentPane().add( new JScrollPane( taEntrada ) );

		// Más layouts
		getContentPane().setLayout( new GridLayout( 7, 2 ) );
		getContentPane().setLayout( new BoxLayout( getContentPane(), BoxLayout.Y_AXIS ));
		getContentPane().setLayout( new BorderLayout() );
		
		// Paneles
		JPanel pInferior = new JPanel();
		JPanel pSuperior = new JPanel();
		JPanel pEste = new JPanel();
		JPanel pOeste = new JPanel();
		JPanel pCentro = new JPanel();

		getContentPane().add( pInferior, BorderLayout.SOUTH );
		getContentPane().add( pSuperior, BorderLayout.NORTH );
		getContentPane().add( pEste, BorderLayout.EAST );
		getContentPane().add( pOeste, BorderLayout.WEST );
		getContentPane().add( pCentro, BorderLayout.CENTER );

		pInferior.add( new JLabel( "Confirmar?" ) );
		pInferior.add( bOk );
		pInferior.add( bCancel );
		pSuperior.add( new JLabel( "Entrada:" ) );
		pSuperior.add( tfCuadro );
		pEste.setLayout( new BoxLayout( pEste, BoxLayout.Y_AXIS ) );
		pEste.add( rb1 );
		pEste.add( rb2 );
		pCentro.add( new JScrollPane( taEntrada ) );
		pCentro.add( slider );
		pCentro.add( pbProgreso );
		pOeste.add( cbTextos );
		
		// 3. Configuración de componentes
		bOk.setBackground( Color.RED );
		taEntrada.setEditable( true );

		// 4. Gestión de eventos
		bCancel.addActionListener( new EscuchadorBotonCancel() );
		bOk.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taEntrada.append( "Pulsado ok\n");
			}
		});
		cbTextos.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taEntrada.append( "Seleccionado combo" );
				bCancel.setEnabled( false );
			}
		});
		slider.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				taEntrada.append( "Cambiado slider " + slider.getValue() + "\n" );
			}
		});
		
		pEste.addMouseListener( new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				taEntrada.append( "ENTERED: " + e.getPoint() + "\n" );
			}
			@Override
			public void mousePressed(MouseEvent e) {
				taEntrada.append( "PRESSED: " + e.getPoint() + "\n" );
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				taEntrada.append( "RELEASED: " + e.getPoint() + "\n" );
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				taEntrada.append( "CLICKED: " + e.getPoint() + "\n" );
			}
			@Override
			public void mouseExited(MouseEvent e) {
				taEntrada.append( "EXITED: " + e.getPoint() + "\n" );
			}
		});
		
		pEste.addMouseMotionListener( new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				taEntrada.append( "MOVED: " + e.getPoint() + "\n" );
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				taEntrada.append( "DRAG: " + e.getPoint() + "\n" );
			}
		});
		
		JButton bTabla = new JButton( "Saca tabla" );
		pInferior.add( bTabla );
		bTabla.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaTabla vent2 = new VentanaTabla();
				vent2.setLocation( 2100, 300 );
				vent2.setVisible( true );
			}
		});
		
	}
	
	class EscuchadorBotonCancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println( "Cancel");
			taEntrada.append( "Botón cancel pulsado!\n");
		}
	}
	
}
