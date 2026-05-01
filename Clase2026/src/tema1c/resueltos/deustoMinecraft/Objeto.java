package tema1c.resueltos.deustoMinecraft;

import java.io.Serializable;

public class Objeto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private Rareza rareza;

	public Objeto() {
		this.nombre = "";
		this.rareza = Rareza.COMUN;
	}

	public Objeto(String nombre, Rareza rareza) {
		this.nombre = nombre;
		this.rareza = rareza;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Rareza getRareza() {
		return rareza;
	}

	public void setRareza(Rareza rareza) {
		this.rareza = rareza;
	}

	@Override
	public String toString() {
		return nombre + " (" + rareza + ")";
	}
}
