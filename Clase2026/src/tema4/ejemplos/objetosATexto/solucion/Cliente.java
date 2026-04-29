package tema4.ejemplos.objetosATexto.solucion;

public class Cliente extends Persona {
	private int codigo;
	private double saldo;

	public Cliente(int dni, char letraDNI, String nombre, String apellidos, int anyoNacimiento, int codigo, double saldo) throws PersonaException {
		super(dni, letraDNI, nombre, apellidos, anyoNacimiento);
		this.codigo = codigo;
		this.saldo = saldo;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + codigo + ") - saldo " + saldo;
	}

	// TODO Tarea 4
	public String aLinea() {
		// Versión 1 - return super.aLinea() + "\t" + codigo + "\t" + saldo;
		return "CLIENTE\t" + super.aLinea() + "\t" + codigo + "\t" + saldo;
	}

	// TODO Tarea 7
	public static Cliente crearDesde(String[] partes) throws PersonaException {
	    int dni = Integer.parseInt(partes[1]);
	    char letraDNI = partes[2].charAt(0);
	    String nombre = partes[3];
	    String apellidos = partes[4];
	    int anyoNacimiento = Integer.parseInt(partes[5]);
	    int codigo = Integer.parseInt(partes[6]);
	    double saldo = Double.parseDouble(partes[7]);
	    return new Cliente(dni, letraDNI, nombre, apellidos, anyoNacimiento, codigo, saldo);
	}
}
