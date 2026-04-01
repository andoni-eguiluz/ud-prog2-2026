package utils.ideasProyectos.funcion2D;

public class Senoidal implements Funcion2D {
	public static enum TipoSenoidal { SENO, COSENO, TANGENTE };
	private double coef1, coef2;
	private TipoSenoidal tipo;
	/** Crea la funci�n coef * f(x) siendo f una senoidal
	 * @param coef
	 * @param tipo	Determina la funci�n f (seno, coseno o tangente)
	 */
	public Senoidal( double coef, TipoSenoidal tipo ) {
		this( coef, 0, tipo );
	}
	/** Crea la funci�n coef1 * f(x) + coef2, siendo f una senoidal
	 * @param coef
	 * @param tipo	Determina la funci�n f (seno, coseno o tangente)
	 */
	public Senoidal( double coef1, double coef2, TipoSenoidal tipo ) {
		this.coef1 = coef1;
		this.coef2 = coef2;
		this.tipo = tipo;
	}
	@Override
	public double f(double x) {
		switch (tipo) {
		case SENO:
			return coef1 * Math.sin( x ) + coef2;
		case COSENO:
			return coef1 * Math.cos( x ) + coef2;
		case TANGENTE:
			return coef1 * Math.tan( x ) + coef2;
		default:
			return 0;
		}
	}
}

