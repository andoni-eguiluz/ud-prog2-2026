package tema1b.ejemplos.bolasYBloques;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class GrupoObjetosAnimacion {
	private ArrayList<ObjetoMovil> lOAs;
	
	/** Crea un nuevo grupo de objetos de animación
	 */
	public GrupoObjetosAnimacion() {
		lOAs = new ArrayList<>();
	}
	
	/** Devuelve un objeto del grupo
	 * @param indice	Posición del objeto (de 0 a size-1)
	 * @return	objeto de esa posición
	 */
	public ObjetoMovil get( int indice ) {
		return lOAs.get(indice);
	}
	
	/** Devuelve el número de bloques guardados
	 * @return	Número de bloques del grupo
	 */
	public int size() {
		return lOAs.size();
	}
	
	/** Añade un bloque nuevo al grupo
	 * @param bola	Bloque a añadir
	 */
	public void add( ObjetoMovil objetoA ) {
		lOAs.add( objetoA );
	}
	
	/** Elimina un bloque del grupo
	 * @param indice	Posición del bloque que queremos borrar
	 */
	public void remove( int indice ) {
		lOAs.remove( indice );
	}
	
	/** Busca un bloque en el gruop
	 * @param objetoA	Bloque a buscar
	 * @return	Si se encuentra un bloque igual a este (equals), se devuelve su posición de índice. Si no se encuentra, se devuelve -1
	 */
	public int buscar( ObjetoMovil objetoA ) {
		return lOAs.indexOf( objetoA );
	}
	
	/** Devuelve el cálculo de energía del grupo de bloques
	 * @return	Energía sumada de todos los bloques del grupo
	 */
	public double getEnergia() {
		double energia = 0;
		for (ObjetoMovil objetoA : lOAs) energia += objetoA.getEnergia();
		return energia;
	}
	
	/** Borra en la ventana gráfica todos los bloques del grupo (las pinta de blanco)
	 * @param v	Ventana en la que borrar
	 */
	public void borrar( VentanaGrafica v ) {
		for (ObjetoMovil objetoA : lOAs) {
			objetoA.borrar( v );
		}
	}
	
	/** Dibuja todos los bloques del grupo
	 * @param v	Ventana en la que dibujar
	 */
	public void dibujar( VentanaGrafica v ) {
		for (ObjetoMovil objetoA : lOAs) {
			objetoA.dibujar(v);
		}
	}

	/** Mueve todos los bloques del grupo
	 * @param milis	Milisegundos de tiempo a mover
	 */
	public void mover( int milis ) {
		for (ObjetoMovil objetoA : lOAs) {
			objetoA.mover( milis );
		}
	}
	
	/** Busca un objeto que tenga dentro el punto indicado y lo devuelve
	 * @param punto	Punto de la ventana (x,y)
	 * @return	Objeto que contiene ese punto, null si no hay ninguno
	 */
	public ObjetoMovil getObjetoEnPunto( Point punto ) {
		for (ObjetoMovil objetoA : lOAs) {
			if (objetoA.contienePunto(punto)) {
				return objetoA;
			}
		}
		return null;
	}
	
	/** Calcula los choques con los bordes de todos los bloques y realiza los rebotes si proceden
	 * @param v	Ventana en la que comprobar choques con bordes
	 * @param milisEntreFrames	Milisegundos del último movimiento
	 */
	public void choqueBordes( VentanaGrafica v, double milisEntreFrames ) {
		for (ObjetoMovil objetoA : lOAs) {
			if (objetoA.chocaBordeHorizontal(v) || objetoA.chocaBordeVertical(v)) {
				Fisica.calcChoqueConBorde( new Rectangle(v.getAnchura(), v.getAltura()), objetoA, milisEntreFrames );
			}
		}
	}
	
	/** Calcula los choques con los bordes de todos los bloques y realiza los rebotes si proceden
	 * @param rect	Rectángulo en el que comprobar choques con bordes
	 * @param milisEntreFrames	Milisegundos del último movimiento
	 */
	public void choqueBordes( Rectangle rect, double milisEntreFrames ) {
		for (ObjetoMovil objetoA : lOAs) {
			if (objetoA.chocaBordeHorizontal(rect) || objetoA.chocaBordeVertical(rect)) {
				Fisica.calcChoqueConBorde(rect, objetoA, milisEntreFrames );
			}
		}
	}
	
	/** Calcula los choques entre bloques del grupo y realiza los rebotes entre ellos si proceden
	 * @param v	Ventana en la que comprobar choques
	 * @param milis	Milisegundos del último movimiento
	 */
	public void choqueEntreObjetos( VentanaGrafica v, int milis ) {
		for (int i=0; i<lOAs.size(); i++) {
			ObjetoMovil objetoA = lOAs.get(i);
			for (int j=i+1; j<lOAs.size(); j++) {
				ObjetoMovil objetoA2 = lOAs.get(j);
				Fisica.calcChoqueEntreObjetos( v, objetoA, objetoA2, milis*1.0 );
			}
		}
	}
	
	/** Calcula los choques entre objetos del grupo y todos los objetos de otro segundo grupo
	 * @param v	Ventana en la que comprobar choques
	 * @param milis	Milisegundos del último movimiento
	 * @param gObjetos	Grupo de otros objetos a comprobar
	 */
	public void choqueConOtrosObjetos( VentanaGrafica v, int milis, GrupoObjetosAnimacion gObjetos ) {
		for (int i=0; i<lOAs.size(); i++) {
			ObjetoMovil objetoA = lOAs.get(i);
			for (int j=0; j<gObjetos.size(); j++) {
				ObjetoMovil objetoA2 = gObjetos.get(j);
				Fisica.calcChoqueEntreObjetos( v, objetoA, objetoA2, milis*1.0 );
			}
		}
	}

	/** Calcula los choques entre objetos del grupo y todos los objetos de otro segundo grupo
	 * Comprueba para cada objeto del grupo actual, si hay choque múltiple con los objetos del segundo grupo
	 * y en ese caso lo procesa de forma múltiple en paralelo
	 * @param v	Ventana en la que comprobar choques
	 * @param milis	Milisegundos del último movimiento
	 * @param gObjetos	Grupo de otros objetos a comprobar
	 */
	public void choqueMultipleConOtrosObjetos( VentanaGrafica v, int milis, GrupoObjetosAnimacion gObjetos ) {
		for (int i=0; i<lOAs.size(); i++) {
			ObjetoMovil objetoA = lOAs.get(i);
			ArrayList<ObjetoMovil> lChoques = new ArrayList<>();
			for (int j=0; j<gObjetos.size(); j++) {
				ObjetoMovil objetoA2 = gObjetos.get(j);
				if (objetoA.chocaCon( objetoA2 )) {
					lChoques.add( objetoA2 );
				}
			}
			// Resolver choque con bloques múltiples
			Fisica.calcChoqueMultipleEntreObjetos( v, objetoA, lChoques, milis*1.0 );
		}
	}

	/** Corrige posiciones de los bloques del grupo que puedan estar incrustados en los bordes tras rebotes
	 * moviéndolos ligeramente hasta que justo no estén incrustados,
	 * partiendo del rectángulo englobaldor de cada objeto
	 * @param v	Ventana en la que comprobar choques
	 */
	public void correccionBordes( VentanaGrafica v ) {
		correccionBordes( new Rectangle( v.getAnchura(), v.getAltura() ) );
	}
	
	/** Corrige posiciones de los bloques del grupo que puedan estar incrustados en los bordes tras rebotes
	 * moviéndolos ligeramente hasta que justo no estén incrustados,
	 * partiendo del rectángulo englobaldor de cada objeto
	 * @param rect	Rectángulo máximo de bordes
	 */
	public void correccionBordes( Rectangle rect ) {
		for (ObjetoMovil objetoA : lOAs) {
			if (objetoA.chocaBordeVertical(rect)) {
				Rectangle2D dimensiones = objetoA.getRectangulo();
				if (dimensiones.getMinY() < 0) {  // Arriba
					double aMover = -dimensiones.getMinY(); // + 0.00001 si se quiere separar
					objetoA.setY( objetoA.getY() + aMover);
				} else if (dimensiones.getMaxY() > rect.height){  // Abajo
					double aMover = dimensiones.getMaxY() - rect.height; // + 0.00001 si se quiere separar
					objetoA.setY( objetoA.getY() - aMover);
				}
			}
			if (objetoA.chocaBordeHorizontal(rect)) {
				Rectangle2D dimensiones = objetoA.getRectangulo();
				if (dimensiones.getMinX() < 0) {  // Izquierda
					double aMover = -dimensiones.getMinX(); // + 0.00001 si se quiere separar
					objetoA.setX( objetoA.getX() + aMover);
				} else if (dimensiones.getMaxX() > rect.width){  // Derecha
					double aMover = dimensiones.getMaxX() - rect.width; // + 0.00001 si se quiere separar
					objetoA.setX( objetoA.getX() - aMover);
				}
			}
		}
	}
	
}
