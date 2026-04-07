package tema3.ejemplos;

public class Jugador implements Comparable<Jugador> {
	private String nombre;

	public Jugador(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	@Override
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Jugador) {
			Jugador j2 = (Jugador) obj;
			return nombre.equals(j2.nombre);
		}
		return false;
	}

	@Override
	public int compareTo(Jugador o) {
		return nombre.compareTo( o.nombre );
	}
	
}
