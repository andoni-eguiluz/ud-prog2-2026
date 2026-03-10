package tema1b.resueltos.objetodle;

import java.util.ArrayList;
import java.util.Arrays;

public class Opciones {
	private ArrayList<Elemento> opciones;
	public Opciones( Elemento... elementos ) {
		opciones = new ArrayList<>();
		add( elementos );
	}
	public void add( Elemento elemento ) {
		opciones.add( elemento );
	}
	public void add( ArrayList<Elemento> elementos ) {
		opciones.addAll( elementos );
	}
	public void add( Elemento... elementos ) {
		opciones.addAll( Arrays.asList( elementos ) );
	}
	public int size() {
		return opciones.size();
	}
	public ArrayList<Elemento> getOpciones() {
		return opciones;
	}
}
