package tema1c.ejercicios.acertijo;

import java.util.ArrayList;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear barcas, rectángulos móviles que contienen objetos (dos máximo)
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Barca extends Rectangulo implements Draggable {
	
	//================= Parte static
	
	/** Tamaño en anchura de la orilla */
	public static final int ANCHURA_BARCA = 300;
	/** Tamaño en altura de la orilla */
	public static final int ALTURA_BARCA = 200;
	/** Imagen de barca */
	public static final String IMAGEN_BARCA = "img/barca.png";
	
	//================= Parte no static

	private ArrayList<ObjetoAcertijo> objetosContenidos = null;
	
	/** Crea una nueva barca vacía (sin objetos dentro)
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param ventana	Ventana del botón
	 */
	public Barca(int x, int y, VentanaGrafica ventana) {
		super(x, y, ventana, ANCHURA_BARCA, ALTURA_BARCA);
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
		if (objetosContenidos.size()>=2) return false;  // La barca solo permite 2 objetos máximo
		objetosContenidos.add( objeto );
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
		// super.dibujar();
		ventana.dibujaImagen( IMAGEN_BARCA, x+anchura/2, y+altura/2, anchura, altura, 1.0, 0.0, 1.0f );
		for (ObjetoAcertijo o : objetosContenidos) {
			o.dibujar();
		}
	}

	@Override
	public void mover(int x, int y) {
		int difX = x - this.x;
		int difY = y - this.y;
		setX( x );
		setY( y );
		for (ObjetoAcertijo o : objetosContenidos) {
			if (o instanceof Draggable) {
				Draggable d = (Draggable) o;
				d.mover( d.getPunto().x+difX, d.getPunto().y+difY );
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Barca)) return false;
		Barca o2 = (Barca) obj;
		return super.equals(obj) && objetosContenidos.equals(o2.objetosContenidos);
	}
	
	@Override
	public String toString() {
		return "Orilla " + super.toString() + " contiene a " + objetosContenidos;
	}

}