package tema1b.resueltos.ejClase;

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

	/** Dibuja todos las figuras del grupo
	 * @param v	Ventana en la que dibujar
	 */
	public void dibujar( VentanaGrafica vent ) {
		for (Figura fig : listaFiguras) {
			fig.dibujar( vent );
		}
	}
	
	/** Mueve todos las figuras del grupo, de acuerdo al movimiento definido en cada figura
	 * @param milis	Milisegundos de tiempo a mover
	 */
	public void mover( long milis ) {
		for (Figura fig : listaFiguras) {
			fig.mover( milis );
		}
	}
	
	@Override
	public String toString() {
		return listaFiguras.toString();
	}
	
}
