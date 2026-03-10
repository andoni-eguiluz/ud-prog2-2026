package tema1b.resueltos.runnerNaves;

import java.awt.Color;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar naves y dibujarlas en una ventana gráfica
 */
public class Nave extends ObjetoEspacial {
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private boolean subiendo;  // true si la nave está subiendo
	private boolean bajando;   // false si la nave está bajando
	private int canal;         // Número de canal en el que está la nave

	/** Crea una nueva nave
	 * @param x	Coordenada x del centro de la nave (en píxels)
	 * @param y	Coordenada y del centro de la nave (en píxels)
	 */
	public Nave( double x, double y ) {
		super( x, y );
		subiendo = false;
		bajando = false;
	}
	
	/** Devuelve el canal de la nave
	 * @return	canal de la nave (0-4)
	 */
	public int getCanal() {
		return canal;
	}

	/** Cambia la información de canal de la nave
	 * @param canal	Nuevo canal de la nave (0-4)
	 */
	public void setCanal(int canal ) {
		this.canal = canal;
	}
	
	/** Devuelve la información de si la nave está subiendo
	 * @return	true si está subiendo, false si no está subiendo
	 */
	public boolean isSubiendo() {
		return subiendo;
	}

	/** Cambia la información de si la nave está subiendo
	 * @param subiendo	true si está subiendo, false si no está subiendo
	 */
	public void setSubiendo( boolean subiendo ) {
		this.subiendo = subiendo;
	}

	/** Devuelve la información de si la nave está bajando
	 * @return	true si está bajando, false si no está bajando
	 */
	public boolean isBajando() {
		return bajando;
	}

	/** Cambia la información de si la nave está bajando
	 * @param subiendo	true si está bajando, false si no está bajando
	 */
	public void setBajando( boolean bajando ) {
		this.bajando = bajando;
	}

	/** Dibuja la nave en una ventana, con inclinación hacia arriba si está subiendo, hacia abajo si está bajando, o sin inclinación si ni sube ni baja
	 * @param v	Ventana en la que dibujar la nave
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		if (subiendo) {
			v.dibujaImagen( "img/nave.png", x, y, 50.0/500.0, -0.2, 1.0f );  // el gráfico tiene 500 píxels y la nave quiere dibujarse con 50, subiendo 0.2 radianes
		} else if (bajando) {
			v.dibujaImagen( "img/nave.png", x, y, 50.0/500.0, +0.2, 1.0f );  // el gráfico tiene 500 píxels y la nave quiere dibujarse con 50, bajando 0.2 radianes
		} else {
			v.dibujaImagen( "img/nave.png", x, y, 50.0/500.0, 0.0, 1.0f );  // el gráfico tiene 500 píxels y la nave quiere dibujarse con 50
		}
		if (DIBUJA_ENVOLVENTE) v.dibujaCirculo( x, y, 25, 2f, Color.orange );  // Pintado a título de referencia de prueba
	}
	
	@Override
	public String toString() {
		return "Nave " + x + "," + y + " (" + (subiendo?"subiendo":"") + (bajando?"bajando":"") + ")";
	}
	
	/** Comprueba si la nave es igual a otro objeto dado. Se entiende que dos naves son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Nave) {
			Nave p2 = (Nave) obj;  // Cast de obj a nave2 (lo entenderemos mejor al ver herencia)
			return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de las naves this y p2
		} else {
			return false;
		}
	}
	
}
