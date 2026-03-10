package tema1b.ejercicios.ventas;

import java.util.ArrayList;

// TODO Pendiente de ser implementada
public class Tienda {
	public Tienda( String nombre ) {
		// TODO 
	}
	public ArrayList<Trabajador> getListaTrabajadores() {
		// TODO 
		return null;
	}
	
	/** Añade un trabajador a la tienda
	 * @param t	Trabajador que se añade
	 */
	public void addTrabajador( Trabajador t ) {
		// TODO 
	}
	
	public ArrayList<Producto> getListaProductos() {
		// TODO 
		return null;
	}
	
	/** Añade un producto a la tienda
	 * @param p	Producto que se añade
	 */
	public void addProducto( Producto p ) {
		// TODO 
	}

		private long tiempoAnterior = System.currentTimeMillis();
	/** Simula el paso de tiempo en la tienda. Si los trabajadores están ocupado, trabajan en su función y disminuye su tiempo pendiente
	 * Llamar a este método cada vez que se quiera actualizar el trabajo
	 */
	public void pasaElTiempo() {
		int tiempoTranscurrido = (int) (System.currentTimeMillis() - tiempoAnterior);
		tiempoAnterior = System.currentTimeMillis(); // Se actualiza el tiempo para la siguiente vez
		for (Trabajador t : getListaTrabajadores()) {
			t.pasaElTiempo( tiempoTranscurrido / 1000 );
		}
	}
	
	/** Devuelve un trabajador libre. Si no hay ninguno, devuelve null
	 * @return	Primer trabajador libre que se encuentra en la tienda, null si ninguno lo está
	 */
	public Trabajador getTrabajadorLibre() {
		// TODO 
		return null;
	}

	/** Devuelve la información de todos los trabajadores con su estado de trabajo
	 * @return	String en formato nombre:libre + nombre:tiempo + ...
	 */
	public String getInfoTrabajadores() {
		// TODO 
		return null;
	}
	
}
