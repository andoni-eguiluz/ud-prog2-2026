package tema1b.resueltos.ej1b7;

import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Grupo de figuras
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class GrupoFiguras {
	
	private ArrayList<Figura> listaFiguras;

	/** Crea un nuevo grupo de figuras
	 */
	public GrupoFiguras() {
		listaFiguras = new ArrayList<Figura>();
	}

	/** Devuelve una figura del grupo
	 * @param indice	Posición del figura (de 0 a size-1)
	 * @return	figura de esa posición
	 */
	public Figura get( int indice ) {
		return listaFiguras.get(indice);
	}
	
	/** Añade una figura nuevo al grupo
	 * @param fig	Figura a añadir
	 */
	public void add( Figura fig ) {
		listaFiguras.add( fig );
	}
	
	/** Elimina una figura del grupo
	 * @param fig	figura a eliminar
	 */
	public void remove( Figura fig ) {
		listaFiguras.remove( fig );
	}

	/** Elimina una figura del grupo
	 * @param indice	Posición del figura que queremos borrar
	 */
	public void remove( int indice ) {
		listaFiguras.remove( indice );
	}
	
	
	/** Devuelve el número de figuras guardados
	 * @return	Número de figuras del grupo
	 */
	public int size() {
		return listaFiguras.size();
	}
	
	/** Busca una figura en el gruop
	 * @param fig	figura a buscar
	 * @return	Si se encuentra una figura igual a este (equals), se devuelve su posición de índice. Si no se encuentra, se devuelve -1
	 */
	public int buscar( Figura fig ) {
		return listaFiguras.indexOf( fig );
	}
	
	/** Dibuja todos las figuras del grupo
	 * @param v	Ventana en la que dibujar
	 */
	public void dibujar( VentanaGrafica vent ) {
		for (Figura fig : listaFiguras) {
			fig.dibujar( vent );
		}
	}
	
	/** Busca una figura del grupo que tenga un punto dado dentro 
	 * @param punto	Punto a chequear
	 * @return	Primer figura que se encuentre que contiene a ese punto. null si no hay ninguno
	 */
	public Figura encuentraFiguraEnPunto( Point punto ) {
		Figura hayFigClicada = null;
		for (Figura fig : listaFiguras) {
			if (fig.contienePunto( punto )) {
				hayFigClicada = fig;
				break;
			}
		}
		return hayFigClicada;
	}
	
	/** Mueve todos las figuras del grupo, de acuerdo al movimiento definido en cada figura
	 * @param milis	Milisegundos de tiempo a mover
	 */
	public void mover( long milis ) {
		for (Figura fig : listaFiguras) {
			fig.mover( milis );
		}
	}
	
	/** Calcula los choques con los bordes de todas las figuras y realiza los rebotes si proceden
	 * @param v	Ventana en la que comprobar choques con bordes
	 */
	public void choqueBordes( VentanaGrafica v ) {
		for (Figura fig : listaFiguras) {
			if (fig.chocaBordeVertical(v)) {
				fig.setVelY( -fig.getVelY() );
			}
			if (fig.chocaBordeHorizontal(v)) {
				fig.setVelX( -fig.getVelX() );
			}
		}
	}
	
}
