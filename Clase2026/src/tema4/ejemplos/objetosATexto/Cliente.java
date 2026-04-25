package tema4.ejemplos.objetosATexto;

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
	// public String aLinea()
	
	// TODO Tarea 7
	// public static Cliente crearDesde(String[] partes) throws PersonaException
	
}
