package utils.audio;

public class PruebaPianillo {

    /**
     * Prueba de clase - toca escala y los acordes básicos del Canon de Pachelbel
     * @param args No utilizado
     */
    public static void main(String[] args) {
        int[] escala = { 0, 2, 4, 5, 7, 9, 11, 12 };  // Salto de semitonos de una escala

        double notaDO = Pianillo.frecuenciaNota( "DO", 4 );

        // Prueba 1. Reproducir canción cargada de fichero (wav o au)
        String fichero = Pianillo.class.getResource( "efecto.wav" ).getFile();
        double[] audioDesdeFich = Pianillo.read( fichero );
        Pianillo.mandaSonido( 0, audioDesdeFich );
        
        // Prueba 2. Escala normal (1 canal)
        for (int i = 0; i < escala.length; i++) {
        	double nota = Pianillo.anyadeSemitonos( notaDO, escala[i] );
            Pianillo.mandaSonido( 0, Pianillo.samplesDeNota(nota, 0.5, 0.5) );
        }
        while (Pianillo.haySamplesSonando()) { Pianillo.esperaMilis( 1000 ); }
        Pianillo.esperaSegs( 1.0 );
        
        // Prueba 3. Canción de acordes (3 canales - 3 notas por acorde) 
        // Base de canon de Pachelbel
        int[] acordeMayor = { 0, 4, 7 };  // Semitonos de salto de las tres notas de un acorde mayor
        int[] acordeMenor = { 0, 3, 7 };  // Semitonos de salto de las tres notas de un acorde menor
        String[] acordes =          {  "DO" , "SOL", "LA" , "MI" , "FA" , "DO" , "FA" , "SOL" };
        int[] numOctava =           {   4   ,   4  ,  4   ,  4   ,  4   ,  4   ,  4   ,   4   };
        boolean[] mayoresOMenores = {   true,  true, false, false,  true,  true,  true,  true };
        double[] duraciones =       {  1.0  ,  1.0 ,  1.0 ,  1.0 ,  1.0 ,  1.0 ,  1.0 ,  1.0  };
        for (int i = 0; i < acordes.length; i++) {
        	double nota = Pianillo.frecuenciaNota( acordes[i], numOctava[i] );
        	// Quitar los silencios si se quiere arpegiar
        	// Pianillo.mandaSilencio( 1, 0.2 );
        	// Pianillo.mandaSilencio( 2, 0.4 );
        	for (int j = 0; j<3; j++) {
        		boolean tipoAcorde = mayoresOMenores[j];
        		int salto = tipoAcorde ? acordeMayor[j] : acordeMenor[j];
                Pianillo.mandaSonido( j, 
                		Pianillo.samplesDeNota( Pianillo.anyadeSemitonos( nota, salto ), duraciones[i], 0.5 ) );
                Pianillo.mandaSilencio( j, 0.1 );
        	}
            // Pianillo.mandaSilencio( 0, 0.4 );
        	// Pianillo.mandaSilencio( 1, 0.2 );
        }
        while (Pianillo.haySamplesSonando()) { Pianillo.esperaSegs( 1.0 ); }
        Pianillo.esperaSegs( 1.0 );
        
        // Hay que llamar a esta rutina para cerrar todo el sistema de sonido, si no
        // el programa seguirá funcionando
        Pianillo.closeCuandoSeAcabe();
        
        // También se podría hacer
        // while (Pianillo.haySamplesSonando()) { Pianillo.esperaMilis( 1000 ); }
        // Pianillo.close(); 
    }

}
