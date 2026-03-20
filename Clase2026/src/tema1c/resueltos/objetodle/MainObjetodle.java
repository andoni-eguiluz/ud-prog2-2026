package tema1c.resueltos.objetodle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MainObjetodle {
	public static void main(String[] args) {
		String[] opciones = new String[] { "Letras", "Dígitos", "Colores", "Palabras correctas", "Números impares" };
		Object elige = JOptionPane.showInputDialog( null, "Elige juego", "Selección", JOptionPane.QUESTION_MESSAGE, null, opciones, "Letras" );
		if ("Letras".equals(elige)) {
			wordleNormalAleatorio();
		} else if ("Colores".equals(elige)) {
			wordleColor();
		} else if ("Palabras correctas".equals(elige)) {
			wordleNormal();
		} else if ("Dígitos".equals(elige)) {
			wordleNumeros();
		} else if ("Números impares".equals(elige)) {
			wordleImpares();
		}
	}
	
	private static void wordleNormalAleatorio() {
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
	}
	
	private static void wordleNormal() {
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
		// Palabras correctas extraidas de https://www.listasdepalabras.es/palabras5letras.htm - leídas de un fichero
		ArrayList<String> listaPalabras = new ArrayList<>();
		Scanner lector = new Scanner( MainObjetodle.class.getResourceAsStream("palabras5.txt") );
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] palabras = linea.split(" ");
			for (String palabra : palabras) {
				listaPalabras.add( palabra );
			}
		}
		lector.close();
		o.setModo( Objetodle.Modo.PALABRAS_CORRECTAS );
		o.setCombinacionesCorrectas( listaPalabras );
		o.run();
	}
	
	private static void wordleColor() {
		Elemento[] elementos = {
			new Color( EstadoElemento.TECLA, java.awt.Color.BLUE, '1' ),
			new Color( EstadoElemento.TECLA, new java.awt.Color( 0, 150, 0 ), '2' ),  // Verde un poco diferente para no confundirlo con el verde de wordle
			new Color( EstadoElemento.TECLA, java.awt.Color.RED, '3' ),
			new Color( EstadoElemento.TECLA, java.awt.Color.CYAN, '4' ),
			new Color( EstadoElemento.TECLA, java.awt.Color.YELLOW, '5' ),
			new Color( EstadoElemento.TECLA, java.awt.Color.MAGENTA, '6' ),
			new Color( EstadoElemento.TECLA, java.awt.Color.WHITE, '7' ),
			new Color( EstadoElemento.TECLA, java.awt.Color.PINK, '8' ),
			new Color( EstadoElemento.TECLA, java.awt.Color.LIGHT_GRAY, '9' ),
		};
		Opciones opciones = new Opciones( elementos );
		Objetodle o = new Objetodle( opciones );
		o.run();
	}
	
	private static void wordleNumeros() {
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
	}
	
	private static void wordleImpares() {
		Elemento[] elementos = new Elemento[10];
		int indice = 0;
		while (indice < 10) {
			Numero n = new Numero( EstadoElemento.TECLA, indice );
			elementos[indice] = n;
			indice++;
		};
		Opciones opciones = new Opciones( elementos );
		Objetodle o = new Objetodle( opciones );
		// Números impares
		o.setModo( Objetodle.Modo.NUMEROS_IMPARES );
		o.run();
	}
	
}
