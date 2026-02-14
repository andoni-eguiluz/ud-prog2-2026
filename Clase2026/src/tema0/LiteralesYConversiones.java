package tema0;

public class LiteralesYConversiones {

	public static void main(String[] args) {
		int i = 5;
		int j = 234_343_343;
		long l = 17; // conversión implícita 
		l = 2342423435L;
		// i = 2342423435L;
		l = i;  // int -> long - implícita
		i = (int) l;  // long -> int hay que forzarlo con conversión explícita
		
		float f = 5;
		f = 5.2f;  // literal float
		double d = 5.2; // literal double
		d = f;
		f = (float) d;
	}
}
