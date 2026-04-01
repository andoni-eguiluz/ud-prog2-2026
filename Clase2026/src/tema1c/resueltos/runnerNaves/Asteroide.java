package tema1c.resueltos.runnerNaves;
import java.awt.Color;
import java.util.ArrayList;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar asteroides y dibujarlos en una ventana gráfica
 */
public class Asteroide extends ObjetoEspacial implements Rotable, Salible, Explotable, Chocable {
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;  // Radio de asteroide
	private double rot = 0.0; // rotación del asteroide (para hacer animación)
	// Atributos para el interfaz explotable
	private static final double TIEMPO_EXPLOSION = 3.0;  // Segundos que dura la explosión
	private double tiempoExplosion; // Tiempo de duración de explosión, activado cuando el asteriode explota
	private boolean explotando;     // false si el asteroide no ha explotado, true en caso contrario
	// Atributo para el interfaz chocable
	private final BoundingCircle BC_AST = new BoundingCircle( 0.0, 0.0, 0.0 );
	
	/** Crea un nuevo asteroide
	 * @param radio	Píxels de radio del asteroide (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del asteroide (en píxels)
	 * @param y	Coordenada y del centro del asteroide (en píxels)
	 */
	public Asteroide( double radio, double x, double y ) {
		super( x, y );
		this.radio = radio;
		BC_AST.setX( x );
		BC_AST.setY( y );
		BC_AST.setRadio( radio );
	}
	
	// Métodos de implementación y redefinidos para choque (cambian el círculo de choque cuando cambian los datos de la nave)
	
	@Override
	public void setX(double x) {
		super.setX(x);
		BC_AST.setX( x );
	}
	
	@Override
	public void setY(double y) {
		super.setY(y);
		BC_AST.setY( y );
	}
	
	@Override
	public Envolvente getEnvolvente() {
		return BC_AST;
	}
	
	
	/** Devuelve el radio del asteroide
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}

	/** Cambia el radio del asteroide. Debe ser mayor que cero
	 * @param radio	Nuevo radio del asteroide (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
		BC_AST.setRadio( radio );  // Cambia el bounding circle
	}

	/** Dibuja el asteroide en una ventana
	 * @param v	Ventana en la que dibujar el asteroide
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		String imagen = "img/asteroid.png";
		float transp = 1.0f;
		// Cambio de dibujado con explosión
		if (explotando) {
			transp = (float) (tiempoExplosion/3.0);   // Relación entre el tiempo que queda y la transparencia (el asteroide se va difuminando)
			imagen = "img/asteroid-roto.png";
		}
		v.dibujaImagen( imagen, x, y, 2*radio/500, rot, transp );  // el gráfico tiene 500 píxels
		if (DIBUJA_ENVOLVENTE) v.dibujaCirculo( x, y, radio, 2f, Color.magenta );  // Pintado a título de referencia de prueba
	}

	/** Rota el asteroide
	 * @param rot	Ángulo a rotar (en radianes)
	 */
	public void rota( double rot ) {
		this.rot += rot;
	}
	
	
	/** Comprueba si el asteroide se sale completamente por el lado izquierdo de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo completamente por la izquierda, false en caso contrario
	 */
	public boolean seSalePorLaIzquierda( VentanaGrafica v ) {
		return x+radio<=0;
	}
	
	@Override
	public void sal( ArrayList<ObjetoEspacial> l ) {
		l.remove( this );
	}

	// Cambios por explosión
	
	@Override
		public void mueve(double segs) {
			super.mueve(segs);
			if (explotando) {
				tiempoExplosion -= segs;
				if (tiempoExplosion<0.0) tiempoExplosion = 0.0;
			}
		}
	
	
	// Interfaz Explotable 
	
	@Override
	public void explota() {
		if (!explotando) {
			explotando = true;
			vY = 0.0; // La nave deja de moverse en vertical cuando ha explotado
			tiempoExplosion = TIEMPO_EXPLOSION;
		}
	}
	
	@Override
	public boolean estaExplotando() {
		return explotando;
	}
	
	@Override
	public boolean haExplotado() {
		return explotando && tiempoExplosion==0.0;
	}
	

	@Override
	public String toString() {
		return "Asteroide " + super.toString() + " (" + radio + ")";
	}
	
	/** Comprueba si el asteroide es igual a otro objeto dado. Se entiende que dos asteroides son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Asteroide) {
			Asteroide p2 = (Asteroide) obj;  // Cast de obj a asteroide2 (lo entenderemos mejor al ver herencia)
			return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los asteroides this y p2
		} else {
			return false;
		}
	}
	
}
