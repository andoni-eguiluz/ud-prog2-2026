package tema1c.ejemplos.deustoMinecraft;

import java.util.ArrayList;

public class Inventario {
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