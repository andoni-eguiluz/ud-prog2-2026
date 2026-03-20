package tema1c.ejemplos;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class PruebaFiguritas {
	
	static VentanaGrafica v;
	
	public static void main(String[] args) {
		v = new VentanaGrafica( 600, 400, "Prueba" );
		Circulo c1 = new Circulo( 50, Color.BLUE, 200, 100 );
		c1.dibujar(v);
		Rectangulo r1 = new Rectangulo( 0, 0, 200, 100, Color.GREEN, 2.5f );
		r1.dibujar(v);
		ArrayList<Figura> lF = new ArrayList<>();
		lF.add( c1 );
		lF.add( r1 );
		lF.add( new Flecha( 100, 100, 200, 200) );
		lF.add( new Imagen( 200, 200, "sonic.png", 1.00) );
		lF.add( new ImagenEscalada( 300, 300, "dama-blanca.png", 150, 150 ) );
		Imagen i = ImagenEscalada.crear( 450, 170, "bubble.png", 640, 627, v );
		i.setZoom(0.25);
		lF.add( i );
		System.out.println( lF ); // Polimorfismo de código
		Flecha f2 = new Flecha( 100, 100, 200, 200);
		f2.rotar( Math.PI/8 );
		f2.setColor( Color.MAGENTA );
		lF.add( f2 );
		lF.add( new Hexagono( 50, 300, Color.MAGENTA, 60 ) );
		for (Figura f : lF) {
			f.dibujar(v);  // Polimorfismo de código
		}
		// Bucle de rotación
		v.setDibujadoInmediato( false ); // Para dibujado sin parpadeo
		// Espera a un click haciendo solo rotación
		v.setMensaje( "Animación solo de rotación");
		while (v.getRatonClicado() == null) {
			// Actualizamos figuras
			for (Figura f : lF) {
				if (f instanceof Rotable) {
					((Rotable)f).rotar( 0.1 );
				}
			}
			// Borramos ventana
			v.borra();
			// Dibujamos todo de nuevo
			for (Figura f : lF) {
				f.dibujar(v);
			}
			v.repaint(); // Dibujado sin flickering (se dibuja todo de golpe)
			v.espera( 40 );
		}
		
		// Bucle ahora con coloreado
		int colorFondo = 0;
		int cambioDeColor = +5;
		for (Figura f: lF) {
			if (f instanceof Coloreable) {
				((Coloreable) f).setColorFondo( new Color(colorFondo,255,colorFondo) );
			}
		}
		
		v.setMensaje( "Animación de rotación y coloreado");
		while (v.getRatonClicado() == null) {
			// Actualización de coloreado
			colorFondo += cambioDeColor;
			if (colorFondo > 255) { // Inversión de animación
				colorFondo = 255;
				cambioDeColor = -cambioDeColor;
			} else if (colorFondo < 0) {
				colorFondo = 0;
				cambioDeColor = -cambioDeColor;
			}
			// Actualizamos figuras (rotación, coloreado)
			for (Figura f : lF) {
				if (f instanceof Rotable) {
					((Rotable)f).rotar( 0.1 );
				}
				if (f instanceof Coloreable) {
					((Coloreable) f).setColorFondo( new Color(colorFondo,255,colorFondo) );
				}
			}
			// Borramos ventana
			v.borra();
			// Dibujamos todo de nuevo
			for (Figura f : lF) {
				f.dibujar(v);
			}
			v.repaint(); // Dibujado sin flickering (se dibuja todo de golpe)
			v.espera( 40 );
		}
		
		v.setMensaje( "Animación de rotación, coloreado y movimiento");
		for (Figura f : lF) {
			if (f instanceof Circulo) {  // Hace falta inicializar la ventana de los círculos para que reboten
				((Circulo) f).setVentana( v );
			}
		}
		while (v.getRatonClicado() == null) {
			// Actualización de coloreado
			colorFondo += cambioDeColor;
			if (colorFondo > 255) { // Inversión de animación
				colorFondo = 255;
				cambioDeColor = -cambioDeColor;
			} else if (colorFondo < 0) {
				colorFondo = 0;
				cambioDeColor = -cambioDeColor;
			}
			// Actualizamos figuras (rotación, coloreado)
			for (Figura f : lF) {
				if (f instanceof Rotable) {
					((Rotable)f).rotar( 0.1 );
				}
				if (f instanceof Movible) {
					((Movible)f).mover( 40 );
				}
				if (f instanceof Coloreable) {
					((Coloreable) f).setColorFondo( new Color(colorFondo,255,colorFondo) );
				}
			}
			// Borramos ventana
			v.borra();
			// Dibujamos todo de nuevo
			for (Figura f : lF) {
				f.dibujar(v);
			}
			v.repaint(); // Dibujado sin flickering (se dibuja todo de golpe)
			v.espera( 40 );
		}
		
		v.setMensaje( "Animación e interacción con el usuario");
		while (!v.estaCerrada()) {
			// Chequeo de interacción
			Point click = v.getRatonClicado();
			if (click !=null) {
				for (Figura f : lF) {
					if (f instanceof Clicable) {
						Clicable cl = (Clicable) f;
						if (cl.contieneCoordenada( click.x, click.y) ) {
							cl.reaccionaAClick();
							break;  // Suponemos que solo se puede actuar sobre un objeto (el primero en el que está contenido el click)
						}
					}
				}
			}
			// Actualización de coloreado
			colorFondo += cambioDeColor;
			if (colorFondo > 255) { // Inversión de animación
				colorFondo = 255;
				cambioDeColor = -cambioDeColor;
			} else if (colorFondo < 0) {
				colorFondo = 0;
				cambioDeColor = -cambioDeColor;
			}
			// Actualizamos figuras (rotación, coloreado)
			for (Figura f : lF) {
				if (f instanceof Rotable) {
					((Rotable)f).rotar( 0.1 );
				}
				if (f instanceof Movible) {
					((Movible)f).mover( 40 );
				}
				if (f instanceof Coloreable) {
					((Coloreable) f).setColorFondo( new Color(colorFondo,255,colorFondo) );
				}
			}
			// Borramos ventana
			v.borra();
			// Dibujamos todo de nuevo
			for (Figura f : lF) {
				f.dibujar(v);
			}
			v.repaint(); // Dibujado sin flickering (se dibuja todo de golpe)
			v.espera( 40 );
		}

		
	}
	
}
