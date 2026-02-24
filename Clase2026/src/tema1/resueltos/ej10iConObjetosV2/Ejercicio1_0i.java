package tema1.resueltos.ej10iConObjetosV2;

import java.util.ArrayList;
import java.util.Arrays;

/** Solución ejercicio 1.0j  -  https://docs.google.com/document/d/1IjFLFCttA1PXIQjiK4nFGRaCODdvbT61_SRniIY6o1s/edit?tab=t.0
 */
public class Ejercicio1_0i {
	//	Crea dos arrays, uno con los nombres de 4 o 5 usuarios de cualquier red social que uses,
	//	y otro con sus seguidores. Realiza un programa que muestre por consola esos usuarios 
	//  primero sin ordenar y luego ordenados por número de seguidores 
	//	(de mayor a menor).

	public static void main(String[] args) {
//		String[] usuarios = { "@sama", "@BillGates", "@JeffBezos", "@elonmusk" };
//		int[] seguidores = { 3400, 66000, 6700, 217700 };
//		UsuarioRedSocial[] listaUsuarios = { new UsuarioRedSocial( "@sama", 3400 ),
//				new UsuarioRedSocial( "@BillGates", 66000), new UsuarioRedSocial( "@JeffBezos", 6700),
//				new UsuarioRedSocial( "@elonmusk", 217700 ) };
		ArrayList<UsuarioRedSocial> listaUsuarios = new ArrayList<>();
		// tb puedes = new ArrayList<UsuarioRedSocial>()
		listaUsuarios.add( new UsuarioRedSocial( "@sama", 3400 ) );
		listaUsuarios.add( new UsuarioRedSocial( "@BillGates", 66000 ) );
		listaUsuarios.add( new UsuarioRedSocial( "@JeffBezos", 6700 ) );
		listaUsuarios.add( new UsuarioRedSocial( "@elonmusk", 217700 ) );
		
		// Se puede inicializar también en un solo paso, por ejemplo:
//		ArrayList<UsuarioRedSocial> listaUsuarios2 = 
//				new ArrayList<>(
//					Arrays.asList( 
//						new UsuarioRedSocial( "@sama", 3400 ),
//						new UsuarioRedSocial( "@BillGates", 66000), new UsuarioRedSocial( "@JeffBezos", 6700),
//						new UsuarioRedSocial( "@elonmusk", 217700 ) )
//				);
		
		System.out.println( "Lista original:" );
		visualizarUsuariosYSeguidores( listaUsuarios );
		ordenaUsuariosPorSeguidores( listaUsuarios );
		System.out.println( "Lista ordenada:" );
		visualizarUsuariosYSeguidores( listaUsuarios );
		
		// Perder seguidores de golpe
		perderSeguidores(listaUsuarios, -5000);
		visualizarUsuariosYSeguidores(listaUsuarios);
		
		// Añadir un usuario @cr7 con 2423444 seguidores
		anyadirUsuario( listaUsuarios, new UsuarioRedSocial( "@cr7", 2423444 ) );
		visualizarUsuariosYSeguidores(listaUsuarios);
		
	}
	
	private static void anyadirUsuario( ArrayList<UsuarioRedSocial> lista, UsuarioRedSocial nuevo ) {
		lista.add(nuevo);
	}

	// Disminuye en todos los usuarios el número de seguidores de descenso indicado
	// decremento debe ser negativo
	private static void perderSeguidores( ArrayList<UsuarioRedSocial> lista, int decremento ) {
		for (UsuarioRedSocial u : lista) {
			// u.numSeguidores += decremento;
			u.setNumSeguidores( u.getNumSeguidores() + decremento );
			// Delegar el control de error en la clase UsuarioRedSocial
		}
	}
	
	
	// Visualiza línea a línea usuario tabulador nº seguidores
	private static void visualizarUsuariosYSeguidores(ArrayList<UsuarioRedSocial> listaUsuarios ) {
		for (int i=0; i<listaUsuarios.size(); i++) {
//			System.out.println( usuarios[i] + "\t" + seguidores[i] );
			System.out.println( listaUsuarios.get(i) );
		}
	}
	
	// Ordenación bubble
	private static void ordenaUsuariosPorSeguidores(ArrayList<UsuarioRedSocial> listaUsuarios ) {
		for (int pasada=0; pasada<listaUsuarios.size()-1; pasada++) {
			for (int comp=0; comp<listaUsuarios.size()-1; comp++) {  // TODO mejorar que solo se hagan las comparaciones necesarias
//				boolean hayQueIntercambiar = seguidores[comp] < seguidores[comp+1];
//				if (hayQueIntercambiar) {
//					int aux = seguidores[comp];
//					seguidores[comp] = seguidores[comp+1];
//					seguidores[comp+1] = aux;
//					String auxNombre = usuarios[comp];
//					usuarios[comp] = usuarios[comp+1];
//					usuarios[comp+1] = auxNombre;
//				}
				boolean hayQueIntercambiar = listaUsuarios.get(comp).getNumSeguidores() < listaUsuarios.get(comp+1).getNumSeguidores();
				if (hayQueIntercambiar) {
					UsuarioRedSocial aux = listaUsuarios.get(comp);
					listaUsuarios.set(comp, listaUsuarios.get(comp+1) );
					listaUsuarios.set(comp+1, aux);
				}
			}
		}
	}
	
}
