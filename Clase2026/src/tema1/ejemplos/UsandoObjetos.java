package tema1.ejemplos;

import java.awt.Point;

// Probando qué funciona diferente con objetos que con primitivos 

public class UsandoObjetos {

	public static void main(String[] args) {
		usandoPoints();
	}
	
	static Point puntoGlobal;
	
	private static void usandoPoints() {
		// String s = new String("  ");  // El string hace trampa
		Point punto1 = new Point( 3, 4 );
		// System.out.println( puntoGlobal.x ); // NullPointer
		System.out.println( punto1.x );
		Point punto2 = punto1;   // ALIASING
		System.out.println( punto1 );
		punto2.x = 7;
		System.out.println( punto1 );
		cambiaPunto( punto1 );
		System.out.println( punto1 );
		// Quiero que no me lo cambie? Lo copio
		Point puntoCopia = new Point( punto1 );
		cambiaPunto( puntoCopia );
		System.out.println( punto1 );
	}
	
	private static void cambiaPunto( Point p ) {
		p.y = p.y*p.y;
	}
	
	
	
}
