package tema1c.resueltos.brickbreaker;

/** Comportamiento de poder acumular puntuación en un juego
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public interface Puntuable {
	/** Define el puntuador que va a esatr asociado al objeto
	 * @param puntuacion	Puntuador asociado al objeto, con el que el objeto podrá conocer o incrementar la puntuación
	 */
	public void setPuntuacion( Puntuacion puntuacion );
	
}
