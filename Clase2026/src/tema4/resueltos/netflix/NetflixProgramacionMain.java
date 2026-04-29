package tema4.resueltos.netflix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NetflixProgramacionMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetxflixProgramacion netxflixProgramacion = new NetxflixProgramacion();
		netxflixProgramacion.cargarDatosProgramas();
		for (int i = 0; i < netxflixProgramacion.getListaProgramas().size(); i++) {
			System.out.println(netxflixProgramacion.getListaProgramas());
		}
		
		System.out.println("------------------------------------");
		
		System.out.println(netxflixProgramacion.cantidadPorTipos());
		
		System.out.println("------------------------------------");
		
		HashMap<String, ArrayList<Programa>> mapaClasificacion = netxflixProgramacion.clasificacionPaises();
		for (Map.Entry<String, ArrayList<Programa>> entry : mapaClasificacion.entrySet()) {
			String key = entry.getKey();
			ArrayList<Programa> val = entry.getValue();
			System.out.println(key + ": " + val );
		}
		// O lo que es lo mismo
		// for (String pais : mapaClasificacion.keySet()) {
		// 	System.out.println( pais + ": " + mapaClasificacion.get(pais) );
		// }
		
		System.out.println("------------------------------------");
		
		LinkedList<Programa> pila = netxflixProgramacion.pilaProgramasAnno(2017);
		for(Programa pr : pila) {
			System.out.println(pr.toString());
		}
		
		System.out.println("------------------------------------");
		
		int anno = netxflixProgramacion.annoMasProgramas();
		System.out.println("El anno que más programas en común tiene la lista de contenidos es: " + anno);
		
		netxflixProgramacion.guardarFicheroPaises();
	}

}
