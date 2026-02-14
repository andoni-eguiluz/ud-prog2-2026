package tema1.resueltos;

public class Ejercicio1_0b {
	
	static int max = 0;  // Podemos tener el mismo nombre de variable en dist. ámbitos
	static final int MINIMO_MCD = 1;  // Ejemplo de constante (en lugar de magic number)
	
	public static void main(String[] args) {
		System.out.println( mcd( 15, 25 ) );
		System.out.println( mcd( 15, 25, 50 ) );
		System.out.println( mcd( 100, 50, 25 ) );
		System.out.println( mcd( 25, 100, 50 ) );
		System.out.println( mcd( 17, 19, 21 ) );
		System.out.println( mcd(10,10,10) );
		System.out.println( mcd( 15, 125, 30, 40, 50 ));
	}
	
	static int mcd( int ent1, int ent2 ) {
		int max = MINIMO_MCD;
		for (int i=2; i<=Math.min(ent1, ent2); i++) {
			if ( (ent1%i == 0) && (ent2 % i == 0)) {
				max = i;
			}
		}
		return max;
	}
	
	// Y de tres parámetros?
	static int mcd( int ent1, int ent2, int ent3 ) {
		int max = mcd(ent1,ent2);
		return mcd(max,ent3);
	}
	
	/** Calcula el máximo común divisor de 2 o más números enteros
	 * @param ent1	Primer número
	 * @param ent2	Segundo número
	 * @param resto	Lista variable de otros números (puede ser ninguno o N)
	 * @return	Resultado del cálculo
	 */
	public static int mcd( int ent1, int ent2, int... resto ) {
		/* Comentario de bloque
		 */
		int max = mcd(ent1,ent2);
		for (int dato : resto) {  // Recorro el resto de datos después del segundo número
			max = mcd(max,dato); 
			// System.out.println( " * " + max );
		}
		return max;
	}
	

}
