package examen.deustopintxos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DeustoPintxos {
	
	// TAREA 1A: cargarProductosCSV
	public static Set<Producto> cargarProductosCSV() {
		Set<Producto> productos = new HashSet<Producto>();
		
		File f = new File("deustopintxos.csv");
		try {
			Scanner sc = new Scanner(f);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(",");
				Producto nuevo;
				if (campos[0].equals("Bebida")) {
					nuevo = new Bebida(campos[1], Double.parseDouble(campos[2]), Double.parseDouble(campos[3]));
				} else {
					nuevo = new Pintxo(campos[1], Double.parseDouble(campos[2]), Boolean.parseBoolean(campos[3]));
				}
				productos.add(nuevo);
			}
			
			sc.close();			
		} catch (FileNotFoundException e) {
			System.err.println("Error al cargar datos: " + e.getMessage());
		}
		
		return productos;
	}
	
	// TAREA 1B: generarPedidos
	public static void generarPedidos(Set<Producto> productos, List<Bar> bares) {
		ArrayList<Producto> lista = new ArrayList<Producto>(productos);
		int c = 10000;
		
		for (Bar bar : bares) {
			// Añadimos 100 pedidos por bar y día
			for (Dia dia: Dia.values()) {
				for (int i = 0; i < 100; i++) {
					Pedido pedido = new Pedido(c, dia);
					
					// Cada pedido tiene 5 productos
					for (int j = 0; j < 5; j++) {
						int alea = (int) (Math.random() * lista.size());
						Producto producto = (Producto) lista.get(alea);
						if (!pedido.getProductos().containsKey(producto)) {
							pedido.getProductos().put(producto, 0);
						}
						pedido.getProductos().replace(producto, pedido.getProductos().get(producto) + 1);
					}
					
					bar.getPedidos().add(pedido);
					c++;					
				}
			}		
			c += 10000;
		}
	}
	
	// TAREA 2D: diaMayorRecaudacion
	public static Dia diaMayorRecaudacion(List<Bar> bares) {
		HashMap<Dia, Double> mapa = new HashMap<>();
		
		for (Dia dia : Dia.values()) {
			double totalDia = 0.0;
			for (Bar bar : bares) {
				totalDia += bar.getRecaudacion().get(dia);
			}
			mapa.put(dia, totalDia);
		}
		
		Dia mayorClave = Dia.LUNES;
		double mayorValor = 0.0;
		
		for (Dia dia : mapa.keySet()) {
			double valor = mapa.get(dia);
			if (valor > mayorValor) {
				mayorClave = dia;
				mayorValor = valor;
			}
		}
		
		return mayorClave;
	}
	
	public static void main(String[] args) {
		Set<Producto> productos = new HashSet<Producto>();
		List<Bar> bares = new ArrayList<Bar>();
		
		// TAREA 1A: cargarProductosCSV
		productos = cargarProductosCSV();
		
		// Creamos los 4 bares
		String[] nombres = {"Kupela", "Zaharra", "Sagarra", "Epelde"};
		for (String nombre : nombres) {
			bares.add(new Bar(nombre));
		}
		
		// TAREA 1B: generarPedidos
		generarPedidos(productos, bares);

		// Descomenta esta linea solamente si no has sabido hacer la TAREA 1
		//datosIniciales(productos, bares);

		System.out.println(productos);
		System.out.println(bares);
		
		// TAREA 2C: recorre el mapa de bares, ejecuta cobrarPedidos y muestra como ha quedado la recaudacion
		for (Bar bar : bares) {
			bar.cobrarPedidos();
			System.out.println("Recaudacion del bar " + bar.getNombre() + ":" + bar.getRecaudacion());
		}
		
		// TAREA 2D: diaMayorRecaudacion
		System.out.println("El dia de mayor recaudacion ha sido el " + diaMayorRecaudacion(bares));
	}

	private static void datosIniciales(Set<Producto> productos, List<Bar> bares) {
		// IMPORTANTE: no copies parte de este codigo en tus soluciones. 
		// Este metodo esta programado de la manera mas manual posible y 
		// NO sera considerado como una solucion valida en tus tareas.
		
		productos.add(new Bebida("Txakoli", 1.50, 10.5));
		productos.add(new Bebida("Estrella Galicia", 1.75, 5.5));
		productos.add(new Bebida("Coca-cola", 1.75, 0.0));
		productos.add(new Bebida("Radler", 2.0, 3.5));
		productos.add(new Bebida("Verdejo", 1.50, 11.5));
		productos.add(new Bebida("Rioja", 1.50, 10.5));
		productos.add(new Bebida("Fanta", 1.75, 0.0));
		productos.add(new Bebida("Mosto", 1.25, 0.0));
		productos.add(new Pintxo("Gilda", 1.50, false));
		productos.add(new Pintxo("Tortilla", 2.25, true));
		productos.add(new Pintxo("Mejillon relleno", 1.75, true));
		productos.add(new Pintxo("Croqueta de boletus", 1.50, false));
		productos.add(new Pintxo("Champi", 1.50, true));
		productos.add(new Pintxo("Brocheta de gambas", 2.25, false));
		productos.add(new Pintxo("Tartaleta de txangurro", 1.50, false));
		productos.add(new Pintxo("Txapela", 1.50, false));
		
		HashMap<Producto, Integer> mapa1 = new HashMap<>();
		mapa1.put(new Pintxo("Tortilla", 2.25, true), 2);
		mapa1.put(new Pintxo("Txapela", 1.50, false), 1);
		HashMap<Producto, Integer> mapa2 = new HashMap<>();
		mapa2.put(new Bebida("Coca-cola", 1.75, 0.0), 1);
		mapa2.put(new Bebida("Txakoli", 1.50, 10.5), 2);
		HashMap<Producto, Integer> mapa3 = new HashMap<>();
		mapa3.put(new Pintxo("Tortilla", 2.25, true), 2);
		mapa3.put(new Bebida("Txakoli", 1.50, 10.5), 2);
		HashMap<Producto, Integer> mapa4 = new HashMap<>();
		mapa4.put(new Pintxo("Txapela", 1.50, false), 1);
		mapa4.put(new Bebida("Coca-cola", 1.75, 0.0), 1);
		HashMap<Producto, Integer> mapa5 = new HashMap<>();
		mapa5.put(new Pintxo("Txapela", 1.50, false), 1);
		mapa5.put(new Bebida("Coca-cola", 1.75, 0.0), 1);
		mapa5.put(new Bebida("Txakoli", 1.50, 10.5), 2);
		HashMap<Producto, Integer> mapa6 = new HashMap<>();
		mapa6.put(new Pintxo("Tortilla", 2.25, true), 2);
		mapa6.put(new Pintxo("Txapela", 1.50, false), 1);
		mapa6.put(new Bebida("Coca-cola", 1.75, 0.0), 1);
		HashMap<Producto, Integer> mapa7 = new HashMap<>();
		mapa7.put(new Pintxo("Tortilla", 2.25, true), 2);
		mapa7.put(new Pintxo("Txapela", 1.50, false), 1);
		mapa7.put(new Bebida("Coca-cola", 1.75, 0.0), 1);
		mapa7.put(new Bebida("Txakoli", 1.50, 10.5), 2);
		
		Pedido p1 = new Pedido(1, Dia.LUNES, mapa1);
		Pedido p2 = new Pedido(2, Dia.MARTES, mapa2);
		Pedido p3 = new Pedido(3, Dia.MIERCOLES, mapa3);
		Pedido p4 = new Pedido(4, Dia.JUEVES, mapa4);
		Pedido p5 = new Pedido(5, Dia.VIERNES, mapa5);
		Pedido p6 = new Pedido(6, Dia.SABADO, mapa6);
		Pedido p7 = new Pedido(7, Dia.DOMINGO, mapa7);
		
		String[] nombres = {"Kupela", "Zaharra", "Sagarra", "Epelde"};
		for (String nombre : nombres) {
			bares.add(new Bar(nombre));
		}
		
		for (Bar bar : bares) {
			bar.getPedidos().add(p1);
			bar.getPedidos().add(p2);
			bar.getPedidos().add(p3);
			bar.getPedidos().add(p4);
			bar.getPedidos().add(p5);
			bar.getPedidos().add(p6);
			bar.getPedidos().add(p7);
		}
	}

}
