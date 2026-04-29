package tema4.resueltos.deustoikea;

public class MuebleOnline extends Mueble {
	protected String url;

	public MuebleOnline(int codigo, String nombre, String categoria, double precio, String url) {
		super(codigo, nombre, categoria, precio);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "(online) " + super.toString() + " / url=" + url;
	}
	
	
}
