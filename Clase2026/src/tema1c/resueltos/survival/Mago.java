package tema1c.resueltos.survival;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Mago extends ObjetoJuego implements ConEnergia, Clickable {
	
	private int ancho;
	private int alto;
	private int energia = 1;
	
	public Mago(double x, double y, int anchura, int altura ) {
		super(x, y);
		setTamanyo( anchura, altura );
	}
	
	
	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		if (ancho<=0) return;
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		if (alto<=0) return;
		this.alto = alto;
	}

	public void setTamanyo( int ancho, int alto ) {
		setAncho(ancho);
		setAlto(alto);
	}

	// Sanación
	private int sanacion = 25;

	public boolean cicloSanacion() {
		if (sanacion >= 125) {
			sanacion = 25;
			return true;
		}
		return false;
	}
	
	@Override
	public void dibujar(VentanaGrafica vent) {
		// Cambio de selección
		if (seleccionado) {
			vent.dibujaCirculo( x, y, Math.min( ancho, alto ) / 2, 1, Color.YELLOW, Color.YELLOW );
		}

		vent.dibujaImagen( "img/mago_azul.png", x, y, ancho, alto, 1, 0, 1.0f );
		// Cambios de sanación
		sanacion += 2;
		if (sanacion > 60) {
			vent.dibujaImagen( "img/mago_rojo.png", x, y, ancho, alto, 1, 0, 1.0f );
		}
		vent.dibujaCirculo( x, y, sanacion, 2, Color.RED );
	}

	@Override
	public boolean chocaCon(Chocable c2) {
		double dist = (new Point2D.Double( this.x, this.y )).distance( c2.getX(), c2.getY() );
		return dist < getRadioChoque() + c2.getRadioChoque();
	}
	
	@Override
	public void setRadioChoque(double radio) {
		setTamanyo( (int) (2*radio), (int) (2*radio) );
	}
	
	@Override
	public double getRadioChoque() {
		return Math.min( ancho, alto ) / 2.0;
	}

	@Override
	public void setEnergia( int energia ) {
		if (energia <0) {
			return;
		}
		this.energia = energia;
	}

	@Override
	public void incEnergia( int incEnergia ) {
		energia += incEnergia;
		if (energia < 0) energia = 0;
	}
	
	@Override
	public int getEnergia() {
		return energia;
	}
	
	@Override
	public boolean estaDestruido() {
		return energia <= 0;
	}

	// Interfaz clickable
	
	@Override
	public boolean puntoDentro(double x, double y) {
		double difx = x - this.x;
		double dify = y - this.y;
		double dist = Math.sqrt( difx * difx + dify * dify );
		return (dist <= Math.min(ancho, alto));
	}

	private boolean seleccionado = false;
	@Override
	public void setSeleccionado (boolean sel) {
		seleccionado = sel;
	}

	
	@Override
	public String toString() {
		return String.format( "Mago (%1$.0f,%2$.0f) tamaño [%3$d,%4$d]", x, y, ancho, alto ); 
	}
	
}
