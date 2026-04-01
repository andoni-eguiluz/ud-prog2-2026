package utils.ideasProyectos.funcion2D;

public class Exponencial implements Funcion2D {
	private double coef1, coef2, coef3, exp;
	/** Crea la funci�n coef1 * ((x + coef2) ^ exp) + coef3
	 * @param coef1
	 * @param coef2
	 * @param coef3
	 * @param exp
	 */
	public Exponencial( double coef1, double coef2, double coef3, double exp ) {
		this.coef1 = coef1;
		this.coef2 = coef2;
		this.coef3 = coef3;
		this.exp = exp;
	}
	@Override
	public double f(double x) {
		return coef1 * Math.pow( (x + coef2), exp ) + coef3;
	}
}

