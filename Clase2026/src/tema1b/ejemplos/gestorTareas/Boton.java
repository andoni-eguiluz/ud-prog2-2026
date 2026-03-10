package tema1b.ejemplos.gestorTareas;

import java.awt.Point;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Boton extends ObjetoGT {
	
	//================= Parte static
	
	/** Tamaño visual del botón (diámetro del círculo) */
	public static final int TAMANYO = 60;
	/** Coordenada inicial de la tarea creada si se pulsa el botón "NUEVA" */
	public static final Point COORD_NUEVA_TAREA = new Point( 110, 100 );
	// Pixels de incremento en y en cada nueva tarea
	private static final int INC_NUEVA_TAREA = 10;
	
	//================= Parte no static

	String nombre;  // Nombre del botón
	String imagen;  // Path de imagen del botón

	/** Crea un nuevo botón
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param ventana	Ventana del botón
	 * @param nombre	nombre del botón
	 * @param imagen imagen del botón
	 */
	public Boton(int x, int y, VentanaGrafica ventana, String nombre, String imagen) {
		super(x, y, ventana);
		this.nombre = nombre;
		this.imagen = imagen;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public void dibujar() {
		ventana.dibujaImagen( imagen, x, y, TAMANYO, TAMANYO, 1.0, 0.0, 1.0f );
	}

	@Override
	public boolean contienePunto(int x, int y) {
		double dist = Math.sqrt( (x-this.x)*(x-this.x) + (y-this.y)*(y-this.y) );
		return dist<=TAMANYO/2;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Boton)) return false;
		Boton t2 = (Boton) obj;
		return super.equals(obj) && imagen.equals(t2.imagen);
	}
	
	@Override
	public String toString() {
		return "Botón " + imagen + " " + super.toString();
	}
	
	/** Lanza el funcionamiento del botón si es "NUEVO". Si no, no hace nada
	 * @param gt	Gestor de tareas en el que se ha pulsado el botón
	 */
	public void ejecuta( GestorTareas gt ) {
		if (nombre.equals( "NUEVA" )) {
			Tarea tNueva = new Tarea( this.ventana );
			tNueva.mover( COORD_NUEVA_TAREA.x, COORD_NUEVA_TAREA.y );
			COORD_NUEVA_TAREA.y = COORD_NUEVA_TAREA.y + INC_NUEVA_TAREA;  // Incrementa un poquito hacia abajo cada vez
			gt.getListaObjetos().add( tNueva );
		}
	}

}