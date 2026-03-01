package tema1.resueltos.ej1_8;

import java.util.Arrays;

import utils.audio.Pianillo;

/** Clase Acorde que permite crear objetos que representan a notas musicales reproducibles en una partitura de acordes de acompañamiento,
 * con nombre musical de la nota, octava y duración
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Acorde {
	String nombreAcorde;  // Nombre del acorde según la nomenclatura castellana de la nota principal
	char tipoAcorde;      // Tipo de acorde ('M' = Mayor, 'm' = menor, '7' = séptima)
	int octava;           // Número de octava (0 a 8 según las octavas de un piano general)
	int duracionNum;      // Duración del acorde (numerador). Relativa al tempo (que no se considera en el acorde)
	int duracionDen;      // Duración del acorde (denominador). Relativa al tempo (que no se considera en el acorde)
	
	public Acorde( String nombreAcorde, char tipoAcorde, int octava, int duracionNum, int duracionDen ) {
		this.nombreAcorde = nombreAcorde;
		this.tipoAcorde = tipoAcorde;
		this.octava = octava;
		this.duracionNum = duracionNum;
		this.duracionDen = duracionDen;
	}
	
	/** Devuelve el nombre de acorde 
	 * @return	Nombre del acorde según la nomenclatura castellana de la nota principal
	 */
	public String getNombreAcorde() {
		return nombreAcorde;
	}

	/** Modifica el nombre de acorde
	 * @param nombreAcorde	Nombre del acorde según la nomenclatura castellana de la nota principal
	 */
	public void setNombreAcorde(String nombreAcorde) {
		this.nombreAcorde = nombreAcorde;
	}
	
	/** Devuelve el tipo de acorde
	 * @return	Tipo de acorde ('M' = Mayor, 'm' = menor, '7' = séptima)
	 */
	public char getTipo() {
		return tipoAcorde;
	}

	/** Cambia el tipo de acorde
	 * @param tipoAcorde	Tipo de acorde ('M' = Mayor, 'm' = menor, '7' = séptima)
	 */
	public void setTipo( char tipoAcorde ) {
		this.tipoAcorde = tipoAcorde;
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

	/** Devuelve la duración del acorde (siempre relativa al tempo que se define externamente)
	 * @return	Numerador de la duración del acorde
	 */
	public int getDuracionNum() {
		return duracionNum;
	}

	/** Modifica la duración del acorde (siempre relativa al tempo que se define externamente)
	 * @param duracionNum	Numerador de la duración
	 */
	public void setDuracionNum(int duracionNum) {
		this.duracionNum = duracionNum;
	}

	/** Devuelve la duración del acorde (siempre relativa al tempo que se define externamente)
	 * @return	Denominador de la duración del acorde
	 */
	public int getDuracionDen() {
		return duracionDen;
	}

	/** Modifica la duración del acorde (siempre relativa al tempo que se define externamente)
	 * @param duracionDen	Denominador de la duración
	 */
	public void setDuracionDen(int duracionDen) {
		this.duracionDen = duracionDen;
	}
	
	/** Devuelve la duración del acorde en formato de número único (no quebrado numerador/denominador)
	 * @return	Duración del acorde (1.0 para entera, 0.5 para blanca, 0.25 para negra, etc.)
	 */
	public double getDuracion() {
		return 1.0 * duracionNum / duracionDen;
	}

	/** Transpone la nota del acorde (cambia el tono)
	 * @param semitonos	Número de semitonos de cambio (negativo hacia valores más graves, positivo hacia agudos)
	 */
	public void transponer( int semitonos ) {
		int posiNota = Arrays.asList(Nota.NOTAS_MUSICALES).indexOf( nombreAcorde.toUpperCase() );
		if (posiNota==-1) posiNota = Arrays.asList(Nota.NOTAS_MUSICALES_BEMOL).indexOf( nombreAcorde.toUpperCase() ); // Intentamos bemol en vez de sostenido
		if (posiNota==-1) return; // Error si la nota no es conocida (notación castellana) - no se puede transponer
		posiNota += semitonos; // Incrementa el número de semitonos
		while (posiNota<0 || posiNota>=Nota.NOTAS_MUSICALES.length) {  // Cambios de escala si se sale (rango 0 a 11)
			if (posiNota<0) {
				posiNota += Nota.NOTAS_MUSICALES.length; // Suma 12 semitonos
				octava--;  // Baja una octava
			} else {
				posiNota -= Nota.NOTAS_MUSICALES.length; // Resta 12 semitonos
				octava++;  // Sube una octava
			}
		}
		nombreAcorde = Nota.NOTAS_MUSICALES[posiNota]; // Coge el nombre definitivo
	}

		private static int[] semitonosAcordeMayor = { 0, 4, 7, 12 };  // Semitonos de salto de las cuatro notas de un acorde mayor (por defecto)
		private static int[] semitonosAcordeMenor = new int[] { 0, 3, 7, 12 };  // Semitonos de salto de las cuatro notas de un acorde menor
		private static int[] semitonosAcordeSeptima = new int[] { 0, 4, 7, 10 };  // Semitonos de salto de las cuatro notas de un acorde de séptima

	/** Reproduce el acorde, con la utilidad {@link tema1.Pianillo}
	 * @param canales	Canales a utilizar
	 * @param tempo	Tempo de la nota (segundos por cada nota entera)
	 * @param volumen	Volumen de la nota (de 0.0 -silencio- a 1.0 -volumen 100%-)
	 */
	public void play( int[] canales, double tempo, double volumen ) {
        double frecBase = Pianillo.frecuenciaNota( getNombreAcorde(), getOctava() );
        int[] semitonosAcorde = semitonosAcordeMayor; // Por defecto
        if (getTipo()=='m') semitonosAcorde = semitonosAcordeMenor;
        else if (getTipo()=='7') semitonosAcorde = semitonosAcordeSeptima;
    	for (int j = 0; j<semitonosAcorde.length; j++) {
    		int salto = semitonosAcorde[j];
    		double frec = Pianillo.anyadeSemitonos( frecBase, salto );
            Pianillo.mandaSonido( canales[j], Pianillo.samplesDeNota( frec, getDuracion()*tempo, volumen ) );
    	}
	}
	
	@Override
	public String toString() {
		return duracionNum + "/" + duracionDen + nombreAcorde + octava + tipoAcorde;
	}
	
}
