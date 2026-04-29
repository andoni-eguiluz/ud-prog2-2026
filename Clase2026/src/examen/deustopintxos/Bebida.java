package examen.deustopintxos;

public class Bebida extends Producto {
	protected double alcohol;

	public Bebida(String nombre, double precio, double alcohol) {
		super(nombre, precio);
		this.alcohol = alcohol;
	}

	public double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(double alcohol) {
		this.alcohol = alcohol;
	}
	
}
