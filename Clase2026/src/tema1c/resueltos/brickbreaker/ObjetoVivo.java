package tema1c.resueltos.brickbreaker;

public interface ObjetoVivo {
	/** Devuelve la vida del objeto
	 * @return	Vida actual
	 */
	public int getVida();

	/** Decrementa la vida del objeto (en una unidad)
	 */
	public void decVida();

	/** Modifica la vida del objeto
	 * @param alto	Nueva vida (positiva
	 */
	public void setVida(int vida);
	
	/** Informa si el objeto está vivo (vida > 0)
	 * @return	true si el objeto está vivo, false si no
	 */
	public boolean estaVivo();

}
