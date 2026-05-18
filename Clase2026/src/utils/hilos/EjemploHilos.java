package utils.hilos;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class EjemploHilos {
	private static ArrayList<Bola> listaBolas;
	private static VentanaGrafica vent;
	
	// TAREA 1 - Monitor  -  corregir el error de ConcurrentModificationException
	//  (hacer click un montón de veces sin la sincronización para que se produzca)
	private static final Object lock = new Object();
	
	public static void main(String[] args) {
		// Crea una bola y la dibuja rebotando
		vent = new VentanaGrafica( 800, 600, "Prueba hilos" );
		listaBolas = new ArrayList<>();
		Bola b = new Bola( 200, 100, 50, Color.GREEN, 50, 20 );
		b.setVel( 350, -140 );
		listaBolas.add( b );
		
		// TAREA 1: Añadir bola nueva con cada click de ratón
		//  con radio y velocidad aleatorios
		//  esperaAClick() de la clase VentanaGrafica
		
		// Hilos en java
		// Manera 1: se crea un objeto de la clase Thread
		//   al que se pasa un Runnable
		// Manera 2: se crea una clase hija de Thread
		//   con un método run redefinido
		Thread hilo = new Thread() {  // Crea una clase hija y construye un objeto de ella
			@Override
			public void run() {
				while (!vent.estaCerrada()) {
					Point click = vent.esperaAClick();
					System.out.println( "Entrada hilo" );
					if (click!=null) {
						Color colorRandom = new Color( (float) Math.random(), (float) Math.random(),  (float) Math.random() );
						double velH = Math.random() * 800 - 400;  // -400 a +400
						double velV = Math.random() * 800 - 400;
						double radio = Math.random() * 40 + 10;
						Bola b2 = new Bola( click.x, click.y, radio, colorRandom, velH, velV );
						synchronized (lock) {
							listaBolas.add( b2 );
						}
					}
				}
				System.out.println( "Final hilo" );
			}
		};
		hilo.start(); // MAGIA!

		// TAREA 2: ¿Cómo añadir gravedad sin cambiar el método mover? (desde "fuera" de la bola) 
		//      suponiendo que el ciclo de refresco de la física es muy rápido (5 milisegundos)? 
		// Gravedad terrestre o lunar (probar la diferencia)
		// Hilo de física: gravedad - supongamos 100 píxeles = 1 metro

		// TAREA 3: Rozamiento (supongamos que queremos simular con alta precisión el rozamiento,
		// disminuyendo la velocidad ortogonal (hor. y vert.) el 0,1% cada milisegundo
		// Hilo de física: rozamiento
		
		dibujado();
	}
	
	private static void dibujado() {
		vent.setDibujadoInmediato( false );
		while (!vent.estaCerrada()) {
			vent.borra();
			// Dibujado, movimiento y rebotado
			synchronized (lock) {
				for (Bola b : listaBolas) {
					b.dibujar(vent);
					b.mover(vent,10);
				}
			}
			vent.repaint();
			vent.espera(10);
		}
	}
}

class Bola {
	private double x;
	private double y;
	private double radio;
	private Color color;
	private double velX;
	private double velY;
	
	public Bola(double x, double y, double radio, Color color, double velX, double velY) {
		super();
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.color = color;
		this.velX = velX;
		this.velY = velY;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	/** Cambia la velocidad de la bola
	 * @param velX	Velocidad horizontal en píxeles por segundo
	 * @param velY	Velocidad vertical en píxeles por segundo
	 */
	public void setVel(double velX, double velY) {
		setVelX(velX);
		setVelY(velY);
	}

	@Override
	public String toString() {
		return "Bola [x=" + x + ", y=" + y + ", radio=" + radio + ", color=" + color + "]";
	}
	
	public void dibujar( VentanaGrafica v ) {
		v.dibujaCirculo( x, y, radio, 1.0f, color, color );
	}
	
	/** Mueve la bola de acuerdo al tiempo transcurrido, corrigiendo su posición si se sale y rebotando en bordes si procede
	 * @param v	Ventana de marco de límites de movimiento de la bola
	 * @param milis	Milisegundos transcurridos para el movimiento
	 */
	public void mover( VentanaGrafica v, int milis ) {
		x += velX * milis / 1000.0;
		y += velY * milis / 1000.0;
		// Rebotado en x:
		if (x-radio < 0) {
			velX = Math.abs(velX);
		} else if (x+radio > v.getAnchura()) {
			velX = -Math.abs(velX);
		}
		// Rebotado en y:
		if (y-radio < 0) {
			velY = Math.abs(velY);
		} else if (y+radio > v.getAltura()) {
			velY = -Math.abs(velY);
		}
		// Corrección de x (no puede nunca "salir")
		if (x-radio < 0) {
			x = radio;
		} else if (x+radio > v.getAnchura()) {
			x = v.getAnchura()-radio;
		}
		// Corrección de y (no puede nunca "salir")
		if (y-radio < 0) {
			y = radio;
		} else if (y+radio > v.getAltura()) {
			y = v.getAltura()-radio;
		}
	}
	
}