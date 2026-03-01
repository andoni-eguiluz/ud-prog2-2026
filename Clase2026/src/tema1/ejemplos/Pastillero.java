package tema1.ejemplos;

/** Ejemplo de contenedor: Pastillero que contiene pastillas
 */
public class Pastillero {
	
	private Pastilla[] misPastillas;
	private int numPastillas;
	private final int TAM_MAX_PASTILLERO = 100;
	/** Crea un pastillero vacío
	 */
	public Pastillero() {
		misPastillas = new Pastilla[TAM_MAX_PASTILLERO];
		numPastillas = 0;
	}
	/** Añade una pastilla nueva al pastillero
	 * ES ERRONEO CUANDO EL PASTILLERO ESTA LLENO ({@link #estaLleno()}
	 * @param pas	Pastilla a añadir
	 */
	public void anyadir( Pastilla pas ) {
		misPastillas[numPastillas] = pas;
		numPastillas++;
	}
	
	/** Informa sobre el llenado del pastillero
	 * @return	true si está lleno (y no se pueden meter más pastillas), false en caso contrario
	 */
	public boolean estaLleno() {
		return (numPastillas >= TAM_MAX_PASTILLERO);
	}
	
	/** Informa sobre el número de pastillas en el pastillero
	 * @return	número de pastillas, 0 si está vacío
	 */
	public int getNumPastillas() {
		return numPastillas;
	}
	
	/** Devuelve la última pastilla que hayamos metido en el pastillero
	 * pero si está vacío devuelve null
	 * @return	la última pastilla o null
	 */
	public Pastilla coger() {
		// Posible error: coger si no hay pastillas (numPastillas = 0)
		if (numPastillas == 0)
			return null;
		else {
			numPastillas--;
			return misPastillas[numPastillas];
		}
	}
	public String toString() {
		String ret = "{ ";
		for (int i=0; i<numPastillas; i++) {
			if (i>0) ret += ", ";
			ret += misPastillas[i].toString();
		}
		return ret + " }";
	}
	
}
