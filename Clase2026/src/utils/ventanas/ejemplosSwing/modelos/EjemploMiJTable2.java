package utils.ventanas.ejemplosSwing.modelos;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/** Ejemplo de cómo adaptar JTable para sacar una tabla sobre cualquier arraylist de objetos que queramos representar en una tabla,
 * una fila por cada elemento 
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class EjemploMiJTable2 {
	private static ArrayList<Jugador> listaJugadores;
	public static void main(String[] args) {
		init(); // Crear datos de prueba
		// Introducir datos en el modelo de datos de la tabla
    	MiTableModel datos = new MiTableModel( Jugador.NOMBRES_COLUMNAS, Jugador.COLUMNAS_EDITABLES );
    	for (Jugador jugador : listaJugadores) {
    		datos.insertar( jugador );
    	}
    	// Crear la tabla (partiendo del modelo de datos) y el scrollpane
    	final MiJTable tabla = new MiJTable( datos );
        JScrollPane scrollPane = new JScrollPane( tabla );
        //Crear e inicializar la ventana con la tabla central
        JFrame frame = new JFrame( "Ejemplo de jugadores en MiJTable" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( scrollPane, "Center" );
        // Ajusta el tamaño de la ventana y la muestra
        frame.pack();
        frame.setVisible(true);
	}
	
	private static void init() {
		listaJugadores = new ArrayList<>();
		listaJugadores.add( new Jugador( "Andoni", 30, 3) );
		listaJugadores.add( new Jugador( "Marta", 35, 5) );
		listaJugadores.add( new Jugador( "Luis", 28, 2) );
		listaJugadores.add( new Jugador( "María", 52, 7) );
		listaJugadores.add( new Jugador( "Aritz", 40, 4) );
		Jugador j = new Jugador( "Iratxe", 15, 1);
		j.setEnActivo( false );
		listaJugadores.add( j );
	}
}

// Clase de ejemplo para usar en la JTable 
// Se podría hacer con cualquier otra simplemente implementando el interfaz DatoParaTabla
class Jugador implements DatoParaTabla {
	
	public static final String[] NOMBRES_COLUMNAS = { "Nombre", "Puntos", "Partidas", "¿En activo?" };
	public static final boolean[] COLUMNAS_EDITABLES = { false, true, true, true };
	
	private String nombre;
	private int puntos;
	private int partidas;
	private boolean enActivo;
	private String otros;  // Este atributo no se usa
	public Jugador( String nombre, int puntos, int partidas ) {
		this.nombre = nombre;
		this.puntos = puntos;
		this.partidas = partidas;
		enActivo = true;
		otros = "";
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public int getPartidas() {
		return partidas;
	}
	public void setPartidas(int partidas) {
		this.partidas = partidas;
	}
	public boolean isEnActivo() {
		return enActivo;
	}
	public void setEnActivo(boolean enActivo) {
		this.enActivo = enActivo;
	}
	public String getOtros() {
		return otros;
	}
	public void setOtros(String otros) {
		this.otros = otros;
	}
	
	// Métodos de interfaz DatoParaTabla
	// getNumColumnas -> Cuántas columnas quiero en la tabla
	@Override
	public int getNumColumnas() {
		return 4;
	}

	// getValor -> Valor que quiero que aparezca en la columna indicada (de 0 a n-1)
	@Override
	public Object getValor(int col) {
		if (col==0) return nombre;
		else if (col==1) return new Integer(puntos);
		else if (col==2) return new Integer(partidas);
		else return new Boolean(enActivo);
	}

	// setValor -> Valor que quiero que modificar en la columna indicada (de 0 a n-1)
	@Override
	public void setValor(Object value, int col) {
		if (col==0) nombre = (String) value;
		else if (col==1) puntos = (Integer) value;
		else if (col==2) partidas = (Integer) value;
		else enActivo = (Boolean) value;
	}
	
}