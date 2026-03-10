package tema1b.ejemplos.pong;

import java.awt.Point;
import java.awt.geom.Point2D;

/** Clase con funcionalidad static de física básica de movimiento y colisiones elásticas sencillas
 * @author andoni.eguiluz at ingenieria.deusto.es
 *
 */
public class Fisica {

	//=======================================
	// Movimiento
	//=======================================
	
	/** Devuelve la distancia entre dos puntos
	 * @param p1	punto 1
	 * @param p2	punto 2
	 * @return	Distancia entre ambos (positiva)
	 */
	public static double distancia (Point p1, Point p2) {
		return distancia( p1.x, p1.y, p2.x, p2.y );
	}
	
	/** Devuelve la distancia entre dos puntos
	 * @param x1	x del punto 1
	 * @param y1	y del punto 1
	 * @param x2	x del punto 2
	 * @param y2	y del punto 2
	 * @return	Distancia entre ambos (positiva)
	 */
	public static double distancia( double x1, double y1, double x2, double y2 ) {
		return Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) ); 
	}
	
	/** Calcula el movimiento de espacio en función de un desplazamiento lineal sin aceleración
	 * @param espacio	Espacio original (en píxels)
	 * @param velocidad	Velocidad (en píxels por segundo)
	 * @param segs	Segundos transcurridos en el movimimento
	 * @return	Nuevo espacio
	 */
	public static double calcEspacio( double espacio, double velocidad, double segs ) {
		// s2 = s1 + v*t
		return espacio + velocidad * segs;
	}
	
	/** Convierte una coordenada cartesiana en una polar
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @return	Coordenada polar (módulo, argumento) correspondiente. Si el valor es 0,0 devuelve argumento 0.
	 */
	public static Polar cartAPolar( double x, double y ) {
		return new Polar( Math.sqrt( x*x + y*y ), Math.atan2( y, x ) );
	}
	
	
	//=======================================
	// Colisiones
	//=======================================
	
	/** Calcula el posible choque entre un círculo y un rectángulo
	 * @param rectangulo	Rectángulo que queremos comprobar si choca
	 * @param circulo	Círculo que queremos comprobar si choca
	 * @return	true si están chocando, false en caso contrario
	 */
	public static boolean hayChoque( Rectangulo rectangulo, Circulo circulo ) {
		// Choque lateral con pala
		if (circulo.getY() >= rectangulo.getY()-rectangulo.getAltura()/2 &&
			circulo.getY() <= rectangulo.getY()+rectangulo.getAltura()/2) {  // La y de la bola está dentro de la pala...
			if (circulo.getX()-circulo.getRadio()<=rectangulo.getX()+rectangulo.getAnchura()/2 &&
				circulo.getX()+circulo.getRadio()>=rectangulo.getX()-rectangulo.getAnchura()/2 ) {  // ...y la x lateral de la bola está dentro de la pala
				return true;
			}
		}
		// Choque vertical con pala
		if (circulo.getX() >= rectangulo.getX()-rectangulo.getAnchura()/2 &&
			circulo.getX() <= rectangulo.getX()+rectangulo.getAnchura()/2) {  // La x de la bola está dentro de la pala...
			if (circulo.getY()-circulo.getRadio()<=rectangulo.getY()+rectangulo.getAltura()/2 &&
				circulo.getY()+circulo.getRadio()>=rectangulo.getY()-rectangulo.getAltura()/2 ) {  // ...y la y borde de la bola está dentro de la pala: choque
				return true;
			}
		}
		// Coche con esquina: calculamos las distancias del centro de la bola a las cuatro esquinas
		double dist1 = Fisica.distancia( circulo.getX(), circulo.getY(), rectangulo.getX()+rectangulo.getAnchura()/2, rectangulo.getY()+rectangulo.getAltura()/2 );
		double dist2 = Fisica.distancia( circulo.getX(), circulo.getY(), rectangulo.getX()+rectangulo.getAnchura()/2, rectangulo.getY()-rectangulo.getAltura()/2 );
		double dist3 = Fisica.distancia( circulo.getX(), circulo.getY(), rectangulo.getX()-rectangulo.getAnchura()/2, rectangulo.getY()+rectangulo.getAltura()/2 );
		double dist4 = Fisica.distancia( circulo.getX(), circulo.getY(), rectangulo.getX()-rectangulo.getAnchura()/2, rectangulo.getY()-rectangulo.getAltura()/2 );
		if (dist1<=circulo.getRadio() || dist2<=circulo.getRadio() || dist3<=circulo.getRadio() || dist4<=circulo.getRadio()) { // Si alguna de esas distancias es inferior al radio, hay choque
			return true;
		}
		return false;  // Si no hay ninguno de los tres choques, es que no chocan
	}

		private static double TIEMPO_RETROCESO = 0.0001; // Unidad de tiempo que retrocedemos (en segundos) para hacer el cálculo preciso de punto de choque
	/** Aplica física de choque de rebote entre dos objetos
	 * @param rectangulo	Objeto rectángulo que choca con un círculo y tiene masa infinita (su velocidad no se ve afectada)
	 * @param circulo	Objeto círculo que choca con el rectángulo y tiene comportamiento perfectamente elástico (sus velocidades se ven afectadas)
	 * @param tiempoMovto	Tiempo del último movimiento en segundos (en ese anterior movimiento no había choque), permite calcular las correcciones de choque por física de baja precisión (la bola se mete "dentro" del rectángulo)
	 */
	public static void aplicaReboteCirculoElastico( Rectangulo rectangulo, Circulo circulo, double tiempoMovto ) {
		// Para calcular la física de colisión necesitamos saber el punto aproximado en el que ocurrió la colisión: en función de eso cambiarán de una forma o de otra las velocidades
		// 1. Hacemos un "slow backwards" del movimiento de rectángulo y círculo hasta que encontremos el punto de choque aproximado ("sacando" de esa forma la bola de la pala)
		// TODO Este movimiento puede mejorarse con aproximaciones sucesivas, como veremos en Programación III. Ahora lo hacemos "a mano", retrocediendo poco a poco el movimiento
		boolean aunChocan = hayChoque( rectangulo, circulo );
		if (!aunChocan) {  // No chocan desde el principio: este método no tiene nada que hacer
			return;
		}
		double tiempoRetroceso = 0.0;
		double vXInicialCirculo = circulo.getVX();
		double vYInicialCirculo = circulo.getVY();
		double vXInicialRect = rectangulo.getVX();
		double vYInicialRect = rectangulo.getVY();
		circulo.setVX( -vXInicialCirculo );  // Invertimos la velocidad del círculo
		circulo.setVY( -vYInicialCirculo ); 
		rectangulo.setVX( -vXInicialRect );  // Invertimos la velocidad del rectángulo
		rectangulo.setVY( -vYInicialRect ); 
		while (aunChocan) {  // Mientras sigan chocando, retroceder el movimiento (este bucle probablemente se ejecute muchas veces si los objetos se mueven rápido)
			tiempoRetroceso += TIEMPO_RETROCESO;
			circulo.mueve( TIEMPO_RETROCESO );  // Movemos círculo y rectángulo hacia atrás
			rectangulo.mueve( TIEMPO_RETROCESO );
			aunChocan = hayChoque( rectangulo, circulo ); // ¿Siguen chocando?
		}
		// 2. Restauramos velocidades iniciales 
		circulo.setVX( vXInicialCirculo );
		circulo.setVY( vYInicialCirculo ); 
		rectangulo.setVX( vXInicialRect );
		rectangulo.setVY( vYInicialRect );
		// 3. En este punto, círculo y rectángulo no chocan. Calculamos el punto aproximado donde sí lo hacen
		// y en función de eso cambiamos las velocidades del círculo (el rectángulo se supone de masa infinita, por tanto no afectado por el choque)
		// 3a. Comprobamos choque lateral
		if (circulo.getY() >= rectangulo.getY()-rectangulo.getAltura()/2 &&
			circulo.getY() <= rectangulo.getY()+rectangulo.getAltura()/2) {  // La y de la bola está dentro de la pala: choque lateral
			// if (circulo.getX()-circulo.getRadio()>rectangulo.getX()+rectangulo.getAnchura()/2) {  // Se podría comprobar si el choque es por la derecha
			// if (circulo.getX()+circulo.getRadio()<rectangulo.getX()-rectangulo.getAnchura()/2) {  // o es por la izquierda, pero el cálculo va a ser el mismo
			circulo.setVX( -vXInicialCirculo + vXInicialRect );  // Restauramos la velocidad original invertida (por el rebote con elasticidad 100%) añadiendo la velocidad que le aporte el rectángulo (si la tiene)
			System.out.println( "Choque lateral" ); // TODO quitar
		} 
		// 3b. Comprobamos choque vertical
		else if (circulo.getX() >= rectangulo.getX()-rectangulo.getAnchura()/2 &&
				circulo.getX() <= rectangulo.getX()+rectangulo.getAnchura()/2) {  // La x de la bola está dentro de la pala: choque vertical
			// Igualmente podríamos comprobar si es superior o inferior, pero el cálculo es el mismo:
			circulo.setVY( -vYInicialCirculo + vYInicialRect );  // Restauramos la velocidad original invertida (por el rebote con elasticidad 100%) añadiendo la velocidad que le aporte el rectángulo (si la tiene)
			System.out.println( "Choque vertical" ); // TODO quitar
		}
		// 3c. Como hay choque seguro, si no es horizontal ni vertical es que es diagonal (con una de las esquinas) que es el cálculo físico más complejo porque hay que calcular la normal al choque
		else {
			Point2D vectorImpacto = null; // Para ver el vértice con el que choca
			double distMax = Double.MAX_VALUE;
			for (double anch : new double[] {-1.0, 1.0}) {
				for (double alt : new double[] {-1.0, 1.0}) {  // Bucles para las 4 esquinas con las que puede chocar la bola
					Point2D esquina = new Point2D.Double( rectangulo.getX()+anch*rectangulo.getAnchura()/2, rectangulo.getY()+alt*rectangulo.getAltura()/2 );
					double dist = Fisica.distancia( circulo.getX(), circulo.getY(), esquina.getX(), esquina.getY() );
					if (dist<distMax) {  // Este puede ser el vértice del choque
						distMax = dist;
						vectorImpacto = new Point2D.Double( esquina.getX()-circulo.getX(), esquina.getY()-circulo.getY() );
					}
				}
			}
			Polar vectorImpactoP = new Polar( vectorImpacto );
			double anguloDesv = vectorImpactoP.getArgumento();
			Point2D vectorVel = new Point2D.Double( circulo.getVX(), circulo.getVY() );
			Polar vectorVelP = new Polar( vectorVel );
			vectorVelP.rotar( -anguloDesv );
			Point2D velRotada = vectorVelP.toPoint();
			velRotada.setLocation( -velRotada.getX(), velRotada.getY() );  // Invierte la x (rebote)
			Polar velRotadaP = new Polar( velRotada );
			velRotadaP.rotar( anguloDesv ); // Volver a poner el vector en términos de x,y original
			Point2D velTrasRebote = velRotadaP.toPoint();
			circulo.setVX( velTrasRebote.getX() );
			circulo.setVY( velTrasRebote.getY() );
			// Flechas de apoyo si se quieren ver los vectores en pantalla (habría que recibir la ventana como parámetro)
			// vent.dibujaFlecha( circulo.getX(), circulo.getY(), circulo.getX()+vectorImpacto.getX()*2, circulo.getY()+vectorImpacto.getY()*2, 1.5f, Color.magenta, 10 ); // Vector de ángulo de contacto
			// vent.dibujaFlecha( circulo.getX(), circulo.getY(), circulo.getX()+circulo.getVX()/2, circulo.getY()+circulo.getVY()/2, 1.5f, Color.green, 10 ); // Vector de velocidad original
			// vent.dibujaFlecha( circulo.getX(), circulo.getY(), circulo.getX()+velRotada.getX()/2, circulo.getY()+velRotada.getY()/2, 1.5f, Color.orange, 10 ); // Vector de velocidad girado
			// vent.repaint();
			System.out.println( "Choque diagonal" ); // TODO quitar
		}
		// 4. Como hemos vuelto "hacia atrás el movimiento", completamos el movimiento restante dentro del fotograma moviendo "hacia adelante" con las nuevas velocidades
		circulo.mueve( tiempoRetroceso );
		rectangulo.mueve( tiempoRetroceso );
	}

	//=======================================
	// Clase interna para coordenadas polares en vez de cartesianas (mejor para ciertos cálculos)
	//=======================================
	
	/** Clase interna para trabajo con vectores en notación polar en lugar de cartesiana
	 * Permite trabajar con puntos/vectores en modo módulo/argumento
	 * @author andoni.eguiluz @ ingenieria.deusto.es
	 */
	public static class Polar {
		private double modulo;
		private double argumento;
		/** Crea un vector en formato polar
		 * @param modulo	Módulo del vector (positivo)
		 * @param argumento	Ángulo del vector (en radianes)
		 */
		public Polar( double modulo, double argumento ) {
			this.modulo = modulo;
			this.argumento = argumento;
		}
		/** Crea un vector en formato polar desde un punto cartesiano
		 * @param punto	en formato cartesiano
		 */
		public Polar( Point2D punto ) {
			this.modulo = Math.sqrt( punto.getX()*punto.getX() + punto.getY()*punto.getY() );
			this.argumento = Math.atan2( punto.getY(), punto.getX() );
		}
		public double getArgumento() {
			return argumento;
		}
		public double getModulo() {
			return modulo;
		}
		/** Convierte el vector en un punto cartesiano (x,y)
		 * @return	Punto correspondiente al vector polar
		 */
		public Point2D toPoint() {
			Point2D ret = new Point2D.Double( modulo*Math.cos(argumento), modulo*Math.sin(argumento) );
			return ret;
		}
		/** Rota el vector
		 * @param rotacionRad	Ángulo de rotación (en radianes)
		 */
		public void rotar( double rotacionRad ) {
			argumento += rotacionRad;
		}
		/** Cambia el módulo del vector
		 * @param nuevoModulo	Nuevo valor de módulo (debe ser positivo)
		 */
		public void setModulo( double nuevoModulo ) {
			modulo = nuevoModulo;
		}
		@Override
		public String toString() {
			return "|" + modulo + "| " + argumento + " rad";
		}
	}
	
}
