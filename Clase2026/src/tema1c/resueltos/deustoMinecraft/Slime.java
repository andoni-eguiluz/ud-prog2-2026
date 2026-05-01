package tema1c.resueltos.deustoMinecraft;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Slime extends Mob {
	private static final long serialVersionUID = 1L;

	public Slime(String nombre, int x, int y, boolean hostil, double puntosVida, VentanaGrafica v ) {
		super(nombre, x, y, hostil, puntosVida);
		setVentana( v );
	}

	// Clase Slime: se crea con una dirección diagonal aleatoria (+1 / -1 en horizontal, +1 / -1 en vertical) y avanza un paso horizontal y otro vertical en la dirección en la que está orientado. Al llegar a los bordes de la ventana, rebota.

	private int hor = Math.random()<0.5 ? +1 : -1;
	private int ver = Math.random()<0.5 ? +1 : -1;
	
	@Override
	public void mover( Jugador j ) {
		if (hor>0) {
			derecha();
		} else {
			izquierda();
		}
		if (ver>0) {
			arriba();
		} else {
			abajo();
		}
		if (x < getRadio()) {  // Rebote izquierda
			hor = +1;
		} else if (x > getVentana().getAnchura() - getRadio()) {  // Rebote arriba
			hor = -1;
		}
		if (y < getRadio()) {  // Rebote arriba
			ver = +1;
		} else if (y > getVentana().getAltura() - getRadio()) {  // Rebote abajo
			ver = -1;
		}
	}

	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		int dibujadoXMob = 0;
		getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, getRadio()*2, getRadio()*2, 1.0, 0.0, 1.0f, dibujadoXMob, dibujadoXMob+124, 125, 249 );
	}
	
}
