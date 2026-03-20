package tema1c.resueltos.objetodle;

import java.util.ArrayList;
import java.util.Random;

public class Combi {
	protected static final Random random = new Random();
	protected int numMaxElementos;
	protected ArrayList<Elemento> lista;
	
	public Combi( int numElementos ) {
		this.numMaxElementos = numElementos;
		lista = new ArrayList<Elemento>();
	}
	
	public Combi() {
		this(5);
	}
	
	/** Genera una combinación aleatoria de elementos partiendo de las opciones indicadas
	 * @param opciones	Opciones entre las que elegir
	 * @param numElementos	Número de elementos de la combinación
	 */
	public Combi( Opciones opciones, int numElementos ) {
		this( numElementos );
		for (int i=0; i<numElementos; i++) {
			int aleatorio = random.nextInt( opciones.getOpciones().size() );
			add( opciones.getOpciones().get( aleatorio ).duplicar() );
		}
	}
	
	public ArrayList<Elemento> getCombinacion() {
		return lista;
	}
	public int getNumActualElementos() {
		return lista.size();
	}
	public int getNumMaxElementos() {
		return numMaxElementos;
	}
	public void add( Elemento elemento ) {
		lista.add( elemento );
	}
	/** Borra los elementos de la combinación y la reinicia vacía
	 */
	public void clear() {
		lista.clear();
	}
	public boolean estaCompletada() {
		return lista.size()==numMaxElementos;
	}
	
	/** Compara la combinación con la solución, modifica el estado de los elementos de acuerdo a eso, y devuelve info de si se ha solucionado 
	 * @param solucion	Combinación con la que comparar la combinación actual
	 * @return	true si todos los elementos son iguales, false en caso contrario
	 */
	public boolean comparaConSolucion( Combi solucion ) {
		ArrayList<Elemento> copiaSol = new ArrayList<>( solucion.lista );
		// Paso 1 - buscar en las mismas posiciones
		int contIguales = 0;
		for (int i=copiaSol.size()-1; i>=0; i--) {
			if (i<lista.size() && lista.get(i).equals(copiaSol.get(i))) {  // Son iguales
				contIguales++;
				copiaSol.remove(i);
				lista.get(i).setEstado( EstadoElemento.ACIERTO_CON_POSICION );
			}
		}
		// Paso 2 - buscar en distintas posiciones
		for (Elemento e : lista) {
			if (e.getEstado()!=EstadoElemento.ACIERTO_CON_POSICION) {
				int estaEnSolucion = copiaSol.indexOf( e );
				if (estaEnSolucion==-1) {
					e.setEstado( EstadoElemento.FALLO );
				} else {
					e.setEstado( EstadoElemento.ACIERTO_SIN_POSICION );
					copiaSol.remove( estaEnSolucion );
				}
			}
		}
		return contIguales == numMaxElementos;
	}

	/** Devuelve el código de caracteres en forma de string de esta combinación
	 * @return	Caracteres combinados en un string
	 */
	public String aString() {
		String ret = "";
		for (Elemento e : lista) {
			ret += e.aCaracter();
		}
		return ret;
	}

	
}
