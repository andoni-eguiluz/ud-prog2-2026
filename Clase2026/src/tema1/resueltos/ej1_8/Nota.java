package tema1.resueltos.ej1_8;

import java.util.Arrays;

import utils.audio.Pianillo;

/** Clase Nota que permite crear objetos que representan a notas musicales reproducibles en una partitura,
 * con nombre musical de la nota, octava y duración.
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Nota {
	
	/** Listado de nombres castellanos de notas musicales de una octava completa, utilizando sostenidos, en orden creciente de semitonos
	 */
	public static String[] NOTAS_MUSICALES = { "DO", "DO#", "RE", "RE#", "MI", "FA", "FA#", "SOL", "SOL#", "LA", "LA#", "SI" };
	/** Listado de nombres castellanos de notas musicales de una octava completa, utilizando bemoles, en orden creciente de semitonos
	 */
	public static String[] NOTAS_MUSICALES_BEMOL = { "DO", "REb", "RE", "MIb", "MI", "FA", "SOLb", "SOL", "LAb", "LA", "SIb", "SI" };

	private static double tempo = 1.0; // Segundos por tempo
	
	/** Cambia el tempo general de la reproducción de notas
	 * @param nuevoTempo	Número de segundos que tarda cada tempo
	 */
	public static void setTempo( double nuevoTempo ) {
		tempo = nuevoTempo;
	}
	
	/// -------------- NO STATIC
	
	String nombreNota;    // Nombre de la nota según la nomenclatura castellana de la nota principal
	int octava;           // Número de octava (0 a 8 según las octavas de un piano general)
	int duracionNum;      // Duración de la nota (numerador). Relativa al tempo (que no se considera en la nota)
	int duracionDen;      // Duración de la nota (denominador). Relativa al tempo (que no se considera en la nota)
	
	public Nota( String nombreNota, int octava, int duracionNum, int duracionDen ) {
		this.nombreNota = nombreNota;
		this.octava = octava;
		this.duracionNum = duracionNum;
		this.duracionDen = duracionDen;
	}
	
	/** Devuelve el nombre de nota
	 * @return	Nombre de la nota según la nomenclatura castellana (DO, DO#, RE, RE#, etc.)
	 */
	public String getNombreNota() {
		return nombreNota;
	}

	/** Modifica el nombre de la nota
	 * @param nombreNota	Nombre de la nota según la nomenclatura castellana (DO, DO#, RE, RE#, etc.)
	 */
	public void setNombreNota(String nombreNota) {
		this.nombreNota = nombreNota;
	}

	/** Devuelve la octava
	 * @return	Octava de la nota (0-8 según el teclado de piano general)
	 */
	public int getOctava() {
		return octava;
	}

	/** Cambia la octava
	 * @param octava	Octava de la nota (0-8 según el teclado de piano general)
	 */
	public void setOctava(int octava) {
		this.octava = octava;
	}

	/** Devuelve la duración de la nota (siempre relativa al tempo que se define externamente)
	 * @return	Numerador de la duración de la nota
	 */
	public int getDuracionNum() {
		return duracionNum;
	}

	/** Modifica la duración de la nota (siempre relativa al tempo que se define externamente)
	 * @param duracionNum	Numerador de la duración de la nota
	 */
	public void setDuracionNum(int duracionNum) {
		this.duracionNum = duracionNum;
	}

	/** Devuelve la duración de la nota (siempre relativa al tempo que se define externamente)
	 * @return	Denominador de la duración de la nota
	 */
	public int getDuracionDen() {
		return duracionDen;
	}

	/** Modifica la duración de la nota (siempre relativa al tempo que se define externamente)
	 * @param duracionDen	Denominador de la duración de la nota
	 */
	public void setDuracionDen(int duracionDen) {
		this.duracionDen = duracionDen;
	}
	
	/** Devuelve la duración de la nota en formato de número único (no quebrado numerador/denominador)
	 * @return	Duración de la nota (1.0 para entera, 0.5 para blanca, 0.25 para negra, etc.)
	 */
	public double getDuracion() {
		return 1.0 * duracionNum / duracionDen;
	}

	/** Transpone la nota (cambia el tono)
	 * @param semitonos	Número de semitonos de cambio (negativo hacia valores más graves, positivo hacia agudos)
	 */
	public void transponer( int semitonos ) {
		int posiNota = Arrays.asList(NOTAS_MUSICALES).indexOf( nombreNota.toUpperCase() );
		if (posiNota==-1) posiNota = Arrays.asList(NOTAS_MUSICALES_BEMOL).indexOf( nombreNota.toUpperCase() ); // Intentamos bemol en vez de sostenido
		if (posiNota==-1) return; // Error si la nota no es conocida (notación castellana) - no se puede transponer
		posiNota += semitonos; // Incrementa el número de semitonos
		while (posiNota<0 || posiNota>=NOTAS_MUSICALES.length) {  // Cambios de escala si se sale (rango 0 a 11)
			if (posiNota<0) {
				posiNota += NOTAS_MUSICALES.length; // Suma 12 semitonos
				octava--;  // Baja una octava
			} else {
				posiNota -= NOTAS_MUSICALES.length; // Resta 12 semitonos
				octava++;  // Sube una octava
			}
		}
		nombreNota = NOTAS_MUSICALES[posiNota]; // Coge el nombre definitivo
	}
	
	/** Reproduce la nota musical, con la utilidad {@link tema1.Pianillo}
	 * @param canal	Canal a utilizar
	 * @param volumen	Volumen de la nota (de 0.0 -silencio- a 1.0 -volumen 100%-)
	 */
	public void play( int canal, double volumen ) {
        double frecNota = Pianillo.frecuenciaNota( this.getNombreNota(), this.getOctava() );
        Pianillo.mandaSonido( canal, Pianillo.samplesDeNota( frecNota, this.getDuracion()*tempo, volumen ) );
	}
	
	@Override
	public String toString() {
		return duracionNum + "/" + duracionDen + nombreNota + octava;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO implementación a mejorar cuando entendamos el casting sobre herencia
		Nota n2 = (Nota) obj;
		return nombreNota.equals(n2.nombreNota) && octava==n2.octava && duracionDen==n2.duracionDen && duracionNum==n2.duracionNum; 
	}
	
}
