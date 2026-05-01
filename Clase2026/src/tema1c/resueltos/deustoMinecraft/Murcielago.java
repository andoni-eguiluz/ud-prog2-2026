package tema1c.resueltos.deustoMinecraft;

import java.io.Serializable;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Murcielago extends Mob implements Serializable {
	private static final long serialVersionUID = 1L;

	public Murcielago(String nombre, int x, int y, boolean hostil, double puntosVida, VentanaGrafica v ) {
		super(nombre, x, y, hostil, puntosVida);
		setVentana( v );
	}

	// Clase Murcielago: lo mismo pero dos pasos
	
	@Override
	public void mover( Jugador j ) {
		int difHorizontal = j.getX() - getX();
		int difVertical = j.getY() - getY();
		if (difVertical<0) {
			this.abajo();
			this.abajo();
		} else if (difVertical>0) {
			this.arriba();
			this.arriba();
		}
		if (difHorizontal<0) {
			this.izquierda();
			this.izquierda();
		} else if (difHorizontal>0) {
			this.derecha();
			this.derecha();
		}
	}

	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		int dibujadoXMob = 125;
		getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, getRadio()*2, getRadio()*2, 1.0, 0.0, 1.0f, dibujadoXMob, dibujadoXMob+124, 125, 249 );
	}
	
}
