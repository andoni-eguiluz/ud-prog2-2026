package tema3.ejemplos;

public class EnteroMutable {
	private int valor;
	public EnteroMutable( int valor ) {
		this.valor = valor;
	}
	public int getValor() {
		return valor;
	}
	public void setValor( int valorNuevo ) {
		this.valor = valorNuevo;
	}
	public void inc1() {
		valor++;
	}
	@Override
	public String toString() {
		return valor + "";
	}
}
