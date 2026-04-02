package tema1c.resueltos.survival;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Disparo extends ObjetoConColor implements Movible, ConEnergia {
	private static int RADIO_POR_DEFECTO = 7;
	private static Random random = new Random();
	
	private double radio = 5;

	public Disparo(double x, double y, Color colorBorde, Color colorFondo, double radio) {
		super(x, y, colorBorde, colorFondo);
		setRadio( radio );
	}
	
	/** Crea un disparo de centro en coordenadas aleatorias dentro de la ventana, borde rojo, fondo cyan, radio {@link #RADIO_POR_DEFECTO} píxeles
	 * @param vent	Ventana a considerar
	 */
	public Disparo( VentanaGrafica vent ) {
		this( random.nextInt(vent.getAnchura()), random.nextInt(vent.getAltura()), Color.RED, Color.CYAN, RADIO_POR_DEFECTO );
		// Interfaz movible
		velocidadX = random.nextDouble()*50 + 30;
		if (random.nextBoolean()) velocidadX = -velocidadX;
		velocidadY = random.nextDouble()*50 + 30;
		if (random.nextBoolean()) velocidadY = -velocidadY;
	}
	
	/** Intenta crear un disparo correcto (en los bordes de 100 píxeles de la ventana, con velocidad que le mantenga en la ventana 5 segundos)
	 * Si no lo consigue devuelve null.
	 * @param vent	Ventana a considerar
	 * @return	nuevo disparo correcto, o null
	 */
	public static Disparo crearDisparoCorrecto( VentanaGrafica vent ) {
		Disparo nuevo = new Disparo( vent );
		if (nuevo.x > 100 && nuevo.x < vent.getAnchura()-100) {
			return null;
		}
		if (nuevo.y > 100 && nuevo.y < vent.getAltura()-100) {
			return null;
		}
		nuevo.mover( 5000 );
		if (!nuevo.estaDentroDeVentana(vent)) {
			return null;
		}
		nuevo.mover( -5000 );
		return nuevo;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		if (radio <=0) {
			return;
		}
		this.radio = radio;
	}

	@Override
	public void dibujar(VentanaGrafica vent) {
		vent.dibujaCirculo( x, y, radio, 1, getColorBorde(), getColorFondo() );
	}

	// Interfaz Movible 
	
	private double velocidadX;
	private double velocidadY;
	@Override
	public void mover(long milisegundos) {
		x += velocidadX * milisegundos / 1000.0;
		y += velocidadY * milisegundos / 1000.0;
	}

	// Interfaz ConEnergia (+ Chocable)

	@Override
	public boolean chocaCon(Chocable c2) {
		double dist = (new Point2D.Double( this.x, this.y )).distance( c2.getX(), c2.getY() );
		return dist < getRadioChoque() + c2.getRadioChoque();
	}
	
	@Override
	public void setRadioChoque(double radio) {
		setRadio( radio );
	}
	
	@Override
	public double getRadioChoque() {
		return radio;
	}

	private int energia = 1;
	
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
	
	@Override
	public String toString() {
		return String.format( "Disparo (%1$.0f,%2$.0f) vel (%3$.0f,%4$.0f)", x, y, velocidadX, velocidadY ); 
	}
	
}
