package tema4.resueltos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PruebaPelis {

	public static void main(String[] args) {
		ArrayList<Peli> l = new ArrayList<>();
		l.add( new Peli( "Oppenheimer", 2023 ) );
		l.add( new Peli( "Barbie", 2023 ) );
		l.add( new Peli( "Todo a la vez en todas partes", 2022 ) );
		l.add( new Peli( "CODA", 2021 ) );
		System.out.println( l );
		guardarListaObjetos( l, "pelis.dat" );
		System.out.println( l );
		ArrayList<Peli> l2 = cargarListaObjetos( "pelis.dat" );
		System.out.println( l2 );
		
		// Probar ficheros de texto
		guardarListaTexto( l, "pelis.txt" );
		System.out.println( l );
		ArrayList<Peli> l3 = cargarListaTexto( "pelis.txt" );
		System.out.println( l3 );
	}
	
	private static void guardarListaObjetos( ArrayList<Peli> l, String nombre ) {  // escribir en fichero
		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( nombre ) );
			oos.writeObject( l );
			oos.close();
		} catch (Exception e) {
			// System.out.println( "Error en fichero" );
			e.printStackTrace();
		}
	}
	
	// Carga fichero binario y devuelve la lista de pelis. Si hay error devuelve null
	@SuppressWarnings("unchecked")
	private static ArrayList<Peli> cargarListaObjetos( String nombre ) {   // leer de fichero
		try {
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream( nombre ) );
			ArrayList<Peli> l = (ArrayList<Peli>) ois.readObject();
			ois.close();
			return l;
		} catch (Exception e) {
			return null;  // Error en lectura
		}
	}

	// Guarda lista de pelis en fichero de texto, saca info de error en consola si ocurre alguno
	// Formato: una peli por línea, en formato nombre,año
	private static void guardarListaTexto( ArrayList<Peli> l, String nombre ) {
		try {
			PrintStream ps = new PrintStream( nombre );
			// ps.println( l );
			for (Peli peli : l) {
				ps.println( peli.aLineaCSV() );
			}
			ps.close();
		} catch (FileNotFoundException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	// Lee lista de pelis y la devuelve de un fichero de texto
	// Si hay error devuelve null
	private static ArrayList<Peli> cargarListaTexto( String nombre ) {
		try {
			ArrayList<Peli> l = new ArrayList<>();
			Scanner scanner = new Scanner( new FileInputStream( nombre ) );
			int cont = 1;
			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				// Construir peli y añadirla a l
				try {
					Peli nueva = Peli.creaPeliDesdeLineaCSV(linea);
					l.add(nueva);
				} catch (NullPointerException | NoSuchElementException | 
						NumberFormatException | ArithmeticException e) {
					System.out.println( "Error en línea " + cont + ": " + linea );
				// } catch (RuntimeException e) {
				}
				cont++;
			}
			scanner.close();
			return l;
		} catch (Exception e) {
			return null;
		}
	}
	
}
