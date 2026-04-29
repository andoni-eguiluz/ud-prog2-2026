package tema4.basicos.conceptoFicheros;

import java.io.IOException;

public class EjemploFicheroVentas {

	public static void main(String[] args) {
		SistemaVentas sv = new SistemaVentas();
		// La primera vez hacer esto que carga datos de prueba y crea el fichero:
		// pruebaGuardadoFicheroBinario( sv );
		// La segunda vez se puede hacer esto para probar si se han guardado bien los datos:
		pruebaCargaFicheroBinario( sv );
		// Lo mismo con fichero de texto
		pruebaGuardadoFicheroTexto( sv );
		pruebaCargaFicheroTexto( sv );
		System.out.println( "Final" );
	}
	
	// Carga datos desde un fichero binario
	private static void pruebaCargaFicheroBinario( SistemaVentas sv ) {
		boolean ok = sv.cargaFicheroBinario( "prueba.dat" );
		if (!ok) {
			System.out.println( "Ha ocurrido un error de carga" );
		}
		System.out.println( "Lista de ventas: " + sv.getListaVentas() );
	}

	// Crea datos de prueba y los guarda en un fichero binario 
	private static void pruebaGuardadoFicheroBinario( SistemaVentas sv ) {
		sv.init();  // Datos de prueba
		// Guardar a fichero?
		try {
			sv.guardaFicheroBinario( "prueba.dat" );
		} catch (IOException exc) {
			// Código de gestión del error
			exc.printStackTrace();
			System.out.println( "No ha podido guardarse el fichero: habría que revisar el nombre" );
		}
		// Sigue
		System.out.println( "Lista de ventas: " + sv.getListaVentas() );
	}
	
	// Carga datos desde un fichero de texto
	private static void pruebaCargaFicheroTexto( SistemaVentas sv ) {
		boolean ok = sv.cargaFicheroTexto( "prueba.txt" );
		if (!ok) {
			System.out.println( "Ha ocurrido un error de carga" );
		}
		System.out.println( "Lista de ventas: " + sv.getListaVentas() );
	}

	// Crea datos de prueba y los guarda en un fichero de texto 
	private static void pruebaGuardadoFicheroTexto( SistemaVentas sv ) {
		sv.init();  // Datos de prueba
		// Guardar a fichero?
		try {
			sv.guardaFicheroTexto( "prueba.txt" );
		} catch (IOException exc) {
			// Código de gestión del error
			exc.printStackTrace();
			System.out.println( "No ha podido guardarse el fichero: habría que revisar el nombre" );
		}
		// Sigue
		System.out.println( "Lista de ventas: " + sv.getListaVentas() );
	}



}
