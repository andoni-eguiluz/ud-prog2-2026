package tema4.ejemplos;

import java.io.Serializable;

/** Clase para objetos de película, ejemplo para practicar gestión de ficheros con ella
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Peli implements Comparable<Peli> {
	private String nombre;
	private int anyo;

	public Peli(String nombre) {
		super();
		this.nombre = nombre;
		this.anyo = 0;
	}
	
	public Peli(String nombre, int anyo) {
		this.nombre = nombre;
		this.anyo = anyo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Peli)) return false;
		return nombre.equals( ((Peli)obj).nombre ); 
	}

	@Override
	public String toString() {
		return nombre + " (" + anyo + ")";
	}

	@Override
	public int compareTo(Peli o) {
		return nombre.compareTo( o.nombre );
	}
	
}
