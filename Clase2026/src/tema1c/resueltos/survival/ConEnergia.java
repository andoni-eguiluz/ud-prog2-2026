package tema1c.resueltos.survival;

public interface ConEnergia extends Chocable {
	/** Modifica la energía
	 * @param energia	Energía nueva que queremos que tenga el objeto ahora, no puede ser negativa (no se cambia en ese caso)
	 */
	void setEnergia( int energia );

	/** Modifica la energía
	 * @param energia	Energía a incrementar al objeto. Si tras añadirla (siendo negativa) pasa a ser negativa, se deja en cero.
	 */
	void incEnergia( int incEnergia );
	
	/** Devuelve la energía de un objeto
	 * @return	Nivel de energía actual
	 */
	int getEnergia();
	
	/** Informa si el objeto está destruido
	 * @return	true si la energía del objeto ha llegado a cero, false si es mayor que cero
	 */
	boolean estaDestruido();
}
