package utils.ideasProyectos.funcion2D;

import java.util.ArrayList;

/** Funci�n formada por otras funciones, dependiendo del rango de X.<p>
 * Por ejemplo, podr�a ser y=sin(x) para x>=0 e y=cos(x) para x<0
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class FuncionConRangos implements Funcion2D {
	private ArrayList<Double> rangosInf;  // Valores inferiores de rango (inclusive)
	private ArrayList<Double> rangosSup;  // Valores superiores de rango (exclusive)
	private ArrayList<Funcion2D> funciones;  // Valores superiores de rango (exclusive)
	
	/** Construye una funci�n compuesta vac�a. Usar #add para a�adir rangos
	 */
	public FuncionConRangos() {
		rangosInf = new ArrayList<>();
		rangosSup = new ArrayList<>();
		funciones = new ArrayList<>();
	}
	
	/** Construye una funci�n compuesta con un rango inicial dado.<p>
	 * Puede usarse #add para a�adir otros rangos
	 */
	public FuncionConRangos( Funcion2D f, double xDesde, double xHasta ) {
		this();
		add( f, xDesde, xHasta );
	}
	
	/** A�ade un rango a la funci�n compuesta. Debe ser un rango v�lido (ver #rangoValido)
	 * @param f	Funci�n a a�adir
	 * @param xDesde	Valor inicial de esa funci�n (inclusive)
	 * @param xHasta	Valor final de esa funci�n (exclusive)
	 * @return	true si se a�ade correctamente el rango, false en caso contrario.
	 */
	public boolean add( Funcion2D f, double xDesde, double xHasta ) {
		if (rangoValido(xDesde, xHasta)) {
			funciones.add( f );
			rangosInf.add( xDesde );
			rangosSup.add( xHasta );
			return true;
		} else
			return false;
	}
	
	/** Indica si el rango dado es v�lido. Para ello el l�mite inferior tiene
	 * que ser menor al superior, y no tiene que haber interferencia con ning�n otro
	 * rango ya existente en la funci�n compuesta.
	 * @param xDesde	Valor inicial del rango
	 * @param xHasta	Valor final del rango
	 * @return	true si el rango es v�lido, false en caso contrario.
	 */
	public boolean rangoValido( double xDesde, double xHasta ) {
		if (xDesde>=xHasta) return false;
		boolean esValido = true;
		for(int i=0; i<rangosInf.size(); i++) {
			if (xHasta>rangosInf.get(i) && xDesde<rangosSup.get(i)) {
				// Estos dos rangos no son compatibles
				esValido = false;
				break;
			}
		}
		return esValido;
	}
	
	/* (non-Javadoc)
	 * Devuelve el valor de la funci�n si est� en un rango definido. Si no devuelve
	 * Double.NaN
	 * @see ud.prog3.varios.funcion2D.Funcion2D#f(double)
	 */
	public double f( double x ) {
		for(int i=0; i<rangosInf.size(); i++) {
			if (x>=rangosInf.get(i) && x<rangosSup.get(i)) {
				return funciones.get(i).f( x );
			}
		}
		return Double.NaN;
	}
}
