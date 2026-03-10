package tema1b.resueltos.objetodle;

import java.awt.Color;
import java.awt.Font;

public class Numero extends Elemento {
	public static final Font TIPO_LETRA = new Font( "Arial", Font.PLAIN, 36 );
	int numero;
	
	public Numero( EstadoElemento estado, int numero ) {
		super( estado );
		this.numero = numero;
	}
	
	@Override
	public void dibuja() {
		super.dibuja();
		if (objetodle==null) return;
		objetodle.dibujaTextoCentrado( x, y, tam, tam, ""+numero, TIPO_LETRA, Color.BLACK, true );
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

	@Override
	public char aCaracter() {
		return (char) ('0' + numero);
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
}
