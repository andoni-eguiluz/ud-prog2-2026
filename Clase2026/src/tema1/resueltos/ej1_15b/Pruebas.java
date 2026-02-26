package tema1.resueltos.ej1_15b;

/** Pruebas de Cancion y ListaReproduccion
 */
public class Pruebas {
	public static void main(String[] args) 
	{
		Cancion c1 = new Cancion( "Edge of Glory", "Lady Gaga", 4.3, false, 0.0 );  // Correcta
		Cancion c2 = new Cancion( null, null, -1.5, false, -10 );  // Errónea
		System.out.println( "Canción c1 = " + c1 );
		System.out.println( "Canción c2 = " + c2 );
		c1.reproducir();
		c1.avanzar( 5 );
		c1.parar();
		System.out.println( "Canción c1 tras 5 segundos = " + c1 );
		
		ListaReproduccion lista = new ListaReproduccion( "Mi lista" );
		lista.anyadirCancion( c1 );
		lista.anyadirCancion(new Cancion("Espresso", "Sabrina Carpenter", 2.9, false, 0.0));
		lista.anyadirCancion(new Cancion("Good Luck, Babe!", "Chappell Roan", 3.4, false, 0.0));
		lista.anyadirCancion(new Cancion("Greedy", "Tate McRae", 2.7, false, 0.0));

		System.out.println( lista );
		
		lista.modificarPosicion( c1, 17 );
		lista.modificarPosicion( c1, 1 );
		
		lista.reproducir();
		System.out.println( lista );
		lista.avanzar( 3 );
		System.out.println( lista );
		lista.avanzar( 5*60 );
		System.out.println( lista );
		
	}
	
}