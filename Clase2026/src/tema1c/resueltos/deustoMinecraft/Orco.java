package tema1c.resueltos.deustoMinecraft;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Orco extends Mob {
	private static final long serialVersionUID = 1L;

	public Orco(String nombre, int x, int y, boolean hostil, double puntosVida, VentanaGrafica v ) {
		super(nombre, x, y, hostil, puntosVida);
		setVentana( v );
	}

	// Clase Orco: se teleporta hacia la dirección en la que está el jugador, máximo de 5 píxeles en cada dimensión.
	
	@Override
	public void mover( Jugador j ) {
		int difHorizontal = j.getX() - getX();
		int difVertical = j.getY() - getY();
		if (difVertical<0) {
			int dist = Math.min( 5, -difVertical );
			for (int i=0; i<dist; i++) { this.abajo(); }
		} else if (difVertical>0) {
			int dist = Math.min( 5, difVertical );
			for (int i=0; i<dist; i++) { this.arriba(); }
		}
		if (difHorizontal<0) {
			int dist = Math.min( 5, -difHorizontal );
			for (int i=0; i<dist; i++) { this.izquierda(); }
		} else if (difHorizontal>0) {
			int dist = Math.min( 5, difHorizontal );
			for (int i=0; i<dist; i++) { this.derecha(); }
		}
	}

	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		int dibujadoXMob = 375;
		getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, getRadio()*2, getRadio()*2, 1.0, 0.0, 1.0f, dibujadoXMob, dibujadoXMob+124, 125, 249 );
	}

	// Comportamiento particular del orco

	@Override
	public void actuaConChoque(Entidad e) {
		if (e instanceof Piedra) {
			puntosVida -= 10;
		}
	}

}
