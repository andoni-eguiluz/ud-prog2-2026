package tema1b.resueltos.ej1b9;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GestionArchivo {
	private static ArrayList<Multimedia> lista;
	public static void main(String[] args) {
		lista = new ArrayList<>();
		menu();
	}
	
	private static void menu() {
		boolean acabar = false;
		do {
			Object resp = JOptionPane.showInputDialog( null, "Elige opción", "¿Qué quieres hacer?", JOptionPane.QUESTION_MESSAGE, null,
					new Object[] { "Añadir", "Modificar", "Buscar", "Borrar", "Ver" }, "Añadir" );
			if ("Añadir".equals(resp)) {
				anyadir();
			} else if ("Modificar".equals(resp)) {
				modificar();
			} else if ("Buscar".equals(resp)) {
				buscar();
			} else if ("Borrar".equals(resp)) {
				borrar();
			} else if ("Ver".equals(resp)) {
				ver();
			} else {
				acabar = true;
			}
		} while (!acabar);
	}

		private static String elegir( String mensaje ) {
			Object resp = JOptionPane.showInputDialog( null, mensaje, "Elige opción", JOptionPane.QUESTION_MESSAGE, null,
					new Object[] { "Canción", "Película", "Videojuego" }, "Canción" );
			return (String) resp;
		}
	
	private static void anyadir() {
		String resp = elegir( "¿Qué recurso añadir?" );
		Multimedia multimedia = null;
		if ("Canción".equals(resp)) {
			multimedia = new Cancion( "", "" );
		} else if ("Película".equals(resp)) {
			multimedia = new Pelicula( "", "" );
		} else if ("Videojuego".equals(resp)) {
			multimedia = new Videojuego( "", false );
		}
		if (multimedia != null) {
			multimedia.pedir();
			lista.add( multimedia );
			System.out.println( "Añadido nuevo elemento: " + multimedia );
		}
	}
	
	private static void modificar() {
		String resp = JOptionPane.showInputDialog( null, "Introduce nombre recurso a modificar:" );
		int posi = lista.indexOf( new Multimedia(resp) );
		if (posi != -1) {
			lista.get( posi ).pedir();
			JOptionPane.showMessageDialog( null, "El recurso " + resp + " ha sido modificado." );
		}
	}

	private static void borrar() {
		String resp = JOptionPane.showInputDialog( null, "Introduce nombre recurso a borrar:" );
		int indiceBusq = -1;
		for (int i=0; i<lista.size(); i++) {
			if (lista.get(i).getNombre().equals(resp)) {
				indiceBusq = i;
				break;
			}
		}
		int posi = lista.indexOf( new Multimedia(resp) );
		if (posi != -1) {
			lista.remove( posi );
			JOptionPane.showMessageDialog( null, "El recurso " + resp + " ha sido borrado." );
		}
	}
	
	private static void ver() {
		String visu = "";
		for (Multimedia m : lista) {
			if (m instanceof Videojuego) {
				Videojuego v = (Videojuego) m;
				if (!v.isRegistrado()) {
					continue;  // No aparece
				}
			}
			visu += m.toString() + "\n";
		}
		JOptionPane.showMessageDialog( null, visu );
	}
	
	private static void buscar() {
		String resp = JOptionPane.showInputDialog( null, "Introduce nombre elemento a buscar:" );
		JOptionPane.showMessageDialog( null, "El recurso " + resp 
				+ (lista.contains( new Multimedia(resp) ) ? " SÍ " : " NO ")
				+ "está en la lista archivada" );
	}
	
}
