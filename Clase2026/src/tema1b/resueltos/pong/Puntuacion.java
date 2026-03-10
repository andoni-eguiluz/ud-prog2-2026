package tema1b.resueltos.pong;
import java.awt.Color;
import java.awt.Font;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar puntuaciones, dibujados como texto gráfico
 */
public class Puntuacion extends Figura {
	
	/** Tipo de letra usado para el texto de la puntuación
	 */
	public static final String TIPO_LETRA = "Arial";
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================

	private Font tipoTexto;       // Tipo de letra para el texto
	private int puntuacion;       // Valor de la puntuación
	private long tiempoAnimacion; // Atributo para la animación de la puntuación (tiempo del sistema hasta el que tiene que animarse)

	/** Crea una nueva puntuación de valor cero
	 * @param x	Coordenada x del centro del círculo (en píxels)
	 * @param y	Coordenada y del centro del círculo (en píxels)
	 * @param color	Color del círculo
	 * @param tamanyoTexto	Tamaño del texto en puntos
	 */
	public Puntuacion( double x, double y, Color color, int tamanyoTexto ) {
		super( x, y, color );
		// this.tamanyoTexto = tamanyoTexto;  // Nota: no hace falta guardar el tamaño en atributo porque se guarda el font
		tipoTexto = new Font( TIPO_LETRA, Font.PLAIN, tamanyoTexto );
		puntuacion = 0;  // Nota: Lo haría por defecto Java si no lo pusiéramos
	}
	
	/** Devuelve el valor de la puntuación
	 * @return	Valor puntuación
	 */
	public int getPuntuacion() {
		return puntuacion;
	}
	
	/** Incrementa la puntuación en 1 unidad
	 */
	public void inc() {
		puntuacion++;
		// Para la animación
		tiempoAnimacion = System.currentTimeMillis() + 510;  // Dura 510 milisegundos (mejor este número con una constante)
		// (lo hacemos 510 = 255*2 para que sea más fácil cambiar el color
	}

	/** Cambia el valor de la puntuación
	 * @param puntuacion	Nuevo valor
	 */
	public void setRadio(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	@Override
	public void dibuja( VentanaGrafica v ) {
		Color colorTexto = color;
		// Animación
		if (tiempoAnimacion > System.currentTimeMillis()) {
			long tiempoFalta = tiempoAnimacion - System.currentTimeMillis();
			colorTexto = new Color( (int) (tiempoFalta / 2), 0, 0);  // El color va a ir cambiando desde 255 hasta 0 en cada dibujado 
		}
		v.dibujaTexto( x, y, puntuacion + "", tipoTexto, colorTexto );
	}
	
	@Override
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y<=0 || y>=v.getAltura();  // No hace falta porque la puntuación no se va a mover, pero hay que implementarlo
	}

	@Override
	public boolean seSaleEnHorizontal( VentanaGrafica v ) {
		return x<=0 || x>=v.getAnchura();  // No hace falta porque la puntuación no se va a mover, pero hay que implementarlo
	}

	@Override
	public String toString() {
		return super.toString() + " (" + puntuacion + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Puntuacion)) {
			return false;
		}
		Puntuacion p2 = (Puntuacion) obj;
		return Math.abs(p2.x-x)<=DISTANCIA_MINIMA_IGUALDAD && Math.abs(p2.y-y)<=DISTANCIA_MINIMA_IGUALDAD; // Devuelve true o false dependiendo de las coordenadas de los círculos this y c2
	}
	
}