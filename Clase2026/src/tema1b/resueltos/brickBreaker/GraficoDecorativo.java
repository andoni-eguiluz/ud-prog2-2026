package tema1b.resueltos.brickBreaker;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class GraficoDecorativo extends TextoInfo {

	protected String rutaGrafico;
	public GraficoDecorativo(double x, double y, int ancho, int alto, String rutaGrafico ) {
		super(x, y, ancho, alto);
		this.rutaGrafico = rutaGrafico;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		v.dibujaImagen( rutaGrafico, x, y, ancho, alto, 1, 0, 1.0f );
	}
	
	@Override
	public String toString() {
		return "Imagen " + x + "," + y + " - "  + rutaGrafico;
	}
}
