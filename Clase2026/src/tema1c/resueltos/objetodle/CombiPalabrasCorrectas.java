package tema1c.resueltos.objetodle;

import java.util.ArrayList;
import java.util.List;

public class CombiPalabrasCorrectas extends Combi implements Comprobable {
	
	// Combinaciones correctas
	private ArrayList<String> combinacionesOk;
	
	public CombiPalabrasCorrectas( int numElementos ) {
		super( numElementos );
		combinacionesOk = null;
	}
	public CombiPalabrasCorrectas() {
		this(5);
	}
	
	/** Genera una combinación aleatoria de las opciones indicadas (atención: las opciones deben corresponder a encadenamientos de elementos válidos disponibles en las opciones)
	 * @param opciones	Opciones entre las que elegir
	 * @param numElementos	Número de elementos de la combinación
	 * @param opcionesValidas	Lista de strings correspondientes a opciones válidas - todas deben corresponder a combinaciones de elementos válidas de acuerdo a su carácter de elemento
	 */
	public CombiPalabrasCorrectas( Opciones opciones, int numElementos, ArrayList<String> opcionesValidas ) {
		this( numElementos );
		// Elegir una palabra aleatoria y convertir a los elementos que corresponden
		String opcionElegida = opcionesValidas.get( random.nextInt( opcionesValidas.size() ) );
		for (char carOpcion : opcionElegida.toCharArray()) {
			for (Elemento elemento : opciones.getOpciones()) {
				if (elemento.aCaracter() == carOpcion) {
					add( elemento.duplicar() );
					break;
				}
			}
			
		}
		combinacionesOk = opcionesValidas;
	}

	// Solo añade elementos si son letras
	@Override
	public void add( Elemento elemento ) {
		if (elemento instanceof Letra) {
			lista.add( elemento );
		} else {
			System.err.println( "Error en combinación de palabras a la que se intenta añadir una no letra" );
		}
	}
	
	// Interfaz Comprobable
	
	/** Modifica las combinaciones que son correctas (si se quieren limitar las palabras aceptadas)
	 * @param combisCorrectas	Lista de combinaciones correctas
	 */
	public void setCombinacionesCorrectas( List<String> combisCorrectas ) {
		combinacionesOk = new ArrayList<>( combisCorrectas );
	}
	
	@Override
	public boolean esComprobable() {
		return combinacionesOk!=null;
	}
	
	@Override
	public boolean intentoCorrecto() {
		if (combinacionesOk==null) {  // Todas las combinaciones son correctas en principio, salvo que haya un filtro de las correctas
			return true;
		}
		String intento = aString();
		for (String combinacionPosible : combinacionesOk) {
			if (combinacionPosible.equals( intento )) {
				return true;
			}
		}
		return false;
	}

}
