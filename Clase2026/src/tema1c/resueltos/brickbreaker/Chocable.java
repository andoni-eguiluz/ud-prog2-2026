package tema1c.resueltos.brickbreaker;

public interface Chocable {
	
	/** Informa si el objeto puede ser tocado (con o sin choque)
	 * @return	true si es tocable (tiene que ser sensible al contacto), false en caso contrario
	 */
	public boolean esTocable();

	/** Informa si el objeto puede chocar o es transparente al choque
	 * @return	true si es chocable, false en caso contrario
	 */
	public boolean esChocable();

	/** Comprueba si hay choque entre objetos
	 * @param objeto2	Objeto de animación con el que comprobar choque
	 * @return	true si se tocan este objeto y objeto2, false en caso contrario
	 */
	public boolean chocaCon( ObjetoMovil objeto2 );

	/** Devuelve el impacto de choque entre dos objetos
	 * @param objeto2	Objeto con el que probar el choque
	 * @return	Devuelve null si no chocan, un vector con forma de punto indicando el ángulo y amplitud del choque sobre el objeto en curso
	 */
	public Polar vectorChoqueConObjeto( ObjetoMovil objeto2 );
	
}
