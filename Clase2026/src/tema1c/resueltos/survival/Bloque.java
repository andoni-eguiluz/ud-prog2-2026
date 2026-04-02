package tema1c.resueltos.survival;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Bloque extends ObjetoConColor implements ConEnergia, Clickable, Rotable {
	public static final int LADO_POR_DEFECTO = 50;
	private static Random random = new Random();
	
	private double lado;
	private double rotacion;
	
	public Bloque(double x, double y, Color colorBorde, Color colorFondo, int lado) {
		super(x, y, colorBorde, colorFondo);
		setLado( lado );
		this.rotacion = 0;
	}

	/** Crea un bloque de centro en coordenadas aleatorias dentro de la ventana, borde verde, lado {@link #LADO_POR_DEFECTO} píxeles
	 * @param vent	Ventana a considerar
	 */
	public Bloque( VentanaGrafica vent ) {
		this( random.nextInt(vent.getAnchura()), random.nextInt(vent.getAltura()), Color.GREEN, null, LADO_POR_DEFECTO );
	}
	
	/** Intenta crear un bloque correcto (centro separado al menos 150 píxeles de cada borde de la ventana, y no choca con ningún otro bloque)
	 * Si no lo consigue devuelve null.
	 * @param vent	Ventana a considerar
	 * @param listaBloques	Lista de bloques adicionales con los que comprobar choques
	 * @return	nuevo bloque correcto, o null
	 */
	public static Bloque crearBloqueCorrecto( VentanaGrafica vent, ArrayList<Bloque> listaBloques ) {
		Bloque nuevo = new Bloque( vent );
		if (nuevo.x < 150 || nuevo.x > vent.getAnchura()-150) {
			return null;
		}
		if (nuevo.y < 100 || nuevo.y > vent.getAltura()-150) {
			return null;
		}
		for (Bloque b : listaBloques) {
			if (b.chocaCon( nuevo )) {
				return null;
			}
		}
		return nuevo;
	}
	
	public double getLado() {
		return lado;
	}
	
	public void setLado(double lado) {
		if (lado <= 0) {
			return;
		}
		this.lado = lado;
	}

	@Override
	public void dibujar(VentanaGrafica vent) {
		vent.dibujaPoligonoRegular( x, y, 6, lado, rotacion, 5, colorBorde );
		// Cambio selección
		if (seleccionado) {
			vent.dibujaPoligonoRegular( x, y, 6, lado, rotacion, 5, Color.YELLOW );
		}
		vent.dibujaTextoCentrado( x-15, y-10, 30, 20, "" + energia, new Font( "Arial", Font.PLAIN, 20 ), Color.ORANGE );
	}

	@Override
	public boolean chocaCon(Chocable c2) {
		double dist = (new Point2D.Double( this.x, this.y )).distance( c2.getX(), c2.getY() );
		return dist < getRadioChoque() + c2.getRadioChoque();
	}
	
	@Override
	public void setRadioChoque(double radio) {
		setLado( radio );
	}
	
	@Override
	public double getRadioChoque() {
		return lado;
	}

	// Con energía
	private int energia = 2;
	
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
		return (dist <= lado);
	}
	
	private boolean seleccionado = false;
	@Override
	public void setSeleccionado (boolean sel) {
		seleccionado = sel;
	}

	// Interfaz Rotable
	@Override
	public void rotar(long tiempo) {
		rotacion += Math.PI/2 * tiempo / 1000.0;
	}
		
	@Override
	public String toString() {
		return String.format( "Bloque (%1$.0f,%2$.0f) lado %3$.0f", x, y, lado ); 
	}
	
}
