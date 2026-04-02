package utils.ideasProyectos.juego2d.datos;
import java.awt.*;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar naves
*/
public class NaveTripulada extends Nave {

	private double energia;
	
	/** Constructor de nave con datos
	 * @param x	Coordenada x del centro de la nave
	 * @param y	Coordenada y del centro de la nave
	 * @param tamanyo	Tamaño de la nave (en pixels)
	 * @param xDest	Coordenada x del vector de velocidad de la nave
	 * @param yDest	Coordenada y del vector de velocidad de la nave
	 * @param color	Color de la nave
	 */
	public NaveTripulada(double x, double y, double tamanyo, double xDest, double yDest, Color color) {
		super( x, y, tamanyo, xDest, yDest, color );
		energia = 100;
	}
	
	/** Constructor de nave con datos mínimos, suponiendo color magenta y sin velocidad
	 * @param x	Coordenada x del centro de la nave
	 * @param y	Coordenada y del centro de la nave
	 * @param tamanyo	Tamaño de la nave (en pixels)
	 */
	public NaveTripulada(double x, double y, double tamanyo) {
		this( x, y, tamanyo, x, y, Color.magenta );
	}

	/** Dibuja la nave en una ventana, en el color correspondiente de la nave (por defecto, negro)
	 * @param v	Ventana en la que dibujar la nave
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaCirculo( x, y, getTamanyo()*Math.sqrt(3)/6, 3.5f, Color.yellow );
		super.dibuja( v );
		v.dibujaCirculo( puntos[0].getX(), puntos[0].getY(), 4, 3.5f, Color.black );
	}
	
	
	/** Borra la nave en una ventana
	 * @param v	Ventana en la que borrar la nave
	 */
	@Override
	public void borra( VentanaGrafica v ) {
		super.borra( v );
		v.borraCirculo( x, y, getTamanyo()/2, 3.5f );
	}

	@Override
	public String toString() {
		return String.format( "naveTripulada %1s (%2$7.2f,%3$7.2f) Vel.=(%4$6.3f,%5$6.3f)", nombre, x, y, velocidadX, velocidadY );
	}
	
	/** Incrementa o decrementa la energía de la nave
	 * @param incremento	Cantidad de energía a incrementar (positivo) o decrementar (negativo)
	 */
	public void cambiaEnergia( double incremento ) {
		energia += incremento;
	}
	
	public double getEnergia() {
		return energia;
	}
	
}
