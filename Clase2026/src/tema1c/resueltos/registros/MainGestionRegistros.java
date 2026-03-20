package tema1c.resueltos.registros;

import java.util.ArrayList;

public class MainGestionRegistros {

	public static void main(String[] args) {
		GrupoRegistros grupo = new GrupoRegistros( "datos-registro.txt" );
		System.out.println( "TODOS LOS REGISTROS:" );
		for (Registro reg : grupo.getListaRegistros()) {
			System.out.println( "  " + reg.toString() );
		}
		// RESUELTO Listar solo los registros de usuario
		for (Registro reg : grupo.getListaRegistros()) {
			if (reg instanceof RegistroDeUsuario) {
				System.out.println( reg );
			}
		}
		// RESUELTO Listar solo los precios
		System.out.println( "LISTA DE PRECIOS");
		for (Registro reg : grupo.getListaRegistros()) {
//			if (reg instanceof PrecioElectricidad) {
//				// ..
//			} else if (reg instanceof PrecioCarburante) {
//				// ..
//			}
			if (reg instanceof ConPrecio) {
				System.out.println( reg.toString() );
			}
		}
		// RESUELTO Calcular la media de todos los precios
		double suma = 0;
		int cont = 0;
		for (Registro reg : grupo.getListaRegistros()) {
			if (reg instanceof ConPrecio) {
				ConPrecio precio = (ConPrecio) reg;
				suma += precio.getPrecio();
				cont++;
			}
		}
		System.out.println( "MEDIA DE PRECIOS: " + (suma/cont) );
		
		// RESUELTO Listar todos los usuarios
		System.out.println( "LISTA DE USUARIOS:");
		for (Registro reg : grupo.getListaRegistros()) {
			if (reg instanceof RegistroDeUsuario) {
				System.out.println( ((RegistroDeUsuario)reg).getUsuario() );
			}
		}
		
		// RESUELTO Listar los precios en rango 0.4 y 1.8
		for (Registro reg : grupo.getListaRegistros()) {
			if (reg instanceof ConPrecio) {
				ConPrecio precio = (ConPrecio) reg;
				if (precio.estaPrecioEnRango(0.4, 1.8)) {
					System.out.println( reg );
				}
			}
		}
		
		// RESUELTO Crear lista solo de RegistroDeUsuarios
		ArrayList<RegistroDeUsuario> listaRU = new ArrayList<>();
		for (Registro reg : grupo.getListaRegistros()) {
			if (reg instanceof RegistroDeUsuario) {
				listaRU.add( (RegistroDeUsuario) reg );
			}
		}
		System.out.println( "LISTA DE USUARIOS: " + listaRU );
		
	}

}
