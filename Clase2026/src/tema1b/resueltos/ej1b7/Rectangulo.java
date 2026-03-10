package tema1b.resueltos.ej1b7;

import java.awt.Point;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Rectangulo extends Figura {
	
	private static Random random = new Random();

	// NO STATIC
	
	private int anchura;
	private int altura;
	private String grafico;
	
	/** Crea un rectángulo nuevo, que se dibuja como un ladrillo
	 * @param xCentro	Coordenada x del centro del rectángulo, en píxeles (de izquierda a derecha)
	 * @param yCentro	Coordenada y del centro del rectángulo, en píxeles (de arriba abajo)
	 * @param anchura	Anchura del rectángulo en píxeles. Debe ser positivo
	 * @param altura	Altura del rectángulo en píxeles. Debe ser positivo
	 */
	public Rectangulo(int xCentro, int yCentro, int anchura, int altura) {
		super( xCentro, yCentro );
		this.anchura = anchura;
		this.altura = altura;
		tiempoCreacionMs = System.currentTimeMillis();
		grafico = "ladrillo" + (1 + random.nextInt(4)) + ".png";
	}
	
	/** Crea un rectángulo nuevo de coordenadas aleatorias, que se dibuja como un ladrillo
	 * La posición será una coordenada válida para la ventana indicada, para que el ladrillo se vea completo
	 * @param anchura	Anchura del rectángulo
	 * @param altura	Altura del rectángulo
	 * @param ventana	Ventana que marca el espacio visible en el que localizar al ladrillo
	 */
	public Rectangulo( int anchura, int altura, VentanaGrafica ventana ) {
		super( random.nextInt( ventana.getAnchura() - anchura ) + anchura/2,
				random.nextInt( ventana.getAltura() - altura ) + altura/2 );
		this.anchura = anchura;
		this.altura = altura;
		grafico = "ladrillo" + (1 + random.nextInt(4)) + ".png";
	}
	
	public int getAnchura() {
		return anchura;
	}

	public void setAnchura(int anchura) {
		this.anchura = anchura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String toString() {
		return String.format( "Rect [%d,%d] - %d,%d", xCentro, yCentro, anchura, altura );
	}

	@Override
	public void dibujar( VentanaGrafica v ) {
		v.dibujaImagen( grafico, xCentro, yCentro, anchura, altura, 1.0, 0.0, 1.0f );
	}

	@Override
	public boolean contienePunto( Point punto ) {
		return punto.x >= xCentro - anchura/2 && punto.x <= xCentro + anchura/2 &&
			   punto.y >= yCentro - altura/2 && punto.y <= yCentro + altura/2; 
	}

	@Override
	public boolean chocaBordeVertical(VentanaGrafica v) {
		return yCentro < altura/2 || yCentro > v.getAltura() - altura/2;
	}

	@Override
	public boolean chocaBordeHorizontal(VentanaGrafica v) {
		return xCentro < anchura/2 || xCentro > v.getAnchura() - anchura/2;
	}
	
}
