package utils.ideasProyectos.funcion2D;

public class FuncionDeDatos implements Funcion2D {
	private double valInicialX;
	private double incX;
	private double[] valY;
	private boolean continua;
	/** Crea la funci�n de datos f(x) siendo cada dato de la funci�n conocido en una serie suministrada
	 * @param datos	Array de datos (valores de y) suministrados
	 * @param valInicialX	Valor inicial de x (para el primer valor de y)
	 * @param incX	Incremento en x (para cada nuevo valor de y)
	 * @param continua	true para que sea continua (lineal) entre los valores de x, false discreta (mantiene el valor hasta el siguiente)
	 */
	public FuncionDeDatos( int[] datos, double valInicialX, double incX, boolean continua ) {
		this.valInicialX = valInicialX;
		this.incX = incX;
		this.continua =continua;
		valY = new double[ datos.length ];
		for (int i=0; i<datos.length; i++) valY[i] = datos[i];
	}
	/** Crea la funci�n de datos f(x) siendo cada dato de la funci�n conocido en una serie suministrada
	 * @param datos	Array de datos (valores de y) suministrados
	 * @param valInicialX	Valor inicial de x (para el primer valor de y)
	 * @param incX	Incremento en x (para cada nuevo valor de y)
	 */
	public FuncionDeDatos( double[] datos, double valInicialX, double incX, boolean continua ) {
		this.valInicialX = valInicialX;
		this.incX = incX;
		this.continua =continua;
		valY = datos;
	}
	@Override
	public double f(double x) {
		if (x<valInicialX) {
			return Double.NaN;
		} else {
			int posicion = (int) ((x-valInicialX)/incX);
			if (posicion >= valY.length)
				return Double.NaN;
			if (continua && posicion<valY.length-1) {
				return valY[posicion] + (valY[posicion+1]-valY[posicion])*(x-(valInicialX+posicion*incX))/incX;
			} else
				return valY[posicion];
		}
	}
}

