package tema1.resueltos.ej10iConObjetos;

public class UsuarioRedSocial {
	// Datos (atributos)
	String nombre;
	int numSeguidores;
	// Podría tener más...
	// int numPublicaciones
	// String textoUltimaPublicacion
	// Date fechaAlta
	
	// Java define por defecto
	// constructor con ()
	
	
	// MÉTODOS
	// Funcionalidad
	
	@Override
	public String toString() {
		return nombre + " " + numSeguidores;
	}
}
