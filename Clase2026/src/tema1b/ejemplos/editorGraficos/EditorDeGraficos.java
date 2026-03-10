package tema1b.ejemplos.editorGraficos;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase de prueba de Vector2D - incluye pequeño sistema de edición de vectores
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class EditorDeGraficos {

	private static final int ANCHO_VENTANA = 900;
	private static final int ALTO_VENTANA = 500;
	
	/** Método principal de prueba de la clase
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		edicionDeGraficos();
	}
	
	private static void edicionDeGraficos() {
		VentanaGrafica vent = new VentanaGrafica( ANCHO_VENTANA, ALTO_VENTANA, "Dibujo de gráficos");
		// Poner esto si se quiere cambiar la posición inicial de la ventana:
		// vent.getJFrame().setLocation( 2000, 0 );
		ArrayList<Grafico> grupo = new ArrayList<Grafico>();
		Point clickInicial = null;
		vent.setMensaje( "Ctrl-click crea vectorOrigen, Drag crea (+R +C), Alt-click borra, Shift-click mueve, Click selecciona, otro click mueve punto" );
		vent.setDibujadoInmediato( false ); // Preparación de doble buffer
		Grafico grafSel = null;
		// BOOLEANAS en cortocircuito
		// log1 || log2 || log3 ...   cuando cualquiera sea TRUE la condición es TRUE
		// log1 && log2 && log3 ... cuando cualquiera sea FALSE la condición es FALSE
		while (!vent.estaCerrada() && (clickInicial==null || (clickInicial.getX() < 580 || clickInicial.getY() < 390))) {
			clickInicial = vent.getRatonPulsado();
			Point clickFinal = clickInicial;
			Point clickIntermedio = clickInicial;
			boolean puedeSerClick = true;
			while (clickFinal!=null) {
				if (!clickInicial.equals(clickFinal)) {
					puedeSerClick = false;
				}
				clickIntermedio = clickFinal;
				clickFinal = vent.getRatonPulsado();
				// System.out.println( clickInicial + " - " + clickFinal );
			}
			if (puedeSerClick && clickInicial!=null) {
				// Click
				// 2 ops distintas Ctrl o con Alt
				if (vent.isTeclaPulsada(KeyEvent.VK_ALT)) {  // Alt = borrar
					// Comprobar si hay un gráfico en ese punto
					int grafABorrar = comprobarGraficoEnClick( grupo, clickInicial );
					if (grafABorrar!=-1) {
						System.out.println( "Borrado el gráfico " + grafABorrar );
						grupo.remove(grafABorrar);
						grafSel = null;
					}
				} else if (vent.isTeclaPulsada( KeyEvent.VK_CONTROL)) {
					Vector2D vec = new Vector2D( clickInicial.getX(), clickInicial.getY() );
					grupo.add( vec );
					if (vec.getModulo()<100) {
						vec.dibujar( vent, Color.RED );
					} else if (vec.getModulo()<250) {
						vec.dibujar( vent, Color.ORANGE );
					} else {
						vec.dibujar( vent, Color.GREEN );
					}
					grafSel = null;
					System.out.println( "Click en " + clickInicial );
					System.out.println( "Hay guardados " + grupo.size() + " gráficos" );
				} else if (vent.isTeclaPulsada( KeyEvent.VK_SHIFT )){
					// Código para mover
					int gASeleccionar = comprobarGraficoEnClick( grupo, clickInicial );
					if (gASeleccionar!=-1) { // Cuando se hace sobre un gráfico - se selecciona
						grafSel = grupo.get(gASeleccionar);
					} else {  // Click en el fondo (sobre ningún gráfico)
						if (grafSel!=null) {
							// Mover - solo algunas clases se mueven
							if (grafSel instanceof Circulo) {
								((Circulo)grafSel).mover( clickInicial.x, clickInicial.y );
							} else if (grafSel instanceof Rectangulo) {
								((Rectangulo)grafSel).mover( clickInicial.x, clickInicial.y );
							} else if (grafSel instanceof VectorConOrigen2D) {
								((VectorConOrigen2D)grafSel).mover( clickInicial.x, clickInicial.y );
							}
						}
					}
				} else {
					// Click sin ctrl ni alt
					int gASeleccionar = comprobarGraficoEnClick( grupo, clickInicial );
					if (gASeleccionar!=-1) { // Cuando se hace sobre un gráfico - se selecciona
						grafSel = grupo.get(gASeleccionar);
					} else {  // Click en el fondo (sobre ningún gráfico)
						if (grafSel!=null) {  // Mover el gráfico ya seleccionado
							grafSel.setXY( clickInicial );
						}
					}
				}
				// System.out.println( clickInicial );
			} else if (!puedeSerClick) {
				// Gestión del drag en lugar del click (añadida variable clickIntermedio)
				// System.out.println( "Drag de " + clickInicial + " a " + clickIntermedio );
				if (vent.getCodTeclaQueEstaPulsada()==KeyEvent.VK_C) {
					// Crear círculo
					double radio = Math.sqrt( (clickInicial.x-clickIntermedio.x)*(clickInicial.x-clickIntermedio.x) + (clickInicial.y-clickIntermedio.y)*(clickInicial.y-clickIntermedio.y));
					Circulo c = new Circulo( clickInicial.x, clickInicial.y, radio, new Color(0, 255, 0) );
					grupo.add( c );
				} else if (vent.getCodTeclaQueEstaPulsada()==KeyEvent.VK_R) {
						// Crear rectángulo
						double distX = Math.abs( clickInicial.x-clickIntermedio.x );
						double distY = Math.abs( clickInicial.y-clickIntermedio.y );
						Rectangulo r = new Rectangulo( clickInicial.x, clickInicial.y, distX*2, distY*2, Color.CYAN );
						grupo.add( r );
				} else {
					// Crear vector con origen
					VectorConOrigen2D vec = new VectorConOrigen2D( clickInicial.getX(), clickInicial.getY(), clickIntermedio.getX(), clickIntermedio.getY() );
					grupo.add( vec );
					if (vec.getModulo()<100) {
						vec.dibujar( vent, Color.RED );
					} else if (vec.getModulo()<250) {
						vec.dibujar( vent, Color.ORANGE );
					} else {
						vec.dibujar( vent, Color.GREEN );
					}
				}
				grafSel = null;
				System.out.println( "Hay guardados " + grupo.size() + " gráficos" );
			}
			// Repintar la ventana en cada frame:
			vent.borra();
			for (int i=0; i<grupo.size(); i++) {
				grupo.get(i).dibujar(vent, grupo.get(i).getColor() );
			}
			if (grafSel!=null) {
				grafSel.dibujar( vent, grafSel.getColor(), 3.0f );
			}
			vent.repaint();  // Volcado de doble buffer (para pintar sin flickering -el cambio de pantalla es suave entre fotogramas-)
			vent.espera(20);  // Espera hasta el siguiente frame (20 msgs = 50 frames por segundo aprox.)  (1000/20 = 50)
		}
		System.out.println( "Fin!" );
		vent.acaba();
	}
	
	private static int comprobarGraficoEnClick( ArrayList<Grafico> grupo, Point clickInicial ) {
		int vEnClick = -1;
		for (int i=0; i<grupo.size(); i++) {
			Vector2D temp = new Vector2D( clickInicial.getX(), clickInicial.getY() );
			// System.out.println( grupo.get(i).distancia( temp ) + " a " + clickInicial );
			// System.out.println( grupo.get(i).toca( temp, 5.0 ) + " a " + clickInicial );
			if (grupo.get(i).toca( temp, 5.0 )) {
				vEnClick = i;
			}
		}
		return vEnClick;
	}
	
}
