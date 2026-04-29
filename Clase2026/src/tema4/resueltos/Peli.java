package tema4.resueltos;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/** Clase para objetos de película, ejemplo para practicar gestión de ficheros con ella
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Peli implements Comparable<Peli>, Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int anyo;

	public Peli(String nombre) {
		super();
		this.nombre = nombre;
		this.anyo = 0;
	}
	
	public Peli(String nombre, int anyo) {
		this.nombre = nombre;
		this.anyo = anyo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Peli)) return false;
		return nombre.equals( ((Peli)obj).nombre ); 
	}

	@Override
	public String toString() {
		return nombre + " (" + anyo + ")";
	}

	@Override
	public int compareTo(Peli o) {
		return nombre.compareTo( o.nombre );
	}

	// Métodos de ficheros de texto
	/** Convierte la peli en una línea de texto en formato csv: nombre,año
	 * @return	Devuelve línea csv de esta peli
	 */
	public String aLineaCSV() {
		return nombre + "," + anyo;
	}
	
	// Por las limitaciones de constructores no se suele hacer esto
	// public Peli( String lineaCSV, boolean esCSV ) throws NoSeQueException {
		
	// sino esto (constructor indirecto)
	
	/** Crea una nueva peli partiendo de una línea codificada csv
	 * @param lineaCSV	Formato nombre,año
	 * @return	Nueva peli creada
	 * @throws NullPointerException	Error si linea de texto null
	 * @throws NoSuchElementException	Error si hay menos de 2 datos en la línea
	 * @throws NumberFormatException	Error si año no es numérico correcto
	 * @throws ArithmeticException	Error si año es negativo
	 */
	public static Peli creaPeliDesdeLineaCSV( String lineaCSV ) throws NullPointerException, 
	NoSuchElementException, NumberFormatException, ArithmeticException {
		StringTokenizer st = new StringTokenizer( lineaCSV, "," );
		// String[] = lineaCSV.split( "," );
		String nombre = st.nextToken();
		int anyo = Integer.parseInt( st.nextToken() );
		if (anyo<0) {
			throw new ArithmeticException( "Año negativo" );
		}
		Peli nuevaPeli = new Peli( nombre, anyo );
		return nuevaPeli;
	}
	
	
}
