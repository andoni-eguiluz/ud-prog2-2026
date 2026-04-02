package tema1c.resueltos.survival;

import java.awt.Color;

public abstract class ObjetoConColor extends ObjetoJuego {
	protected Color colorBorde;
	protected Color colorFondo;
	
	public ObjetoConColor(double x, double y, Color colorBorde, Color colorFondo) {
		super(x, y);
		this.colorBorde = colorBorde;
		this.colorFondo = colorFondo;
	}

	public Color getColorBorde() {
		return colorBorde;
	}

	public void setColorBorde(Color colorBorde) {
		this.colorBorde = colorBorde;
	}

	public Color getColorFondo() {
		return colorFondo;
	}

	public void setColorFondo(Color colorFondo) {
		this.colorFondo = colorFondo;
	}
	
	
	
}
