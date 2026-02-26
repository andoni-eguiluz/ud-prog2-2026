package tema1.resueltos.ej1_15b;

import java.util.Objects;

public class Cancion {

	// ATRIBUTOS
	
	private String nombre = "<Indefinida>"; // (1) Evita que pueda producirse un NullPointer.
	private String cantante = "<Indefinido>"; // (1) Evita que pueda producirse un NullPointer.
	private double duracion = 3.5; // tiempo que dura en minutos (double). Por defecto: 3.5
	private boolean estado = false; // si está siendo reproducida o no (boolean). Por defecto: false
	private double puntoReproduccion; // minuto en el que está siendo reproducida (double).

	// CONSTRUCTORES
	
	/** Crea una canción nueva 
	 * @param nombre	Nombre de la canción - no puede ser null
	 * @param cantante	Nombre del cantante - no puede ser null
	 * @param duracion	Tiempo que dura la canción en minutos - debe ser mayor que cero
	 * @param estado	Estado de reproducción (false - no está siendo reproducida, true - está siendo reproducida)
	 * @param puntoReproduccion	Minuto en el que está siendo reproducida - debe ser mayor que cero y menor o igual que la duración
	 */
	public Cancion(String nombre, String cantante, double duracion, boolean estado, double puntoReproduccion) {
		// this.nombre = nombre;  // Ojo que hacerlo así podría causar potencialmente un nullpointer después al intentar hacer algo con ese nombre
		setNombre( nombre );
		setCantante( cantante );
		setDuracion( duracion );
		setEstado( estado );
		setPuntoReproduccion( puntoReproduccion );
	}
	
	/** Crea una nueva canción con nombre "<Indefinida>", con cantante "<Indefinido>", con duración 3.5 minutos, en estado de no reproducción, y en tiempo de reproducción 0
	 */
	public Cancion() {
	}

	// SETTERS Y GETTERS

	public String getNombre() {
		return nombre;
	}

	// (2) Evita que pueda producirse un nullpointer
	/** Cambia el nombre de la canción. Si es incorrecto se saca un mensaje de error y se mantiene con el valor anterior
	 * @param nombre	Nombre de la canción - no puede ser null
	 */
	public void setNombre(String nombre) {
		if (nombre==null) {
			System.err.println( "Error al intentar nombre de canción nula" );
			return;
		}
		this.nombre = nombre;
	}

	public String getCantante() {
		return cantante;
	}

	// (2) Evita que pueda producirse un nullpointer
	/** Cambia el nombre del cantante. Si es incorrecto se saca un mensaje de error y se mantiene con el valor anterior
	 * @param cantante	Nombre del cantante - no puede ser null
	 */
	public void setCantante(String cantante) {
		if (cantante==null) {
			System.err.println( "Error al intentar nombre de cantante nulo" );
			return;
		}
		this.cantante = cantante;
	}

	/** Devuelve la duración de la canción
	 * @return	Tiempo de duración, en minutos
	 */
	public double getDuracion() {
		return duracion;
	}

	// Tiempo que dura la canción en minutos - debe ser mayor que cero
	/**
	 * @param duracion
	 */
	public void setDuracion(double duracion) {
		if (duracion <= 0) {
			System.err.println( "Error al intentar duración de canción 0 o negativo" );
			return;
		}
		this.duracion = duracion;
	}

	/** Consulta el estado de reproducción
	 * @return	false si no se está reproduciendo, true en caso contrario 
	 */
	public boolean isEstado() {
		return estado;
	}

	// OJO - PRIVATE porque esta funcionalidad se gestiona con los métodos de reproducción: reproducir / parar / reiniciar
	private void setEstado(boolean estado) {
		this.estado = estado;
	}

	/** Devuelve el punto en el que la canción está siendo reproducida
	 * @return	Tiempo en minutos
	 */
	public double getPuntoReproduccion() {
		return puntoReproduccion;
	}

	/** Modifica el punto de reproducción de la canción. Debe estar entre 0 y la duración.
	 * @param puntoReproduccion	Nuevo tiempo de reproducción, en minutos
	 */
	public void setPuntoReproduccion(double puntoReproduccion) {
		if (puntoReproduccion < 0 || puntoReproduccion > duracion) {
			System.err.println( "Error al intentar punto de reproducción imposible" );
			return;
		}
		this.puntoReproduccion = puntoReproduccion;
	}

	@Override
	public String toString() {
		return "Cancion \"" + nombre + "\" de " + cantante + " (" + formateaMMSS( duracion ) +
				") " + (estado ? ("reproduciendo en " + formateaMMSS( puntoReproduccion)) : "");
	}
		private static String formateaMMSS( double duracion ) {
			return String.format( "%02d:%02d", (int) duracion, (int) Math.round((duracion - (int) duracion) * 60 ) );
		}

	// Generada por eclipse
	@Override
	public int hashCode() {
		return Objects.hash(cantante, duracion, estado, nombre, puntoReproduccion);
	}

	// Generada por eclipse
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cancion other = (Cancion) obj;
		return Objects.equals(cantante, other.cantante)
				&& Double.doubleToLongBits(duracion) == Double.doubleToLongBits(other.duracion)
				&& estado == other.estado && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(puntoReproduccion) == Double.doubleToLongBits(other.puntoReproduccion);
	}

	// METODOS

	/** Lanza la reproducción
	 */
	public void reproducir() {
		estado = true;
	}
	
	/** Detiene la reproducción
	 */
	public void parar() {
		estado = false;
	}
	
	/** Reinicia la reproducción y reinicia a 0 el punto de reproducción
	 */
	public void reiniciar() {
		parar();
		puntoReproduccion = 0.0;
	}
	
	/** Si la canción se está reproduciendo, avanza el tiempo de reproducción. Si no, no hace nada.
	 * @param segundos
	 */
	public void avanzar( int segundos ) {
		if (!estado) {
			return;
		}
		puntoReproduccion += segundos / 60.0;   // Ojo a la conversión a minutos
	}
	
	/** devuelve información de si la reproducción ha acabado o no y detiene la reproducción si procede (el tiempo de reproducción es mayor o igual que la duración).
	 * @return	true si ha acabado la reproducción, false si no ha acabado
	 */
	public boolean compruebaFinal() {
		if (puntoReproduccion >= duracion) {
			parar();
			return true;
		} else {
			return false;
		}
	}
	
}

