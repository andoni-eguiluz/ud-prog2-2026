package tema1.resueltos.ej10iConObjetos;

public class PruebaUsuarios {
	public static void main(String[] args) {
		UsuarioRedSocial u = new UsuarioRedSocial();
		u.nombre = "Emily";
		u.numSeguidores = 500;
		System.out.println( u.nombre );
		System.out.println( u );
	}
}
