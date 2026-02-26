package tema1.resueltos.ej10iConObjetosV3;

public class PruebaRedesSociales {

	private static RedSocial x;
	private static RedSocial linkedin;
	
	public static void main(String[] args) {
		x = new RedSocial( "x", "x.com", TipoRedSocial.GENERALISTA );
		x.anyadir( new UsuarioRedSocial( "@sams", 1300 ) );
		x.anyadir( new UsuarioRedSocial( "@JeffBezos", 61000 ) );
		x.anyadir( new UsuarioRedSocial( "@BillGates", 62000 ) );
		x.anyadir( new UsuarioRedSocial( "@elonmusk", 128900 ) );
		x.visualizarUsuariosYSeguidores();
		x.ordenaUsuariosPorSeguidores();
		x.visualizarUsuariosYSeguidores();

		// Añadir usuarios
		x.anyadir( new UsuarioRedSocial( "Andoni" ) );
		x.ordenaUsuariosPorSeguidores();
		System.out.println( "\nRed social " + x );

		// Buscar usuario
		UsuarioRedSocial usuarioABuscar = new UsuarioRedSocial( "@BillGates", 62000 );
//		System.err.println( "Está bill gates? " + usuariosRS.contains( usuarioABuscar ) );
		System.out.println( "En qué posición está " + usuarioABuscar + "? " + x.buscar( usuarioABuscar ) );
		System.out.println( "Media de seguidores: " + x.calcularMediaSeguidores() + " (miles)" );
		
		// Trabajo con linkedin
		linkedin = new RedSocial( "linkedin", "linkedin.com", TipoRedSocial.PROFESIONAL );
		linkedin.anyadir( new UsuarioRedSocial( "luistorres", 6 ) );
		linkedin.anyadir( new UsuarioRedSocial( "amaiagil", 9000 ) );
		
		System.out.println( "\nRed social " + linkedin );		
		
	}
		
}