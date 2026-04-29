package tema4.basicos.conceptoFicheros;

import java.io.Serializable;

public class Venta implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idCliente;
	private double importe;
	public Venta(String idCliente, double importe) {
		super();
		this.idCliente = idCliente;
		this.importe = importe;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	@Override
	public String toString() {
		return "Venta a " + idCliente + " de importe " + importe + " euros ";
	}
	/** Método que convierte una venta a texto en una línea separada por tabuladores
	 * @return
	 */
	public String toLinea() {
		return idCliente + "\t" + importe;
	}
	/** Construye una nueva venta desde una línea de texto separada por tabuladores
	 * @param linea	Línea de texto
	 * @throws IndexOutOfBoundsException	Error generado si la línea de texto no corresponde a una venta correcta
	 * @throws NumberFormatException	Error generado si el importe de la venta no es un número correcto
	 */
	public Venta( String linea ) throws IndexOutOfBoundsException, NumberFormatException {
		String[] partes = linea.split("\t");  // Divide la línea en partes separadas por tabuladores
		String val1 = partes[0];  // Puede generar IndexOutOfBounds
		double val2 = Double.parseDouble( partes[1] );  // Puede generar IndexOutOfBounds o NumberFormatException
		idCliente = val1;
		importe = val2;
	}
}
