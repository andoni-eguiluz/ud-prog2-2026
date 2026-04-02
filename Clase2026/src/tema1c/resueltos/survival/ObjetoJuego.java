package tema1c.resueltos.survival;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public abstract class ObjetoJuego {
	protected double x;
	protected double y;
	
	public ObjetoJuego(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void moverA( double x, double y) {
		setX( x ); setY( y );
	}
	
	public boolean estaDentroDeVentana( VentanaGrafica vent ) {
		return x>=0 && y>=0 && x<=vent.getAnchura() && y<=vent.getAltura();
	}
	
	public double distanciaA( ObjetoJuego o2 ) {
		double difx = x - o2.x;
		double dify = y - o2.y;
		return Math.sqrt( difx * difx + dify * dify );
	}
	
	public abstract void dibujar(VentanaGrafica vent);
	
}
