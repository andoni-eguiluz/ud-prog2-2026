package tema1c.ejemplos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Imagen extends Figura implements Rotable {

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
		v.dibujaImagen(imagen, x, y, zoom, rotacion, 1.0f );
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

	
	
}
