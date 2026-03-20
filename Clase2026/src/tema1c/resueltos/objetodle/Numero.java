package tema1c.resueltos.objetodle;

import java.awt.Color;
import java.awt.Font;

public class Numero extends Elemento implements Animable {
	public static final Font TIPO_LETRA = new Font( "Arial", Font.PLAIN, 36 );
	int numero;
	// Atributos de animación
	int colorAnimacion = 0;  // Inicia en negro y va cambiando
	int incrementoColor = +10;
	
	public Numero( EstadoElemento estado, int numero ) {
		super( estado );
		this.numero = numero;
	}
	
	@Override
	public void dibuja() {
		super.dibuja();
		if (objetodle==null) return;
		// Diferente dibujo si está o no en animación
		if (estaEnAnimacion()) {
			colorAnimacion += incrementoColor;
			if (colorAnimacion>255) {
				incrementoColor = -incrementoColor;
				colorAnimacion = 255;
			} else if (colorAnimacion<0) {
				incrementoColor = -incrementoColor;
				colorAnimacion = 0;
			}
			objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+numero, TIPO_LETRA, new Color( 0, colorAnimacion, colorAnimacion ), true );  // Se cambian G y B y se deja R fijo para la animación
		} else {
			objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+numero, TIPO_LETRA, Color.BLACK, true );
		}
	}

	@Override
	public String toString() {
		return "Número " + numero;
	}

	public Elemento duplicar() {
		Numero n = new Numero( estado, numero );
		n.x = x;
		n.y = y;
		n.tam = tam;
		n.objetodle = objetodle;
		n.estado = EstadoElemento.INTENTO;
		return n;
	}
	
	/** Devuelve el número
	 * @return
	 */
	public int getNumero() {
		return numero;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Numero) {
			Numero l = (Numero) obj;
			return l.numero == numero;
		} else {
			return false;
		}
	}
	
	@Override
	public char aCaracter() {
		return (char) ('0' + numero);
	}
	
	// Interfaz Animacion

	@Override
	public boolean estaEnAnimacion() {
		return estado==EstadoElemento.ACIERTO_CON_POSICION || estado==EstadoElemento.ACIERTO_SIN_POSICION; 
	}
	
}
