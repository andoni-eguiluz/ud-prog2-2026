package tema1.resueltos.ej10iConObjetosV2;

public class UsuarioRedSocial {

	// PARTE STATIC
	
	public static final int CAPACIDAD_MAXIMA_TEXTO_PUBLICACION = 200;
	private static int numUsuarios = 0;
	
	public static int getNumUsuarios() {
		return numUsuarios;
	}
	
	
	// ======================
	// PARTE NO STATIC
	
	
	// Datos (atributos)
	private String nombre;
	private int numSeguidores = 0;  // inicialización que se lanza en cada new
	// Podría tener más...
	// int numPublicaciones
	// String textoUltimaPublicacion
	// Date fechaAlta
	
	// CONSTRUCTORES
	// Java define por defecto
	// constructor con ()
	
	public UsuarioRedSocial( String nombre, int numSeguidores ) {
		// 1.- Crea especio de memoria para un objeto (Java)
		// 2.- Inicializar los atributos (programador)
		// 3.- Devuelve la referencia del objeto creado (Java)
		this.nombre = nombre;
		this.numSeguidores = numSeguidores;
		numUsuarios++;
	}
	
	/** Crea un nuevo usuario de red social con 0 seguidores
	 * @param nombre	Nombre único de ese usuario, no vacío
	 */
	public UsuarioRedSocial(String nombre) {
		// super();
		this.nombre = nombre;
		numUsuarios++;
	}

	// SETS Y GETS
	
	public String getNombre() {
		return nombre;
	}

	/** Devuelve el número de seguidores del usuario
	 * @return	Número en MILES de seguidores
	 */
	public int getNumSeguidores() {
		return numSeguidores;
	}

	/** Cambia el número de seguidores. No puede ser negativo, en el caso de serlo
	 * se dejará en cero y saldrá un mensaje de error en la consola de error
	 * @param numSeguidores	Nuevo número de seguidores, EN MILES
	 */
	public void setNumSeguidores(int numSeguidores) {
		this.numSeguidores = numSeguidores;
		if (this.numSeguidores < 0) {  // Control de error
			this.numSeguidores = 0;
			System.err.println( "Se ha intentado actualizar un número de segs. negativo" );
		}
	}	

	
	// MÉTODOS
	// Funcionalidad
	








	@Override
	public String toString() {
		return nombre + " " + this.numSeguidores;
	}

}
