package tema4.ejemplos.objetosATexto.solucion;

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
	public String aLinea() {
		// Por qué tab en lugar de espacios??? Para poder discriminar al leer
		// ¿Cuáles serían buenos separadores?
		return dni + "\t" + letraDni + "\t" + nombre + "\t" + apellidos + "\t" + anyoNacimiento;
	}

	// TODO Tarea 7
	/**
	 * @param linea	Línea de texto desde la que crear la persona (datos separados por tabuladores)
	 * @return	null si no se ha podido crear correctamente la persona
	 */
	public static Persona crearDesdeLinea(String linea) {
		try {
		    String[] partes = linea.split("\t");
		    switch (partes[0]) {
		        case "EMPLEADO":
		            return Empleado.crearDesde(partes);
		        case "CLIENTE":
		            return Cliente.crearDesde(partes);
		        default:
					System.err.println( "Error en tipo de persona: ni EMPLEADO ni CLIENTE" );
		    }
		} catch (NumberFormatException e) {
			System.err.println( "Número incorrecto en proceso de persona" );
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println( "Datos incompletos en proceso de persona" );
		} catch (PersonaException e) {
			System.err.println( "Letra de DNI incorrecta" );
		}
		return null;
	}
	
}
