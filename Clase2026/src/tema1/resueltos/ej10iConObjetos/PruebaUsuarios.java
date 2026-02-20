package tema1.resueltos.ej10iConObjetos;

public class PruebaUsuarios {
	public static void main(String[] args) {
		UsuarioRedSocial u = new UsuarioRedSocial( "Emily", 500 );
// Haría falta si no tengo constructor por defecto:
//		u.nombre = "Emily";
//		u.numSeguidores = 500;
		System.out.println( u.nombre );
		System.out.println( u );
		
		// Prueba lateral ¿puedo cambiar un string?
		// String a = "Hola";
		// a = a.replaceAll( "H", "l) ");
		
		UsuarioRedSocial u2 = new UsuarioRedSocial( "Lola", 100 );
		
		UsuarioRedSocial uNuevo = new UsuarioRedSocial( "Andoni" );
		
		System.out.println( "Cap. máxima de publicación:" + UsuarioRedSocial.CAPACIDAD_MAXIMA_TEXTO_PUBLICACION );
		System.out.println( "Núm. de usuarios actual: " + UsuarioRedSocial.getNumUsuarios() );
		// UsuarioRedSocial.numUsuarios = 0;   No se puede hacer porque es private
	}
}
