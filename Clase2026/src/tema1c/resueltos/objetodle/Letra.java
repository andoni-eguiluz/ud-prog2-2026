package tema1c.resueltos.objetodle;

import java.awt.Color;
import java.awt.Font;

public class Letra extends Elemento implements Animable, SensibleAlRaton {
	public static final Font TIPO_LETRA = new Font( "Arial", Font.PLAIN, 36 );
	char letra;
	// Atributos de animación
	int colorAnimacion = 0;  // Inicia en negro y va cambiando
	int incrementoColor = +10;
	
	public Letra( EstadoElemento estado, char letra ) {
		super( estado );
		this.letra = letra;
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
			objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+letra, TIPO_LETRA, new Color( 0, colorAnimacion, colorAnimacion ), true );  // Se cambian G y B y se deja R fijo para la animación
		} else {
			objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+letra, TIPO_LETRA, Color.BLACK, true );
		}
	}

	@Override
	public String toString() {
		return "Letra " + letra;
	}

	public Elemento duplicar() {
		Letra l = new Letra( estado, letra );
		l.x = x;
		l.y = y;
		l.tam = tam;
		l.objetodle = objetodle;
		l.estado = EstadoElemento.INTENTO;
		return l;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Letra) {
			Letra l = (Letra) obj;
			return l.letra == letra;
		} else {
			return false;
		}
	}

	@Override
	public char aCaracter() {
		return letra;
	}

	// Interfaz Animacion

	@Override
	public boolean estaEnAnimacion() {
		return estado==EstadoElemento.ACIERTO_CON_POSICION || estado==EstadoElemento.ACIERTO_SIN_POSICION; 
	}

	// Interfaz SensibleAlRaton
	
	@Override
	public void dibujaInverso() {
		objetodle.dibujaRect( x, y, tam, tam, 2f, Color.WHITE, Color.BLACK );
		objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+letra, TIPO_LETRA, Color.WHITE, true );
	}

	
}
