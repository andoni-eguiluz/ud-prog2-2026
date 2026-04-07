package tema3.basicos;

import java.util.LinkedList;

public class PruebaPilasColas {
	public static void main(String[] args) {
		LinkedList<Integer> pila = new LinkedList<Integer>();
		LinkedList<Integer> cola = new LinkedList<Integer>();
		LinkedList<Integer> lista = new LinkedList<Integer>();
		LinkedList<Integer> lista2 = new LinkedList<Integer>();
		System.out.println( "Meter y sacar 1, 3, 5 en pila:");
		pila.push( 1 ); pila.push( 3 ); pila.push( 5 );
		while (!pila.isEmpty())
			System.out.print( " " + pila.pop() );
		System.out.println();
		System.out.println( "Meter y sacar 1, 3, 5 en cola:");
		cola.addLast( 1 ); cola.addLast( 3 ); cola.addLast( 5 );
		while (!cola.isEmpty())
			System.out.print( " " + cola.removeFirst() );
		System.out.println();
		int numEls = 150000;
		visuTiempo( "Análisis de tiempos:", true );
		for (int i=0; i<numEls; i++) lista.add(i);
		visuTiempo( "Tiempo de crear lista de " + numEls + " datos" );
		for (int i=0; i<numEls; i++) lista.removeFirst();
		visuTiempo( "Tiempo de vaciar lista" );
		
		for (int i=0; i<numEls; i++) cola.addLast(i);
		visuTiempo( "Tiempo de crear cola de " + numEls + " datos" );
		for (int i=0; i<numEls; i++) cola.removeFirst();
		visuTiempo( "Tiempo de vaciar cola" );
		
		for (int i=0; i<numEls; i++) pila.push(i);
		visuTiempo( "Tiempo de crear pila de " + numEls + " datos" );
		for (int i=0; i<numEls; i++) pila.pop();
		visuTiempo( "Tiempo de vaciar pila" );
		
		for (int i=0; i<numEls; i++) { lista2.add( i/2, i ); if (i%10000==0) System.out.print( " " + i ); } System.out.println();
		visuTiempo( "Tiempo de crear lista por el medio" );
		for (int i=numEls-1; i>=0; i--) { lista2.remove( i/2 ); if (i%10000==0) System.out.print( " " + i ); } System.out.println();
		visuTiempo( "Tiempo de vaciar lista por el medio" );

	}

	
	// Utilidades para visualizar tiempo transcurrido
	
	private static long tiempoRecienUsado = -1;
	private static long ultimoTiempo = -1;

	/** Visualiza el tiempo usado desde la llamada anterior en System.out
	 * @param mens	Mensaje a visualizar en consola System.out, seguido del tiempo.
	 * 				Si es null se calculan los tiempos relativos pero no se visualizan.
	 * @param separa	Si true, separa una línea
	 */
	private static void visuTiempo( String mens, boolean separa ) {
		if (separa) System.out.println();
		visuTiempo(mens);
	}
	
	/** Visualiza el tiempo usado desde la llamada anterior en System.out
	 * @param mens	Mensaje a visualizar en consola System.out, seguido del tiempo.
	 * 				Si es null se calculan los tiempos relativos pero no se visualizan.
	 */
	private static void visuTiempo( String mens ) {
		if (mens!=null) {
			if (ultimoTiempo != -1) {
				tiempoRecienUsado = System.currentTimeMillis() - ultimoTiempo;
				if (mens!=null) System.out.print( "[" + String.format( "%1$,1d", tiempoRecienUsado ) + " msgs.] " );
			}
			System.out.println( mens );
		}
		ultimoTiempo = System.currentTimeMillis();
	}
	
	
}
