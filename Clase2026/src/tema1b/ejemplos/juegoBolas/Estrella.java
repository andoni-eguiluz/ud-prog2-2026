package tema1b.ejemplos.juegoBolas;

import java.awt.Point;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Estrella extends ObjetoJuego {
	// double x; double y;  - se heredan, no hace falta definirlos 
	private int tamanyo; // Tamaño de la estrella (píxels de alto y de ancho)
	private int puntos;
	
	public Estrella(double x, double y, int tamanyo, int puntos) {
		super( x, y );
		this.tamanyo = tamanyo;
		this.puntos = puntos;
	}
	
	/** Devuelve el tamaño en píxels de la estrella
	 * @return	Píxels de ancho y alto de la estrella
	 */
	public int getTamanyo() {
		return tamanyo;
	}
	/** Modifica el tamaño de la estrella
	 * @param tamanyo	Nuevo tamaño (píxels de ancho y alto)
	 */
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	/** Dibuja la estrella
	 * @param v	Ventana en la que dibujar
	 */
	public void dibuja( VentanaGrafica v ) {
		// v.dibujaImagen( "ud-estrella.png", x, y, tamanyo, tamanyo, 1.0, 0, 1.0f );
		// Cambio de dibujado con animación
		v.dibujaImagen( "img/ud-estrella.png", x, y, tamanyo, tamanyo, zoom, rotacion, transparencia );
	}
	
	@Override
	/** Comprueba si un punto está dentro o no de la pelota
	 * @param p	punto a comprobar
	 * @return	true si está dentro, false si no
	 */
	public boolean contieneA( Point p ) {
		double distancia = Math.sqrt( (p.x-this.x) * (p.x-this.x) + (p.y-this.y) * (p.y-this.y) );
		return distancia <= tamanyo/2;
	}
	


	@Override
	public String toString() {
		return "(" + x + "," + y + ") T" + tamanyo + " Pt" + puntos;
	}
	
	@Override
	public boolean equals(Object obj) {
		Estrella e2 = (Estrella) obj;
		return x==e2.x && y==e2.y;
	}

	// Añadido de animación de la estrella
	// Atributos para animar
	private float transparencia = 1.0f;
	private double zoom = 1.0;
	private double rotacion = 0.0;
	private long milis = 0;
	/** Prepara la animación gráfica de la estrella (se visualizará al ser dibujada), con la siguiente lógica:
	 * - Cada segundo hace un giro completo
	 * - Cada segundo hace un zoom de 100% a 200% y el siguiente segundo de 200% a 100% y así sucesivamente
	 * - Cada segundo hace una transición de opaco a transparente y el siguiente segundo de transparente a opaco, y así sucesivamente
	 * @param milis	Milisegundos que han pasado desde la última animación
	 */
	public void anima( long milis ) {
		rotacion += 2 * Math.PI * milis / 1000.0;  // Una rotación completa (2PI radianes) cada 1000 milis
		this.milis += milis;  // Guardamos tiempo de animación
		float incrTransparencia = -1.0f * milis / 1000.0f;  // Un decremento completo de transparencia cada 1000 milis
		double incrZoom = 1.0 * milis / 1000.0;  // Un incremento completo de 1.0 a 2.0 de zoom cada 1000 milis
		if ((this.milis / 1000) % 2 == 1) {  // Segundos impares
			incrTransparencia = -incrTransparencia;
			incrZoom = -incrZoom;
		}
		transparencia += incrTransparencia;
		if (transparencia<0f) transparencia += (-transparencia); // Corrige la transparencia si se pasa de límites
		else if (transparencia>1.0f) transparencia -= (transparencia-1.0f);
		zoom += incrZoom;
		if (zoom<1.0) zoom = 1.0 + (1.0-zoom); // Corrige el zoom si se pasa de límites
		else if (zoom>2.0) zoom -= (zoom-2.0);
	}
	
}
