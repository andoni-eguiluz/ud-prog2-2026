package tema1c.resueltos.objetodle;

public class CombiNumerosImpares extends Combi implements Comprobable {
	
	public CombiNumerosImpares( int numElementos ) {
		super( numElementos );
	}
	
	public CombiNumerosImpares() {
		this(5);
	}
	
	/** Genera una combinación aleatoria válida de números (el último tiene que ser impar)
	 * @param opciones	Opciones entre las que elegir (deben ser de tipo Numero)
	 * @param numElementos	Número de elementos de la combinación
	 */
	public CombiNumerosImpares( Opciones opciones, int numElementos ) {
		this( numElementos );
		for (int i=0; i<numElementos-1; i++) {
			int aleatorio = random.nextInt( opciones.getOpciones().size() );
			add( opciones.getOpciones().get( aleatorio ).duplicar() );
		}
		Numero ultimoImpar;
		do {
			int ultimoAleatorio = random.nextInt( opciones.getOpciones().size() );
			ultimoImpar = (Numero) (opciones.getOpciones().get( ultimoAleatorio ));
		} while (ultimoImpar.getNumero() % 2 == 0); // Repite mientras sea par, hasta que se encuentre algún impar aleatorio
		add( ultimoImpar );
	}
	
	// Solo añade elementos si son numeros
	@Override
	public void add( Elemento elemento ) {
		if (elemento instanceof Numero) {
			lista.add( elemento );
		} else {
			System.err.println( "Error en combinación de números a la que se intenta añadir un no número" );
		}
	}
	
	// Interfaz Comprobable
	
	@Override
	public boolean esComprobable() {
		return true;
	}
	
	@Override
	public boolean intentoCorrecto() {
		return lista.size()==numMaxElementos && ((Numero)(lista.get(numMaxElementos-1))).getNumero() % 2 == 1;  // correcto si último dígito es impar
	}

}
