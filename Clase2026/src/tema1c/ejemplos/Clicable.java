package tema1c.ejemplos;

public interface Clicable {
	/** Comprueba si una coordenada de click toca al objeto 
	 * @param x	Coordenada x de click
	 * @param y	Coordenada y de click
	 * @return	true si esa posición toca al objeto, false en caso contrario
	 */
	boolean contieneCoordenada( int x, int y); 
	
	/** Hace que el objeto reaccione al click de la manera que el objeto corresponda
	 */
	void reaccionaAClick();
	
}
