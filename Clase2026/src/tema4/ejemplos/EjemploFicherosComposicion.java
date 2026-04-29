package tema4.ejemplos;

import java.util.ArrayList;
import java.util.Arrays;

public class EjemploFicherosComposicion {
	public static void main(String[] args) {
		// TODO Prueba a guardar y cargar la lista de pedidos tanto en binario como en texto.
		// Valora las posibilidades y problemáticas que tiene el guardado/cargado en texto cuando hay composición
		Articulo art1 = new Articulo( 1, "XBox Elite Controller", 150 );
		Articulo art2 = new Articulo( 2, "Apple Watch 6", 200 );
		Articulo art3 = new Articulo( 3, "Phillips Hue LED Strip Light", 125 );
		Articulo art4 = new Articulo( 4, "Meta Quest 3", 530 );
		Pedido pedido1 = new Pedido( "a", System.currentTimeMillis() );
		Pedido pedido2 = new Pedido( "b", System.currentTimeMillis() );
		Pedido pedido3 = new Pedido( "c", System.currentTimeMillis() );
		pedido1.getListaArticulos().add( art1 );
		pedido1.getListaArticulos().add( art2 );
		pedido2.getListaArticulos().add( art1 );
		pedido3.getListaArticulos().add( art3 );
		pedido3.getListaArticulos().add( art1 );
		pedido3.getListaArticulos().add( art1 );
		pedido3.getListaArticulos().add( art2 );
		ArrayList<Pedido> listaPedidos = new ArrayList<>( Arrays.asList( pedido1, pedido2, pedido3 ) );
		System.out.println( "Antes de guardar: " + listaPedidos );
		System.out.println( "Artículos en pedidos: " + listaArticulosEnPedidos( listaPedidos ) );  // TODO ver qué pasa con este método después de cargar
		
		// TODO guardar y cargar como binario
		
		System.out.println( "Tras guardar/cargar binario: " + listaPedidos );
		System.out.println( "Artículos en pedidos: " + listaArticulosEnPedidos( listaPedidos ) );  // TODO ver qué pasa con este método después de cargar

		// TODO guardar y cargar como texto
		
		System.out.println( "Tras guardar/cargar texto: " + listaPedidos );
		System.out.println( "Artículos en pedidos: " + listaArticulosEnPedidos( listaPedidos ) );  // TODO ver qué pasa con este método después de cargar
		
	}
	
	private static ArrayList<Articulo> listaArticulosEnPedidos( ArrayList<Pedido> listaPedidos ) {
		ArrayList<Articulo> ret = new ArrayList<>();
		for (Pedido p : listaPedidos) {
			for (Articulo a : p.getListaArticulos()) {
				boolean articuloYaExiste = false;
				for (Articulo aTotal : ret) {
					if (aTotal == a) {
						articuloYaExiste = true;
						break;
					}
				}
				if (!articuloYaExiste) {
					ret.add( a );
				}
			}
		}
		return ret;
	}
	
	private static class Pedido {
		private static int ultimoCodigoGenerado = 0;
		private int codigo;
		private String cliente;
		private long fecha;
		private ArrayList<Articulo> listaArticulos;
		/** Crea un pedido nuevo, con código único
		 * @param cliente	Cliente que hace el pedido
		 * @param fecha	Fecha de pedido
		 */
		public Pedido( String cliente, long fecha ) {
			this.cliente = cliente;
			this.fecha = fecha;
			ultimoCodigoGenerado++;
			codigo = ultimoCodigoGenerado;
			listaArticulos = new ArrayList<>();
		}
		public ArrayList<Articulo> getListaArticulos() {
			return listaArticulos;
		}
		public int getCodigo() {
			return codigo;
		}
		public long getFecha() {
			return fecha;
		}
		public String getCliente() {
			return cliente;
		}
		@Override
		public String toString() {
			return codigo + "-" + cliente + " -> " + listaArticulos;
		}
	}
	
	private static class Articulo {
		private int codigo;
		private String nombre;
		private double precio;
		public Articulo(int codigo, String nombre, double precio) {
			super();
			this.codigo = codigo;
			this.nombre = nombre;
			this.precio = precio;
		}
		public int getCodigo() {
			return codigo;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		@Override
		public String toString() {
			return nombre;
		}
	}
	
}
