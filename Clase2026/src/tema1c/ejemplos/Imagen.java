package tema1c.ejemplos;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Imagen extends Figura implements Rotable, Clicable {

	// Atributos
	private String imagen;
	private double zoom;
	
	private double rotacion;
	
	// Consntructores
		
	public Imagen(int x, int y, String imagen, double zoom) {
		super(x, y, null);  // El color no se usa (null)
		this.setImagen( imagen );
		this.zoom = zoom;
	}

	// get/set
	
	/** Modifica el fichero de imagen.
	 * @param imagen	Nombre no vacío ni null
	 */
	public void setImagen(String imagen) {
		if (imagen!=null && !imagen.isEmpty()) {
			this.imagen = imagen;
		}
	}



	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public String getImagen() {
		return imagen;
	}

	// métodos
	@Override
	public void dibujar(VentanaGrafica v) {
		// v.dibujaImagen(imagen, x, y, zoom, rotacion, 1.0f );
		v.dibujaImagen(imagen, x, y, zoom, rotacion, opacidad );  // Cambio tras implementar clicable (opacidad)
	}

	@Override
	public String toString() {
		return "Imagen " + super.toString();
	}

	@Override
	public void rotar(double anguloRadianes) {
		rotacion += anguloRadianes;
	}

	@Override
	public double getRotacion() {
		return rotacion;
	}


	// INTERFAZ CLICABLE

	protected float opacidad = 1.0f; 
	protected float incOpacidad = -0.1f;
	
	@Override
	public boolean contieneCoordenada(int x, int y) {
		// Se podría simplificar con el contenido ortogonal o una aproximación. Si lo queremos hacer con el rectángulo y con rotación podemos usar la API de Java:
		Rectangle r = VentanaGrafica.getTamanyoImagen( imagen );  // Rectángulo original
		r.setSize( (int) (r.width*zoom), (int) (r.height*zoom) );  // Escala
		r.x = this.x-r.width/2;  // Coordenada superior izquierda (antes de rotar)
		r.y = this.y-r.height/2;
		AffineTransform at = AffineTransform.getRotateInstance( rotacion, r.getCenterX(), r.getCenterY() );
		Shape rotatedRect = at.createTransformedShape(r);
		boolean estaDentro = rotatedRect.contains( new Point2D.Double(x, y) );
		return estaDentro;
	}

	// En la reacción imágenes cambian su transparencia disminuyendo la opacidad 0.1 en cada click (por debajo del 0.5 vuelven al 1.0)
	
	@Override
	public void reaccionaAClick() {
		opacidad += incOpacidad;
		if (opacidad < 0.1f) {
			opacidad = 0.1f;
			incOpacidad = -incOpacidad;
			opacidad += incOpacidad;
		} else if (opacidad > 1.0f) {
			opacidad = 1.0f;
			incOpacidad = -incOpacidad;
			opacidad += incOpacidad;
		}
	}
	
}
