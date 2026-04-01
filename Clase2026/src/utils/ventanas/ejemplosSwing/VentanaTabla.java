package utils.ventanas.ejemplosSwing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;

public class VentanaTabla extends JFrame {
	JTable tabla;
	JButton bConfirmar = new JButton( "Fin!" );
	public VentanaTabla() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize ( 400, 300 );
		setTitle( "Tabla" );
		Vector<Vector<Object>> datos = new Vector<>();
		datos.add( new Vector<>( Arrays.asList( "Andoni", 35, true ) ) );
		datos.add( new Vector<>( Arrays.asList( "Emilio", 37, false ) ) );
		datos.add( new Vector<>( Arrays.asList( "Ana", 25, true ) ) );
		Vector<String> cabs = new Vector<>( Arrays.asList( "Nombre", "Edad", "Activo?" ) );
		tabla = new JTable( datos, cabs );
		// BorderLayout
		getContentPane().add( new JScrollPane( tabla), BorderLayout.CENTER );
		JPanel pSur = new JPanel();
		pSur.add( new JLabel ("Confirmar?") );
		pSur.add( bConfirmar );
		getContentPane().add( pSur, BorderLayout.SOUTH );
		
		bConfirmar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaTabla.this.dispose();
			}
		});
	}
}
