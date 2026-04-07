package tema2.ejemplos;

public class Persona implements Comparable<Persona> {
	private String nombre;
	private String apellidos;
	private String dni;
	private String email;
	public Persona(String nombre, String apellidos, String dni, String email) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return nombre + " " + apellidos + "/ dni=" + dni + "/ email=" + email;
	}

	@Override
	public int compareTo(Persona o) {
		// Por apellidos
		int compAps = this.apellidos.compareTo( o.apellidos );
		System.out.println( "Comparando " + this + " con " + o );
		if (compAps == 0) {
			return this.nombre.compareTo( o.nombre );
		}
		return compAps;
	}
	
}
