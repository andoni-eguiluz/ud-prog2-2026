package utils.ideasProyectos.funcion2D;

public class Logaritmica implements Funcion2D {
	private double coef1, coef2, coef3, base;
	/** Crea la funci�n coef1 * log-base(coef2*x) + coef3
	 * @param base
	 * @param coef1
	 * @param coef2
	 * @param coef3
	 */
	public Logaritmica( double base, double coef1, double coef2, double coef3 ) {
		this.coef1 = coef1;
		this.coef2 = coef2;
		this.coef3 = coef3;
		this.base = base;
	}
	/** Crea la funci�n coef1 * ln(coef2*x) + coef3
	 * @param coef1
	 * @param coef2
	 * @param coef3
	 */
	public Logaritmica( double coef1, double coef2, double coef3 ) {
		this.coef1 = coef1;
		this.coef2 = coef2;
		this.coef3 = coef3;
		this.base = 0;
	}
	@Override
	public double f(double x) {
		if (base==0)
			return coef1 * Math.log( x*coef2 ) + coef3;
		else
			return coef1 * Math.log( x*coef2 ) / Math.log( base ) + coef3;
	}
}

