package tema1.resueltos.ej1_8;

import utils.audio.Pianillo;

/** Clase principal de ejercicio 1.8 de tocar música partiendo de notas. Toca Frere Jacques
 * Usa la clase de utilidad {@link tema1.Pianillo}
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class MainFrereJacques {

	private static double TEMPO = 2.0;       // 2 segundos por cada tiempo
	private static double VOL_CANCION = 0.7; // Volumen de canción (entre 0.0 y 1.0)
	private static double VOL_ACOMP = 0.2;   // Volumen de acompañamiento (entre 0.0 y 1.0)
	
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		tocaFrereJacques();
	}
	
	private static void tocaFrereJacques() {
		boolean conAcompanyamiento = true;
		// Crear canción
		Cancion cancion = new Cancion( "Frere Jacques" );
		cancion.addPartitura( "do re mi do do re mi do mi fa 1/2sol mi fa 1/2sol " +
				"1/8sol 1/8la 1/8sol 1/8fa mi do 1/8sol 1/8la 1/8sol 1/8fa mi do re sol3 1/2do re sol3 1/2do", 1, 4 );
		System.out.println( "Canción: " + cancion );

		Acompanyamiento acomp = null;
		if (conAcompanyamiento) {
			// Crear acompañamiento (acordes)
			acomp = new Acompanyamiento( "Frere Jacques - acordes" );
			acomp.setTempo( TEMPO );
			acomp.setOctavaDefecto( 3 );
			acomp.addPartitura( "solM re4M 1/2solM solM re4M 1/2solM solM do4M 1/2re4M solM do4M 1/2re4M " +
					"1/2re47 1/2solM 1/2re47 1/2solM do4M reM 1/2solM do4M reM 1/2solM", 1, 4 );
			acomp.transponer( -7 );  // El acompañamiento está en sol y la melodía en do (disminuimos 7 semitonos el acompañamiento)
			System.out.println( "Acompañamiento: " + acomp );
			acomp.setVolumen( VOL_ACOMP );
		}
		
		// Lanzar canción (y acompañamiento si existe al mismo tiempo)
		cancion.play( 0, TEMPO, VOL_CANCION );  // Lanza canción por el canal 0
		if (conAcompanyamiento)
			acomp.play( new int[] { 1, 2, 3, 4 } );  // Y acompañamiento por los canales 1-4
		
		Pianillo.closeCuandoSeAcabe();
	}

}
