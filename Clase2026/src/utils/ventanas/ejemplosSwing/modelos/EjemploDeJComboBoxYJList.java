package utils.ventanas.ejemplosSwing.modelos;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;

/** Ejemplo de jcombobox y jlist
 * @author andoni.eguiluz.moran
 */
@SuppressWarnings("serial")
public class EjemploDeJComboBoxYJList extends JFrame {
	
	public static void main(String[] args) {
		EjemploDeJComboBoxYJList v = new EjemploDeJComboBoxYJList();
		v.setVisible( true );
	}

	public EjemploDeJComboBoxYJList() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 400, 200 );
		setLocationRelativeTo( null );
		// setAlwaysOnTop( true );
		
		String opciones[] = { "Rojo", "Verde", "Azul", "Amarillo" };
		final JComboBox<String> cPrueba = new JComboBox<String>( opciones );
		cPrueba.setEditable( false );
		getContentPane().add( cPrueba, BorderLayout.NORTH );
		
		DefaultComboBoxModel<String> datos = new DefaultComboBoxModel<>( opciones );
		cPrueba.setModel( datos );
		datos.addElement( "Negro" );
		
		JList<String> listaColores = new JList<String>( opciones );
		JScrollPane pScroll = new JScrollPane( listaColores );
		getContentPane().add( pScroll, BorderLayout.CENTER );
		
		getContentPane().add( new JLabel( "Arriba JComboBox, en el centro JList"), BorderLayout.SOUTH );
		
		// Eventos de combo y lista
		cPrueba.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println( "Seleccionado elemento en el combo: " + cPrueba.getSelectedItem() );
			}
		});
		listaColores.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {  // Para evitar eventos repetidos
					System.out.println( "Seleccionado elemento en la lista: " + listaColores.getSelectedValue() );
				}
			}
		});
	}
	
}
