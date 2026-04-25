package tema4.ejemplos.objetosATexto;

public abstract class Persona {
	private int dni;
	private char letraDni;
	private String nombre;
	private String apellidos;
	private int anyoNacimiento;
	
	public Persona(int dni, char letraDNI, String nombre, String apellidos, int anyoNacimiento) throws PersonaException {
		setDNI( dni );
		setLetraDNI( letraDNI );
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.anyoNacimiento = anyoNacimiento;
	}

	public int getDNI() {
		return dni;
	}

	public void setDNI(int dni) {
		this.dni = dni;
	}
	
	public char getLetraDNI() {
		return letraDni;
	}

	public String getDNICompleto() {
		return "" + dni + letraDni;
	}

	public void setLetraDNI(char letraDNI) throws PersonaException {
		// Cálculo de letra según definición oficial
	    String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
	    int indice = dni % 23;
	    char letraOficial = letras.charAt(indice);
	    if (letraOficial != letraDNI) {
	    	throw new PersonaException( "DNI Incorrecto: " + dni + letraDNI + " (debería ser " + letraOficial + ")" );  
	    	// Rompe el flujo de ejecución
	    }
		this.letraDni = letraDNI;
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
	
	public int getAnyoNacimiento() {
		return anyoNacimiento;
	}

	public void setAnyoNacimiento(int anyoNacimiento) {
		this.anyoNacimiento = anyoNacimiento;
	}

	@Override
	public String toString() {
		return "" + dni + letraDni + " - " + nombre + " " + apellidos;
	}

	// TODO Tarea 4
	// public String aLinea()

	// TODO Tarea 7
	
}
