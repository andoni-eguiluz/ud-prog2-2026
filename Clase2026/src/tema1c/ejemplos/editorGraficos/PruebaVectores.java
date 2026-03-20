package tema1c.ejemplos.editorGraficos;

/** Clase de prueba de la herencia de vectores
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class PruebaVectores {
	public static void main(String[] args) {
		// Crear dos vectores iguales pero con distinto origen
		Vector2D v1 = new Vector2D( 10, 6 );
		VectorConOrigen2D v2 = new VectorConOrigen2D( 100, 50, 110, 56 );
		// Comprobación de la distancia en ambos casos
		System.out.println( "Distancia a inicio: " + v1.distancia(new Vector2D(0, 0)) + " / " + v2.distancia(new Vector2D(100, 50)) );
		System.out.println( "Distancia a centro: " + v1.distancia(new Vector2D(5, 3)) + " / " + v2.distancia(new Vector2D(105, 53)) );
		System.out.println( "Distancia a externo: " + v1.distancia(new Vector2D(7, 5)) + " / " + v2.distancia(new Vector2D(107, 55)) );
		// Comprobación de toque
		System.out.println( "Toque con inicio: " + v1.toca(new Vector2D(0, 0), 0.1) + " / " + v2.toca(new Vector2D(100, 50), 0.1) );
		System.out.println( "Toque con centro: " + v1.toca(new Vector2D(5, 3), 0.1) + " / " + v2.toca(new Vector2D(105, 53), 0.1) );
		System.out.println( "Toque con externo: " + v1.toca(new Vector2D(7, 5), 0.1) + " / " + v2.toca(new Vector2D(107, 55), 0.1) );
		// Escala
		Vector2D v1bis = new Vector2D( v1.getX(), v1.getY() );
		VectorConOrigen2D v2bis = new VectorConOrigen2D( v2.getxOrigen(), v2.getyOrigen(), v2.getX(), v2.getY() );
		v1bis.escala( 1.5 );
		v2bis.escala( 1.5 );
		System.out.println( "Escala 1.5: " + v1bis + " / " + v2bis );
		// Módulo y argumento
		System.out.println( "Módulo: " + v1.getModulo() + " / " + v2.getModulo() );
		System.out.println( "Argumento: " + v1.getArgumento() + " / " + v2.getArgumento() );
		// Suma
		// TODO - Hay que corregir la suma
		Vector2D v3 = new Vector2D( 2, 2 );
		System.out.println( "Suma de vector2d " + v1 + " con " + v3 + " = " + v1.sumar( v3 ) );
		System.out.println( "Suma de vector con origen " + v2 + " con " + v3 + " = " + v2.sumar( v3 ) );
		System.out.println( "Esto hay que arreglarlo!");
		// Prueba de equals (observar la codificación de equals de la clase VectorConOrigen)
		VectorConOrigen2D v4 = new VectorConOrigen2D( 100, 50, 110, 56 );
		VectorConOrigen2D v5 = new VectorConOrigen2D( 100, 50, 110, 56 );
		System.out.println( "V4 == V5? " + (v4==v5) );
		System.out.println( "V4 equals V5? " + v4.equals(v5) );
	}
}
