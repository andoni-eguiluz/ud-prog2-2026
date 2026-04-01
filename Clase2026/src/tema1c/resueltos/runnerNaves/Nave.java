package tema1c.resueltos.runnerNaves;
import java.awt.Color;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar naves y dibujarlas en una ventana gráfica
 */
public class Nave extends ObjetoEspacial implements Explotable, Chocable {  // INTERFACE Explotable
	
	public static final double RADIO_NAVE = 25;      // Radio de la nave en píxels (círculo imaginario)
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private boolean subiendo;      // true si la nave está subiendo
	private boolean bajando;       // false si la nave está bajando
	private int canal;             // Número de canal en el que está la nave
	private double segsProteccion; // Segundos que tiene de "escudo" protector la nave
	// Atributos para el interfaz explotable
	private static final Random random = new Random();   // Aleatorio para la imagen de la explosión (se alterna una entre 2 imágenes)
	private static final double TIEMPO_EXPLOSION = 1.5;  // Segundos que dura la explosión
	private double tiempoExplosion; // Tiempo de duración de explosión, activado cuando la nave explota
	private boolean explotando;     // false si la nave no ha explotado, true en caso contrario
	// Atributo para el interfaz chocable
	private final BoundingCircle BC_NAVE = new BoundingCircle( 0.0, 0.0, RADIO_NAVE );
	
	/** Crea una nueva nave
	 * @param x	Coordenada x del centro de la nave (en píxels)
	 * @param y	Coordenada y del centro de la nave (en píxels)
	 */
	public Nave( double x, double y ) {
		super( x, y );
		subiendo = false;
		bajando = false;
		segsProteccion = 0.0;
		explotando = false;
		BC_NAVE.setX( x );
		BC_NAVE.setY( y );
	}
	
	// Métodos de implementación y redefinidos para choque (cambian el círculo de choque cuando cambian los datos de la nave)
	
	@Override
	public void setX(double x) {
		super.setX(x);
		BC_NAVE.setX( x );
	}
	
	@Override
	public void setY(double y) {
		super.setY(y);
		BC_NAVE.setY( y );
	}
	
	@Override
	public Envolvente getEnvolvente() {
		return BC_NAVE;
	}
	
	/** Informa a la nave de que ha chocado 
	 * @param choc	Objeto con el que la nave ha colisionado
	 */
	public void chocaCon( Chocable choc ) {
		if (choc instanceof Asteroide) {
			Asteroide a = ((Asteroide)choc);
			if (!a.estaExplotando()) { // Si el asteroide ya está explotando no importa la colisión. Si está enterito entonces...
				if (getProteccion()<=0.0) { // Choque de nave sin protección
					// Generar explosión
					explota();  // Las naves no se quitan directamente de pantalla, solo después de la explosión
				}
				a.explota();  // El asteroide explota al chocar con una nave (esté o no protegida)
			}
		} else if (choc instanceof Bonus) {
			addProteccion( ((Bonus)choc).getTiempoProteccion() );
		}
	}
	
	public void miraSiEstaEnSuCanal( int CANAL[] ) {
		if (isSubiendo()) {
			if (getY() <= CANAL[getCanal()]) {
				setY( CANAL[getCanal()]);
				setVY( 0.0 );
				// setSubiendo( false );  Lo pone setVY
			}
		} else if (isBajando()) {
			if (getY() >= CANAL[getCanal()]) {
				setY( CANAL[getCanal()]);
				setVY( 0.0 );
				// setBajando( false );  Lo pone setVY
			}
		}
	}
	
	/** Devuelve el tiempo de protección que le queda a la nave
	 * @return	tiempo de protección en segundos
	 */
	public double getProteccion() {
		return segsProteccion;
	}

	/** Cambia la información de canal de la nave
	 * @param proteccion	Tiempo de protección a añadir (en segundos)
	 */
	public void addProteccion( double proteccion ) {
		this.segsProteccion += proteccion;
	}
	
	/** Mueve la nave y modifica el tiempo de protección (si procede)
	 * @param	segs	Tiempo de movimiento (se resta al tiempo de protección)
	 * @see tema4.runnerSinInterfaces.ObjetoEspacial#mueve(double)
	 */
	@Override
	public void mueve(double segs) {
		super.mueve(segs);
		// Modificación de protección
		if (segsProteccion>0.0) {
			segsProteccion -= segs;
			if (segsProteccion<0.0) segsProteccion = 0.0;
		}
		// Modificación de explosión
		if (explotando) {
			tiempoExplosion -= segs;
			if (tiempoExplosion<0.0) tiempoExplosion = 0.0;
		}
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
		if (explotando) return false;  // Al estar explotando no está ni subiendo ni bajando
		return subiendo;
	}

	/** Devuelve la información de si la nave está bajando
	 * @return	true si está bajando, false si no está bajando
	 */
	public boolean isBajando() {
		if (explotando) return false;  // Al estar explotando no está ni subiendo ni bajando
		return bajando;
	}

	/** Dibuja la nave en una ventana, con inclinación hacia arriba si está subiendo, hacia abajo si está bajando, o sin inclinación si ni sube ni baja
	 * @param v	Ventana en la que dibujar la nave
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		double angulo = 0.0;
		if (subiendo) {
			angulo = -0.2;  // la nave quiere dibujarse subiendo 0.2 radianes
		} else if (bajando) {
			angulo = +0.2;  // la nave quiere dibujarse bajando 0.2 radianes
		}
		String imagen = "img/nave.png";    // Dibujo de nave normal
		if (vX>0.0) {
			imagen = "img/nave-prop.png";  // Si la nave avanza gráfico con propulsores traseros
		} else if (vX<0.0) {
			imagen = "img/nave-frena.png"; // Si la nave frena gráfico con propulsores delanteros
		}
		if (!explotando) {
			// Dibujado de nave con posible escudo
			v.dibujaImagen( imagen, x, y, 50.0/500.0, angulo, 1.0f );  // el gráfico tiene 500 píxels y la nave quiere dibujarse con 50
			if (segsProteccion>0.0) {
				float transp = 1.0f;
				imagen = "img/escudo.png";
				if (segsProteccion<2.0) transp = (float) (segsProteccion/2.0);  // Los últimos 2 segundos decrece visualmente la opacidad del escudo
				v.dibujaImagen( imagen, x, y, 50.0/500.0, 0.0, transp );  // burbuja escudo (transparente creciente en el último segundo)
			}
			if (DIBUJA_ENVOLVENTE) v.dibujaCirculo( x, y, 25, 2f, Color.orange );  // Pintado a título de referencia de prueba
		} else {
			// Dibujado explosión
			imagen = random.nextInt(2)==0 ? "img/explosion1.png" : "img/explosion2.png";
			float transp = 1.0f;
			if (tiempoExplosion<1.0) transp = (float) tiempoExplosion;
			v.dibujaImagen( imagen, x, y, 50.0/500.0, 0.0, transp );  // explosión en vez de nave (transparencia creciente en el último segundo)
		}
	}

	// Modificaciones por explosión
	
	@Override
	public void setVY(double vy) {
		super.setVY(vy);
		if (explotando) {  // Si está explotando la velocidad vertical se fija donde haya explotado
			vY = 0.0;
		} else {  // Cambia la información de si la nave está subiendo o bajando
			bajando = (vy>0.0);
			subiendo = (vy<0.0);
		}
	}
	
	@Override
		public void setVX(double vx) {
			if (explotando) return;  // Si está explotando la velocidad no cambia
			super.setVX( vx );
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
	
	// Métodos genéricos (Object)
	
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
