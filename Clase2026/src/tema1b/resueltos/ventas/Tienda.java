package tema1b.resueltos.ventas;

import java.util.ArrayList;

public class Tienda {
	private String nombre;
	private ArrayList<Trabajador> listaTrabajadores;
	private ArrayList<Producto> listaProductos;
	
	public Tienda( String nombre ) {
		super();
		this.nombre = nombre;
		listaTrabajadores = new ArrayList<>();
		listaProductos = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public ArrayList<Trabajador> getListaTrabajadores() {
		return listaTrabajadores;
	}
	
	/** Añade un trabajador a la tienda
	 * @param t	Trabajador que se añade
	 */
	public void addTrabajador( Trabajador t ) {
		listaTrabajadores.add( t );
	}
	
	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}
	
	/** Añade un producto a la tienda
	 * @param p	Producto que se añade
	 */
	public void addProducto( Producto p ) {
		listaProductos.add( p );
	}

		private long tiempoAnterior = System.currentTimeMillis();
	/** Simula el paso de tiempo en la tienda. Si los trabajadores están ocupado, trabajan en su función y disminuye su tiempo pendiente
	 * Llamar a este método cada vez que se quiera actualizar el trabajo
	 */
	public void pasaElTiempo() {
		int tiempoTranscurrido = (int) (System.currentTimeMillis() - tiempoAnterior);
		tiempoAnterior = System.currentTimeMillis(); // Se actualiza el tiempo para la siguiente vez
		for (Trabajador t : listaTrabajadores) {
			t.pasaElTiempo( tiempoTranscurrido / 1000 );
		}
	}
	
	/** Devuelve un trabajador libre. Si no hay ninguno, devuelve null
	 * @return	Primer trabajador libre que se encuentra en la tienda, null si ninguno lo está
	 */
	public Trabajador getTrabajadorLibre() {
		for (Trabajador t : listaTrabajadores) {
			if (t.isLibre()) return t;
		}
		return null;
	}

	/** Devuelve la información de todos los trabajadores con su estado de trabajo
	 * @return	String en formato nombre:libre + nombre:tiempo + ...
	 */
	public String getInfoTrabajadores() {
		String ret = "|";
		for (Trabajador t : listaTrabajadores) {
			if (t.isLibre()) {
				ret += " " + t.getNombre() + ":libre |"; 
			} else {
				ret += " " + t.getNombre() + ":trabajando-" + t.getTiempoPendiente() + "sgs. |"; 
			}
		}
		return ret;
	}
	
}
