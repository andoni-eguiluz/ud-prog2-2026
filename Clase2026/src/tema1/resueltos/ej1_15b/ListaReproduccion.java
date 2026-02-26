package tema1.resueltos.ej1_15b;

import java.util.ArrayList;

/**
 * @author andoni.eguiluz @ ingenieria.deusto.es
 *
 */
public class ListaReproduccion {

	private String nombre = "<Sin nombre>";
	private ArrayList<Cancion> listaCanciones = new ArrayList<Cancion>();
	private int cancionActual = 0; // Índice de la canción que está siendo reproducida (o la próxima a reproducir).

	/** Crea una lista de reproducción vacía - el nombre no debe ser nulo
	 * @param nombre	Nombre de la lista
	 */
	public ListaReproduccion(String nombre) {
		if (nombre ==null) {
			System.err.println( "Error en nombre de lista nulo" );
		} else {
			this.nombre = nombre;
		}
	}

	public String getNombre() {
		return nombre;
	}
	
	public ArrayList<Cancion> getListaCanciones() {
		return listaCanciones;
	}

	@Override
	public String toString() {
		return "ListaReproduccion " + nombre + ": " + listaCanciones + 
				(cancionActual>=0 && cancionActual<listaCanciones.size() ? " [Canción actual: " + listaCanciones.get(cancionActual) + "]" : "");
	}

//	funcionalidad para recuperar / añadir / quitar / buscar canciones en la lista, con control de error.
	
	/** Añade una canción al final de la lista
	 * @param cancion	Nueva canción
	 */
	public void anyadirCancion( Cancion cancion ) {
		listaCanciones.add( cancion );
	}
	
	/** Borra la canción indicada
	 * @param indice	Posición (0 a n-1) de la canción. Error si no existe
	 */
	public void borrarCancion( int indice ) {
		if (indice>=0 && indice<listaCanciones.size()) {
			listaCanciones.remove( indice );
		} else {
			System.err.println( "Posición incorrecta en borrado: " + indice );
		}
	}
	
	/** Recupera canción de la lista
	 * @param indice	Posición de la canción
	 * @return	Canción de esa posición, null si no existe
	 */
	public Cancion getCancion( int indice ) {
		if (indice>=0 && indice<listaCanciones.size()) {
			return listaCanciones.get( indice );
		} else {
			System.err.println( "Posición incorrecta en recuperación: " + indice );
			return null;
		}
	}
	
	/** Busca una canción en la lista
	 * @param nombre	Nombre de canción a buscar
	 * @return	Posición de la canción si existe, -1 si no existe
	 */
	public int buscarCancion( String nombre ) {
		for (int i=0; i<listaCanciones.size(); i++) {
			Cancion c = listaCanciones.get(i);
			if (c.getNombre().equals( nombre )) {
				return i;
			}
		}
		return -1;
	}

	/** Busca una canción en la lista
	 * @param cancion	Canción a buscar
	 * @return	Posición de la canción si existe, -1 si no existe
	 */
	public int buscarCancion( Cancion cancion ) {
		return listaCanciones.indexOf( cancion );
	}
	
	/** Modifica la posición de una canción en la lista. Si no se encuentra o la posición es imposible, error y no se cambia nada
	 * @param cancion	Canción a modificar
	 * @param nuevaPosicion	Nueva posición a incluir
	 */
	public void modificarPosicion( Cancion cancion, int nuevaPosicion ) {
		int posicionAntigua = listaCanciones.indexOf( cancion );
		if (posicionAntigua == -1 || nuevaPosicion < 0 || nuevaPosicion >= listaCanciones.size()) {
			System.err.println( "Posición incorrecta en modificación" );
			return;
		}
		Cancion quitada = listaCanciones.remove( posicionAntigua );
		listaCanciones.add( nuevaPosicion, quitada );

		// Plus: ver cómo cambia la canción en reproducción según el cambio
		if (posicionAntigua == cancionActual) {
			cancionActual = nuevaPosicion;
		} else if (posicionAntigua < nuevaPosicion) { // se desplazan a la izquierda los índices entre (posicionAntigua+1 .. nuevaPosicion)
			if (cancionActual > posicionAntigua && cancionActual <= nuevaPosicion) {
				cancionActual--;
			}
		} else { // posicionAntigua > nuevaPosicion	- se desplazan a la derecha los índices entre (nuevaPosicion .. posicionAntigua-1)
			if (cancionActual >= nuevaPosicion && cancionActual < posicionAntigua) {
				cancionActual++;
			}
		}
	}
	
	public double tiempoTotal() {
		double tiempo = 0.0;
		for (Cancion c : listaCanciones) {
			tiempo += c.getDuracion();
		}
		return tiempo;
	}

	public void reproducir() {
		if (cancionActual>=0 && cancionActual<listaCanciones.size()) {
			listaCanciones.get(cancionActual).reproducir();
		}
	}
	
	public void parar() {
		if (cancionActual>=0 && cancionActual<listaCanciones.size()) {
			listaCanciones.get(cancionActual).parar();
		}
	}

	/** Avanza el tiempo de reproducción si hay algo reproduciéndose y avanza de canción si se acaba. Si no, no hace nada.
	 * @param segundos	Segundos a avanzar
	 */
	public void avanzar( int segundos ) {
		while (segundos > 0 && cancionActual>=0 && cancionActual<listaCanciones.size() && listaCanciones.get(cancionActual).isEstado()) {
			double tiempoCancionAnterior  =listaCanciones.get(cancionActual).getPuntoReproduccion();
			Cancion c = listaCanciones.get(cancionActual);
			c.avanzar( segundos );
			if (c.compruebaFinal()) {  // La canción en curso se ha acabado, hay que continuar la siguiente
				int segundosAvanzados = (int) ((c.getDuracion() - tiempoCancionAnterior) * 60.0);
				segundos -= segundosAvanzados;
				c.parar();
				cancionActual++;  // Avanza a la siguiente canción
				if (cancionActual < listaCanciones.size()) {
					listaCanciones.get(cancionActual).reiniciar();
					listaCanciones.get(cancionActual).reproducir();
				}
			} else {  // Sigue la misma canción en curso, no hay que avanzar más
				segundos = 0;
			}
		}
	}	
	
}


//nombre (String). Nombre dado a la lista de reproducción. Evita que pueda producirse un NullPointer.
//Define un solo constructor con nombre de lista, getters y setters para ocultar lo más posible los detalles de implementación, toString y equals.
//Métodos a implementar dentro de la clase:
//funcionalidad para recuperar / añadir / quitar / buscar canciones en la lista, con control de error.
//modificaPosicion( Cancion cancion, int nuevaPosicion ): método para modificar la posición de una canción en la lista, con control de error.
//tiempoTotal() que devuelve el tiempo total de reproducción de la lista en minutos.
//reproducir / parar

