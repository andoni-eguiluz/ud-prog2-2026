package tema1b.resueltos.ventas;

public class Movil extends Producto {
	private static final int TIEMPO_VENTA = 60; // Suponemos tiempo de venta de 60 segundos de un móvil
	
	protected String imagen;
	private String marca;
	private String modelo;
	
	public Movil(String nombre, double precioEuros, String imagen, String marca, String modelo) {
		super(nombre, precioEuros);
		this.marca = marca;
		this.modelo = modelo;
		this.imagen = imagen;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public int getTiempoTrabajo() {
		return TIEMPO_VENTA;
	}
	
}
