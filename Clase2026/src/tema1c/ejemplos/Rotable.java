package tema1c.ejemplos;

public interface Rotable {
	/** Rota el objeto
	 * @param anguloRadianes	Ángulo a rotar, añadido al que tuviera. Si es positivo en sentido horario, si es negativo en antihorario
	 */
	public void rotar( double anguloRadianes );
	
	/** Devuelve la rotación actual del objeto
	 * @return	Rotación en radianes
	 */
	public double getRotacion();
}
