package tema1b.ejemplos.ofertas;

import java.io.Serializable;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;

/** Oferta medida en unidades
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class OfertaUnidades extends Oferta implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private int numUnidades;  // Num. de unidades que se compran
	
	/** Crea una nueva oferta (medida en unidades)
	 * @param nombre	Nombre de producto ofertado
s	 * @param precio	Precio de cada unidad (en euros)
	 * @param numUnidades	Número de unidades que se compran
	 */
	public OfertaUnidades(String nombre, double precio, int numUnidades) {
		super(nombre, precio);
		this.numUnidades = numUnidades;
	}
	
	/** Crea una nueva oferta (medida en unidades), con 0 unidades de compra
	 * @param nombre	Nombre de producto ofertado
	 * @param precio	Precio de cada unidad (en euros)
	 */
	public OfertaUnidades(String nombre, double precio) {
		this(nombre, precio, 0);  // Reutilizamos un constructor desde el otro
	}

	// Método de copia (nos interesa poder duplicar ofertas de este tipo) - por eso implementa Cloneable
	@Override
	public Object clone() {
		return new OfertaUnidades(nombre, precio, numUnidades);
	}

	public int getNumUnidades() {
		return numUnidades;
	}

	public void setNumUnidades(int numUnidades) {
		this.numUnidades = numUnidades;
	}

	@Override
	public double getPrecioTotal() {
		return precio * numUnidades;
	}
	
	@Override
	public boolean pedirDatos( VentanaConsolaConBotones ventana ) {
		ventana.println( "Introduce unidades a comprar de " + getNombre() );
		int numero = ventana.leeInt();
		if (numero == Integer.MAX_VALUE) {  // Error en la lectura
			return false;
		} else {
			setNumUnidades( numero );  // Actualiza el atributo
			return true;
		}
	}
	
	
	@Override
	public String toString() {
		String ret = super.toString() + String.format( "(%4.2f€)", precio );
		if (numUnidades == 0) {
			return ret;
		} else {
			return ret + " - compra de " + numUnidades + " unidades.";
		}
	}
	
}
