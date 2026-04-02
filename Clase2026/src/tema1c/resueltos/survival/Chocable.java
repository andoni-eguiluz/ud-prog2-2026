package tema1c.resueltos.survival;

public interface Chocable {
	/** Informa si hay choque entre this y c2
	 * @param c2	Segundo objeto
	 * @return	true si this choca con c2, false en caso contrario
	 */
	boolean chocaCon( Chocable c2 );
	
	/** Actualiza el radio de choque 
	 * @param radio	Radio del círculo imaginario de choque del objeto, en píxeles
	 */
	void setRadioChoque( double radio );
	
	/** Devuelve el radio de choque en píxeles
	 */
	double getRadioChoque();
	
	public double getX();

	public double getY();
	

}
