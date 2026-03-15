package tema1c.ejercicios.registros;

import java.util.Date;

public class PrecioCarburante extends Registro {
	
	/** Crea un nuevo precio de carburante partiendo de una línea de texto con todos los datos en orden, separados por tabulador
	 * Ejemplo de línea: "PrecioCarburante	18/03/2022 12:00	Repsol	GASOLEO_A	1.733"
	 * @param linea	Línea de texto de la que extraer los datos
	 * @return	nuevo objeto con esos datos, null si hay algún error
	 */
	public static PrecioCarburante crearDeLinea( String linea ) {
		if (!linea.startsWith("PrecioCarburante\t")) {
			return null;
		}
		String[] partes = linea.split( "\t" );
		Date fecha =  Registro.getFechaFromString( partes[1] );
		TipoCarburante tipo = TipoCarburante.valueOf( partes[3] );
		double precio = Double.parseDouble( partes[4] );
		PrecioCarburante nueva = new PrecioCarburante( fecha, partes[2], tipo, precio );
		return nueva;
	}
	
	protected String companyia;
	protected TipoCarburante tipo; 
	protected double precioPorLitro;
	public PrecioCarburante(Date fecha, String companyia, TipoCarburante tipo, double precioPorLitro) {
		super(fecha);
		this.companyia = companyia;
		this.tipo = tipo;
		this.precioPorLitro = precioPorLitro;
	}
	public String getCompanyia() {
		return companyia;
	}
	public void setCompanyia(String companyia) {
		this.companyia = companyia;
	}
	public TipoCarburante getTipo() {
		return tipo;
	}
	public void setTipo(TipoCarburante tipo) {
		this.tipo = tipo;
	}
	public double getPrecioPorLitro() {
		return precioPorLitro;
	}
	public void setPrecioPorLitro(double precioPorLitro) {
		this.precioPorLitro = precioPorLitro;
	}

	@Override
	public String toString() {
		return "{" + super.toString() + " " + tipo + " en " + companyia + " - " + precioPorLitro + "/litro}";
	}
	
}
