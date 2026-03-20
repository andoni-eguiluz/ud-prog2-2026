package tema1c.ejercicios.acertijo;

import java.awt.Color;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Objeto genérico de acertijo con base de dibujado en forma de rectángulo
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public abstract class Rectangulo extends ObjetoAcertijo {

	//================= Parte static
	
	/** Color del rectángulo */
	public static final Color COLOR_RECTANGULO = Color.BLUE;
	public static final float GROSOR_RECTANGULO = 2.0f;
	
	//================= Parte no static
	
	int anchura;  // Anchura en píxels
	int altura;	  // Altura en píxels
	
	/** Crea un nuevo rectángulo
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param ventana	Ventana del objeto
	 * @param anchura	Anchura del rectángulo (en píxels)
	 * @param altura	Altura del rectángulo (en píxels)
	 */
	public Rectangulo(int x, int y, VentanaGrafica ventana, int anchura, int altura ) {
		super(x, y, ventana);
		this.anchura = anchura;
		this.altura = altura;
	}
	
	public int getAnchura() {
		return anchura;
	}

	public void setAnchura(int anchura) {
		this.anchura = anchura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/** Informa si el rectángulo colisiona con otro rectángulo
	 * @param r	Rectángulo de comprobación
	 * @return	true si ambos rectángulos tocan, false en caso contrario
	 */
	public boolean colisionaCon( Rectangulo r ) {
		if (x+anchura < r.x) return false;  // Está a la izquierda
		else if (x > r.x+r.anchura) return false;  // Está a la derecha
		else if (y+altura < r.y) return false;  // Está arriba
		else if (y > r.y+r.altura) return false;  // Está abajo
		return true;  // Toca
	}

	@Override
	public void dibujar() {
		ventana.dibujaRect( x, y, anchura, altura, GROSOR_RECTANGULO, COLOR_RECTANGULO );
	}

	@Override
	public boolean contienePunto(int x, int y) {
		return x>=this.x && x<=(this.x+anchura) && y>=this.y && y<=(this.y+altura);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Rectangulo)) return false;
		Rectangulo r2 = (Rectangulo) obj;
		return super.equals(obj) && anchura==r2.anchura && altura==r2.altura;
	}
	
	@Override
	public String toString() {
		return super.toString() + " [" + anchura + "x" + altura + "]";
	}
	
}