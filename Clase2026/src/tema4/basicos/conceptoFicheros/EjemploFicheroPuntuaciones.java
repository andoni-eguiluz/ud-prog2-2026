package tema4.basicos.conceptoFicheros;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EjemploFicheroPuntuaciones {
	public static void main(String[] args) {
		try {
			pruebaSalida();
			pruebaEntrada();
		} catch (Exception e) {
			e.printStackTrace();
			// Algo más si procede
		}
		System.out.println( "Final" );
		// Las pruebas en fichero de texto
		pruebaSalidaTexto();
		pruebaEntradaTexto();
	}
	
	private static void pruebaSalida() {
		SistemaPuntuaciones sp = new SistemaPuntuaciones();
		sp.crearDatosPrueba();
		try {
			sp.guardarEnFicheroBinario( "punts.dat" );
		} catch (FileNotFoundException e) {
			System.out.println( "Error en el nombre del fichero" );
			// TODO podríamos cambiar nombre del fichero e intentarlo otra vez, o gestionar de cualquier otra forma
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println( "Error en guardado" );
			// TODO es cambiar nombre del fichero e intentarlo otra vez
		}
		System.out.println( "Salida de sistema (binario): " + sp );
	}
	
	private static void pruebaEntrada() {
		SistemaPuntuaciones sp = new SistemaPuntuaciones();
		boolean ok = sp.cargarDeFicheroBinario( "punts.dat" );
		if (!ok) {
			System.out.println( "No se ha podido hacer la lectura" );
		} 
		System.out.println( "Lectura de sistema (binario): " + sp );
	}
	
	private static void pruebaSalidaTexto() {
		SistemaPuntuaciones sp = new SistemaPuntuaciones();
		sp.crearDatosPrueba();
		try {
			sp.guardarEnFicheroTexto( "punts.txt" );
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println( "Error en guardado" );
		}
		System.out.println( "Salida de sistema (texto): " + sp );
	}
	
	private static void pruebaEntradaTexto() {
		SistemaPuntuaciones sp = new SistemaPuntuaciones();
		try {
			sp.cargarDeFicheroTexto( "punts.txt" );
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println( "Error en lectura" );
		}
		System.out.println( "Lectura de sistema (texto): " + sp );
	}
	
}
