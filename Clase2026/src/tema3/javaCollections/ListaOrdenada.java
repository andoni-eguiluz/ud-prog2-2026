package tema3.javaCollections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

// Ejemplo de creación de clase genérica
// Java no incorpora la lista ordenada como una clase dentro de Java Collections porque "violaría" el concepto de lista
// (interfaz List) en el que se puede meter un elemento en *cualquier* posición
// Pero podemos implementar una lista ordenada, siempre que aceptemos que no cumple el interfaz List
// https://stackoverflow.com/questions/8725387/why-is-there-no-sortedlist-in-java

/** Clase lista ordenada (no implementa List)
 * @author andoni.eguiluz at ingenieria.deusto.es
 * @param <E>	Tipo base de la lista ordenada, debe implementar el interfaz Comparable<E>
 */
public class ListaOrdenada<E extends Comparable<E>> 
	implements Iterable<E> {  // Implementamos el interfaz Iterable para permitir for each (ver ejemplo de main)
	
	/** Programa de prueba de la clase
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		// Prueba sin repeticiones
		String[] datosEjemplo = { "España", "Portugal", "Irlanda", "Francia", "Italia", "Bélgica", "Austria", "Países Bajos", "Alemania", "Luxemburgo", "Grecia", "Malta", "Chipre", "Croacia", "República Checa", "Hungría", "Rumanía", "Bulgaria", "Polonia", "Eslovaquia", "Eslovenia", "Estonia", "Letonia", "Lituania", "Dinamarca",  "Suecia", "Finlandia" };
		ListaOrdenada<String> listaO = new ListaOrdenada<>();
		for (String dato : datosEjemplo) {
			listaO.add( dato );
		}
		System.out.println( listaO );
		// Prueba con repeticiones
		String[] datosEjemplo2 = { "I", "A", "I", "E", "I", "A" };
		ListaOrdenada<String> listaO2 = new ListaOrdenada<>();
		for (String dato : datosEjemplo2) {
			listaO2.add( dato );
		}
		System.out.println( listaO2 );
		// Prueba con 1000 aleatorios entre 0 y 500
		ListaOrdenada<Integer> listaO3 = new ListaOrdenada<Integer>();
		Random r = new Random();
		for (int i=0; i<1000; i++) {
			listaO3.add( r.nextInt( 501 ) );
		}
		System.out.println( listaO3 );
		
		// No funciona con un no comparable
		// ListaOrdenada<java.awt.Point> listaError = new ListaOrdenada<>(); 
		
		// Es iterable
		for (String s : listaO2) {
			System.out.print( "  " + s );
		}
	}


	private ArrayList<E> lista;  // Se implementa con un arraylist normal

	/** Crea una nueva lista vacía
	 */
	public ListaOrdenada() {
		lista = new ArrayList<E>();
	}
	
    // No se permite añadir en CUALQUIER posición, solo al añadir se mete en el sitio que corresponde
	// (por búsqueda binaria)
    /** Añade un elemento nuevo a la lista. Se insertará en la posición correspondiente a su orden
     * @param e	Nuevo elemento a insertar
     */
    public void add(E e) {
    	int ini = 0;
    	int fin = lista.size()-1;
    	while (ini<=fin) {
    		int medio = (ini + fin) / 2;
    		if (lista.get(medio).compareTo(e) <= 0) {
    			ini = medio+1;
    		} else {
    			fin = medio-1;
    		}
    	}
        lista.add(ini,e);
    }

    /** Devuelve un elemento de la lista
     * @param i	Posición del elemento
     * @return	Elemento situado en esa posición
     */
    public E get(int i) {
        return lista.get(i);
    }

    /** Devuelve el tamaño de la lista
     * @return	Número de elementos actualmente en la lista
     */
    public int size() {
        return lista.size();
    }

    @Override
    public String toString() {
    	return lista.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof ListaOrdenada)) {
    		return false;
    	}
    	ListaOrdenada<?> lista2 = (ListaOrdenada<?>) obj;
    	if (size()!=lista2.size()) {
    		return false;
    	}
    	for (int i=0; i<size(); i++) {
    		if (get(i)==null) {
    			if (lista2.get(i)!=null) {
    				return false;
    			}
    		} else if (!get(i).equals( lista2.get(i) )) {
    			return false;
    		}
    	}
    	return true;
    }
    
    // Interfaz iterable

	@Override
	public Iterator<E> iterator() {
		return lista.iterator();  // Aprovechamos que el ArrayList ya tiene iterator
	}
}
