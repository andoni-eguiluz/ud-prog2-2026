package tema4.resueltos.deustoikea;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class DeustoIkea {

	public static void main(String[] args) {
		ArrayList<Tienda> tiendas = new ArrayList<Tienda>();
		ArrayList<Mueble> muebles = new ArrayList<Mueble>();
		HashMap<String, Integer> totalPorCategoria = new HashMap<>();
		HashMap<String, ArrayList<Mueble>> mueblesPorCategoria = new HashMap<>();
		
		cargarTiendasCSV(tiendas);
		System.out.println(tiendas.size());
		cargarMueblesCSV(muebles);
		System.out.println(muebles.size());
		rellenarAlmacenesTiendas(tiendas, muebles);
		
		// Mostramos el almacen de la primera tienda
		System.out.println(tiendas.get(0).getNombre());
		HashMap<Mueble, Integer> almacen = tiendas.get(0).getAlmacen();
		for (Mueble mueble : almacen.keySet()) {
			System.out.println(mueble.getNombre() + "->" + almacen.get(mueble));
		}
		
		System.out.println( "\nConteo de categorías de muebles:" );
		System.out.println( totalCategorias(muebles) );

		System.out.println( "\nConteo de muebles por categoría:" );
		totalPorCategoria = calcularTotalesPorCategoria(muebles);
		System.out.println( totalPorCategoria );
		
		System.out.println( "\nMuebles por categoría:" );
		mueblesPorCategoria = asociarMueblesPorCategoria(muebles);
		// System.out.println(mueblesPorCategoria);
		for (String cat : mueblesPorCategoria.keySet()) {
			System.out.println( "  " + cat + ": " + mueblesPorCategoria.get(cat).size() + " muebles en categoría." );
		}
		
		Tienda tienda = tiendaMasOnline(tiendas);
		System.out.println("\nLa tienda que más productos online tiene es " + tienda);
	
		Mueble mueble = muebleMasDisponible(tiendas);
		System.out.println("\nEl mueble que más tenemos nuestros almacenes es " + mueble);
		
		guardarTiendas(tiendas);
	}
	
	private static void guardarTiendas(ArrayList<Tienda> tiendas) {
		try {
			FileOutputStream fos = new FileOutputStream("tiendas.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tiendas);
			oos.close();
		} catch (IOException e) {
			System.err.println("Error al guardar tiendas " + e.getMessage());
		}
	}

	private static Mueble muebleMasDisponible(ArrayList<Tienda> tiendas) {
		// Preparamos un mapa con la suma de todos los valores
		HashMap<Mueble, Integer> totalMuebles = new HashMap<>();
		
		for (Tienda tienda : tiendas) {
			for (Mueble mueble : tienda.getAlmacen().keySet()) {
				int valor = tienda.getAlmacen().get(mueble);
				if (totalMuebles.containsKey(mueble)) {
					// sumar al total el valor
					int contador = totalMuebles.get(mueble);
					contador += valor;
					totalMuebles.put(mueble, contador);
				} else {
					// añadir este mueble al mapa
					totalMuebles.put(mueble, valor);
				}
			}
		}
		
		// Buscamos el mueble que mayor valor tenga en totalMuebles
		
		/*
		 * En Python se hacía así:
		 * 
		 * diccionario = {
		 *   "bilbao": 7,
		 *   "barcelona": 12,
		 *   "madrid": 3,
		 *   "malaga": 3
		 * }
		 * 
		 * mayor_clave = 0
		 * mayor_valor = 0
		 * 
		 * for clave in diccionario:
		 *   valor = diccionario[clave]
		 *   if valor > mayor_valor:
		 *     mayor_clave = clave
		 *     mayor_valor = valor
		 * 
		 * return mayor_clave
		 */
		
		Mueble mayor_clave = null;
		int mayor_valor = 0;
		
		for (Mueble clave : totalMuebles.keySet()) {
			int valor = totalMuebles.get(clave);
			if (valor > mayor_valor) {
				mayor_clave = clave;
				mayor_valor = valor;
			}
		}
		
		return mayor_clave;
	}

	private static Tienda tiendaMasOnline(ArrayList<Tienda> tiendas) {
		Tienda mayor = tiendas.get(0);
		
		for (Tienda tienda : tiendas) {
			if ( tienda.totalMueblesOnline() > mayor.totalMueblesOnline() ) {
				mayor = tienda;
			}
		}
		
		return mayor;
	}

	private static void rellenarAlmacenesTiendas(ArrayList<Tienda> tiendas, ArrayList<Mueble> muebles) {
		for (Tienda tienda : tiendas) {
			// Pedimos el almacen a la tienda, inicialmente está vacío
			HashMap<Mueble, Integer> almacen = tienda.getAlmacen();
			// Rellenamos el almacen con 5000 muebles aleatorios;
			for (int i = 0; i < 5000; i++) {
				// Elegir un mueble aletorio
				int alea = (int) (Math.random() * muebles.size());
//				Random random = new Random();
//				alea = random.nextInt( muebles.size() )
				Mueble mueble = muebles.get(alea);
				
				// Añadir el mueble elegido al almacen
				if (almacen.containsKey(mueble)) {
					// Actualizar el contador
					int valor = almacen.get(mueble) + 1;
					almacen.put(mueble, valor);
				} else {
					// Añadir esta clave
					almacen.put(mueble, 1);
				}
			}
		}
	}

	private static void cargarTiendasCSV(ArrayList<Tienda> tiendas) {
		// File -> Scanner -> while -> nextLine -> split -> new Objeto
		File f = new File("ikea-tiendas.csv");
		try {
			Scanner sc = new Scanner(f, "UTF-8");  // Ojo al UTF-8
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				String nombre = campos[0];
				String direc = campos[1];
				String tipo = campos[2];
				String horario = campos[3];
				Tienda nueva = new Tienda(nombre, direc, tipo, horario);
				tiendas.add(nueva);
			}			
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static HashMap<String, ArrayList<Mueble>> asociarMueblesPorCategoria(ArrayList<Mueble> muebles) {
		HashMap<String, ArrayList<Mueble>> mapa = new HashMap<>();
		
		for (Mueble mueble : muebles) {
			String cat = mueble.getCategoria();
			if (!mapa.containsKey(cat)) {
				// introduzco esa categoria en el mapa
				mapa.put(cat, new ArrayList<Mueble>());
				// añado este mueble a la lista de esta categoria
				mapa.get(cat).add(mueble);
			} else {
				// añado este mueble a la lista de esta categoria
//				mapa.get(cat).add(mueble);
				// O esto que es lo mismo
				ArrayList<Mueble> l = mapa.get(cat);
				l.add(mueble);
			}
		}
		
		return mapa;
	}

	private static HashMap<String, Integer> calcularTotalesPorCategoria(ArrayList<Mueble> muebles) {
		HashMap<String, Integer> mapa = new HashMap<>();
		
		for (Mueble mueble : muebles) {
			String cat = mueble.getCategoria();
			if (mapa.containsKey(cat)) {
				// sumo 1 al total
				int valor = mapa.get(cat) + 1;
				mapa.put(cat, valor);
			} else {
				// introduzco esa categoria en el mapa
				mapa.put(cat, 1);  // replace
			}
		}
		
		return mapa;
	}

	private static int totalCategorias(ArrayList<Mueble> muebles) {
		HashSet<String> conjuntoCategorias = new HashSet<String>();
		
		for (Mueble mueble : muebles) {
			conjuntoCategorias.add(mueble.getCategoria());
		}
		
		return conjuntoCategorias.size();
	}

	private static void cargarMueblesCSV(ArrayList<Mueble> muebles) {
		/// muebles = new ArrayList<Mueble>();   NOOOOOOOOOOOOOOOOOOOOOOOOOO
		muebles.clear();
		File f = new File("ikea-muebles.csv");
		try {
			Scanner sc = new Scanner(f, "UTF-8");  // Ojo al UTF-8
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				try {
					String[] campos = linea.split(",");  // StringTokenizer
					int codigo = Integer.parseInt(campos[0]);
					String nombre = campos[1];
					String cat = campos[2];
					double precio = Double.parseDouble(campos[3]);
					boolean online = Boolean.parseBoolean(campos[4]);
					String url = campos[5];
					Mueble nuevo;
					if (online) {
						nuevo = new MuebleOnline(codigo, nombre, cat, precio, url);
					} else {
						nuevo = new Mueble(codigo, nombre, cat, precio);
					}
					muebles.add(nuevo);
				} catch (Exception e) {
					System.out.println( "Error línea de texto de mueble: " + linea);
				}
			}
			sc.close();
		// } catch (NumberFormatException e) {  // Este error deja de leer el fichero
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
