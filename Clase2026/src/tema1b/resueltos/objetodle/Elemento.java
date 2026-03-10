package tema1b.resueltos.objetodle;

import java.awt.Color;
import java.awt.Point;

/** Elemento visual del objetodle
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public abstract class Elemento {
	protected Objetodle objetodle;	// Ventana de objetodle del elemento
	protected EstadoElemento estado;	// Estado del elemento (ver EstadoElemento)
	protected int x;	// Píxel x de la esquina sup izquierda
	protected int y;	// Píxel y de la esquina sup izquierda
	protected int tam;	// Tamaño en píxels (el elemento es cuadrado: ancho = alto = tam)
	
	/** Crea un nuevo elemento
	 * @param estado	Estado del elemento (ver clase EstadoElemento)
	 */
	public Elemento( EstadoElemento estado ) {
		this.estado = estado;
	}
	
	/** Devuelve el estado del elemento
	 * @return	Estado actual
	 */
	public EstadoElemento getEstado() {
		return estado;
	}
	/** Modifica el estado del elemento
	 * @param estado	Nuevo estado
	 */
	public void setEstado(EstadoElemento estado) {
		this.estado = estado;
	}
	
	/** Cambia la ventana de objetodle a la que pertenece este elemento
	 * @param objetodle	Nueva ventana de objetodle
	 */
	public void setObjetodle( Objetodle objetodle ) {
		this.objetodle = objetodle;
	}
	
	/** Modifica la posición en la ventana del elemento
	 * @param x	Coordenada x de la esquina superior izquierda del elemento
	 * @param y	Coordenada y de la esquina superior izquierda del elemento
	 */
	public void setPosi( int x, int y ) {
		this.x = x;
		this.y = y;
	}
	/** Modifica el tamaño del elemento
	 * @param tam	Nuevo tamaño (ancho y alto) en píxeles
	 */
	public void setTam( int tam ) {
		this.tam = tam;
	}
	
	/** Dibuja el elemento en la ventana
	 */
	public void dibuja() {
		if (objetodle==null) return;
		if (estado==EstadoElemento.TECLA) {
			objetodle.dibujaRect( x, y, tam, tam, 2f, Color.BLACK );
		} else if (estado==EstadoElemento.FALLO) {
			objetodle.dibujaRect( x, y, tam, tam, 1f, Color.GRAY, Color.GRAY );
		} else if (estado==EstadoElemento.ACIERTO_SIN_POSICION) {
			objetodle.dibujaRect( x, y, tam, tam, 1f, Color.GRAY, Color.ORANGE );
		} else if (estado==EstadoElemento.ACIERTO_CON_POSICION) {
			objetodle.dibujaRect( x, y, tam, tam, 1f, Color.GRAY, Color.GREEN );
		}
	}
	
	/** Informa si un punto de ventana está dentro del elemento
	 * @param punto	Punto a consultar
	 * @return	true si ese punto está dentro del cuadrado visual del elemento, false en caso contrario
	 */
	public boolean estaPuntoDentro( Point punto ) {
		return punto.x>=x && punto.y>=y && punto.x<=x+tam && punto.y<=y+tam;
	}

	/** Devuelve una copia exacta del elemento pero con estado INTENTO
	 * @return	Devuelve nuevo elemento duplicado
	 */
	public abstract Elemento duplicar();

	/** Devuelve un carácter representativo de cada elemento particular, para poder visualizarlo de forma resumida
	 * @return	Carácter representando ese elemento
	 */
	public abstract char aCaracter();
	
}
