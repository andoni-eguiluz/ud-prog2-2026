package tema1c.resueltos.registros;

import java.util.Date;

public class PrecioElectricidad extends Registro implements ConPrecio {

	/** Crea una nuevo precio de electricidad partiendo de una línea de texto con todos los datos en orden, separados por tabulador
	 * Ejemplo de línea: "PrecioElectricidad	18/03/2022 16:00	0.310"
	 * @param linea	Línea de texto de la que extraer los datos
	 * @return	nuevo objeto con esos datos, null si hay algún error
	 */
	public static PrecioElectricidad crearDeLinea( String linea ) {
		if (!linea.startsWith("PrecioElectricidad\t")) {
			return null;
		}
		String[] partes = linea.split( "\t" );
		Date fecha =  Registro.getFechaFromString( partes[1] );
		double precio = Double.parseDouble( partes[2] );
		PrecioElectricidad nueva = new PrecioElectricidad( fecha, precio );
		return nueva;
	}
	
	protected double precioKwH;
	public PrecioElectricidad(Date fecha, double precioKwH) {
		super(fecha);
		this.precioKwH = precioKwH;
	}
	public double getPrecioKwH() {
		return precioKwH;
	}
	public void setPrecioKwH(double precioKwH) {
		this.precioKwH = precioKwH;
	}
	
	@Override
	public String toString() {
		return "{" + super.toString() + " " + precioKwH + "/KwH}";
	}

	@Override
	public double getPrecio() {
		return precioKwH;
	}

	@Override
	public boolean estaPrecioEnRango(double precioInferior, double precioSuperior) {
		return (precioKwH>=precioInferior && precioKwH<=precioSuperior);
//		if (precioKwH>=precioInferior && precioKwH<=precioSuperior) {
//			return true;
//		} else {
//			return false;
//		}
	}

}
