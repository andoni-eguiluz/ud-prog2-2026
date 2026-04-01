package tema1c.resueltos.runnerNaves;

import java.util.ArrayList;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar imágenes de fondo y dibujarlas en una ventana gráfica
 */
public class Fondo extends ObjetoEspacial implements Salible {
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private String imagen;               // Nombre de la imagen
	private double zoom;                 // Zoom de dibujado
	private float transp;                // Transparencia de dibujado
	private int ancho;                   // Ancho de la imagen

	/** Crea un nuevo fondo
	 * @param imagen	Nombre de la imagen (png mejor que jpg), ruta relativa al proyecto
	 * @param x	Coordenada x de la imagen
	 * @param y	Coordenada y de la imagen
	 * @param zoom	Zoom de dibujado (1.0 = 100%)
	 * @param transp	Transparencia de dibujado (1.0f = completamente opaco, 0.0f = completamente transparente)
	 * @param ancho	Ancho de la imagen (en píxels)
	 */
	public Fondo( String imagen, double x, double y, double zoom, float transp, int ancho ) {
		super( x, y );
		this.imagen = imagen;
		this.zoom = zoom;
		this.transp = transp;
		this.ancho = ancho;
	}
	
	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	/** Dibuja el fondo en una ventana
	 * @param v	Ventana en la que dibujar la imagen
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaImagen( imagen, x, y, zoom, 0.0, transp );
	}
	
	/** Comprueba si la imagen se sale completamente por el lado izquierdo de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo completamente por la izquierda, false en caso contrario
	 */
	public boolean seSalePorLaIzquierda( VentanaGrafica v ) {
		return x+ancho/2<0;
	}

	@Override
	public void sal( ArrayList<ObjetoEspacial> l ) {
		setX( getX() + 3000 );  // 1000 píxels de ancho cada fondo - no se quita sino que se mueve a la derecha fuera de pantalla
	}
	
	@Override
	public String toString() {
		return "fondo " + super.toString() + " (" + zoom + ")";
	}
	
	/** Comprueba si el fondo es igual a otro objeto dado. Se entiende que dos fondos son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales y su imagen también lo es
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fondo) {
			Fondo p2 = (Fondo) obj;
			return imagen.equals(p2.imagen) && Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y);
		} else {
			return false;
		}
	}
	
}
