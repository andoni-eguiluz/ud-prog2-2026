package tema4.ejemplos.objetosATexto;

public class Empleado extends Persona {
	private String cargo;

	public Empleado(int dni, char letraDNI, String nombre, String apellidos, int anyoNacimiento, String cargo) throws PersonaException {
		super(dni, letraDNI, nombre, apellidos, anyoNacimiento);
		this.cargo = cargo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	@Override
	public String toString() {
		return super.toString() + " [" + cargo + "]";
	}

	// TODO Tarea 4
	// public String aLinea() {
	
	// TODO Tarea 7
	// public static Empleado crearDesde(String[] partes) throws PersonaException
}
