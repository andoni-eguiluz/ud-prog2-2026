package tema1c.ejemplos.deustoMinecraft;

import java.util.ArrayList;

public class Mundo {
	private ArrayList<Entidad> listaEntidades;

	public Mundo() {
		this.listaEntidades = new ArrayList<>();
	}

	public void agregarEntidad(Entidad e) {
		this.listaEntidades.add(e);
	}

	// 2.3 Lógica de Inventario (Objetos Legendarios de un jugador)
	public ArrayList<Objeto> getObjetosLegendarios(String nombreJugador) {
		ArrayList<Objeto> legendarios = new ArrayList<>();
		for (Entidad e : listaEntidades) {
			if (e instanceof Jugador && e.getNombre().equals(nombreJugador)) {
				Jugador j = (Jugador) e;
				for (Objeto obj : j.getInventario().getListaObjetos()) {
					if (obj.getRareza() == Rareza.LEGENDARIO) {
						legendarios.add(obj);
					}
				}
			}
		}
		return legendarios;
	}

	// 3.1 Conteo de Peligro
	public int countHostileMobs() {
		int contador = 0;
		for (Entidad e : listaEntidades) {
			if (e instanceof Mob) {
				if (((Mob) e).isHostil()) {
					contador++;
				}
			}
		}
		return contador;
	}

	// 3.2 Localización
	public String getNombreEntidadEnPosicion(int x, int y) {
		for (Entidad e : listaEntidades) {
			if (e.getX() == x && e.getY() == y) {
				return e.getNombre();
			}
		}
		return null;
	}

	public ArrayList<Entidad> getListaEntidades() {
		return listaEntidades;
	}
}