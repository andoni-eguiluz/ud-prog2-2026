package tema1c.ejercicios.registros;

import java.util.ArrayList;
import java.util.Scanner;

public class GrupoRegistros {
	protected ArrayList<Registro> listaRegistros;
	public GrupoRegistros( String nomFichero ) {
		listaRegistros = new ArrayList<>();
		Scanner lector = new Scanner( GrupoRegistros.class.getResourceAsStream( "datos-registro.txt" ) );
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			Registro registro = Registro.crearDeLinea( linea );
			if (registro!=null) {
				listaRegistros.add( registro );
			}
		}
		lector.close();
	}
	public ArrayList<Registro> getListaRegistros() {
		return listaRegistros;
	}
	
}
