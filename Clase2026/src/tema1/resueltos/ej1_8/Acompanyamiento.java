package tema1.resueltos.ej1_8;

/** Clase que modela el acompañamiento de una canción basado en las notas musicales de sus acordes, y permite crear instancias con un acompañamiento, 
 * partiendo de sus acordes de partitura y el tempo. Permite además reproducir el audio del acompañamiento con la utilidad {@link tema1.Pianillo}
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Acompanyamiento {
	
	/** Volumen por defecto del acompañamiento al ser reproducido
	 */
	public static final double VOLUMEN_POR_DEFECTO = 0.5;
	
	private String nombre;         // Nombre del acompañamiento
	private int numOctavaDefecto;  // octava por defecto del acompañamiento (0-8)
	private double tempo;	       // tempo del acompañamiento (segundos por cada tiempo)
	private Acorde[] acordes;	   // Vector de acordes del acompañamiento
	private int numAcordes;        // Número de notas en ese vector (0 al principio)
	private double volumen = VOLUMEN_POR_DEFECTO;  // Volumen del acompañamiento (0-1)
	
	/** Crea un nuevo acompañamiento, vacío, con tempo 1.0 (segundos / tempo), con octava 4 por defecto
	 * @param nombre	Nombre de ese acompañamiento
	 */
	public Acompanyamiento( String nombre ) {
		this.nombre = nombre;
		this.numOctavaDefecto = 4;
		this.tempo = 1.0;  // por defecto, tempo es 1 segundo por tiempo (= nota redonda o entera)
		numAcordes = 0;
		acordes = new Acorde[1000];  // Reservamos espacio para 1000 acordes
	}
	
	/** Modifica el volumen de reproducción (0.5 por defecto)
	 * @param volumen	Nuevo volumen de reproducción (de 0.0 a 1.0)
	 */
	public void setVolumen( double volumen ) {
		this.volumen = volumen;
	}
	
	/** Modifica la octava por defecto de la composición (se utiliza al añadir notas)
	 * @param numOctava	Nueva octava por defecto (0-8)
	 */
	public void setOctavaDefecto( int numOctava ) {
		numOctavaDefecto = numOctava;
	}
	
	/** Modifica el tempo de la composición
	 * @param tempo	Nuevo tempo (número de segundos por cada tiempo de partitura)
	 */
	public void setTempo( double tempo ) {
		this.tempo = tempo;
	}
	
	/** Transpone el acompañamiento (transpone todas sus notas)
	 * @param semitonos	Número de semitonos de cambio (negativo hacia valores más graves, positivo hacia agudos)
	 */
	public void transponer( int semitonos ) {
		for (int i=0; i<numAcordes; i++) {
			acordes[i].transponer( semitonos );
		}
	}
	
	/** Añade una partitura de acordes en formato string. Los acordes se deben separar por espacios y cada acorde
	 * tiene su indicación de nota textual, y puede ir precedida de su duración, debe ir seguida del tipo de acorde, y puede ir seguida de su octava, del siguiente modo:<br>
	 * - La duración hace referencia al tiempo de cada acorde: 1 para una redonda (tiempo completo), 1/2 para blanca, 1/4 para corchea, etc. (valores habituales: 4/1, 2/1, 1/1, 1/2, 1/4, 1/8<br>
	 * - La nota es el texto en castellano: do, re, mi, fa, sol, la, si, do. También los sostenidos: do#, re#, fa#, sol#, la#, o los equivalentes bemoles: reb, mib, solb, lab, sib<br>
	 * - La octava es la octava del teclado de piano, entre 0 y 8 (las medias entre 3 y 5)<br>
	 * - El tipo de acorde es "M" para mayor, "m" para menor, "7" para séptima
	 * Ejemplo: "doM sol3M 1/2la3m"
	 * @param partitura	Partitura a añadir
	 * @param durDefectoNum	Duración por defecto (numerador)
	 * @param durDefectoDen	Duración por defecto (denominador)
	 */
	public void addPartitura( String partitura, int durDefectoNum, int durDefectoDen ) {
		String[] acordes = partitura.split( " " );
		for (String acorde : acordes) {
			// Cálculo de tipo de acorde (último carácter: "M", "m" o "7". Si no existe, por defecto M)
			char tipoDeAcorde = acorde.charAt( acorde.length()-1 );
			if (tipoDeAcorde=='m' || tipoDeAcorde=='M' || tipoDeAcorde=='7') {
				acorde = acorde.substring( 0, acorde.length()-1 );
			} else {
				tipoDeAcorde = 'M';  // Por defecto es mayor
			}
			// Cálculo de octava (último carácter tras quitar el tipo de acorde: 0 a 8. Si no existe, la indicada por defecto.
			int octava = numOctavaDefecto;
			char posibleOctava = acorde.charAt( acorde.length()-1 );
			if (Character.isDigit(posibleOctava)) {
				octava = Integer.parseInt( acorde.substring( acorde.length()-1 ) );
				acorde = acorde.substring( 0, acorde.length()-1 );
			}
			// Cálculo de duración (primeros caracteres en formato n/m. Si no empieza en dígito, duración por defecto.
			int duracionNum = durDefectoNum;
			int duracionDen = durDefectoDen;
			char posibleDuracion = acorde.charAt( 0 );
			if (Character.isDigit(posibleDuracion)) {
				int inicioNota = 1;
				while (!Character.isLetter(acorde.charAt(inicioNota))) inicioNota++;
				int barra = acorde.indexOf( "/" );
				if (barra==-1) { // Nota mayor de un tiempo
					duracionNum = Integer.parseInt( acorde.substring( 0, inicioNota ));  
					duracionDen = 1;
				} else {  // Nota quebrada (1/2, 1/4, 1/8...)
					duracionNum = Integer.parseInt( acorde.substring( 0, barra ));  
					duracionDen = Integer.parseInt( acorde.substring( barra+1, inicioNota ));  
				}
				acorde = acorde.substring( inicioNota );
			}
			// Lo que queda es el acorde ("do", "do#", "re" ...)
			addAcorde( new Acorde( acorde, tipoDeAcorde, octava, duracionNum, duracionDen ) );
		}
	}
	
	/** Añade un acorde al acompañamiento
	 * @param nota	Acorde a añadir
	 */
	public void addAcorde( Acorde nota ) {
		acordes[numAcordes] = nota;
		numAcordes++;
	}
	
	/** Reproduce el acompañamiento por los canales indicados de audio, con la utilidad {@link tema1.Pianillo}
	 * @param canales	Canales a utilizar
	 */
	public void play( int[] canales ) {
		if (canales.length<4) return;  // No se puede reproducir con menos de 4 canales
		for (int i=0; i<numAcordes; i++) {
			Acorde acorde = acordes[i];
			acorde.play( canales, tempo, volumen );
		}
	}
	
	@Override
	public String toString() {
		String mens = nombre + ". Notas: { ";
		for (int i=0; i<numAcordes; i++) mens += acordes[i] + " ";
		return mens + "}";
	}
	
}
