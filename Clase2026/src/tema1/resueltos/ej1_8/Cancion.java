package tema1.resueltos.ej1_8;

import java.util.*;

/** Clase que modela una canción basada en notas musicales y permite crear instancias con una canción, 
 * partiendo de sus notas de partitura y el tempo. Permite además reproducir el audio de la canción con la utilidad {@link tema1.Pianillo}
 * Cambiado el array de notas por un ArrayList de notas
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Cancion {
	
	private String nombre;         // Nombre de la canción
	private int numOctavaDefecto;  // octava por defecto de la canción (0-8)
//	private Nota[] notas;	       // Vector de notas de la canción
//	private int numNotas;	       // Número de notas en ese vector (0 al principio)
	private ArrayList<Nota> notas;
	 
	/** Crea una nueva canción, vacía, con tempo 1.0 (segundos / tempo), con octava 4 por defecto
	 * @param nombre	Nombre de esa canción
	 */
	public Cancion( String nombre ) {
		this.nombre = nombre;
		this.numOctavaDefecto = 4;
		// numNotas = 0;
		// notas = new Nota[1000];  // Reservamos espacio para 1000 notas
		notas = new ArrayList<Nota>();
	}
	
	/** Modifica la octava por defecto de la composición (se utiliza al añadir notas)
	 * @param numOctava	Nueva octava por defecto (0-8)
	 */
	public void setOctavaDefecto( int numOctava ) {
		numOctavaDefecto = numOctava;
	}
	
	/** Añade una partitura en formato string. Las notas se deben separar por espacios y cada nota
	 * tiene su indicación textual, y puede ir precedida de su duración, y seguida de su octava, del siguiente modo:<br>
	 * - La duración hace referencia al tiempo de cada nota: 1 para una redonda (tiempo completo), 1/2 para blanca, 1/4 para corchea, etc. (valores habituales: 4/1, 2/1, 1/1, 1/2, 1/4, 1/8, 1/16, 1/32, 1/64, 1/128, 1/256<br>
	 * - La nota es el texto en castellano: do, re, mi, fa, sol, la, si, do. También los sostenidos: do#, re#, fa#, sol#, la#, o los equivalentes bemoles: reb, mib, solb, lab, sib<br>
	 * - La octava es la octava del teclado de piano, entre 0 y 8 (las medias entre 3 y 5)<br>
	 * Ejemplo: "1/2sol3 1/4mi3 1/4fa3 1/2sol3 1/8sol3 1/8do4 1/8sol3 1/8fa3 1/4mi3"
	 * @param partitura	Partitura a añadir
	 * @param durDefectoNum	Duración por defecto (numerador)
	 * @param durDefectoDen	Duración por defecto (denominador)
	 */
	public void addPartitura( String partitura, int durDefectoNum, int durDefectoDen ) {
		String[] notas = partitura.split( " " );
		for (String nota : notas) {
			// Cálculo de octava (último carácter: 0 a 8. Si no existe, la indicada por defecto.
			int octava = numOctavaDefecto;
			char posibleOctava = nota.charAt( nota.length()-1 );
			if (Character.isDigit(posibleOctava)) {
				octava = Integer.parseInt( nota.substring( nota.length()-1 ) );
				nota = nota.substring( 0, nota.length()-1 );
			}
			// Cálculo de duración (primeros caracteres en formato n/m. Si no empieza en dígito, duración por defecto.
			int duracionNum = durDefectoNum;
			int duracionDen = durDefectoDen;
			char posibleDuracion = nota.charAt( 0 );
			if (Character.isDigit(posibleDuracion)) {
				int inicioNota = 1;
				while (!Character.isLetter(nota.charAt(inicioNota))) inicioNota++;
				int barra = nota.indexOf( "/" );
				if (barra==-1) { // Nota mayor de un tiempo
					duracionNum = Integer.parseInt( nota.substring( 0, inicioNota ));  
					duracionDen = 1;
				} else {  // Nota quebrada (1/2, 1/4, 1/8...)
					duracionNum = Integer.parseInt( nota.substring( 0, barra ));  
					duracionDen = Integer.parseInt( nota.substring( barra+1, inicioNota ));  
				}
				nota = nota.substring( inicioNota );
			}
			// Lo que queda es el acorde ("do", "do#", "re" ...)
			addNota( new Nota( nota, octava, duracionNum, duracionDen ) );
		}
	}
	
	/** Añade una nota a la canción
	 * @param nota	Nota a añadir
	 */
	public void addNota( Nota nota ) {
		notas.add( nota );
		// notas[numNotas] = nota;
		// numNotas++;
	}
	
	/** Reproduce el audio de la canción, con la utilidad {@link tema1.Pianillo}
	 * @param canal	Canal a utilizar
	 * @param tempo	Tempo de la canción (segundos por cada tiempo musical)
	 * @param volumen	Volumen de la canción (rango 0.0-1.0) (0.0 = silencio, 1.0 = volumen máximo)
	 */
	public void play( int canal, double tempo, double volumen ) {
		Nota.setTempo( tempo );
		// for (int i=0; i<numNotas; i++) {
		for (int i=0; i<notas.size(); i++) {
			// Nota nota = notas[i];
			Nota nota = notas.get(i);
			nota.play( canal, volumen );
		}
	}
	
	/** Transpone la canción (transpone todas sus notas)
	 * @param semitonos	Número de semitonos de cambio (negativo hacia valores más graves, positivo hacia agudos)
	 */
	public void transponer( int semitonos ) {
		// for (int i=0; i<numNotas; i++) {
		// 	notas[i].transponer( semitonos );
		// }
		for (int i=0; i<notas.size(); i++) {
			notas.get(i).transponer( semitonos );
		}
	}
	
	@Override
	public String toString() {
		String mens = nombre + ". Notas: { ";
		// for (int i=0; i<numNotas; i++) mens += notas[i] + " ";
		for (int i=0; i<notas.size(); i++) {
			mens += notas.get(i) + " ";
		}
		return mens + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO implementación a mejorar cuando entendamos el casting sobre herencia
		Cancion c2 = (Cancion) obj;
		return nombre.equals(c2.nombre); // Se podrían comprobar también que las notas son iguales, con arraylist es muy fácil: equals().  (con array: NO) 
	}
	
}
