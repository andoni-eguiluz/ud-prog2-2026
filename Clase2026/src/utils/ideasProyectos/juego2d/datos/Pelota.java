package utils.ideasProyectos.juego2d.datos;
import java.awt.Color;
import java.awt.geom.Point2D;

import utils.ideasProyectos.juego2d.utils.Fisica;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

// Con (*) las novedades

/** Clase que permite crear y gestionar pelotas
 */
public class Pelota extends ObjetoMovil {
	private double radio;  // Radio de pelota
	private boolean bota;
	
	/** Constructor de pelota por defecto: nombre vacío, centro (0,0), radio 10, color azul, bota true
	 */
	public Pelota() {
		this( 0, 0, 10, Color.blue, true );
	}
	
	/** Constructor de pelota con datos
	 * @param x	Coordenada x del centro de la pelota
	 * @param y	Coordenada y del centro de la pelota
	 * @param radio	Radio de la pelota
	 * @param color	Color de la pelota
	 * @param bota	Información de si la pelota bota (true) o no (false)
	 */
	public Pelota(double x, double y, double radio, Color color, boolean bota) {
		super( x, y, color );
		this.radio = radio;
		this.bota = bota;
	}
	
	/** Constructor de pelota con datos mínimos, suponiendo color azul y bota = true
	 * @param x	Coordenada x del centro de la pelota
	 * @param y	Coordenada y del centro de la pelota
	 * @param radio	Radio de la pelota
	 */
	public Pelota(double x, double y, double radio) {
		this( x, y, radio, Color.blue, true );
	}
	
	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	/** Calcula el volumen de la pelota partiendo de su información de radio
	 * @return	Volumen de la pelota suponiendo una esfera perfecta
	 */
	@Override
	public double getVolumen() {
		return 4.0/3*Math.PI*radio*radio*radio;
	}
	
	/** Calcula el área de la pelota partiendo de su información de radio
	 * @return	Area de la pelota suponiendo una circunferencia perfecta
	 */
	@Override
	public double getArea() {
		return Math.PI*radio*radio;
	}
	
	/** Dibuja la pelota en una ventana, en el color correspondiente de la pelota (por defecto, negro)
	 * @param v	Ventana en la que dibujar la pelota
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaCirculo( x, y, radio, 1.5f, color );
		super.dibuja( v );  // Para dibujar la velocidad si procede
	}
	
	/** Borra la pelota en una ventana
	 * @param v	Ventana en la que borrar la pelota
	 */
	@Override
	public void borra( VentanaGrafica v ) {
		v.borraCirculo( x, y, radio, 1.5f );
	}

	/** Ajusta la pelota al suelo, si se ha "pasado" del suelo. Ajusta la velocidad a la que tenía cuando lo tocó.
	 * @param v	Ventana de la que ajustar al suelo
	 * @param dibujar	true si se quiere dibujar la pelota, false en caso contrario
	 */
	public void corrigeChoqueInferior( VentanaGrafica v, boolean dibujar ) {
		double dondeToca = v.getAltura() - radio;  // Coordenada y en la que se toca el suelo, a la que hay que ajustar
		if (dondeToca >= y) return;  // Si no está pasando el suelo, no se hace nada 
		setY( dondeToca );
		// Si estaba cayendo con una velocidad positiva (hacia abajo), entonces la velocidad real que tenía cuando
		// "tocó" el suelo era algo menor. Corregimos posición y velocidad (aproximadamente)
		if (velYInicial>0 && velocidadY>0) {
			double tiempoChoque = Fisica.calcTiempoHastaEspacio( velYInicial, antY, Fisica.getGravedad(), dondeToca);
			if (tiempoChoque > 0) {  // Si hay un error en el cálculo no se aplica corrección
				if (dibujar) borra( v );
				long tiempoMsgs = Math.round(tiempoChoque*1000.0);
				setX( Fisica.calcEspacio( antX, tiempoMsgs, velXInicial ) );  // Corrige la posición
				setVelocidadY( Fisica.calcVelocidad( velYInicial, tiempoMsgs, Fisica.getGravedad() )); // Corrige la velocidad
				if (dibujar) dibuja( v );
			}
			// System.out.println( "Datos: " + tiempoChoque + " " + velYInicial + " " + antY + " " + radio + " " + (velYInicial*velYInicial - 2.0*Fisica.GRAVEDAD*(antY-(v.getAltura()-radio))) );
		}
	}
	
	@Override
	public void corrigeChoqueLateral(VentanaGrafica v, boolean dibujar) {
		if (dibujar) borra( v );
		if (x<radio) {
			setX( radio );
		} else if (x>v.getAnchura()-radio){
			setX( v.getAnchura() - radio );
		}
		if (dibujar) dibuja( v );
	}
	
	/** Ajusta la pelota al lateral
	 * @param v	Ventana de la que ajustar al lateral
	 * @param izquierda	true para izquierda, false para derecha
	 * @param dibujar	true si se quiere dibujar la pelota, false en caso contrario
	 */
	public void corrigeChoqueLateral( VentanaGrafica v, boolean izquierda, boolean dibujar ) {
		// Corregimos al lateral sin tocar la velocidad
		if (dibujar) borra( v );
		if (izquierda) {
			setX( radio );
		} else {
			setX( v.getAnchura() - radio );
		}
		if (dibujar) dibuja( v );
	}
	
	@Override
	public void corrigeChoqueVertical(VentanaGrafica v, boolean dibujar) {
		if (dibujar) borra( v );
		if (y > v.getAltura()-radio) {
			setY( v.getAltura()-radio );
		}
		if (dibujar) dibuja( v );
	}

	/** Detecta el choque de la pelota con los bordes de la ventana
	 * @param v	Ventana con la que probar el choque
	 * @return	Devuelve un número formado por la suma de: 0 si no choca, 1 si choca con la izquierda, 2 con la derecha, 4 arriba, 8 abajo.
	 */
	@Override
	public int chocaConBorde( VentanaGrafica v ) {
		int ret = 0;
		if (x-radio<=0) ret += 1;
		if (x+radio>=v.getAnchura()) ret += 2;
		if (y-radio<=0) ret += 4;
		if (y+radio>=v.getAltura()) ret += 8;
		return ret;
	}
	
	/** Detecta el choque de la pelota con otro objeto
	 * @param pelota2	Pelota con la que probar el choque
	 * @return	Devuelve null si no chocan, un vector con forma de punto indicando el ángulo y amplitud del choque sobre la pelota en curso
	 */
	@Override
	public Point2D chocaConObjeto( ObjetoMovil objeto2 ) {
		if (objeto2 instanceof Pelota) {
			Pelota pelota2 = (Pelota) objeto2;
			Point2D p = new Point2D.Double( pelota2.x - x, pelota2.y - y );
			double dist = p.distance(0,0);
			double moduloChoque = radio + pelota2.radio - dist;
			if (moduloChoque < 0) return null;
			p.setLocation( p.getX() * moduloChoque / dist, p.getY() * moduloChoque / dist );
			return p;
		} else if (objeto2 instanceof Bloque) {  // El cálculo de choque con bloque lo gestiona el bloque
			Point2D choca = objeto2.chocaConObjeto( this );
			if (choca!=null)
				choca.setLocation( -choca.getX(), -choca.getY() );
			return choca;
		} else if (objeto2 instanceof UDcito) {  // Todo el cálculo de choque con UDcito lo gestiona él
			Point2D choca = objeto2.chocaConObjeto( this );
			if (choca!=null)
				choca.setLocation( -choca.getX(), -choca.getY() );
			return choca;
		} else {
			return null;
		}
	}
	
	/** Comprueba si la pelota incluye a un punto dado
	 * @param punto	Punto a chequear
	 * @return	true si el punto está dentro de la pelota, false en caso contrario
	 */
	@Override
	public boolean contieneA( Point2D punto ) {
		double dist = punto.distance( x, y );
		return dist <= radio;
	}

	@Override
	public boolean isBota() {
		return bota;
	}
	
	@Override
	public boolean isFijo() {
		return false;
	}
	
	@Override
	public String toString() {
		return String.format( "Pelota %1s (%2$7.2f,%3$7.2f) R=%4$5.1f Vel.=(%5$6.3f,%6$6.3f)", nombre, x, y, radio, velocidadX, velocidadY );
	}

}
