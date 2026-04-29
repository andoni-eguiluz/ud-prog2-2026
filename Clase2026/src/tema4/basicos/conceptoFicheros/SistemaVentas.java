package tema4.basicos.conceptoFicheros;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaVentas {
	private ArrayList<Venta> listaVentas = new ArrayList<>();

	public ArrayList<Venta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(ArrayList<Venta> listaVentas) {
		this.listaVentas = listaVentas;
	}
	
	/** Inicializar el sistema de ventas con datos de ejemplo
	 */
	public void init() {
		listaVentas.clear();
		listaVentas.add( new Venta( "1", 200.0 ) );
		listaVentas.add( new Venta( "1", 300.0 ) );
		listaVentas.add( new Venta( "2", 150.0 ) );
		listaVentas.add( new Venta( "3", 50.0 ) );
	}
	
	/** Guarda los datos del sistema de ventas en un fichero binario
	 * @param nombreFic	Nombre del fichero en el que se va a guardar
	 * @throws IOException	Error si hay algún problema en la creación del fichero o en el guardado de datos
	 */
	public void guardaFicheroBinario( String nombreFic ) throws IOException {
		ObjectOutputStream salida = new ObjectOutputStream( new FileOutputStream( nombreFic ) );
		salida.writeObject( listaVentas );
		salida.close();
	}
	
	/** Carga el fichero binario de ventas
	 * @param nombreFic	Nombre del fichero
	 * @return	true si la carga ha sido correcta, false si no se ha podido cargar
	 */
	@SuppressWarnings("unchecked")  // Anotación que evita el warning de la línea 46  (se puede no poner)
	public boolean cargaFicheroBinario( String nombreFic ) {
		try {
			ObjectInputStream entrada = new ObjectInputStream( new FileInputStream( nombreFic ) );
			listaVentas = (ArrayList<Venta>) entrada.readObject();
			entrada.close();
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}
	
	/** Guarda los datos del sistema de ventas en un fichero de texto
	 * @param nombreFic	Nombre del fichero en el que se va a guardar
	 * @throws IOException	Error si hay algún problema en la creación del fichero o en el guardado de datos
	 */
	public void guardaFicheroTexto( String nombreFic ) throws IOException {
		PrintStream salida = new PrintStream( nombreFic );
		// A un fichero de texto ya no se puede volcar una lista... hay que convertir todo a texto para sacarlo
		// y el texto tiene que ser fácil de luego interpretar leyendo.
		// Por ejemplo aquí lo sacamos venta a venta, una línea por cada venta, 
		for (Venta venta : listaVentas) {
			salida.println( venta.toLinea() );
		}
		salida.close();
	}
	
	/** Carga el fichero de texto de ventas
	 * @param nombreFic	Nombre del fichero
	 * @return	true si la carga ha sido correcta, false si no se ha podido cargar
	 */
	public boolean cargaFicheroTexto( String nombreFic ) {
		try {
			Scanner entrada = new Scanner( new FileInputStream( nombreFic ) );
			// De un fichero de texto habrá que leer línea a línea y convertir cada línea en la venta correspondiente
			listaVentas.clear();  // Inicializa la lista
			while (entrada.hasNextLine()) {
				String linea = entrada.nextLine();
				Venta venta = new Venta( linea );  // Convierte la línea a venta
				listaVentas.add( venta );
			}
			entrada.close();
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}
	
}
