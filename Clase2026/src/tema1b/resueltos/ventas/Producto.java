package tema1b.resueltos.ventas;

public abstract class Producto {
	protected String nombre;
	protected double precioEuros;
	
	public Producto(String nombre, double precioEuros) {
		super();
		this.nombre = nombre;
		this.precioEuros = precioEuros;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioEuros() {
		return precioEuros;
	}

	public void setPrecioEuros(double precioEuros) {
		this.precioEuros = precioEuros;
	}
	
	/** Devuelve el tiempo de trabajo que lleva asociado este producto
	 * @return	tiempo en segundos
	 */
	public abstract int getTiempoTrabajo();

	
	@Override
	public String toString() {
		return nombre + " - " + precioEuros + " €";
	}
	
}
