package tema4.basicos.conceptoFicheros;

import java.io.Serializable;

public class Puntuacion implements Serializable {
	private static final long serialVersionUID = 1L;  // Valor por defecto para evitar el warning de Serializable
	
	private String jugador;
	private int puntos;
	
	public Puntuacion(String jugador, int puntuacion) {
		super();
		this.jugador = jugador;
		this.puntos = puntuacion;
	}
	
	public String getJugador() {
		return jugador;
	}
	public void setJugador(String jugador) {
		this.jugador = jugador;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	@Override
	public String toString() {
		return "{Puntuacion " + jugador + ": " + puntos + "}";
	}
	
	/** Método que convierte una puntuación a texto en una línea separada por tabuladores
	 * @return
	 */
	public String toLinea() {
		return jugador + "\t" + puntos;
	}

	// Salida (constructor indirecto)
	/** Construye una nueva puntuación desde una línea de texto separada por tabuladores
	 * @param linea	Línea de texto	(debe tener código de jugador - tabulador - valor de puntuación)
	 * @throws IndexOutOfBoundsException	Error generado si la línea de texto no corresponde a una puntuación correcta
	 * @throws NumberFormatException	Error generado si el valor de puntos no es un número correcto
	 */
	public static Puntuacion desdeLinea( String linea ) throws IndexOutOfBoundsException, NumberFormatException {
		String[] partes = linea.split("\t");  // Divide la línea en partes separadas por tabuladores
		String val1 = partes[0];  // Puede generar IndexOutOfBounds
		int val2 = Integer.parseInt( partes[1] );  // Puede generar IndexOutOfBounds o NumberFormatException
		Puntuacion ret = new Puntuacion( val1, val2 );
		return ret;
	}

//  Podría también tener forma de constructor directo
//	public Puntuacion( String linea ) throws IndexOutOfBoundsException, NumberFormatException {
//		String[] partes = linea.split("\t");  // Divide la línea en partes separadas por tabuladores
//		String val1 = partes[0];  // Puede generar IndexOutOfBounds
//		int val2 = Integer.parseInt( partes[1] );  // Puede generar IndexOutOfBounds o NumberFormatException
//		jugador = val1;
//		puntos = val2;
//	}
	
}
