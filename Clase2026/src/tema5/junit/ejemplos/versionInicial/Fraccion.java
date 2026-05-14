package tema5.junit.ejemplos.versionInicial;


// TODO Hacer pruebas con JUnit para mejorar esta clase.
// Añadir lanzamiento de excepciones en los métodos en los que proceda por imposibles matemáticos y probarlos también con JUnit
// Añadir también excepciones si hay null donde no debería

/** Clase fracción
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Fraccion implements Comparable<Fraccion> {
	protected int num;
	protected int den;
	
	/** Crea una nueva fracción
	 * @param num	Numerador
	 * @param den	Denominador
	 */
	public Fraccion(int num, int den) {
		super();
		this.num = num;
		this.den = den;
	}

	/** Calcula el número double que corresponde a la fracción
	 * @return
	 */
	public double calcular() {
		return 1.0*num/den;
	}

	/** Simplifica la fracción si procede (si no, la deja igual)
	 * dividiendo numerador y denominador por su MCD, y dejando el signo (si lo hubiera) en el numerador
	 * @return	Modifica la fracción simplificándola y la devuelve
	 */
	public Fraccion simplifica() {
		int mcd = mcd( num, den );
		if (mcd > 1) {
			num /= mcd;
			den /= mcd;
		}
		if (den<0) {
			den = -den;
			num = -num;
		}
		return this;
	}
		// Devuelve el Máximo Común Divisor de dos números
		private static int mcd( int a, int b ) {
			a = Math.abs(a); b = Math.abs(b);
			int mcd = Math.min(a, b);
			while ((a%mcd!=0 || b%mcd!=0) && mcd>1)
				mcd--;
			return mcd;
		}
	
	/** Suma dos fracciones y devuelve nueva fracción resultado
	 * @param f1	Operando 1
	 * @param f2	Operando 2
	 * @return	Fracción resultado f1+f2
	 */
	public static Fraccion suma( Fraccion f1, Fraccion f2 ) {
		return new Fraccion( f1.num*f2.den + f2.num*f1.den, f1.den*f2.den );
	}
	
	/** Resta dos fracciones y devuelve nueva fracción resultado
	 * @param f1	Operando 1
	 * @param f2	Operando 2
	 * @return	Fracción resultado f1-f2
	 */
	public static Fraccion resta( Fraccion f1, Fraccion f2 ) {
		return new Fraccion( f1.num*f2.den - f2.num*f1.den, f1.den*f2.den );
	}
	
	/** Multiplica dos fracciones y devuelve nueva fracción resultado
	 * @param f1	Operando 1
	 * @param f2	Operando 2
	 * @return	Fracción resultado f1*f2
	 */
	public static Fraccion multiplica( Fraccion f1, Fraccion f2 ) {
		return new Fraccion( f1.num*f2.num, f1.den*f2.den );
	}
	
	/** Divide dos fracciones y devuelve nueva fracción resultado
	 * @param f1	Operando 1
	 * @param f2	Operando 2
	 * @return	Fracción resultado f1/f2
	 */
	public static Fraccion divide( Fraccion f1, Fraccion f2 ) {
		return new Fraccion( f1.num*f2.den, f1.den*f2.num );
	}
	
	@Override
	public String toString() {
		return num + "/" + den;
	}
	
	public String toStringConValor() {
		return num + "/" + den + " = " + calcular();
	}

	@Override
	public int hashCode() {
		return den+num;
	}

	/** Compara dos fracciones
	 * Devuelve true si son iguales o equivalentes
	 * (las simplificadas son iguales a las no simplificadas)
s	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fraccion) {
			Fraccion f2 = (Fraccion) obj;
			Fraccion f1s = this.simplifica();
			Fraccion f2s = f2.simplifica();
			return f1s.num==f2s.num && f1s.den==f2s.den;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Fraccion o) {
		double yo = this.calcular();
		double el = o.calcular();
		if (yo>el) { 
			return +1; 
		} else if (yo==el) { 
			return 0; 
		} else { 
			return -1; 
		}
	}

}
