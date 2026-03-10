package tema1b.ejemplos.ofertas;

import java.io.Serializable;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;

/** Oferta medida en peso
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class OfertaPeso extends Oferta implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private static final double COSTE_PESADO = 0.25;  // 0.25 euros cuesta pesar	
	
	private double pesoKg;  // Peso que se compra (en kg.)
	
	/** Crea una nueva oferta (medida en peso)
	 * @param nombre	Nombre de producto ofertado
s	 * @param precio	Precio de cada kilogramo
	 * @param pesoKg	Número de kg. que se compran
	 */
	public OfertaPeso(String nombre, double precio, double pesoKg) {
		super(nombre, precio);
		this.pesoKg = pesoKg;
	}
	
	/** Crea una nueva oferta (medida en peso), con 0 kg de compra
	 * @param nombre	Nombre de producto ofertado
	 * @param precio	Precio de cada kilogramo
	 */
	public OfertaPeso(String nombre, double precio) {
		this(nombre, precio, 0);  // Reutilizamos un constructor desde el otro
	}

	// Método de copia (nos interesa poder duplicar ofertas de este tipo) - por eso implementa Cloneable
	@Override
	public Object clone() {
		return new OfertaPeso(nombre, precio, pesoKg );
	}

	public double getPesoKg() {
		return pesoKg;
	}

	public void setPesoKg(double pesoKg) {
		this.pesoKg = pesoKg;
	}

	@Override
	public double getPrecioTotal() {
		return precio * pesoKg + COSTE_PESADO;
	}
	
	public double getCosteOperacion() {
		return COSTE_PESADO;
	}

	@Override
	public boolean pedirDatos( VentanaConsolaConBotones ventana ) {
		ventana.println( "Introduce kg. a comprar de " + getNombre() );
		double numero = ventana.leeDouble();
		if (numero == Double.MAX_VALUE) {  // Error en la lectura
			return false;
		} else {
			setPesoKg( numero );  // Actualiza el atributo
			return true;
		}
	}
	
	@Override
	public String toString() {
		String ret = super.toString() + String.format( "(%4.2f€ por kg.)", precio );
		if (pesoKg == 0.0) {
			return ret;
		} else {
			return ret + String.format( " - compra de %4.2f kg.", pesoKg );
		}
	}
	
}
