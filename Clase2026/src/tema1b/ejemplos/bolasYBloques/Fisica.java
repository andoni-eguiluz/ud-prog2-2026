package tema1b.ejemplos.bolasYBloques;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase con funcionalidad static de física básica de movimiento y colisiones elásticas de rectángulos (Bloques) y círculos (Bolas)
 * @author andoni.eguiluz at ingenieria.deusto.es
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

	/** Calcula el choque entre dos objetos
	 * @param ventana	Ventana en la que ocurre el choque
	 * @param objeto1	Objeto 1 que choca
	 * @param objeto2	Objeto 2 que choca
	 * @param milis	Milisegundos que pasan en el paso de movimiento
	 * @param visualizarChoque	(opcional) true para visualizar la info del choque en la ventana y en consola
	 */
	public static void calcChoqueEntreObjetos( VentanaGrafica ventana, ObjetoMovil objeto1, ObjetoMovil objeto2, double milis, boolean... visualizarChoque ) {
		if (objeto1 instanceof Bola && objeto2 instanceof Bola) {
			calcChoqueEntreObjetos( ventana, (Bola) objeto1, (Bola) objeto2, milis, visualizarChoque.length>0 && visualizarChoque[0] );
		} else if (objeto1 instanceof Bloque && objeto2 instanceof Bloque) {
			calcChoqueEntreObjetos( ventana, (Bloque) objeto1, (Bloque) objeto2, milis );
		} else if (objeto1 instanceof Bloque && objeto2 instanceof Bola) {
			calcChoqueEntreObjetos( ventana, (Bloque) objeto1, (Bola) objeto2, milis );
		} else if (objeto1 instanceof Bola && objeto2 instanceof Bloque) {
			calcChoqueEntreObjetos( ventana, (Bloque) objeto2, (Bola) objeto1, milis );
		}
	}
	
	/** Calcula el choque entre dos objetos
	 * @param ventana	Ventana en la que ocurre el choque
	 * @param pelota	Objeto 1 que choca
	 * @param pelota2	Objeto 2 que choca
	 * @param milis	Milisegundos que pasan en el paso de movimiento
	 * @param visualizarChoque	true para visualizar la info del choque en la ventana y en consola
	 */
	public static void calcChoqueEntreObjetos( VentanaGrafica ventana, Bola pelota, Bola pelota2, double milis, boolean visualizarChoque ) {
		Polar choque = pelota.vectorChoqueConObjeto( pelota2 );  // Dirección y magnitud de choque
		if (choque==null) return;  // No hay choque
		Point2D choqueLinea = choque.toPoint();
		if (visualizarChoque) {
			System.out.println( "Choque entre " + pelota + " y " + pelota2 + " con vector " + choque );
		}
		Polar tangente = new Polar( choque );
		tangente.transformaANuevoEje( Math.PI/2.0 );  // La tangente es la del choque girada 90 grados
		Point2D tangenteXY = tangente.toPoint();
		Point2D.Double velPelotaXY = new Point.Double( pelota.getVelX(), pelota.getVelY() );
		Point2D.Double velPelota2XY = new Point.Double( pelota2.getVelX(), pelota2.getVelY() );
		Polar velPelota = new Polar( velPelotaXY );
		Polar velPelota2 = new Polar( velPelota2XY );
		velPelota.transformaANuevoEje( tangenteXY );
		velPelota2.transformaANuevoEje( tangenteXY );
		Point2D nuevaVelPelota = velPelota.toPoint();
		Point2D nuevaVelPelota2 = velPelota2.toPoint();
		double[] velChoque = Fisica.calcChoque( pelota.getVolumen(), nuevaVelPelota.getY(), pelota2.getVolumen(), nuevaVelPelota2.getY() );
		nuevaVelPelota.setLocation( nuevaVelPelota.getX(), velChoque[0] );
		nuevaVelPelota2.setLocation( nuevaVelPelota2.getX(), velChoque[1] );
		if (visualizarChoque) {
			// Velocidades antes del choque
			ventana.dibujaFlecha( pelota.getX(), pelota.getY(), pelota.getX()+velPelotaXY.getX()/1000*milis, pelota.getY()+velPelotaXY.getY()/1000*milis, 4.0f, Color.green );
			ventana.dibujaFlecha( pelota2.getX(), pelota2.getY(), pelota2.getX()+velPelota2XY.getX()/1000*milis, pelota2.getY()+velPelota2XY.getY()/1000*milis, 4.0f, Color.green );
			// Eje de choque (magenta) y tangente (negro)
			ventana.dibujaLinea( 500, 200, 500+choqueLinea.getX(), 200+choqueLinea.getY(), 2.0f, Color.magenta );
			ventana.dibujaLinea( 500, 200, 500+tangenteXY.getX(), 200+tangenteXY.getY(), 2.0f, Color.black );
			// Vista de datos en consola
			System.out.println( "Cambio en choque:");
			System.out.println( "  Pelota 1: " + velPelotaXY + " es " + velPelota + " o sea " + nuevaVelPelota );
			System.out.println( "  Pelota 2: " + velPelota2XY + " es " + velPelota2 + " o sea " + nuevaVelPelota2 );
			System.out.println( "  Nueva vel pelota 1: " + nuevaVelPelota );
			System.out.println( "  Nueva vel pelota 2: " + nuevaVelPelota2 );
		}
		velPelota = new Polar(nuevaVelPelota);
		velPelota2 = new Polar(nuevaVelPelota2);
		velPelota.transformaANuevoEje( -Math.atan2( tangenteXY.getY(), tangenteXY.getX() ) );
		velPelota2.transformaANuevoEje( -Math.atan2( tangenteXY.getY(), tangenteXY.getX() ) );
		Point2D velPelotaFin = velPelota.toPoint();
		Point2D velPelota2Fin = velPelota2.toPoint();
		if (visualizarChoque) {
			// Velocidades después del choque
			ventana.dibujaFlecha( pelota.getX(), pelota.getY(), pelota.getX()+velPelotaFin.getX()/1000*milis, pelota.getY()+velPelotaFin.getY()/1000*milis, 4.0f, Color.red );
			ventana.dibujaFlecha( pelota2.getX(), pelota2.getY(), pelota2.getX()+velPelota2Fin.getX()/1000*milis, pelota2.getY()+velPelota2Fin.getY()/1000*milis, 4.0f, Color.red );
			System.out.println( "  Vel fin pelota 1: " + velPelotaFin );
			System.out.println( "  Vel fin pelota 2: " + velPelota2Fin );
		}
		pelota.setVelXY( velPelotaFin );
		pelota2.setVelXY( velPelota2Fin );
		if (visualizarChoque) {  // Pelotas tras el choque sin corrección
			ventana.dibujaCirculo( pelota.getX(), pelota.getY(), pelota.getRadio(), 2.5f, pelota.getColor() );
			ventana.dibujaCirculo( pelota2.getX(), pelota2.getY(), pelota2.getRadio(), 2.5f, pelota2.getColor() );
			System.out.println( "Montado exacto: " + choque );
		}
		// Corrige posición para que no se monten (en función de los avances previos)
		if (Fisica.igualACero(choqueLinea.getX()) && Fisica.igualACero(choqueLinea.getY())) { // Caso de choque estático en suelo
			double diferencia = 0.01;
			if (pelota.getX() < pelota2.getX()) diferencia = -diferencia;
			if (visualizarChoque) {  // Corrección x
				System.out.println( "  pelota 1 - x: " + pelota.getX() + " - corrección directa " + diferencia );
				System.out.println( "  pelota 2 - x: " + pelota2.getX() + " - corrección directa " + -diferencia );
			}
			pelota.setX( pelota.getX()+diferencia );  // Corrige y aleja un poquito para que no choquen
			pelota2.setX( pelota2.getX()-diferencia );
		}
		if (!Fisica.igualACero(choqueLinea.getX())) {
			double diferencia = 0.0;
			if (!Fisica.igualACero(pelota.getAvanceX())) diferencia = Math.abs(pelota.getAvanceX()) / (Math.abs(pelota.getAvanceX()) + Math.abs(pelota2.getAvanceX()));
			double diferencia2 = 1 - diferencia;
			if (visualizarChoque) {  // Corrección x
				System.out.println( "  pelota 1 - x: " + pelota.getX() + " - corrección " + diferencia );
				System.out.println( "  pelota 2 - x: " + pelota2.getX() + " - corrección " + diferencia2 );
			}
			pelota.setX( pelota.getX()-choqueLinea.getX()*diferencia*1.1 );  // Corrige y aleja un poquito para que no choquen
			pelota2.setX( pelota2.getX()+choqueLinea.getX()*diferencia2*1.1 );
		}
		if (!Fisica.igualACero(choqueLinea.getY())) {
			double diferencia = 0.0;
			if (!Fisica.igualACero(pelota.getAvanceY())) diferencia = Math.abs(pelota.getAvanceY()) / (Math.abs(pelota.getAvanceY()) + Math.abs(pelota2.getAvanceY()));
			double diferencia2 = 1 - diferencia;
			if (visualizarChoque) {  // Corrección y
				System.out.println( "  pelota 1 - y: " + pelota.getY() + " - corrección " + diferencia );
				System.out.println( "  pelota 2 - y: " + pelota2.getY() + " - corrección " + diferencia2 );
			}
			pelota.setY( pelota.getY()-choqueLinea.getY()*diferencia*1.1 );  // Corrige y aleja un poquito para que no choquen
			pelota2.setY( pelota2.getY()+choqueLinea.getY()*diferencia2*1.1 );
		}
		if (visualizarChoque) {  // Pelotas tras el choque con corrección
			ventana.dibujaCirculo( pelota.getX(), pelota.getY(), pelota.getRadio(), 3f, pelota.getColor() );
			ventana.dibujaCirculo( pelota2.getX(), pelota2.getY(), pelota2.getRadio(), 3f, pelota2.getColor() );
			ventana.repaint();
		}
	}
	
	/** Calcula el choque entre dos objetos (bloques)
	 * @param ventana	Ventana en la que ocurre el choque
	 * @param objeto	Objeto 1 que choca
	 * @param objeto2	Objeto 2 que choca
	 * @param milis	Milisegundos que pasan en el paso de movimiento
	 */
	public static void calcChoqueEntreObjetos( VentanaGrafica ventana, Bloque objeto, Bloque objeto2, double milis ) {
		Polar choque = objeto.vectorChoqueConObjeto( objeto2 );  // Dirección y magnitud de choque
		if (choque==null) return;  // No hay choque
		// Simplificación: donde haya mayor choque se considera que el choque es ortogonal (vertical o horizontal)
		Point2D choqueXY = choque.toPoint();
		double difVelsX = objeto.getVelX() - objeto2.getVelX();
		double difVelsY = objeto.getVelY() - objeto2.getVelY();
		if (Fisica.igualACero(choqueXY.getX()) || (!Fisica.igualACero(choqueXY.getY()) && Math.abs(difVelsY) > Math.abs(difVelsX))) {  // Choque vertical
			double vel12 = objeto.getVelY();
			double vel21 = objeto2.getVelY();
			double[] velChoque = Fisica.calcChoque( objeto.getVolumen(), vel12, objeto2.getVolumen(), vel21 );
			// System.out.println( "VER " + java.util.Arrays.toString( velChoque) + "   (" + vel12 + "," + vel21 + ")" );
			objeto.setVelY( velChoque[0] );
			objeto2.setVelY( velChoque[1] );
		} else {  // Choque horizontal
			double vel12 = objeto.getVelX();
			double vel21 = objeto2.getVelX();
			double[] velChoque = Fisica.calcChoque( objeto.getVolumen(), vel12, objeto2.getVolumen(), vel21 );
			// System.out.println( "HOR " + java.util.Arrays.toString( velChoque) + "   (" + vel12 + "," + vel21 + ")" );
			objeto.setVelX( velChoque[0] );
			objeto2.setVelX( velChoque[1] );
		}
	}

	
	/** Calcula el choque entre dos objetos (bloque y bola)
	 * @param ventana	Ventana en la que ocurre el choque
	 * @param bloque	Objeto 1 que choca (bloque) con consideración de masa infinita (su velocidad no se ve afectada)
	 * @param bola	Objeto 2 que choca (bola) con comportamiento perfectamente elástico (su velocidad x-y se ve afectada)
	 * @param milis	Milisegundos que pasan en el paso de movimiento, permite calcular las correcciones de choque por física de baja precisión (la bola se mete "dentro" del rectángulo)
	 */
	public static void calcChoqueEntreObjetos( VentanaGrafica ventana, Bloque bloque, Bola bola, double milis ) {
		ArrayList<ObjetoMovil> l = new ArrayList<>(); l.add( bloque ); 
		calcChoqueMultipleEntreObjetos( ventana, bola, l, milis);
		/* Implementación con iteraciones pequeñas... se ha sustituido por la (mejor) implementación por aproximaciones sucesivas de varios bloques
		// Para calcular la física de colisión necesitamos saber el punto aproximado en el que ocurrió la colisión: en función de eso cambiarán de una forma o de otra las velocidades
		// 1. Hacemos un "slow backwards" del movimiento de rectángulo y círculo hasta que encontremos el punto de choque aproximado ("sacando" de esa forma la bola de la pala)
		// Este movimiento puede mejorarse con aproximaciones sucesivas, como veremos en Programación III. Ahora lo hacemos "a mano", retrocediendo poco a poco el movimiento
		Polar vectorChoque = bloque.vectorChoqueConObjeto( bola );
		if (vectorChoque==null) {  // No chocan desde el principio: este método no tiene nada que hacer
			return;
		}
		double tiempoRetroceso = 0.0;
		double vXInicialCirculo = bola.getVelX();
		double vYInicialCirculo = bola.getVelY();
		double vXInicialRect = bloque.getVelX();
		double vYInicialRect = bloque.getVelY();
		bola.setVelX( -vXInicialCirculo );  // Invertimos la velocidad del círculo
		bola.setVelY( -vYInicialCirculo ); 
		bloque.setVelX( -vXInicialRect );  // Invertimos la velocidad del rectángulo
		bloque.setVelY( -vYInicialRect ); 
		while (vectorChoque!=null) {  // Mientras sigan chocando, retroceder el movimiento (este bucle probablemente se ejecute muchas veces si los objetos se mueven rápido)
			tiempoRetroceso += TIEMPO_RETROCESO;
			bola.mover( TIEMPO_RETROCESO );  // Movemos círculo y rectángulo hacia atrás
			bloque.mover( TIEMPO_RETROCESO );
			vectorChoque = bloque.vectorChoqueConObjeto( bola ); // ¿Siguen chocando?
		}
		// 2. Restauramos velocidades iniciales 
		bola.setVelX( vXInicialCirculo );
		bola.setVelY( vYInicialCirculo ); 
		bloque.setVelX( vXInicialRect );
		bloque.setVelY( vYInicialRect );
		// 3. En este punto, círculo y rectángulo no chocan. Calculamos el punto aproximado donde sí lo hacen
		// y en función de eso cambiamos las velocidades del círculo (el rectángulo se supone de masa infinita, por tanto no afectado por el choque)
		// 3a. Comprobamos choque lateral
		if (bola.getY() >= bloque.getY()-bloque.getAlto()/2 &&
			bola.getY() <= bloque.getY()+bloque.getAlto()/2) {  // La y de la bola está dentro de la pala: choque lateral
			// if (circulo.getX()-circulo.getRadio()>rectangulo.getX()+rectangulo.getAnchura()/2) {  // Se podría comprobar si el choque es por la derecha
			// if (circulo.getX()+circulo.getRadio()<rectangulo.getX()-rectangulo.getAnchura()/2) {  // o es por la izquierda, pero el cálculo va a ser el mismo
			bola.setVelX( -vXInicialCirculo + vXInicialRect );  // Restauramos la velocidad original invertida (por el rebote con elasticidad 100%) añadiendo la velocidad que le aporte el rectángulo (si la tiene)
		} 
		// 3b. Comprobamos choque vertical
		else if (bola.getX() >= bloque.getX()-bloque.getAncho()/2 &&
				bola.getX() <= bloque.getX()+bloque.getAncho()/2) {  // La x de la bola está dentro de la pala: choque vertical
			// Igualmente podríamos comprobar si es superior o inferior, pero el cálculo es el mismo:
			bola.setVelY( -vYInicialCirculo + vYInicialRect );  // Restauramos la velocidad original invertida (por el rebote con elasticidad 100%) añadiendo la velocidad que le aporte el rectángulo (si la tiene)
		}
		// 3c. Como hay choque seguro, si no es horizontal ni vertical es que es diagonal (con una de las esquinas) que es el cálculo físico más complejo porque hay que calcular la normal al choque
		else {
			Point2D vectorImpacto = null; // Para ver el vértice con el que choca
			double distMax = Double.MAX_VALUE;
			for (double anch : new double[] {-1.0, 1.0}) {
				for (double alt : new double[] {-1.0, 1.0}) {  // Bucles para las 4 esquinas con las que puede chocar la bola
					Point2D esquina = new Point2D.Double( bloque.getX()+anch*bloque.getAncho()/2, bloque.getY()+alt*bloque.getAlto()/2 );
					double dist = Fisica.distancia( bola.getX(), bola.getY(), esquina.getX(), esquina.getY() );
					if (dist<distMax) {  // Este puede ser el vértice del choque
						distMax = dist;
						vectorImpacto = new Point2D.Double( esquina.getX()-bola.getX(), esquina.getY()-bola.getY() );
					}
				}
			}
			Polar vectorImpactoP = new Polar( vectorImpacto );
			double anguloDesv = vectorImpactoP.getArgumento();
			Point2D vectorVel = new Point2D.Double( bola.getVelX(), bola.getVelY() );
			Polar vectorVelP = new Polar( vectorVel );
			vectorVelP.rotar( -anguloDesv );
			Point2D velRotada = vectorVelP.toPoint();
			velRotada.setLocation( -velRotada.getX(), velRotada.getY() );  // Invierte la x (rebote)
			Polar velRotadaP = new Polar( velRotada );
			velRotadaP.rotar( anguloDesv ); // Volver a poner el vector en términos de x,y original
			Point2D velTrasRebote = velRotadaP.toPoint();
			bola.setVelX( velTrasRebote.getX() );
			bola.setVelY( velTrasRebote.getY() );
		}
		// 4. Como hemos vuelto "hacia atrás el movimiento", completamos el movimiento restante dentro del fotograma moviendo "hacia adelante" con las nuevas velocidades
		bola.mover( tiempoRetroceso );
		bloque.mover( tiempoRetroceso );
		*/
	}

	/** Calcula el choque entre un objeto y el borde de la ventana
	 * ATENCIÓN: Funciona actualmente para bola y bloque
	 * @param rect	Rectángulo de límites en los que ocurre el choque
	 * @param objetoA	objeto que choca con comportamiento perfectamente elástico (debe ser instancia de Bola o Bloque)
	 * @param milis	Milisegundos que pasan en el paso de movimiento, permite calcular las correcciones de choque por física de alta precisión (evitando que la bola se meta "dentro" del borde)
	 */
	public static void calcChoqueConBorde( Rectangle rect, ObjetoMovil objetoA, double milis ) {
		if (objetoA instanceof Bola) {
			calcChoqueConBorde( rect, (Bola) objetoA, milis );
		} else if (objetoA instanceof Bloque) {
			calcChoqueConBorde( rect, (Bloque) objetoA, milis );
		}
	}
	
	/** Calcula el choque entre un bloque y el borde de la ventana
	 * @param rect	Rectángulo de límites en los que ocurre el choque
	 * @param bola	objeto que choca con comportamiento perfectamente elástico
	 * @param milis	Milisegundos que pasan en el paso de movimiento, permite calcular las correcciones de choque por física de alta precisión (evitando que la bola se meta "dentro" del borde)
	 */
	public static void calcChoqueConBorde( Rectangle rect, Bloque bloque, double milis ) {
		if (bloque.chocaBordeVertical(rect)) {
			bloque.setVelY( -bloque.getVelY() );
		}
		if (bloque.chocaBordeHorizontal(rect)) {
			bloque.setVelX( -bloque.getVelX() );
		}
	}

	/** Calcula el choque entre una bola y el borde de la ventana
	 * @param rect	Rectángulo de límites en los que ocurre el choque
	 * @param bola	objeto que choca con comportamiento perfectamente elástico
	 * @param milis	Milisegundos que pasan en el paso de movimiento, permite calcular las correcciones de choque por física de alta precisión (evitando que la bola se meta "dentro" del borde)
	 */
	public static void calcChoqueConBorde( Rectangle rect, Bola bola, double milis ) {
		if (!bola.chocaBordeHorizontal(rect) && !bola.chocaBordeVertical(rect)) {  // No chocan desde el principio: este método no tiene nada que hacer
			return;
		}
		double tiempoChoqueVertical = -1;
		double tiempoChoqueHorizontal = -1;
		if (bola.chocaBordeVertical(rect)) {
			double posYInicialBola = bola.getY() - bola.getAvanceY();
			double distInicialABorde = (bola.getAvanceY()<0) ? posYInicialBola : rect.height-posYInicialBola;
			tiempoChoqueVertical = (distInicialABorde - bola.getRadio()) / Math.abs(bola.getAvanceY()) * milis;
		}
		if (bola.chocaBordeHorizontal(rect)) {
			double posXInicialBola = bola.getX() - bola.getAvanceX();
			double distInicialABorde = (bola.getAvanceX()<0) ? posXInicialBola : rect.width-posXInicialBola;
			tiempoChoqueHorizontal = (distInicialABorde - bola.getRadio()) / Math.abs(bola.getAvanceX()) * milis;
		}
		// System.out.println( "Tiempos de choque: H " + tiempoChoqueHorizontal + " - V " + tiempoChoqueVertical + " de " + milis + " en bola " + bola );
		// Comprobar si el choque es doble y hacer primero el que sea antes. Luego hacer los simples
		if (tiempoChoqueHorizontal >= 0 && tiempoChoqueVertical >= 0) {
			if (tiempoChoqueHorizontal > tiempoChoqueVertical) {
				correccionVertical(bola,milis-tiempoChoqueVertical,tiempoChoqueHorizontal-tiempoChoqueVertical);
				correccionHorizontal(bola,tiempoChoqueHorizontal-tiempoChoqueVertical,milis-tiempoChoqueHorizontal);
			} else {
				correccionHorizontal(bola,milis-tiempoChoqueHorizontal,tiempoChoqueVertical-tiempoChoqueHorizontal);
				correccionVertical(bola,tiempoChoqueVertical-tiempoChoqueVertical,milis-tiempoChoqueVertical);
			}
		} else {
			if (tiempoChoqueHorizontal>=0) {
				correccionHorizontal(bola,milis-tiempoChoqueHorizontal,milis-tiempoChoqueHorizontal);
			} else if (tiempoChoqueVertical>=0) {
				correccionVertical(bola,milis-tiempoChoqueVertical,milis-tiempoChoqueVertical);
			}
		}
	}
		private static void correccionVertical( Bola bola, double milisADeshacer, double milisAHacerTrasChoque ) {
			bola.setVelXY( -bola.getVelX(), -bola.getVelY() );
			bola.mover( milisADeshacer );  // Deshace el movimiento extra sobre el borde
			bola.setVelX( -bola.getVelX() );  // Recupera la velocidad en X dejando invertida la Y para después del choque
			bola.mover( milisAHacerTrasChoque );  // Hace el movimiento extra tras rebotar
		}
		private static void correccionHorizontal( Bola bola, double milisADeshacer, double milisAHacerTrasChoque ) {
			bola.setVelXY( -bola.getVelX(), -bola.getVelY() );
			bola.mover( milisADeshacer );  // Deshace el movimiento extra sobre el borde
			bola.setVelY( -bola.getVelY() );  // Recupera la velocidad en X dejando invertida la Y para después del choque
			bola.mover( milisAHacerTrasChoque );  // Hace el movimiento extra tras rebotar
		}
	
	/** Calcula el choque múltiple entre varios objetos. 
	 * ATENCIÓN: Actualmente funciona solo para una bola y una lista de bloques
	 * @param ventana	Ventana en la que ocurre el choque
	 * @param bola	Objeto 1 que choca (DEBE SER una bola) con comportamiento perfectamente elástico (su velocidad x-y se ve afectada por el choque con los bloques)
	 * @param lBloques	Lista de objetos (DEBEN SER bloques) con los que choca la bola. Los bloques tienen consideración de masa infinita (su velocidad no se ve afectada)
	 * @param milis	Milisegundos que pasan en el paso de movimiento, permite calcular las correcciones de choque por física de alta precisión (evitando que la bola se meta "dentro" de los bloques)
	 */
	public static void calcChoqueMultipleEntreObjetos( VentanaGrafica ventana, ObjetoMovil bola, ArrayList<ObjetoMovil> lBloques, double milis ) {
		if (lBloques.isEmpty() || !lBloques.get(0).chocaCon(bola)) {  // No chocan desde el principio: este método no tiene nada que hacer
			return;
		}
		if (!(bola instanceof Bola)) return;  // No funciona si no es una bola
		for (ObjetoMovil oa : lBloques) if (!(oa instanceof Bloque)) return;  // No funciona si no son bloques
		ArrayList<Bloque> nuevaLista = new ArrayList<Bloque>();
		for (ObjetoMovil oa : lBloques) nuevaLista.add( (Bloque) oa );
		iteracionChoqueBolaBloques(ventana, (Bola) bola, new ArrayList<Bloque>(nuevaLista), nuevaLista, milis, 1);
	}

		// Método de iteraciones múltiples (recursivo) para calcular posibles varios choques de la bola con distintos bloques dentro de un mismo fotograma
		private static void iteracionChoqueBolaBloques( VentanaGrafica ventana, Bola bola, ArrayList<Bloque> lBloquesChoque, ArrayList<Bloque> lBloquesPosibleChoque, double milis, int numIteraciones ) {
			// Retrasamos la física hasta el punto del primer choque:
			ArrayList<Bloque> listaPrimerChoque = new ArrayList<>( lBloquesChoque );
			double tiempoRetroceso = retrocederMovimientoHastaChoque( bola, listaPrimerChoque, milis );
			Bloque bloque = listaPrimerChoque.get(0);  // Este es el bloque con el que calcular el choque (el primero con el que choca
			// 3. En este momento, círculo y rectángulos o no chocan pero están muy cerca, o chocan pero solo con uno de todos los rectángulos. Calculamos las tres opciones de choque
			// y en función de eso cambiamos las velocidades del círculo (el rectángulo se supone de masa infinita, por tanto no afectado por el choque)
			// 3a. Comprobamos choque lateral
			if (bola.getY() >= bloque.getY()-bloque.getAlto()/2 &&
				bola.getY() <= bloque.getY()+bloque.getAlto()/2) {  // La y de la bola está dentro de la pala: choque lateral
				// if (circulo.getX()-circulo.getRadio()>rectangulo.getX()+rectangulo.getAnchura()/2) {  // Se podría comprobar si el choque es por la derecha
				// if (circulo.getX()+circulo.getRadio()<rectangulo.getX()-rectangulo.getAnchura()/2) {  // o es por la izquierda, pero el cálculo va a ser el mismo
				bola.setVelX( -bola.getVelX() + bloque.getVelX() );  // Restauramos la velocidad original invertida (por el rebote con elasticidad 100%) añadiendo la velocidad que le aporte el rectángulo (si la tiene)
			} 
			// 3b. Comprobamos choque vertical
			else if (bola.getX() >= bloque.getX()-bloque.getAncho()/2 &&
					bola.getX() <= bloque.getX()+bloque.getAncho()/2) {  // La x de la bola está dentro de la pala: choque vertical
				// Igualmente podríamos comprobar si es superior o inferior, pero el cálculo es el mismo:
				bola.setVelY( -bola.getVelY() + bloque.getVelY() );  // Restauramos la velocidad original invertida (por el rebote con elasticidad 100%) añadiendo la velocidad que le aporte el rectángulo (si la tiene)
			}
			// 3c. Como hay choque seguro, si no es horizontal ni vertical es que es diagonal (con una de las esquinas) que es el cálculo físico más complejo porque hay que calcular la normal al choque
			else {
				Point2D vectorImpacto = null; // Para ver el vértice con el que choca
				double distMax = Double.MAX_VALUE;
				for (double anch : new double[] {-1.0, 1.0}) {
					for (double alt : new double[] {-1.0, 1.0}) {  // Bucles para las 4 esquinas con las que puede chocar la bola
						Point2D esquina = new Point2D.Double( bloque.getX()+anch*bloque.getAncho()/2, bloque.getY()+alt*bloque.getAlto()/2 );
						double dist = Fisica.distancia( bola.getX(), bola.getY(), esquina.getX(), esquina.getY() );
						if (dist<distMax) {  // Este puede ser el vértice del choque
							distMax = dist;
							vectorImpacto = new Point2D.Double( esquina.getX()-bola.getX(), esquina.getY()-bola.getY() );
						}
					}
				}
				Polar vectorImpactoP = new Polar( vectorImpacto );
				double anguloDesv = vectorImpactoP.getArgumento();
				Point2D vectorVel = new Point2D.Double( bola.getVelX(), bola.getVelY() );
				Polar vectorVelP = new Polar( vectorVel );
				vectorVelP.rotar( -anguloDesv );
				Point2D velRotada = vectorVelP.toPoint();
				velRotada.setLocation( -velRotada.getX(), velRotada.getY() );  // Invierte la x (rebote)
				Polar velRotadaP = new Polar( velRotada );
				velRotadaP.rotar( anguloDesv ); // Volver a poner el vector en términos de x,y original
				Point2D velTrasRebote = velRotadaP.toPoint();
				bola.setVelX( velTrasRebote.getX() + bloque.getVelX()/10 ); // Hacemos corrección de velocidad con la velocidad que aplica el bloque a la bola (para que no "atropelle" el bloque a la bola)
				bola.setVelY( velTrasRebote.getY() + bloque.getVelY()/10 );
			}
			// 4. Como hemos vuelto "hacia atrás el movimiento", completamos el movimiento restante dentro del fotograma moviendo "hacia adelante" con las nuevas velocidades
			bola.mover( tiempoRetroceso );
			for (Bloque b : lBloquesChoque) b.mover( tiempoRetroceso );
			// 5. Pero puede ocurrir que en este movimiento vuelva a haber una colisión con alguno de los otros bloques... así que hay que calcularlo
			ArrayList<Bloque> lChoques = new ArrayList<Bloque>();
			for (int j=0; j<lBloquesPosibleChoque.size(); j++) {
				Bloque b = lBloquesPosibleChoque.get(j);
				if (b.vectorChoqueConObjeto( bola )!=null) {
					lChoques.add( bloque );
				}
			}
			// 5a. Y si hay colisiones, repetimos iteración solo con el tiempo restante del fotograma (salvo que se nos haya acabado el tiempo, o que hagamos más de 10 iteraciones)
			if (lChoques.size()>0 && !Fisica.igualACero(tiempoRetroceso) && numIteraciones<10) {
				iteracionChoqueBolaBloques( ventana, bola, lChoques, lBloquesPosibleChoque, tiempoRetroceso, numIteraciones+1 );
			}
		}

			private static final int NUM_ITERACIONES = 20;  // Número de iteraciones para aproximaciones sucesivas
		// Pre hay choque con toda la lista de choques indicada
		// Post se devuelve esa lista solo con el primer bloque con el que se choca (se borran los demás bloques)
		// Se devuelve además el tiempo en el que ocurre ese choque
		// Versión iterativa con aproximaciones sucesivas incrementos mínimos (más eficiente que la siguiente implementación)
		private static double retrocederMovimientoHastaChoque( Bola bola, ArrayList<Bloque> lBloquesChoque, double milisIntervalo ) {
			// Para calcular la física de colisión necesitamos saber el punto aproximado en el que ocurrió la colisión: en función de eso cambiarán de una forma o de otra las velocidades
			// 1. Hacemos aproximaciones sucesivas del movimiento de rectángulo y círculo hasta que encontremos el punto de choque inicial lo más aproximado posible
			//   (vamos hacia atrás, hacia adelante, hacia atrás... dependiendo de cuándo hay choque o no lo hay, aproximando con intervalos de mitad,mitad,mitad)
			double tiempoInicial = 0.0;
			double tiempoFinal = milisIntervalo;
			// Guardamos velocidades previas 
			double vXInicialCirculo = bola.getVelX();
			double vYInicialCirculo = bola.getVelY();
			ArrayList<Double> vXInicialRect = new ArrayList<>();
			for (Bloque bloque : lBloquesChoque) vXInicialRect.add( bloque.getVelX() );
			ArrayList<Double> vYInicialRect = new ArrayList<>();
			for (Bloque bloque : lBloquesChoque) vYInicialRect.add( bloque.getVelY() );
			boolean irHaciaAdelante = false;  // Empezamos retrocediendo
			Bloque bloquePrimerChoque = null;  // Para guardar el primer bloque con el que se choca
			for (int numIteraciones=0; numIteraciones<NUM_ITERACIONES; numIteraciones++) {  // Hacemos suficientes iteraciones para la aproximación suficiente
				if (irHaciaAdelante) { // Restauramos velocidades para avanzar tiempo en el frame
					bola.setVelXY( vXInicialCirculo, vYInicialCirculo ); 
					for (int i=0; i<lBloquesChoque.size(); i++) { // Invertimos la velocidad de los rectángulos
						lBloquesChoque.get(i).setVelXY( vXInicialRect.get(i), vYInicialRect.get(i) ); 
					}
				} else { // Invertimos velocidades para retroceder tiempo en el frame
					bola.setVelXY( -vXInicialCirculo, -vYInicialCirculo );  // Invertimos la velocidad del círculo
					for (int i=0; i<lBloquesChoque.size(); i++) { // Invertimos la velocidad de los rectángulos
						lBloquesChoque.get(i).setVelXY( -vXInicialRect.get(i), -vYInicialRect.get(i) ); 
					}
				}
				// Movemos al punto medio círculo y rectángulos (hacia adelante o hacia atrás, como proceda)
				double tiempoMedio = (tiempoFinal - tiempoInicial) / 2.0;
				bola.mover( tiempoMedio );  
				for (Bloque bloque : lBloquesChoque) bloque.mover( tiempoMedio );
				// Calculamos choques y vemos si en el punto medio hay choque o no
				int choquesBloques = 0;
				for (Bloque bloque : lBloquesChoque) {
					if (bloque.chocaCon(bola)) {
						if (choquesBloques==0) bloquePrimerChoque = bloque;  // Guarda el primero de los bloques que choca
						choquesBloques++;
					}
				}
				if (choquesBloques>=1) {  // Si sigue habiendo choques en el medio, toca atrasar
					irHaciaAdelante = false;
					tiempoFinal = tiempoFinal - tiempoMedio;
				} else {  // Si no hay choque en el medio, toca adelantar
					irHaciaAdelante = true;
					tiempoInicial = tiempoInicial + tiempoMedio;
				}
			}
			// 2. Restauramos velocidades iniciales 
			bola.setVelXY( vXInicialCirculo, vYInicialCirculo ); 
			for (int i=0; i<lBloquesChoque.size(); i++) {
				lBloquesChoque.get(i).setVelXY( vXInicialRect.get(i), vYInicialRect.get(i) );
			}
			lBloquesChoque.clear();
			lBloquesChoque.add( bloquePrimerChoque );
			return milisIntervalo - tiempoFinal;
		}

		// Implementación no recomendada:
			private static double TIEMPO_RETROCESO = 0.0001; // Unidad de tiempo que retrocedemos (en segundos) para hacer el cálculo preciso de punto de choque
		// Pre hay choque con toda la lista de choques indicada
		// Post se devuelve esa lista solo con el primer bloque con el que se choca (se borran los demás bloques)
		// Se devuelve además el tiempo en el que ocurre ese choque
		// Versión iterativa con incrementos mínimos (menos eficiente)
		@SuppressWarnings("unused")
		private static double retrocederMovimientoHastaChoqueViterativa( Bola bola, ArrayList<Bloque> lBloquesChoque ) {
			// Para calcular la física de colisión necesitamos saber el punto aproximado en el que ocurrió la colisión: en función de eso cambiarán de una forma o de otra las velocidades
			// 1. Hacemos un "slow backwards" del movimiento de rectángulo y círculo hasta que encontremos el punto de choque aproximado ("sacando" de esa forma la bola de la pala)
			// Este movimiento puede mejorarse con aproximaciones sucesivas. En esta implementación lo hacemos "a mano", retrocediendo poco a poco el movimiento
			
			// Guardamos velocidades previas
			double tiempoRetroceso = 0.0;
			double vXInicialCirculo = bola.getVelX();
			double vYInicialCirculo = bola.getVelY();
			ArrayList<Double> vXInicialRect = new ArrayList<>();
			for (Bloque bloque : lBloquesChoque) vXInicialRect.add( bloque.getVelX() );
			ArrayList<Double> vYInicialRect = new ArrayList<>();
			for (Bloque bloque : lBloquesChoque) vYInicialRect.add( bloque.getVelY() );

			// Invertimos velocidades para retroceder tiempo en el frame
			bola.setVelX( -vXInicialCirculo );  // Invertimos la velocidad del círculo
			bola.setVelY( -vYInicialCirculo ); 
			for (int i=0; i<lBloquesChoque.size(); i++) { // Invertimos la velocidad de los rectángulos
				Bloque bloque = lBloquesChoque.get(i);
				bloque.setVelX( -vXInicialRect.get(i) );  
				bloque.setVelY( -vYInicialRect.get(i) ); 
			}
			
			// Calculamos choques y vamos retrocediendo tiempo hasta que solo choque un bloque
			int choquesBloques = 0;
			int bloqueElegido = -1;
			do {
				choquesBloques = 0;
				for (int i=0; i<lBloquesChoque.size(); i++) {
					Bloque bloque = lBloquesChoque.get(i);
					Polar vectorChoque = bloque.vectorChoqueConObjeto( bola );
					if (vectorChoque!=null) {
						if (choquesBloques==0) bloqueElegido = i;  // Guarda el índice del primero de los bloques que chocan en cada retroceso
						choquesBloques++;
					}
				}
				if (choquesBloques>=1) {  // Si sigue habiendo choques retrocedemos todo un poquito
					tiempoRetroceso += TIEMPO_RETROCESO;
					bola.mover( TIEMPO_RETROCESO );  // Movemos círculo y rectángulos hacia atrás
					for (Bloque bloque : lBloquesChoque) bloque.mover( TIEMPO_RETROCESO );
				}
			} while (choquesBloques>=1);
			
			// 2. Restauramos velocidades iniciales 
			bola.setVelX( vXInicialCirculo );
			bola.setVelY( vYInicialCirculo ); 
			for (int i=0; i<lBloquesChoque.size(); i++) {
				Bloque bloque = lBloquesChoque.get(i);
				bloque.setVelX( vXInicialRect.get(i) );
				bloque.setVelY( vYInicialRect.get(i) );
			}
			Bloque choqueFinal = lBloquesChoque.get( bloqueElegido );
			lBloquesChoque.clear();
			lBloquesChoque.add( choqueFinal );
			return tiempoRetroceso;
		}

		
	/** Calcula un choque elástico entre dos cuerpos
	 * @param masa1	Masa del cuerpo 1 (Kg)
	 * @param vel1	Velocidad del cuerpo 1 lineal hacia el otro cuerpo en el sentido del cuerpo 1 al cuerpo 2
	 * @param masa2	Masa del cuerpo 2 (Kg)
	 * @param vel2	Velocidad del cuerpo 2 lineal hacia el otro cuerpo en el sentido del cuerpo 1 al cuerpo 2
	 * @return
	 */
	public static double[] calcChoque( double masa1, double vel1, double masa2, double vel2 ) {
		// Fórmula de choque perfectamente elástico. Ver por ejemplo  http://www.fis.puc.cl/~rbenguri/cap4(Dinamica).pdf
		double[] velocFinal = new double[2];
		velocFinal[0] = ((masa1-masa2)*vel1 + 2*masa2*vel2)/(masa1+masa2);
		velocFinal[1] = ((masa2-masa1)*vel2 + 2*masa1*vel1)/(masa1+masa2);
		return velocFinal;
	}
	
	/** Comprueba la igualdad a cero de un valor double
	 * @param num	Valor a comprobar
	 * @return	true si está muy cerca de cero (10^-12), false en caso contrario
	 */
	public static boolean igualACero( double num ) {
		return Math.abs(num)<=1E-12;  // 1 * 10^-12
	}
	
	
}
