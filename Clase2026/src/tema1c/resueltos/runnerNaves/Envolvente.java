package tema1c.resueltos.runnerNaves;

/** Envolvente de colisión de objeto visual
 * (corresponde al concepto de Bounding Box o Bounding Circle de juegos)
 * Permite definir formas geométricas 2D que pueden chocar con otras
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public abstract class Envolvente {
	
	/** Informa si hay choque entre dos envolventes de colisión
	 * @param envolvente	Segunda envolvente de colisión
	 * @return	true si la envolvente actual colisiona con la segunda, false en caso contrario
	 */
	public abstract boolean chocaCon( Envolvente envolvente );
	
	
}
