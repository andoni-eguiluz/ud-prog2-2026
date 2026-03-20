package tema1c.resueltos.objetodle;

public interface Animable {
	
	/** Informa si el objeto está actualmente en animación
	 * @return	true si está en animación, false si no lo está
	 */
	public boolean estaEnAnimacion();
	
	/** Dibuja el objeto en su ventana con la animación correspondiente
	 */
	public void dibuja();
	
}
