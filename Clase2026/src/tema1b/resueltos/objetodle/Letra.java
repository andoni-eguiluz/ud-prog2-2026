package tema1b.resueltos.objetodle;

import java.awt.Color;
import java.awt.Font;

public class Letra extends Elemento {
	public static final Font TIPO_LETRA = new Font( "Arial", Font.PLAIN, 36 );
	char letra;
	
	public Letra( EstadoElemento estado, char letra ) {
		super( estado );
		this.letra = letra;
	}
	
	@Override
	public void dibuja() {
		super.dibuja();
		if (objetodle==null) return;
		objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+letra, TIPO_LETRA, Color.BLACK, true );
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
	public char aCaracter() {
		return letra;
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
}
