package tema1c.resueltos.deustoMinecraft;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Objeto> listaObjetos;

	public Inventario() {
		this.listaObjetos = new ArrayList<>();
	}

	public ArrayList<Objeto> getListaObjetos() {
		return listaObjetos;
	}

	public void agregarObjeto(Objeto o) {
		this.listaObjetos.add(o);
	}

	@Override
	public String toString() {
		return listaObjetos.toString();
	}
}