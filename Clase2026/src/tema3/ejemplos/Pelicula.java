package tema3.ejemplos;

public class Pelicula implements Comparable<Pelicula> {
	private String nombre;
	private int anyo;
	public Pelicula(String nombre, int anyo) {
		super();
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
	public String toString() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pelicula) {
			Pelicula p2 = (Pelicula) obj;
			return nombre.equals( p2.nombre ) && anyo==p2.anyo;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return nombre.hashCode() + anyo;
	}
	
	@Override
	public int compareTo(Pelicula o) {
		return - nombre.compareTo(o.nombre);
	}
}
