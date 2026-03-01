package utils.ventanas.ventanaBitmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;

public class PruebaVentanaGrafica {

	// ====================================================
	//   Parte estática - pruebas de funcionamiento
	// ====================================================

	private static VentanaGrafica v;
	/** Método main de prueba de la clase
	 * @param args	No utilizado 
	 */
	public static void main(String[] args) {
		v = new VentanaGrafica( 800, 600, "Test Ventana Gráfica" );
		v.anyadeBoton( "Pon/quita dibujado inmediato", new ActionListener() {  // Para ver cómo se ve con flickering si se dibujan cosas una a una
			@Override
			public void actionPerformed(ActionEvent e) {
				v.setDibujadoInmediato( !v.isDibujadoInmediato() );
				v.setMensaje( "Dibujado inmediato está " + (v.isDibujadoInmediato() ? "ACTIVADO" : "DESACTIVADO") );
			}
		});
		v.setDibujadoInmediato( false );
		Object opcion = JOptionPane.showInputDialog( v.getJFrame(), "¿Qué quieres probar?",
				"Selección de test", JOptionPane.QUESTION_MESSAGE, null, 
				new String[] { "Movimiento", "Giros", "Tiro", "Texto", "Zoom", "Desplazamiento", "Petición texto" }, "Movimiento" );
		if ( "Movimiento".equals( opcion ) ) {
			movimiento();
		} else if ( "Giros".equals( opcion ) ) {
			giros();
		} else if ( "Tiro".equals( opcion ) ) {
			tiro();
		} else if ( "Texto".equals( opcion ) ) {
			texto();
		} else if ( "Zoom".equals( opcion ) ) {
			zoom();
		} else if ( "Desplazamiento".equals( opcion ) ) {
			desplazamiento();
		} else if ( "Petición texto".equals( opcion ) ) {
			peticionTexto();
		}
	}

	// Prueba 1: escudo verde que se mueve y sube y baja
	private static void movimiento() {
		int altura = v.getAltura();
		boolean subiendo = true;
		for (int i=0; i<=800; i++) {
			v.borra();
			v.dibujaTexto( i+100, 100+(i/4), "texto móvil", new Font( "Arial", Font.PLAIN, 16 ), Color.black );
			v.dibujaImagen( "img/UD-green.png", i, altura, 1.0, 0.0, 1.0f );
			if (subiendo) {
				altura--;
				if (altura<=0) subiendo = false;
			} else {
				altura++;
				if (altura>=v.getAltura()) subiendo = true;
			}
			v.repaint();
			v.espera( 10 );
		}
		v.espera( 5000 );
		v.acaba();
	}
	
	// Prueba 2: escudos y formas girando y zoomeando
	private static void giros() {
		for (int i=0; i<=1000; i++) {
			v.borra();
			v.dibujaImagen( "img/UD-green.png", 100, 100, 0.5+i/200.0, Math.PI / 100 * i, 0.9f );
			v.dibujaImagen( "img/UD-magenta.png", 500, 100, 100, 50, 1.2, Math.PI / 100 * i, 0.1f );
			v.dibujaRect( 20, 20, 160, 160, 0.5f, Color.red );
			v.dibujaRect( 0, 0, 100, 100, 1.5f, Color.blue );
			v.dibujaCirculo( 500, 100, 50, 1.5f, Color.orange );
			v.dibujaPoligono( 2.3f, Color.magenta, true, 
				new Point(200,250), new Point(300,320), new Point(400,220) );
			v.repaint();
			v.espera( 10 );
		}
		v.espera( 5000 );
		v.acaba();
	}

	// Prueba 3: tiro parabólico
	private static void tiro() {
		boolean seguir = true;
		v.setMensaje( "Click ratón para disparar (con fuerza y ángulo)");
		double xLanz = 20;
		double yLanz = v.getAltura()-20;
		while (seguir) {
			Point pMovto = v.getRatonMovido();
			Point pPuls = v.getRatonPulsado();
			v.borra();
			v.dibujaCirculo( xLanz, yLanz, 10, 3.0f, Color.MAGENTA );
			if (pPuls!=null) {  // Se hace click: disparar!
				disparar( xLanz, yLanz, pPuls.getX(), pPuls.getY() );
			} else if (pMovto!=null) {  // No se hace click: dibujar flecha
				v.dibujaFlecha( xLanz, yLanz, pMovto.getX(), pMovto.getY(), 2.0f, Color.GREEN, 25 );
			}
			v.repaint();
			if (v.estaCerrada()) seguir = false;  // Acaba cuando el usuario cierra la ventana
			v.espera( 20 ); // Pausa 20 msg (aprox 50 frames/sg)
		}
	}
		// Hace un disparo con la velocidad marcada por el vector con gravedad
		private static void disparar( double x1, double y1, double x2, double y2 ) {
			double velX = x2-x1; double velY = y2-y1;
			double G = 9.8; // Aceleración de la gravedad
			dispararVA( x1, y1, velX, velY, G );
		}
		// Hace un disparo con la velocidad y ángulo indicados
		private static void dispararVA( double x1, double y1, double velX, double velY, double acel ) {
			v.setMensaje( "Calculando disparo con velocidad (" + velX + "," + velY + ")" );
			double x = x1; double y = y1;  // Punto de disparo
			int pausa = 10; // msg de pausa de animación
			double tempo = pausa / 1000.0; // tiempo entre frames de animación (en segundos)
			do {
				v.dibujaCirculo( x, y, 1.0, 1.0f, Color.blue );  // Dibuja punto
				x = x + velX * tempo; // Mueve x (según la velocidad)
				y = y + velY * tempo;// Mueve y (según la velocidad)
				velY = velY + acel * 10 * tempo; // Cambia la velocidad de y (por efecto de la gravedad)
				v.repaint();
				v.espera( pausa ); // Pausa 20 msg (aprox 50 frames/sg)
			} while (y<y1);  // Cuando pasa hacia abajo la vertical se para
			v.espera( 2000 ); // Pausa de 2 segundos
			v.setMensaje( "Vuelve a disparar!" );
		}
	
	// Prueba 4: petición de texto en la ventana
	private static void texto() {
		v.setDibujadoInmediato( true );
		v.dibujaImagen( "img/UD-roller.jpg", 400, 300, 1.0, 0.0, 1.0f );
		Font f = new Font( "Arial", Font.PLAIN, 30 );
		String t1 = v.leeTexto( 100, 100, 200, 50, "Modifica texto", f, Color.magenta );
		v.setMensaje( "Introduce texto" );
		v.dibujaTexto( 100, 200, "Texto introducido: " + t1, f, Color.white );
		v.setMensaje( "Introduce texto otra vez" );
		t1 = v.leeTexto( 100, 300, 200, 50, "", f, Color.blue );
		v.setMensaje( "Textos leídos." );
		v.dibujaTexto( 100, 400, "Texto introducido: " + t1, f, Color.white );
		v.espera( 5000 );
		v.acaba();
	}		
	
	// Prueba 5: zoom y movimiento de referencia de la ventana
	private static void zoom() {
		boolean sigue = true;
		int offset = 0;
		double zoom = 1.0;
		boolean bajando = true;
		while (sigue) {
			dibujoEjemplo( false );
			while (v.isControlPulsado()) v.espera(10);  // Con Ctrl se pausa la animación
			v.espera( 10 );
			if (offset<200) {  // Amplía el offset hasta 200,200
				offset += 4;
				v.setOffsetDibujo( new Point( offset, offset ) );
			} else if (bajando) {
				zoom *= 0.99; // Va bajando el zoom
				v.setEscalaDibujo( zoom );
				if (zoom < 0.5) {  // A partir de 0.5 empieza a subir
					bajando = false; 
				}
			}
			if (!bajando) {
				zoom *= 1.01; // Va subiendo el zoom
				v.setEscalaDibujo( zoom );
				if (zoom > 3.0) {  // Al llegar a 3 se para el programa
					sigue = false;
				}
			}
			v.setMensaje( "Offset " + offset + " - zoom " + zoom );
		}
		while (!v.estaCerrada()) {
			dibujoEjemplo( true );
			Point antMouse = null;
			Point mouse = v.getRatonPulsado();
			Point origen = mouse;
			boolean clickEnOrigen = false;
			if (origen!=null) {
				Point2D.Double puntoClick = v.getPuntoEnEscala( origen );
				if (Math.abs(puntoClick.x)*v.getEscalaDibujo()<5 && Math.abs(puntoClick.y)*v.getEscalaDibujo()<5) {
					clickEnOrigen = true;
				}
			}
			boolean soloClick = true;
			while (mouse!=null) {
				if (clickEnOrigen) {
					int distX = mouse.x-origen.x; // Solo se considera el drag en la x (derecha crecer, izquierda decrecer)
					double incZoom;
					if (distX>=0) {
						incZoom = 1.0 + distX/100.0;  // 100 pixels a la derecha duplica
					} else {
						incZoom = 1.0 + distX/500.0;  // 500 pixels a la derecha minimiza al máximo
					}
					double nuevoZoom = zoom * incZoom;
					if (nuevoZoom < 0.01) nuevoZoom = 0.01;  // Mínimo zoom
					v.setEscalaDibujo( nuevoZoom );
					dibujoEjemplo( true );
					v.setMensaje( "Zoom = " + nuevoZoom );
				} else if (antMouse!=null) {
					v.setOffsetDibujo( mouse.x-antMouse.x, mouse.y-antMouse.y );
					dibujoEjemplo( true );
				}
				v.espera( 10 );
				antMouse = mouse;
				mouse = v.getRatonPulsado();
				if (mouse!=null && !antMouse.equals(mouse)) soloClick = false;
			}
			zoom = v.getEscalaDibujo();
			if (soloClick && origen!=null) {
				String mens = String.format( "  (click en punto anterior (%4.2f,%4.2f)", v.getPuntoEnEscala(origen).x, v.getPuntoEnEscala(origen).y );
				int xOffset = v.getOffsetDibujo().x;
				int yOffset = v.getOffsetDibujo().y;
				v.setEjeYInvertido( !v.getEjeYInvertido() );
				v.setOffsetDibujo( new Point( xOffset, v.getAltura() - yOffset ) );
				v.setMensaje( "Eje Y invertido = " + (v.getEjeYInvertido() ? "SI" : "NO" ) + mens + ", nuevo " + String.format( "(%4.2f,%4.2f)", v.getPuntoEnEscala(origen).x, v.getPuntoEnEscala(origen).y ) + ")");
				dibujoEjemplo( true );
			}
			v.espera( 10 );
		}
		v.espera( 5000 );
		v.acaba();
	}
		private static void dibujoEjemplo( boolean conMensaje ) {
			v.borra();
			v.dibujaEjes( 100, Color.BLACK, 1.0f );
			v.dibujaLinea( -100, 50, 800, 50, 0.5f, Color.CYAN );
			v.dibujaTexto( 100, 50, "texto dibujado", new Font( "Arial", Font.PLAIN, 16 ), Color.black );
			v.dibujaRect( 100, 100, 200, 50, 0.5f, Color.ORANGE );
			v.dibujaTextoCentrado( 100, 100, 200, 50, "texto centrado entre 100 y 300", new Font( "Arial", Font.PLAIN, 16 ), Color.green, true );
			v.dibujaImagen( "img/UD-green.png", 400, 150, 1.0, Math.PI/6.0, 0.4f );
			v.dibujaCirculo( 400, 150, 100.0, 1.0f, Color.BLACK );
			v.dibujaFlecha( 100, 350, 300, 550, 2.0f, Color.MAGENTA, 10 );
			v.dibujaCirculo( 400, 350, 50.0, 2.0f, Color.BLUE, Color.YELLOW );
			v.dibujaPoligono( 3.0f, Color.GREEN, true, new Point( 100, 400 ), new Point( 400, 410 ), new Point( 380, 440 ), new Point( 110, 450 ) );
			v.dibujaRect( 550, 350, 120,  60,  2.0f, Color.MAGENTA, Color.CYAN );
			if (conMensaje) v.dibujaTexto( 10, -20, "Drag para desplazar, drag sobre origen para zoom, Click inversión eje Y", new Font( "Arial", Font.PLAIN, 16 ), Color.RED );
			v.repaint();
		}

	
	// Prueba 6: Desplazamiento de pantalla en mundo virtual mayor que lo que se ve en pantalla
	private static void desplazamiento() {
		v.setDibujadoInmediato( false );
		// Hay un elemento que se dibuja siguiendo al centro de pantalla (empieza 400,300) y otros fijos 
		// El personaje del centro se mueve con cursores y la pantalla se mueve con él
		int xPersonaje = 400;
		int yPersonaje = 300;
		double zoom = 1.0;
		v.setMensaje( "Mueve con cursores. Zoom con +/-" );
		while (!v.estaCerrada()) {
			// Movimiento de personaje
			if (v.isTeclaPulsada(KeyEvent.VK_UP)) {
				yPersonaje -= 5;
			}
			if (v.isTeclaPulsada(KeyEvent.VK_DOWN)) {
				yPersonaje += 5;
			}
			if (v.isTeclaPulsada(KeyEvent.VK_LEFT)) {
				xPersonaje -= 5;
			}
			if (v.isTeclaPulsada(KeyEvent.VK_RIGHT)) {
				xPersonaje += 5;
			}
			// Cambio de zoom
			if (v.isTeclaPulsada(KeyEvent.VK_PLUS)) {
				zoom = zoom *1.01;
				v.setEscalaDibujo(zoom);
			} 
			if (v.isTeclaPulsada(KeyEvent.VK_MINUS)) {
				zoom = zoom / 1.01;
				v.setEscalaDibujo(zoom);
			} 
			v.setOffsetDibujo( new Point( (int) (xPersonaje - v.getAnchuraConEscala()/2), (int) (yPersonaje - v.getAlturaConEscala()/2) ) );
			v.borra();
			v.dibujaCirculo( 0, 0, 80, 5f, Color.PINK, Color.MAGENTA );  // Elemento fijo
			v.dibujaImagen( "img/UD-blue-girable.png", 500, 200, 1.0, 0.0, 1.0f );  // Elemento fijo
			v.dibujaImagen( "img/sonic.png", xPersonaje, yPersonaje, 1.0, 0.0, 1.0f );  // Elemento móvil
			// picking adaptado a coordenadas de ventana
			Point click = v.getRatonPulsado();
			if (click!=null) {
				double xClick = v.getXDePixelEnCoordsVentana( click.x );
				double yClick = v.getYDePixelEnCoordsVentana( click.y );
				System.out.println( click + " -> (" + xClick + "," + yClick + " offset " + v.getOffsetDibujo() + " zoom" + v.getEscalaDibujo() );
				v.dibujaCirculo( xClick, yClick, 10, 2f, Color.MAGENTA, Color.CYAN );
				v.dibujaLinea( xClick, yClick-5, xClick, yClick+5, 1f, Color.BLACK );
				v.dibujaLinea( xClick-5, yClick, xClick+5, yClick, 1f, Color.BLACK );
			}
			v.repaint();
			v.espera(10);
		}
		v.acaba();
	}		
		
	// Prueba 7: Petición de texto, uno a uno y en bloque con botón
	private static void peticionTexto() {
		v.setDibujadoInmediato( true );
		Font font = new Font( "Arial", Font.PLAIN, 20 );
		// Petición texto único
		v.dibujaTexto( 50, 40, "Introduce texto 1", font, Color.BLUE );
		String entrada = v.leeTexto( 250, 10, 200, 40, "<texto>", font, Color.MAGENTA );
		v.dibujaTexto( 50, 80, "Texto introducido: " + entrada, font, Color.BLACK );
		// Petición múltiple
		v.dibujaTexto( 50, 120, "Texto múltiple 1 (int)", font, Color.GREEN );
		v.dibujaTexto( 50, 160, "Texto múltiple 2 (double)", font, Color.GREEN );
		v.dibujaTexto( 50, 200, "Texto múltiple 3 (check)", font, Color.GREEN );
		v.dibujaTexto( 50, 240, "Texto múltiple 4 (String)", font, Color.GREEN );
		v.pideTextoMultiple( 300, 90, 200, 40, "<num 1>", font, Color.MAGENTA );
		v.pideTextoMultiple( 300, 130, 150, 40, "<num 2>", font, Color.MAGENTA );
		v.pideCheckBoxMultiple( 300, 170, 40, 40, true );
		v.pideTextoMultiple( 300, 210, 100, 40, "<texto 4>", font, Color.MAGENTA );
		String boton = v.esperaEntradaMultiple( "Aceptar", "Cancelar" );
		int entrada1 = v.leeIntMultiple(1);
		double entrada2 = v.leeDoubleMultiple(2);
		Boolean entrada3 = v.leeCheckBoxMultiple(3);
		String entrada4 = v.leeStringMultiple(4);
		v.dibujaTexto( 50, 280, "Valores introducidos: " + entrada1 + " / " + entrada2 + " / " + entrada3 + " / " + entrada4, font, Color.BLACK );
		v.dibujaTexto( 50, 320, "Botón pulsado: " + boton, font, Color.BLACK );
		// Otra petición múltiple
		v.dibujaTexto( 50, 360, "Y otra petición (int)", font, Color.GREEN );
		v.dibujaTexto( 50, 400, "Y otra petición (string)", font, Color.GREEN );
		v.pideTextoMultiple( 300, 330, 200, 40, "<num>", font, Color.MAGENTA );
		v.pideTextoMultiple( 300, 370, 150, 40, "<texto>", font, Color.MAGENTA );
		boton = v.esperaEntradaMultiple( "Aceptar", "Cancelar" );
		entrada1 = v.leeIntMultiple(1);
		entrada4 = v.leeStringMultiple(2);
		v.dibujaTexto( 50, 440, "Valores introducidos: " + entrada1 + " / " + entrada4, font, Color.BLACK );
		v.dibujaTexto( 50, 480, "Botón pulsado: " + boton, font, Color.BLACK );
		v.dibujaTexto( 50, 520, "Borrando en 3 segundos...", font, Color.RED );
		v.espera( 3000 );
		// Y otra tras borrar
		v.borra();
		v.dibujaTexto( 50, 160, "Última petición (double)", font, Color.GREEN );
		v.dibujaTexto( 50, 200, "Y otra petición (string)", font, Color.GREEN );
		v.pideTextoMultiple( 300, 130, 200, 40, "<num>", font, Color.MAGENTA );
		v.pideTextoMultiple( 300, 170, 150, 40, "<texto>", font, Color.MAGENTA );
		boton = v.esperaEntradaMultiple( "Aceptar", "Cancelar", "Ignorar" );
		entrada1 = v.leeIntMultiple(1);
		entrada2 = v.leeDoubleMultiple(2);
		v.dibujaTexto( 50, 240, "Valores introducidos: " + entrada1 + " / " + entrada2, font, Color.BLACK );
		v.dibujaTexto( 50, 280, "Botón pulsado: " + boton, font, Color.BLACK );
	}
	
}
