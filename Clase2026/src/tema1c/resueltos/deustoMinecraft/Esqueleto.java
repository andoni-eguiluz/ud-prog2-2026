package tema1c.resueltos.deustoMinecraft;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Esqueleto extends Mob {
	private static final long serialVersionUID = 1L;

	public Esqueleto(String nombre, int x, int y, boolean hostil, double puntosVida, VentanaGrafica v ) {
		super(nombre, x, y, hostil, puntosVida);
		setVentana( v );
	}

	// Clase Esqueleto: va hacia el jugador, mirando la distancia a este en las 4 direcciones y avanzando un paso en la que más cerca se encuentre.
	
	@Override
	public void mover( Jugador j ) {
		int difHorizontal = j.getX() - getX();
		int difVertical = j.getY() - getY();
		if (difVertical<0) {
			this.abajo();
		} else if (difVertical>0) {
			this.arriba();
		}
		if (difHorizontal<0) {
			this.izquierda();
		} else if (difHorizontal>0) {
			this.derecha();
		}
	}

	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		int dibujadoXMob = 250;
		getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, getRadio()*2, getRadio()*2, 1.0, 0.0, 1.0f, dibujadoXMob, dibujadoXMob+124, 125, 249 );
	}
	
}
