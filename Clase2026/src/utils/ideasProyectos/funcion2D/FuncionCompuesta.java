package utils.ideasProyectos.funcion2D;

public class FuncionCompuesta implements Funcion2D {
	public enum TipoComposicion { SUMA, PROD, DIV, COMP }
	private double coef1, coef2;
	private Funcion2D func, func2;
	private TipoComposicion tipo = null;
	
	/** Crea la funci�n coef1 * f(x) + coef2
	 * @param f
	 * @param coef1
	 * @param coef2
	 */
	public FuncionCompuesta( Funcion2D f, double coef1, double coef2 ) {
		this.coef1 = coef1;
		this.coef2 = coef2;
		this.func = f;
	}
	
	/** Crea la funci�n compuesta entre f(x) y g(x), de acuerdo al tipo:<p>
	 * SUMA: coef1*f(x) + coef2*g(x) <p>
	 * PROD: coef1*f(x) * coef2*g(x) <p> 
	 * DIV: coef1*f(x) / coef2*g(x) <p>
	 * COMP: coef1*f(coef2*g(x)) <p>
	 * @param f
	 * @param g
	 * @param tipo
	 * @param coef1
	 * @param coef2
	 */
	public FuncionCompuesta( Funcion2D f, Funcion2D g, TipoComposicion tipo, double coef1, double coef2 ) {
		this.coef1 = coef1;
		this.coef2 = coef2;
		this.func = f;
		this.func2 = g;
		this.tipo = tipo;
	}
	
	@Override
	public double f(double x) {
		if (tipo==null)
			return coef1 * func.f(x) + coef2;
		else
			switch (tipo) {
				case SUMA: return coef1 * func.f(x) + coef2 * func2.f(x);
				case PROD: return coef1 * func.f(x) * coef2 * func2.f(x);
				case DIV: return coef1 * func.f(x) / coef2 / func2.f(x);
				case COMP: return coef1 * func.f( coef2 * func2.f(x) );
				default: return Double.MAX_VALUE;
			}
	}
}

