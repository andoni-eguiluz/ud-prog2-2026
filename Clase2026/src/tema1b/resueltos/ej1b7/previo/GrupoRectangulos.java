package tema1b.resueltos.ej1b7.previo;

import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Grupo de rectángulos
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class GrupoRectangulos {
	
	private ArrayList<Rectangulo> lRectangulos;
	
	/** Crea un nuevo grupo de rectángulos
	 */
	public GrupoRectangulos() {
		lRectangulos = new ArrayList<>();
	}
	
	/** Devuelve un rectángulo del grupo
	 * @param indice	Posición del rectángulo (de 0 a size-1)
	 * @return	rectángulo de esa posición
	 */
	public Rectangulo get( int indice ) {
		return lRectangulos.get(indice);
	}
	
	/** Devuelve el número de rectángulos guardados
	 * @return	Número de rectángulos del grupo
	 */
	public int size() {
		return lRectangulos.size();
	}
	
	/** Añade un rectángulo nuevo al grupo
	 * @param rectangulo	rectángulo a añadir
	 */
	public void add( Rectangulo rectangulo ) {
		lRectangulos.add( rectangulo );
	}
	
	/** Elimina un rectángulo del grupo
	 * @param indice	Posición del rectángulo que queremos borrar
	 */
	public void remove( int indice ) {
		lRectangulos.remove( indice );
	}
	
	/** Elimina un rectángulo del grupo
	 * @param rectangulo	rectángulo a eliminar
	 */
	public void remove( Rectangulo rectangulo ) {
		lRectangulos.remove( rectangulo );
	}
	
	/** Busca un rectángulo en el gruop
	 * @param rectangulo	rectángulo a buscar
	 * @return	Si se encuentra un rectángulo igual a este (equals), se devuelve su posición de índice. Si no se encuentra, se devuelve -1
	 */
	public int buscar( Rectangulo rectangulo ) {
		return lRectangulos.indexOf( rectangulo );
	}
	
	/** Dibuja todos los rectángulos del grupo
	 * @param v	Ventana en la que dibujar
	 */
	public void dibujar( VentanaGrafica v ) {
		for (Rectangulo rectangulo : lRectangulos) {
			rectangulo.dibujar(v);
		}
	}

	/** Busca un rectángulo del grupo que tenga un punto dado dentro 
	 * @param punto	Punto a chequear
	 * @return	Primer rectángulo que se encuentre que contiene a ese punto. null si no hay ninguno
	 */
	public Rectangulo encuentraRectanguloEnPunto( Point punto ) {
		Rectangulo hayRectClicado = null;
		for (Rectangulo rect : lRectangulos) {
			if (rect.contienePunto( punto )) {
				hayRectClicado = rect;
				break;
			}
		}
		return hayRectClicado;
	}
	
}
