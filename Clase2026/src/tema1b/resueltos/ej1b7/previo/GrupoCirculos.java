package tema1b.resueltos.ej1b7.previo;

import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Grupo de círculos 
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class GrupoCirculos {
	
	private ArrayList<Circulo> listaCirculos;

	/** Crea un nuevo grupo de círculos
	 */
	public GrupoCirculos() {
		listaCirculos = new ArrayList<Circulo>();
	}

	/** Devuelve un círculo del grupo
	 * @param indice	Posición del círculo (de 0 a size-1)
	 * @return	círculo de esa posición
	 */
	public Circulo get( int indice ) {
		return listaCirculos.get(indice);
	}
	
	/** Añade un círculo nuevo al grupo
	 * @param circulo	Círculo a añadir
	 */
	public void add( Circulo circulo ) {
		listaCirculos.add( circulo );
	}
	
	/** Elimina un círculo del grupo
	 * @param circulo	Círculo a eliminar
	 */
	public void remove( Circulo circulo ) {
		listaCirculos.remove( circulo );
	}

	/** Elimina un círculo del grupo
	 * @param indice	Posición del círculo que queremos borrar
	 */
	public void remove( int indice ) {
		listaCirculos.remove( indice );
	}
	
	
	/** Devuelve el número de círculos guardados
	 * @return	Número de círculos del grupo
	 */
	public int size() {
		return listaCirculos.size();
	}
	
	/** Busca un círculo en el gruop
	 * @param circulo	círculo a buscar
	 * @return	Si se encuentra un círculo igual a este (equals), se devuelve su posición de índice. Si no se encuentra, se devuelve -1
	 */
	public int buscar( Circulo circulo ) {
		return listaCirculos.indexOf( circulo );
	}
	
	/** Dibuja todos los círculos del grupo
	 * @param v	Ventana en la que dibujar
	 */
	public void dibujar( VentanaGrafica vent ) {
		for (Circulo circulo : listaCirculos) {
			circulo.dibujar( vent );
		}
	}
	
	/** Busca un círculo del grupo que tenga un punto dado dentro 
	 * @param punto	Punto a chequear
	 * @return	Primer círculo que se encuentre que contiene a ese punto. null si no hay ninguno
	 */
	public Circulo encuentraCirculoEnPunto( Point punto ) {
		Circulo hayCirculoClicado = null;
		for (Circulo circulo : listaCirculos) {
			if (circulo.contienePunto( punto )) {
				hayCirculoClicado = circulo;
				break;
			}
		}
		return hayCirculoClicado;
	}
	
	/** Mueve todos los círculos del grupo, de acuerdo al movimiento definido en cada círculo
	 * @param milis	Milisegundos de tiempo a mover
	 */
	public void mover( long milis ) {
		for (Circulo circulo : listaCirculos) {
			circulo.mover( milis );
		}
	}
	
	/** Calcula los choques con los bordes de todos los círculos y realiza los rebotes si proceden
	 * @param v	Ventana en la que comprobar choques con bordes
	 */
	public void choqueBordes( VentanaGrafica v ) {
		for (Circulo circulo : listaCirculos) {
			if (circulo.chocaBordeVertical(v)) {
				circulo.setVelY( -circulo.getVelY() );
			}
			if (circulo.chocaBordeHorizontal(v)) {
				circulo.setVelX( -circulo.getVelX() );
			}
		}
	}
		
}
