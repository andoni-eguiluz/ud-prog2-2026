package tema1c.resueltos.runnerNaves;
import java.awt.Color;
import java.util.ArrayList;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar bonus y dibujarlos en una ventana gráfica
 */
public class Bonus extends ObjetoEspacial implements Rotable, Salible, Chocable {
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;                // Radio de bonus
	private double rot = 0.0;            // Rotación del bonus (para hacer animación)
	private double tiempoProteccionSgs;  // Tiempo de protección en segundos
	// Atributo para el interfaz chocable
	private final BoundingCircle BC_BONUS = new BoundingCircle( 0.0, 0.0, 0.0 );

	/** Crea un nuevo bonus
	 * @param radio	Píxels de radio del bonus (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del bonus (en píxels)
	 * @param y	Coordenada y del centro del bonus (en píxels)
	 * @param tiempoProteccion	Tiempo de protección que da coger el bonus (en segundos)
	 */
	public Bonus( double radio, double x, double y, double tiempoProteccion ) {
		super( x, y );
		this.radio = radio;
		this.tiempoProteccionSgs = tiempoProteccion;
		BC_BONUS.setX( x );
		BC_BONUS.setY( y );
		BC_BONUS.setRadio( radio );
	}
	
	// Métodos de implementación y redefinidos para choque (cambian el círculo de choque cuando cambian los datos de la nave)
	
	@Override
	public void setX(double x) {
		super.setX(x);
		BC_BONUS.setX( x );
	}
	
	@Override
	public void setY(double y) {
		super.setY(y);
		BC_BONUS.setY( y );
	}
	
	@Override
	public Envolvente getEnvolvente() {
		return BC_BONUS;
	}
	
	// Sets y gets
	
	/** Devuelve el radio del bonus
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}

	/** Cambia el radio del bonus. Debe ser mayor que cero
	 * @param radio	Nuevo radio del bonus (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
		BC_BONUS.setRadio( radio );  // Cambia el bounding circle
	}

	/** Devuelve el tiempo de protección del bonus
	 * @return	Tiempo de protección en segundos
	 */
	public double getTiempoProteccion() {
		return tiempoProteccionSgs;
	}

	/** Cambia el tiempo de protección del bonus. Debe ser mayor que cero
	 * @param radio	Nuevo tiempo de protección del bonus en segundos (debe ser mayor que cero)
	 */
	public void setTiempoProteccion(double tiempoProteccion) {
		this.tiempoProteccionSgs = tiempoProteccion;
	}

	/** Dibuja el bonus en una ventana
	 * @param v	Ventana en la que dibujar el bonus
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaImagen( "img/UD-green.png", x, y, 2*radio/500, rot, 1.0f );  // el gráfico tiene 500 píxels
		if (DIBUJA_ENVOLVENTE) v.dibujaCirculo( x, y, radio, 2f, Color.magenta );  // Pintado a título de referencia de prueba
	}
	
	/** Rota el bonus
	 * @param rot	Ángulo a rotar (en radianes)
	 */
	public void rota( double rot ) {
		this.rot += rot;
	}
	
	/** Comprueba si el bonus se sale completamente por el lado izquierdo de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo completamente por izquierda, false en caso contrario
	 */
	public boolean seSalePorLaIzquierda( VentanaGrafica v ) {
		return x+radio<=0;
	}

	@Override
	public void sal( ArrayList<ObjetoEspacial> l ) {
		l.remove( this );
	}
	
	@Override
	public String toString() {
		return "bonus " + super.toString() + " (" + radio + ")";
	}
	
	/** Comprueba si el bonus es igual a otro objeto dado. Se entiende que dos bonus son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bonus) {
			Bonus p2 = (Bonus) obj;  // Cast de obj a bonus2 (lo entenderemos mejor al ver herencia)
			return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los bonus this y p2
		} else {
			return false;
		}
	}
	
}
