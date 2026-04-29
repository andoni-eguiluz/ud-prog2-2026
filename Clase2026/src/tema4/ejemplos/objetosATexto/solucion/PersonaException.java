package tema4.ejemplos.objetosATexto.solucion;

public class PersonaException extends Exception {
	private static final long serialVersionUID = 1L; // Para implementar Serializable sin warning

	public PersonaException( String mensaje ) {
		super( mensaje );
	}
}
