package tema4.resueltos.deustoikea;

import java.io.Serializable;

public class Mueble implements Serializable {
	protected int codigo;
	protected String nombre;
	protected String categoria;
	protected double precio;
	
	public Mueble(int codigo, String nombre, String categoria, double precio) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return nombre + " (" + codigo + ") cat=" + categoria + ", " + precio;
	}

	@Override
	public int hashCode() {
		return codigo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Mueble) {
			Mueble m = (Mueble) obj;
			return m.codigo == codigo;
		} else {
			return false;
			
		}
	}
	
}
