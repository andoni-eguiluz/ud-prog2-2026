package tema1b.ejemplos.xsoldier;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Juego de ejemplo sencillo estilo space invaders
 */
public class Juego {

	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.init();
		juego.loop();
		juego.fin();
	}
	
	private VentanaGrafica v;  // Ventana de juego
	private ArrayList<Personaje> listaPersonajes = new ArrayList<Personaje>();   // Lista de personajes que aparecen en el juego (enemigos y nubes)

	public void init() {
		v = new VentanaGrafica(800, 600, "Xsoldier");
		v.setColorFondo( Color.BLACK );
		for (int i = 0; i < 5; i++) {
			listaPersonajes.add(new Enemigo(50+i*80, 50, 1, 0, 10, "enemy1.png", 1));
			listaPersonajes.add(new Enemigo(50+i*80, 100, -2, 0, 20, "enemy2.png", 2));
		}
		for (int i=0; i<5; i++) {
			listaPersonajes.add( new Nube() );
		}
		v.setDibujadoInmediato(false);
	}
	
	public void fin() {
		v.acaba();
		System.out.println( "Fin de juego" );
	}
	
	public void loop() {
		Jugador jugador = new Jugador(v.getAnchura()/2, v.getAltura() - 50, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP);
		ArrayList<Disparo> disparosBorrar = new ArrayList<Disparo>();
		ArrayList<Enemigo> enemigosBorrar = new ArrayList<Enemigo>();
		boolean salir = false;
		while(!salir) {
			// Atender eventos
			if (v.isTeclaPulsada(jugador.getTeclaDerecha())) {
				jugador.setVx(1);
			}
			if (v.isTeclaPulsada(jugador.getTeclaIzquierda())) {
				jugador.setVx(-1);
			}
			if (v.isTeclaPulsada(jugador.getTeclaDisparo())) {
				if (jugador.getPotencia() >= jugador.getDisparos().size()) {
					jugador.getDisparos().add(new Disparo(jugador.getX(), jugador.getY()));
					v.espera(30);
				}
			}
			// Modificar el estado del juego
			for (Personaje pers : listaPersonajes) {
				pers.mover(v);
				if (pers.chocando(jugador)) {
					salir = true;
					break;
				}
			}
			jugador.mover(v);
			for (Disparo disparo : jugador.getDisparos()) {
				disparo.mover(v);
				for (Personaje pers : listaPersonajes) {
					if (pers instanceof Enemigo && disparo.chocando(pers)) {
						Enemigo enemigo = (Enemigo) pers;
						disparosBorrar.add(disparo);
						enemigo.setVida(enemigo.getVida()-1);
						if (enemigo.getVida() == 0) {
							enemigosBorrar.add(enemigo);
							jugador.setPuntos(jugador.getPuntos() + 100);
						}
					}
				}
				if (disparo.getY() < 0) {
					disparosBorrar.add(disparo);
				}
			}
			for (Disparo disparo : disparosBorrar) {
				jugador.getDisparos().remove(disparo);
			}
			disparosBorrar.clear();
			for (Enemigo enemigo : enemigosBorrar) {
				listaPersonajes.remove(enemigo);
			}
			enemigosBorrar.clear();
			// Dibujar la escena
			v.borra();
			for (Personaje pers : listaPersonajes) {
				pers.dibujar(v);
			}
			for (Disparo disparo : jugador.getDisparos()) {
				disparo.dibujar(v);
			}
			jugador.dibujar(v);
			v.repaint();
			v.espera(10);
		}
		
	}

}
