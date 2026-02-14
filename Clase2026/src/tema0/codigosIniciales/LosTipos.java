package tema0.codigosIniciales;

public class LosTipos {

	static byte b; // Entero de 8 bits
	static short s; // Entero de 16 bits
	static int i; // Entero codificado en 32 bits
	static long l; // Entero de 64
	
	static float f;  // Reales de 32 bits
	static double d; // 64 bits
	
	static char c; // carácter en un par de bytes
	static boolean bo; // lógico en un bit
	
	public static void main(String[] args) {
		System.out.println( Integer.MAX_VALUE );
		System.out.println( Integer.MIN_VALUE );
		System.out.println( Long.MAX_VALUE );
		System.out.println( System.currentTimeMillis() );
		i = 5;
		System.out.println( Float.MAX_VALUE );
		System.out.println( Double.MAX_VALUE );
		System.out.println( Double.MIN_VALUE );
		bo = false;
		bo = true;
	}
}
