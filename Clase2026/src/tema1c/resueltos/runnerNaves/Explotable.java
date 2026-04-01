package tema1c.resueltos.runnerNaves;

/** Interfaz para objetos que pueden explotar.
 * Permite configurar tres estados que ocurren en el tiempo:
 * - normal (no está explotando) [estado inicial]
 * - en explosión (está explotando) [este estado puede durar unas pocas décimas de segundo]
 * - ya explotado [estado final, irreversible]
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public interface Explotable {
	/** Informa al objeto de que debe explotar
	 */
	public void explota();
	/** Devuelve la información de si el objeto está explotando
	 * @return	true si el objeto está explotando, false si no ha explotado
	 */
	public boolean estaExplotando();
	/** Devuelve la información de si el objeto ha explotado
	 * @return	true si el objeto ha explotado por completo, false en caso contrario
	 */
	public boolean haExplotado();
}
