package tema4.basicos.conceptoFicheros;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaPuntuaciones {
	private ArrayList<Puntuacion> listaPuntuaciones;
	public SistemaPuntuaciones() {
		listaPuntuaciones = new ArrayList<>();
	}
	public void anyadirPuntuacion( Puntuacion puntuacion ) {
		listaPuntuaciones.add( puntuacion );
	}
	
	public void crearDatosPrueba() {
		listaPuntuaciones.clear();
		listaPuntuaciones.add( new Puntuacion( "Andoni", 10 ) );
		listaPuntuaciones.add( new Puntuacion( "Beñat", 100 ) );
		listaPuntuaciones.add( new Puntuacion( "Lucía", 115 ) );
		listaPuntuaciones.add( new Puntuacion( "Elena", 80 ) );
	}
	
	/** Guarda los datos de punts en fichero
	 * @param nomFic	Nombre del fichero a guardar
	 * @throws Exception	Lanzada si hay cualquier error de escritura
	 */
	public void guardarEnFicheroBinario( String nomFic ) throws IOException {
		ObjectOutputStream salida = new ObjectOutputStream( new FileOutputStream( nomFic ) );
		salida.writeObject( listaPuntuaciones );
		salida.close();
	}
	
	/** Carga sistema de fichero binario
	 * @param nomFic	Nombre del fichero
	 * @return	true si la carga ha sido correcta, false en caso contrario
	 */
	@SuppressWarnings("unchecked")
	public boolean cargarDeFicheroBinario( String nomFic ) {
		try {
			ObjectInputStream entrada = new ObjectInputStream( new FileInputStream( nomFic ));
			listaPuntuaciones = (ArrayList<Puntuacion>) (entrada.readObject());
			entrada.close();
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
			System.out.println( "Fallo en lectura" );
			return false;
		}
	}

	// Métodos para ficheros de texto
	
	/** Guarda los datos de punts en fichero
	 * @param nomFic	Nombre del fichero a guardar
	 * @throws Exception	Lanzada si hay cualquier error de escritura
	 */
	public void guardarEnFicheroTexto( String nomFic ) throws IOException {
		PrintStream salida = new PrintStream( nomFic );
		// A un fichero de texto ya no se puede volcar una lista... hay que convertir todo a texto para sacarlo
		// y el texto tiene que ser fácil de luego interpretar leyendo.
		// Por ejemplo aquí lo sacamos puntuación a puntuación, una línea por cada puntuación: 
		for (Puntuacion punt : listaPuntuaciones) {
			salida.println( punt.toLinea() );
		}
		salida.close();
	}
	
	/** Carga sistema de fichero binario
	 * @param nomFic	Nombre del fichero
	 * @throws Exception	Lanzada si hay cualquier error de lectura
	 */
	public void cargarDeFicheroTexto( String nomFic ) throws IOException {
		Scanner entrada = new Scanner( new FileInputStream( nomFic ) );
		// De un fichero de texto habrá que leer línea a línea y convertir cada línea en la venta correspondiente
		listaPuntuaciones.clear();  // Inicializa la lista
		while (entrada.hasNextLine()) {
			String linea = entrada.nextLine();
			Puntuacion punt = Puntuacion.desdeLinea( linea );  // Convierte la línea a venta
			listaPuntuaciones.add( punt );
		}
		entrada.close();
	}

	@Override
	public String toString() {
		return listaPuntuaciones.toString();
	}

}
