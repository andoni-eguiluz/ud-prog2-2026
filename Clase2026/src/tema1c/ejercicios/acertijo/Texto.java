package tema1c.ejercicios.acertijo;

import java.awt.Color;
import java.awt.Font;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear textos, rectángulos fijos que contienen un texto dibujado
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Texto extends Rectangulo {
	
	//================= Parte static
	
	/** Tipo de letra del texto */
	public static final Font FONT_TEXTO = new Font( "Arial", Font.PLAIN, 18 );
	/** Color del texto */
	public static final Color COLOR_TEXTO = Color.BLACK;
	
	//================= Parte no static
	
	private String texto;
	/** Crea un nuevo texto
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param ventana	Ventana del botón
	 * @param anchura	Anchura del rectángulo de texto en píxels
	 * @param altura	Altura del rectángulo de texto en píxels
	 * @param texto	Texto a visualizar dentro del rectángulo
	 */
	public Texto(int x, int y, VentanaGrafica ventana, int anchura, int altura, String texto) {
		super(x, y, ventana, anchura, altura );
		this.texto = texto;
	}
	
	@Override
	public void dibujar() {
		// super.dibujar();  // Rectángulo si quisiéramos dibujarlo
		ventana.dibujaTexto( x+10, y+16, texto, FONT_TEXTO, COLOR_TEXTO );
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Texto)) return false;
		Texto o2 = (Texto) obj;
		return super.equals(obj) && texto.equals(o2.texto);
	}
	
	@Override
	public String toString() {
		return "Texto " + super.toString() + " " + texto;
	}
	
}