package tema1c.resueltos.deustoMinecraft;

import java.io.Serializable;
import java.util.ArrayList;

public class Mundo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Entidad> listaEntidades;

	public Mundo() {
		this.listaEntidades = new ArrayList<>();
	}

	public void agregarEntidad(Entidad e) {
		this.listaEntidades.add(e);
	}

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
	
	/** Devuelve el jugador que haya en el mundo
	 * @return	jugador, null si no hay ninguno, el primero encontrado si hay varios
	 */
	public Jugador getJugador() {
		for (Entidad e : listaEntidades) {
			if (e instanceof Jugador) {
				return (Jugador) e;
			}
		}
		return null;
	}
	
	public void dibujar() {
		for (Entidad e : listaEntidades) {
			e.dibujar();
		}
	}
	
}