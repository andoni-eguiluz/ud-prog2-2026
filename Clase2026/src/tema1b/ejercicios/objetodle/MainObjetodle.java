package tema1b.ejercicios.objetodle;

public class MainObjetodle {
	public static void main(String[] args) {
		// wordleNormal();  // Descomenta esta llamada para probar el wordle normal de letras
		// wordleColor();  // Descomenta esta llamada para probar el wordle de colores
		// wordleNumeros();  // Descomenta esta llamada para probar el wordle de números
		wordleVacio();
	}

	// Ejemplo de funcionamiento de objetodle sin contenido en los elementos
	private static void wordleVacio() {
		Elemento[] elementos = new Elemento[15];  // 15 elementos por ejemplo
		for (int i=0; i<15; i++) {
			Elemento e = new Elemento( EstadoElemento.TECLA );
			elementos[i] = e;
		};
		Opciones opciones = new Opciones( elementos );
		Objetodle o = new Objetodle( opciones );
		o.run();
	}
	
	private static void wordleNormal() {
		/* TODO Descomenta este código si quieres crear un wordle con la clase Letra
		Elemento[] elementos = new Elemento[26];
		char letra = 'A';
		int indice = 0;
		while (letra <= 'Z') {
			Letra l = new Letra( EstadoElemento.TECLA, letra );
			letra = (char) (letra + 1);
			elementos[indice] = l;
			indice++;
		};
		Opciones opciones = new Opciones( elementos );
		Objetodle o = new Objetodle( opciones );
		o.run();
		*/
	}
	
	private static void wordleColor() {
		/* TODO Descomenta este código si quieres crear un wordle con la clase Color  (tendrás que definirla)
		Elemento[] elementos = {
			new Color( EstadoElemento.TECLA, java.awt.Color.BLUE ),
			new Color( EstadoElemento.TECLA, new java.awt.Color( 0, 150, 0 ) ),  // Verde un poco diferente para no confundirlo con el verde de wordle
			new Color( EstadoElemento.TECLA, java.awt.Color.RED ),
			new Color( EstadoElemento.TECLA, java.awt.Color.CYAN ),
			new Color( EstadoElemento.TECLA, java.awt.Color.YELLOW ),
			new Color( EstadoElemento.TECLA, java.awt.Color.MAGENTA ),
			new Color( EstadoElemento.TECLA, java.awt.Color.WHITE ),
			new Color( EstadoElemento.TECLA, java.awt.Color.PINK ),
			new Color( EstadoElemento.TECLA, java.awt.Color.LIGHT_GRAY ),
		};
		Opciones opciones = new Opciones( elementos );
		Objetodle o = new Objetodle( opciones );
		o.run();
		*/
	}
	
	private static void wordleNumeros() {
		/* TODO Descomenta este código si quieres crear un wordle con la clase Numero  (tendrás que definirla)
		Elemento[] elementos = new Elemento[10];
		int indice = 0;
		while (indice < 10) {
			Numero n = new Numero( EstadoElemento.TECLA, indice );
			elementos[indice] = n;
			indice++;
		};
		Opciones opciones = new Opciones( elementos );
		Objetodle o = new Objetodle( opciones );
		o.run();
		*/
	}
	
}
