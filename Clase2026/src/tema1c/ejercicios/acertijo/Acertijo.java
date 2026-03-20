package tema1c.ejercicios.acertijo;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase principal del acertijo del lobo, la oveja y la col
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Acertijo {

	/** Método principal de ejecución del ejercicio
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		Acertijo acertijo = new Acertijo();
		acertijo.run();
	}
	
	// =================== Constantes static
	
	public static final int ANCHURA_VENTANA = 1000;
	public static final int ALTURA_VENTANA = 800;
	
	// =================== Parte no static
	
	VentanaGrafica ventana;
	ArrayList<ObjetoAcertijo> listaObjetos;
	ArrayList<Orilla> listaOrillas;  // Lista de orillas, la primera es la origen y la segunda la destino
	Pastor pastor;
	Barca barca;
	
	/** Crea el acertijo con una ventana gráfica asociada de tamaño {@link #ANCHURA_VENTANA} x {@link #ALTURA_VENTANA}
	 */
	public Acertijo() {
		ventana = new VentanaGrafica( ANCHURA_VENTANA, ALTURA_VENTANA, "Acertijo" );
		ventana.setDibujadoInmediato( false );  // Preparar la ventana para doble buffer (evita el parpadeo en las animaciones)
		listaObjetos = new ArrayList<>();
		listaOrillas = new ArrayList<>();
	}
	
	public ArrayList<ObjetoAcertijo> getListaObjetos() {
		return listaObjetos;
	}

	/** Finaliza el gestor de tareas, cerrando su ventana
	 */
	public void fin() {
		ventana.acaba();
	}
			
	/** Ejecuta el gestor de tareas, lanzando el proceso de ejecución principal
	 */
	public void run() {
		initDatos();
		interaccion();
	}

	// Proceso de inicializar datos de la ventana de acertijo
	private void initDatos() {
		// Añade orillas y barca
		Orilla orilla1 = new Orilla( 100, 100, ventana );
		listaOrillas.add( orilla1 );
		listaOrillas.add( new Orilla( 700, 100, ventana ) );
		listaObjetos.addAll( listaOrillas );
		barca = new Barca( 280, 300, ventana );
		listaObjetos.add( barca );
		// Gestiona la lógica inicial de los objetos
		pastor = new Pastor( 200, 200, ventana );
		orilla1.anyadeObjeto( pastor );
		orilla1.anyadeObjeto( new Lobo( 200, 350, ventana ) );
		orilla1.anyadeObjeto( new Oveja( 200, 480, ventana ) );
		orilla1.anyadeObjeto( new Col( 200, 610, ventana ) );
		// Añade personajes al juego
		listaObjetos.addAll( orilla1.getObjetosContenidos() );
		// Añade texto de descripción
		listaObjetos.add( new Texto( 40, 10, ventana, 900, 20, "Si tú no estás el lobo come a la oveja y la oveja come a la col. ¡No los descuides!" ) );
		listaObjetos.add( new Texto( 40, 30, ventana, 900, 20, "Cambia todo de orilla. La barca solo admite 2 viajeros y uno tienes que ser tú. Ojo ¡que nadie se coma!" ) );
	}
	
	// Proceso de gestión de interacción de ratón por parte del usuario
	private void interaccion() {
		dibujaTodo();
		ventana.setMensaje( "Haz drag sobre los elementos que quieras mover. Si no es posible volverán a su posición." );
		boolean fin = false;
		while (!fin && !ventana.estaCerrada()) {
			Point p = ventana.getRatonPulsado();
			if (p!=null) {
				for (int i=listaObjetos.size()-1; i>=0; i--) { // Atención: la recorremos al revés para que se detecten los clicks primero en los elementos más "ontop"
					ObjetoAcertijo o = listaObjetos.get(i);
					if (o.contienePunto( p.x, p.y )) {
						fin = pulsadoObjeto( o, p );
						break;
					}
				}
			}
			ventana.espera( 20 );
		}
		if (!ventana.estaCerrada()) ventana.acaba();  // Cierra cuando el juego llega al fin
	}
	
	// Acción si se pulsa un objeto con el ratón. Devuelve true si se acaba el juego
	private boolean pulsadoObjeto( ObjetoAcertijo objPulsado, Point pPulsado ) {
		if (objPulsado instanceof Draggable) {  // Tratamiento de tarea o trabajador: puede ser un click o un drag
			Point p = ventana.getRatonPulsado();
			while (p!=null && p.equals(pPulsado)) {  // Esperar a que se mueva o se suelte (el click no mueve)
				ventana.espera( 20 ); // Pequeña espera
				p = ventana.getRatonPulsado();
			}
			if (p!=null) { // Se ha movido es un drag
				boolean fin = dragObjeto( (Draggable)objPulsado, pPulsado, p );
				return fin;
			}
		}
		return false;
	}
	
	// Informa si se acaba el juego (true acaba)
	private boolean dragObjeto( Draggable obj, Point pInicial, Point pActual ) {
		ventana.setMensaje( "Haz drag sobre los elementos que quieras mover. Si no es posible volverán a su posición." );
		int xInicial = obj.getPunto().x;
		int yInicial = obj.getPunto().y;
		// Mientras haya drag, visualizarlo
		Point pFinal = pActual;
		while (pActual!=null) {
			pFinal = pActual;
			int difX = pActual.x - pInicial.x;
			int difY = pActual.y - pInicial.y;
			obj.mover( obj.getPunto().x+difX, obj.getPunto().y+difY );
			dibujaTodo( obj );  // Dibuja el objeto móvil "encima" para que se vea siempre mientas se hace el drag
			ventana.espera( 20 ); // Pequeña espera
			pInicial = pActual;
			pActual = ventana.getRatonPulsado();
		}
		// Al final del drag, hay que comprobar si es viable o no 
		boolean movimientoOk = false; // Por defecto no es viable
		boolean movimientoBarca = false; // Marcamos el movimiento de la barca para la lógica final
		// Una barca puede ir solo de una orilla a otra y solo si dentro está el pastor
		if (obj instanceof Barca) {
			if (barca.getObjetosContenidos().contains( pastor )) {
				for (Orilla orilla : listaOrillas) { // Veamos si está en alguna orilla
					if (orilla.colisionaCon( barca )) {  // Está en esta
						movimientoOk = true; // Se permite el movimiento - no cambia de sitio
						movimientoBarca  = true;  // Se mueve la barca
						ventana.setMensaje( "Barca movida a orilla." );
						break;
					}
				}
			} else {
				ventana.setMensaje( "La barca solo puede moverse si estás en ella." );
			}
		}
		// Un personaje puede ir de la barca a la orilla si está pegada
		// T1
		else if (((obj instanceof Pastor) || (obj instanceof Lobo) || (obj instanceof Oveja) || (obj instanceof Col)) 
				&& barca.getObjetosContenidos().contains( (ObjetoAcertijo)obj )) {   // El personaje está originalmente en la barca
			if (barca.contienePunto( obj.getPunto() )) {  // Va a la misma barca - ok
				ventana.setMensaje( obj + " movido dentro de la barca." );
				movimientoOk = true; // Se permite el movimiento - no cambia de sitio
			} else {
				for (Orilla orilla : listaOrillas) {
					if (orilla.contienePunto(pFinal) && orilla.colisionaCon( barca )) {
						movimientoOk = true; // Se permite el movimiento - cambia de sitio
						barca.quitaObjeto( (ObjetoAcertijo)obj ); // Se quita de la barca
						orilla.anyadeObjeto( (ObjetoAcertijo)obj );   // Se pone en la orilla
						ventana.setMensaje( obj + " movido de la barca a la orilla." );
						break;
					}
				}
			}
		}
		// Un personaje puede ir de la orilla a la misma orilla o a la barca si está pegada y no está llena
		// T1
		else if ((obj instanceof Pastor) || (obj instanceof Lobo) || (obj instanceof Oveja) || (obj instanceof Col)) { // Está en una orilla
			for (Orilla orilla : listaOrillas) { // Veamos en cuál
				if (orilla.getObjetosContenidos().contains( (ObjetoAcertijo)obj )) {  // En esta
					if (orilla.contienePunto(pFinal)) {  // Va a la misma orilla
						ventana.setMensaje( obj + " movido a la misma orilla." );
						movimientoOk = true; // Se permite el movimiento - no cambia de sitio
					} else if (barca.contienePunto(pFinal) && barca.colisionaCon(orilla)) {  // Va a la barca y está pegada
						boolean posibleMov = barca.anyadeObjeto( (ObjetoAcertijo)obj );   // Se pone en la barca si se puede
						if (posibleMov) {
							orilla.quitaObjeto( (ObjetoAcertijo)obj ); // Se quita de la orilla
							ventana.setMensaje( obj + " movido de la orilla a la barca." );
							movimientoOk = true; // Se permite el movimiento - cambia de sitio
						} else { // Si no no se puede el movimiento y no se hace nada
							ventana.setMensaje( "La barca está llena." );
						}
					}
					break;
				}
			}
		}
		// Si el drag no es correcto, volver a su sitio
		if (!movimientoOk) {  
			obj.mover( xInicial, yInicial );  // Podría hacerse con animación: lo hacemos de golpe (cambiar si se desea animado)
		} else if (movimientoBarca) { // Si se ha hecho algún movimiento de barca hay que comprobar la posible pérdida de juego
			// ¿En qué orilla está el pastor?
			Orilla orillaSegura = null;
			for (Orilla orilla : listaOrillas) {
				if (barca.colisionaCon( orilla )) {  // El pastor está en esta orilla - el problema está en la otra
					orillaSegura = orilla;
					break;
				}
			}
			// Comprobar la otra orilla (la insegura)
			for (Orilla orilla : listaOrillas) {
				if (orilla != orillaSegura) {
					// Ver si todos con todos alguno come a alguno
					for (ObjetoAcertijo o1 : orilla.getObjetosContenidos()) {
						for (ObjetoAcertijo o2 : orilla.getObjetosContenidos()) {
							if (o1!=o2) {
								// T2
								if (o1 instanceof Oveja) {
									Oveja o = (Oveja) o1;
									if (o.puedeSerComidoPor( o2 )) {
										// OOOOOHHHH... HEMOS PERDIDO
										JOptionPane.showMessageDialog( ventana.getJFrame(), "Ooooh has perdido. " + o2 + " se ha comido a " + o1 );
										return true;
									}
								} else if (o1 instanceof Col) {
									Col o = (Col) o1;
									if (o.puedeSerComidoPor( o2 )) {
										// OOOOOHHHH... HEMOS PERDIDO
										JOptionPane.showMessageDialog( ventana.getJFrame(), "Ooooh has perdido. " + o2 + " se ha comido a " + o1 );
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		// Ver si orilla destino está llena - y eso es que hemos ganado
		if (listaOrillas.get(1).getObjetosContenidos().size()==4) {  // La destino es la segunda orilla
			JOptionPane.showMessageDialog( ventana.getJFrame(), "Has ganado! Todo el mundo se ha salvado." );
			return true;
		}
		// Al final se dibuja todo
		dibujaTodo();
		return false;
	}

	// Dibuja todo
	private void dibujaTodo( ) {
		dibujaTodo( null );
	}
	
	// Dibuja todo y el objeto indicado lo dibuja el último (para que quede por encima)
	private void dibujaTodo( Draggable objetoFinal ) {
		ventana.borra();
		for (ObjetoAcertijo o : listaObjetos) {
			o.dibujar();
		}
		if (objetoFinal!=null) {
			((ObjetoAcertijo)objetoFinal).dibujar();
		}
		ventana.repaint();  // Dibuja por doble buffer (evitando el parpadeo)
	}

}
