package tema1c.ejercicios.registros;

public class MainGestionRegistros {

	public static void main(String[] args) {
		GrupoRegistros grupo = new GrupoRegistros( "datos-registro.txt" );
		System.out.println( "TODOS LOS REGISTROS:" );
		for (Registro reg : grupo.getListaRegistros()) {
			System.out.println( "  " + reg.toString() );
		}
		// TODO Listar solo los registros de usuario
		// TODO Listar solo los precios
		// TODO Calcular la media de todos los precios
		// TODO Listar todos los usuarios
		// TODO Listar los precios en rango 0.4 y 1.8
	}

}
