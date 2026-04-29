package tema4.ejemplos.objetosATexto.solucion;

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
	public String aLinea() {
		// Versión 1 - return super.aLinea() + "\t" + cargo;
		return "EMPLEADO\t" + super.aLinea() + "\t" + cargo;
	}
	
	// TODO Tarea 7
	public static Empleado crearDesde(String[] partes) throws PersonaException {
	    int dni = Integer.parseInt(partes[1]);
	    char letraDNI = partes[2].charAt(0);
	    String nombre = partes[3];
	    String apellidos = partes[4];
	    int anyoNacimiento = Integer.parseInt(partes[5]);
	    String cargo = partes[6];
	    return new Empleado(dni, letraDNI, nombre, apellidos, anyoNacimiento, cargo);
	}
}
