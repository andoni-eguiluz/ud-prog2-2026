package tema3.ejercicios;

// TODO Descomenta (ctrl+7) estos imports
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.PrintStream;
// import java.util.Scanner;
// import javax.swing.JOptionPane;

/** Programa de prueba de HighScores y Scores (ejercicios 3b.1 y 3b.2)
 * Utiliza E/S con JOptionPane y ficheros de texto para guardar y cargar datos básicos
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class PruebaHighScoresYScores {

	// TODO Descomenta (ctrl+7) todo el siguiente código para que el programa funcione.
	// Debes crear antes las clases Scores y HighScores propuestas en los ejercicios 3b.1 y 3b.2

//	private static Scores misScores;
//	private static HighScores misHighScores;
//	
//	public static void main(String[] args) throws FileNotFoundException {
//		pruebaHighScoresYScores();
//	}
//	
//	private static void pruebaHighScoresYScores() throws FileNotFoundException {
//		misHighScores = new HighScores();
//		misScores = new Scores(); 
//		String[] opciones = { "Carga fichero", "Guarda fichero", "Añade score" };
//		String input;
//		do {
//			input = (String) JOptionPane.showInputDialog( null, "Elige opción (Escape para acabar)", "Menú", JOptionPane.QUESTION_MESSAGE, null, opciones, "Añade score" );
//			if ("Carga fichero".equals(input)) {
//				cargaFichero();
//			} else if ("Guarda fichero".equals(input)) { 
//				guardaFichero();
//			} else if ("Añade score".equals(input)) {
//				pideYAnyadeScore();
//			}
//		} while (input!=null);
//	}
//
//	
//	private static void pideYAnyadeScore() {
//		String usuario = JOptionPane.showInputDialog( "Introduce usuario a añadir:" );
//		if (usuario==null || usuario.isEmpty()) {
//			return;
//		}
//		String puntuacion = JOptionPane.showInputDialog( "Introduce puntuación a añadir (debe ser numérica):" );
//		if (puntuacion==null || puntuacion.isEmpty()) {
//			return;
//		}
//		int puntuacionNumerica = Integer.parseInt( puntuacion );
//		misScores.addPuntuacion( usuario, puntuacionNumerica );
//		misHighScores.addPuntuacion( usuario , puntuacionNumerica );
//		visualizarHighScores();
//		visualizarScores();
//	}
//	
//	private static void visualizarScores() {
//		String aMostrar = "Lista de Scores:";
//		for (String usuario : misScores.getUsuarios()) {
//			aMostrar += "\n" + usuario + "  " + misScores.getScores(usuario);
//		}
//		JOptionPane.showMessageDialog( null, aMostrar );
//	}
//
//	private static void visualizarHighScores() {
//		String aMostrar = "Lista de Highscores:";
//		for (String usuario : misHighScores.getUsuarios()) {
//			aMostrar += "\n" + usuario + "  " + misHighScores.getHighScore( usuario );
//		}
//		JOptionPane.showMessageDialog( null, aMostrar );
//	}
//	
//	private static void cargaFichero() throws FileNotFoundException {
//		Scanner ficEntrada = new Scanner( new File( "scores.txt" ) );
//		while (ficEntrada.hasNextLine()) {
//			String linea = ficEntrada.nextLine();
//			String[] valores = linea.split("\t");
//			if (valores.length>1) {
//				for (int i=1;i<valores.length;i++) {
//					misScores.addPuntuacion( valores[0], Integer.parseInt( valores[i] ) );
//					misHighScores.addPuntuacion( valores[0], Integer.parseInt( valores[i] ) );
//				}
//			}
//		}
//		ficEntrada.close();
//		visualizarHighScores();
//		visualizarScores();
//	}
//	
//	private static void guardaFichero() throws FileNotFoundException {
//		PrintStream ficSalida = new PrintStream( "scores.txt" );
//		for (String usuario : misScores.getUsuarios()) {
//			ficSalida.print( usuario );
//			for (Integer puntuacion : misScores.getScores(usuario)) {
//				ficSalida.print( "\t" + puntuacion );
//			}
//			ficSalida.println();
//		}
//		ficSalida.close();
//	}
//	
}
