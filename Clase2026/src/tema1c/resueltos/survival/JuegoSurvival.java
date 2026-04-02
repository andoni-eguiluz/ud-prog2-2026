package tema1c.resueltos.survival;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class JuegoSurvival {
	public static final int ANCHURA = 800;
	public static final int ALTURA = 600;	
	public static final long REFRESCO_MS = 40; // 40 milisegundos entre fotogramas
	public static final Font FONT_FINAL = new Font( "Arial", Font.BOLD, 30 );
	// private static Random random = new Random();
	
	private VentanaGrafica vent;
	private ArrayList<ObjetoJuego> listaObjetos;
	private Mago mago;
	private Clickable objSeleccionado = null;
	private int contFotogramas = 0;
	private int cicloDisparos = 35;
	private boolean finJuego = false;
	
	public JuegoSurvival() {
		vent = new VentanaGrafica( ANCHURA, ALTURA, "Juego de supervivencia" );
		listaObjetos = new ArrayList<>();
	}
	
	public void init() {
		// Añade disparos
		// listaObjetos.add( new Disparo( random.nextInt(vent.getAnchura()), random.nextInt(vent.getAltura()), Color.RED, Color.LIGHT_GRAY, 5 ) );
		// listaObjetos.add( new Disparo( random.nextInt(vent.getAnchura()), random.nextInt(vent.getAltura()), Color.BLUE, Color.YELLOW, 7 ) );
		// Quitado para cambiar la dinámica a que se generen en función del tiempo
//		for (int i=0; i<15; i++) {
//			Disparo nuevo = null;
//			do {
//				nuevo = Disparo.crearDisparoCorrecto(vent);
//			} while (nuevo==null);
//			listaObjetos.add( nuevo );
//		}
		// Añade bloques
		ArrayList<Bloque> listaB = new ArrayList<Bloque>();
		for (int i=0; i<5; i++) {
			Bloque nuevo = null;
			do {
				nuevo = Bloque.crearBloqueCorrecto(vent,listaB);
			} while (nuevo==null);
			listaB.add( nuevo );
		}
		listaObjetos.addAll( listaB );
		// Añade un mago en el centro
		mago = new Mago( vent.getAnchura()/2, vent.getAltura()/2, 80, 80 );
		listaObjetos.add( mago );
		// Saca objetos a consola
		for (ObjetoJuego oj : listaObjetos) {
			System.out.println( oj );
		}
	}
	
	public void jugar() {
		vent.setDibujadoInmediato( false );
		long tiempo = System.currentTimeMillis();
		while (!vent.estaCerrada() && !finJuego) {
			generacionDeDisparos();
			seleccionYTransporte();
			movimiento();
			rotacion();
			calculoChoques();
			sanacionMago();
			dibujado();
			finDeJuego();
			contFotogramas++;
		}
		tiempo = System.currentTimeMillis() - tiempo;
		vent.dibujaTextoCentrado( 100, 100, 600, 400, "Juego finalizado: has durado hoy " + tiempo + " milisegs.", FONT_FINAL, Color.RED );
		vent.repaint();
	}
	
	private void generacionDeDisparos() {
		if (contFotogramas % cicloDisparos == 0) {
			Disparo nuevo = null;
			do {
				nuevo = Disparo.crearDisparoCorrecto(vent);
			} while (nuevo==null);
			listaObjetos.add( nuevo );
			if (cicloDisparos > 5) {
				cicloDisparos--;
			}
		}
	}
	
	private void seleccionYTransporte() {
		Point click = vent.getRatonClicado();
		if (click!=null) {
			boolean clickEnElemento = false;
			for (ObjetoJuego oj : listaObjetos) {
				if (oj instanceof Clickable && oj!=objSeleccionado) {
					Clickable c = (Clickable) oj;
					if (c.puntoDentro( click.x, click.y )) {
						clickEnElemento = true;
						if (objSeleccionado==null) {  // No había objeto seleccionado - seleccionarlo
							objSeleccionado = c;
							c.setSeleccionado( true );
						}
						break;
					}
				}
			}
			if (!clickEnElemento && objSeleccionado!=null) {  // Ya había objeto seleccionado - moverlo salvo que colisione con otro
				ObjetoJuego ojs = (ObjetoJuego) objSeleccionado;
				double xAnt = ojs.getX();
				double yAnt = ojs.getY();
				ojs.moverA( click.x, click.y );
				// Comprobar que no haya colisión
				for (ObjetoJuego oj2 : listaObjetos) {
					if (oj2!=ojs && oj2 instanceof Chocable) {  // Salvo consigo mismo
						Chocable choc = (Chocable) oj2;
						if (choc.chocaCon( (Chocable)ojs )) {
							ojs.moverA( xAnt, yAnt );  // Deshacer movimiento
							break;
						}
					}
				}
				objSeleccionado.setSeleccionado( false );
				objSeleccionado = null;
			}
		}
	}

	private void movimiento() {
		for (ObjetoJuego oj : listaObjetos) {
			if (oj instanceof Movible) {
				((Movible) oj).mover( REFRESCO_MS );
			}
		}
	}

	private void rotacion() {
		for (ObjetoJuego oj : listaObjetos) {
			if (oj instanceof Rotable) {
				((Rotable) oj).rotar( REFRESCO_MS );
			}
		}
	}

	// Choque y cambio de energía. Recorrer las balas y mirar choques con el resto de elementos chocables
	private void calculoChoques() {
		// Cálculo de choques de disparos con resto
		for (ObjetoJuego oj : listaObjetos) {
			if (oj instanceof Disparo) {
				Disparo disparo = (Disparo) oj;
				for (ObjetoJuego oj2 : listaObjetos) {
					if (!(oj2 instanceof Disparo) && oj2 instanceof ConEnergia) {
						ConEnergia objeto2 = (ConEnergia) oj2;
						if (disparo.chocaCon(objeto2)) {
							int energiaChoque = Math.min( disparo.getEnergia(), objeto2.getEnergia() );
							disparo.incEnergia( -energiaChoque );
							objeto2.incEnergia( -energiaChoque );
							break;
						}
					}
				}
			}
		}
		// Eliminar todos los objetos destruidos
		// for (Objeto oj : listaObjetos) {  // Como vamos a modificar la lista no se puede hacer for each
		for (int i=listaObjetos.size()-1; i>=0; i--) {
			ObjetoJuego oj = listaObjetos.get(i);
			if (oj instanceof ConEnergia) {
				ConEnergia objeto = (ConEnergia) oj;
				if (objeto.estaDestruido()) {
					listaObjetos.remove( i );
				}
			}
		}
	}

	private void sanacionMago() {
		if (mago.cicloSanacion()) {
			for (ObjetoJuego oj : listaObjetos) {
				if (oj instanceof Bloque) {
					Bloque b = (Bloque) oj;
					if (b.distanciaA(mago) <= 125) {
						b.incEnergia( +1 );
					}
				}
			}
		}
	}
	
	private void dibujado() {
		vent.borra();
		for (ObjetoJuego oj : listaObjetos) {
			oj.dibujar( vent );
		}
		vent.repaint();
		vent.espera( REFRESCO_MS );
	}
	
	private void finDeJuego() {
		for (ObjetoJuego oj : listaObjetos) {
			if (oj instanceof Mago) {
				return;
			}
		}
		// Ha muerto el mago
		finJuego = true;
	}
}
