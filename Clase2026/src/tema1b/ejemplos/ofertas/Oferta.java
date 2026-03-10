package tema1b.ejemplos.ofertas;

import java.io.Serializable;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;

/** Clase padre de la jerarquía de ofertas
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public abstract class Oferta implements Cloneable, Serializable {  // Queremos que todas sus hijas se puedan duplicar
	private static final long serialVersionUID = 1L;
	protected String nombre;  // Nombre de la oferta
	protected double precio;  // Precio unitario
	
	/** Crea una nueva oferta
	 * @param nombre	Nombre de producto ofertado
	 * @param precio	Precio de cada unidad (en euros)
	 */
	public Oferta(String nombre, double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
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
	
	/** Devuelve el precio total (en euros) de la oferta que se desea comprar (dependiendo de la cantidad), incluyendo posibles costes de operación)
	 */
	public abstract double getPrecioTotal();
	
	/** Pide interactivamente los datos que necesita la compra de la oferta
	 * @param ventana	Ventana en la que pedir los datos
	 * @return	true si se han introducido los datos correctamente (y se dejan en los atributos correspondientes), false si no se han introducido correctamente
	 */
	public abstract boolean pedirDatos( VentanaConsolaConBotones ventana );

	@Override
	public abstract Object clone();
	
	@Override
	public String toString() {
		return nombre + " ";
	}
	
}
