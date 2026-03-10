package tema1b.ejemplos.editorGraficos;

import java.awt.Color;
import java.util.ArrayList;

/** Grupo secuencial indexado de vectores (implementado con un arraylist)
 * (Implementación alternativa a GrupoVectoresArray)
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class GrupoVectores {
	private ArrayList<Vector2D> lista;

	/** Crea un grupo de vectores
	 * @param tamanyo	Tamaño máximo (número de vectores que cabrán) - NO UTILIZADO EN ESTA IMPLEMENTACIÓN
	 */
	public GrupoVectores( int tamanyo ) {
		lista = new ArrayList<Vector2D>();
	}
	
	/** Devuelve el número de vectores guardados en este grupo
	 * @return	Número actual de vectores
	 */
	public int size() {
		return lista.size();
	}
	
	/** Devuelve el vector situado en una posición dada
	 * @param indice	Su posición
	 * @return	El vector situado en esa posición
	 */
	public Vector2D get( int indice ) {
		return lista.get(indice);
	}
	
	/** Añade un nuevo vector al final del grupo
	 * @param vec	Vector nuevo
	 */
	public void anyadir( Vector2D vec ) {
		lista.add( vec );
	}
	
	/** Inserta un vector en el grupo (moviendo el resto de vectores de acuerdo al nuevo).
	 * Esta operación es errónea si el vector es nulo o si el índice está fuera de rango (menor que 0 o mayor que el size())
	 * @param indice	Posición en la que se va a insertar
	 * @param vec	Vector nuevo
	 * @return	true si la inserción ha sido correcta, false si no se ha podido hacer
	 */
	public boolean insertar( int indice, Vector2D vec ) {
		if (vec==null) {
			return false;  // Error por vector nulo
		}
		if (indice<0 || indice>lista.size()) {
			return false;  // Error por índice de inserción fuera de rango válido
		}
		lista.add( indice, vec );
		return true;
	}
	
	/** Borra un vector del grupo
	 * @param indice	Posición del elemento que queremos borrar
	 */
	public void borrar( int indice ) {
		lista.remove(indice);
	}
	
	/** Comprueba que los vectores naranjas tengan la longitud correcta (en el rango [100, 250) píxels), 
	 * muestra información a consola y devuelve info de error
	 * @return	true si todos los vectores naranjas tienen la longitud correcta y todos los no naranjas no tienen esa longitud, false en caso contrario
	 */
	public boolean hayCorreccionNaranja() {
		boolean ret = true;
		for (int i=0; i<lista.size(); i++) {
			if (lista.get(i).getModulo()>=100 && lista.get(i).getModulo()<250) {
				System.out.println( "Debe ser naranja el " + lista.get(i) );
				if (!lista.get(i).getColor().equals(Color.ORANGE)) {
					ret = false;
				}
			} else {
				System.out.println( "NO debe ser naranja el " + lista.get(i) );
				if (lista.get(i).getColor().equals(Color.ORANGE)) {
					ret = false;
				}
			}
		}
		return ret;
	}
	
	@Override
	public String toString() {
		return lista.toString();
	}
	
}
