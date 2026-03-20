package tema1c.resueltos.brickbreaker;

public interface Animable {
	/** Informa si el objeto está actualmente en animación
	 * @return	true si está en animación, false si no lo está
	 */
	public boolean estaEnAnimacion();
	
	/** Informa de la animación en curso 
	 * @return	tipo de animación actual, null si no hay ninguna
	 */
	public TipoAnimacion getAnimacionEnCurso();
}
