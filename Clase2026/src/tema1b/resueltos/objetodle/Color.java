package tema1b.resueltos.objetodle;

public class Color extends Elemento {
	java.awt.Color color;  // Color del elemento
	private char codigo;   // Para simplificar la visualización resumida del elemento
	
	public Color( EstadoElemento estado, java.awt.Color color, char codigo ) {
		super( estado );
		this.color = color;
		this.codigo = codigo;
	}
	
	@Override
	public void dibuja() {
		super.dibuja();
		if (objetodle==null) return;
		objetodle.dibujaCirculo( x+tam/2, y+tam/2, tam/2-8, 0f, java.awt.Color.BLACK, color );
	}

	@Override
	public String toString() {
		return "Color " + color;
	}

	public Elemento duplicar() {
		Color c = new Color( estado, color, codigo );
		c.x = x;
		c.y = y;
		c.tam = tam;
		c.objetodle = objetodle;
		c.estado = EstadoElemento.INTENTO;
		return c;
	}

	@Override
	public char aCaracter() {
		return codigo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Color) {
			Color c = (Color) obj;
			return c.color.equals( color );
		} else {
			return false;
		}
	}
}
