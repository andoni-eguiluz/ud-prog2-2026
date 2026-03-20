package tema1c.resueltos.brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase para incluir texto de información en el juego  (HUD)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class TextoInfo extends ObjetoMovil {
	
	public static final Font FONT_POR_DEFECTO = new Font( "Arial", Font.PLAIN, 40 );
	
	protected int ancho;
	protected int alto;
	protected String texto;
	protected Font font;
	
	/** Construye un nuevo texto en las coordenadas y tamaño indicados, de color de texto amarillo y de fondo transparente
	 * 
	 */
	public TextoInfo( double x, double y, int ancho, int alto ) {
		super( x, y, Color.YELLOW, new Color( 0, 0, 0, 0 ) ); // Componente alfa = 0, transparente
		this.ancho = ancho;
		this.alto = alto;
		font = FONT_POR_DEFECTO;
		texto = "";
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Rectangle2D getRectangulo() {
		return new Rectangle2D.Double( x-ancho/2.0, y-ancho/2.0, ancho, alto );
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		v.dibujaRect( x-ancho/2.0, y-alto/2.0, ancho, alto, 0f, colorFondo, colorFondo );
		v.dibujaTextoCentrado( x-ancho/2.0, y-alto/2.0, ancho, alto, texto, font, colorBorde, true );
	}

	@Override
	public void borrar(VentanaGrafica v) {
		v.dibujaRect( x-ancho/2.0, y-alto/2.0, ancho, alto, 0f, Color.WHITE, Color.WHITE );
	}

	@Override
	public boolean chocaBordeVertical(Rectangle rect) {
		return false;
	}

	@Override
	public boolean chocaBordeHorizontal(Rectangle rect) {
		return false;
	}

	@Override
	public boolean contienePunto(Point punto) {
		return false;
	}

	@Override
	public double getArea() {
		return 0;
	}

	@Override
	public double getVolumen() {
		return 0;
	}

	@Override
	public double getEnergia() {
		return 0;
	}

	@Override
	public String toString() {
		return "Texto " + x + "," + y + " - "  + texto;
	}
}
