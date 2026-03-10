package tema1b.ejemplos.bolasYBloques;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Grupo de bolas implementado con array
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class GrupoBolasArray {
	private Bola[] arrayBolas;
	private int num;           // Número de elementos en el array
	
	/** Crea un nuevo grupo de bolas de 20 bolas máximo
	 */
	public GrupoBolasArray() {
		this( 20 );
	}
	
	/** Crea un nuevo grupo de bolas
	 * @param numMax	Número máximo de bolas que tendrá el grupo (debe ser positivo)
	 */
	public GrupoBolasArray(int numMax) {
		arrayBolas = new Bola[numMax];
		num = 0;
	}
	
	/** Devuelve una bola del grupo
	 * @param indice	Posición de la bola (de 0 a size-1)
	 * @return	bola de esa posición
	 */
	public Bola get( int indice ) {
		return arrayBolas[indice];
	}
	
	/** Devuelve el número de bolas guardadas
	 * @return	Número de bolas del grupo
	 */
	public int size() {
		return num;
	}
	
	/** Añade una bola nueva al grupo. Si el grupo está lleno no se añade
	 * @param bola	Bola a añadir
	 */
	public void add( Bola bola ) {
		if (num==arrayBolas.length) {  // No se añade si no cabe
			return;
		}
		arrayBolas[ num ] = bola;
		num++;
	}
	
	/** Elimina una bola del grupo
	 * @param indice	Posición de la bola que queremos borrar
	 */
	public void remove( int indice ) {
		for (int i=indice+1; i<num; i++) {
			arrayBolas[i-1] = arrayBolas[i];
		}
		arrayBolas[num-1] = null;  // Opcional (más limpias las referencias internas del array - el objeto puede liberarse por el garbage collector)
		num--;
	}
	
	/** Busca una bola en el gruop
	 * @param bola	Bola a buscar
	 * @return	Si se encuentra una bola igual a esta (equals), se devuelve su posición de índice. Si no se encuentra, se devuelve -1
	 */
	public int buscar( Bola bola ) {
		for (int i=0; i<num; i++) {
			if (arrayBolas[i].equals( bola )) {
				return i;
			}
		}
		return -1;
	}
	
	/** Devuelve el cálculo de energía del grupo de bolas
	 * @return	Energía sumada de todas las bolas del grupo
	 */
	public double getEnergia() {
		double energia = 0;
		for (int i=0; i<num; i++) {
			energia += arrayBolas[i].getEnergia();
		}
		return energia;
	}
	
	/** Borra en la ventana gráfica todas las bolas del grupo (las pinta de blanco)
	 * @param v	Ventana en la que borrar
	 */
	public void borrar( VentanaGrafica v ) {
		for (int i=0; i<num; i++) {
			arrayBolas[i].borrar( v );
		}
	}
	
	/** Dibuja todas las bolas del grupo
	 * @param v	Ventana en la que dibujar
	 */
	public void dibujar( VentanaGrafica v ) {
		for (int i=0; i<num; i++) {
			arrayBolas[i].dibujar( v );
		}
	}

	/** Mueve todas las bolas del grupo
	 * @param milis	Milisegundos de tiempo a mover
	 */
	public void mover( int milis ) {
		for (int i=0; i<num; i++) {
			arrayBolas[i].mover( milis );
		}
	}
	
	/** Calcula los choques con los bordes de todas las bolas y realiza los rebotes si proceden
	 * @param v	Ventana en la que comprobar choques con bordes
	 */
	public void gestionChoqueBordes( VentanaGrafica v ) {
		for (int i=0; i<num; i++) {
			Bola bola = arrayBolas[i];
			if (bola.chocaBordeVertical(v)) {
				bola.setVelY( -bola.getVelY() );
			}
			if (bola.chocaBordeHorizontal(v)) {
				bola.setVelX( -bola.getVelX() );
			}
		}
	}
	
	/** Calcula los choques entre bolas del grupo y realiza los rebotes entre ellas si proceden
	 * @param v	Ventana en la que comprobar choques
	 * @param milis	Milisegundos del último movimiento
	 */
	public void gestionChoqueBolas( VentanaGrafica v, int milis ) {
		for (int i=0; i<num; i++) {
			Bola bola1 = arrayBolas[i];
			for (int j=i+1; j<num; j++) {
				Bola bola2 = arrayBolas[j];
				Fisica.calcChoqueEntreObjetos( v, bola1, bola2, milis*1.0, false );
			}
		}
	}
	
	/** Corrige posiciones de las bolas que puedan estar incrustadas en los bordes tras rebotes
	 * @param v	Ventana en la que comprobar choques
	 */
	public void correccionBordes( VentanaGrafica v ) {
		for (int i=0; i<num; i++) {
			Bola bola = arrayBolas[i];
			if (bola.chocaBordeVertical(v)) {
				if (bola.getY()-bola.getRadio() <= 0) {  // Arriba
					bola.setY( bola.getRadio() + 0.00001 );
				} else {  // Abajo
					bola.setY( v.getAltura() - bola.getRadio() - 0.00001 );
				}
			}
			if (bola.chocaBordeHorizontal(v)) {
				if (bola.getX()-bola.getRadio() <= 0) {  // Izquierda
					bola.setX( bola.getRadio() + 0.00001 );
				} else {  // Derecha
					bola.setX( v.getAnchura() - bola.getRadio() - 0.00001 );
				}
			}
		}
	}
	
}
