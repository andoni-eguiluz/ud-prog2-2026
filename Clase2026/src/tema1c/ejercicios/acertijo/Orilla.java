package tema1c.ejercicios.acertijo;

import java.util.ArrayList;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear orillas, rectángulos fijos que contienen objetos (cualquier número)
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Orilla extends Rectangulo {
	
	//================= Parte static
	
	/** Tamaño en anchura de la orilla */
	public static final int ANCHURA_ORILLA = 200;
	/** Tamaño en altura de la orilla */
	public static final int ALTURA_ORILLA = 600;
	
	//================= Parte no static

	private ArrayList<ObjetoAcertijo> objetosContenidos = null;
	
	/** Crea una nueva orilla vacía (sin objetos dentro)
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param ventana	Ventana del botón
	 */
	public Orilla(int x, int y, VentanaGrafica ventana) {
		super(x, y, ventana, ANCHURA_ORILLA, ALTURA_ORILLA);
		objetosContenidos = new ArrayList<>();
	}
	
	/** Devuelve la lista de objetos contenidos en la barca
	 * @return	Lista de objetos contenidos en la barca
	 */
	public ArrayList<ObjetoAcertijo> getObjetosContenidos() {
		return objetosContenidos;
	}
	
	/** Método que, si es posible, añade un objeto a la lista de objetos contenidos
	 * @param objeto	Objeto a añadir
	 * @return	false si no se puede añadir (y no se añade); true si se puede añadir (y se añade)
	 */
	public boolean anyadeObjeto( ObjetoAcertijo objeto ) {
		objetosContenidos.add( objeto );  // La orilla admite cualquier número de objetos
		return true;
	}	
	
	/** Método que, si es posible, quita un objeto de la lista de objetos contenidos
	 * @param objeto	Objeto a quitar
	 * @return	true si se ha podido quitar, false si no estaba
	 */
	public boolean quitaObjeto( ObjetoAcertijo objeto ) {
		if (!objetosContenidos.contains( objeto )) return false;
		objetosContenidos.remove( objeto );
		return true;
	}

	@Override
	public void dibujar() {
		super.dibujar();
		for (ObjetoAcertijo o : objetosContenidos) {
			o.dibujar();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Orilla)) return false;
		Orilla o2 = (Orilla) obj;
		return super.equals(obj) && objetosContenidos.equals(o2.objetosContenidos);
	}
	
	@Override
	public String toString() {
		return "Orilla " + super.toString() + " contiene a " + objetosContenidos;
	}
	
}