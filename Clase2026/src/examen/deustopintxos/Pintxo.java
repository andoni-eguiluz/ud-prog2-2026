package examen.deustopintxos;

public class Pintxo extends Producto {
	protected boolean caliente;

	public Pintxo(String nombre, double precio, boolean caliente) {
		super(nombre, precio);
		this.caliente = caliente;
	}

	public boolean isCaliente() {
		return caliente;
	}

	public void setCaliente(boolean caliente) {
		this.caliente = caliente;
	}
	
}
