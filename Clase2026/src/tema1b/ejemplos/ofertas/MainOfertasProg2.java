package tema1b.ejemplos.ofertas;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;

/** Clase principal del ejemplo de ofertas
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class MainOfertasProg2 {

	private static OfertasProg2 sistema;       // Sistema de trabajo
	private static VentanaConsolaConBotones ventana;  // Ventana de interacción
	
	/** Método principal - crea un sistema nuevo y lanza la interacción 
	 * @param args	No utilizado
	 */
	public static void main(String[] args) throws Exception {
		sistema = new OfertasProg2();
		init();
		interaccion();
	}

	// Inicializa el sistema con tres productos de ejemplo
	private static void init() {
		sistema.getListaOfertas().add( new OfertaUnidades( "Rollo de papel higiénico", 0.75 ) );
		sistema.getListaOfertas().add( new OfertaPeso( "Paracetamol en polvo", 61.25 ) );
		sistema.getListaOfertas().add( new OfertaMedida( "Tela de mascarilla", 10.28 ) );
	}

	// Interacción principal del sistema
	private static void interaccion() {
		ventana = new VentanaConsolaConBotones( "OfertasProg2 a su servicio" );
		ventana.println( "Bienvenido/a a nuestra tienda virtual" );
		String resp = null;
		do {
			muestraOfertas();
			ventana.println( "¿Desea algo? Pulse el botón correspondiente" );
			int numOfertas = sistema.getListaOfertas().size();
			String[] botones = new String[numOfertas + 2];
			for (int i=0; i<numOfertas; i++) {
				botones[i] = sistema.getListaOfertas().get(i).getNombre();
			}
			botones[numOfertas] = "Info";
			botones[numOfertas+1] = "Salir";
			resp = ventana.sacaBotonesYEsperaRespuesta( botones );
			if (resp!=null && !resp.equals("Salir")) {  // Se ha pulsado un botón de compra o de info. Gestionarlo
				if (resp.equals("Info")) {
					informacion();
				} else {
					gestionCompra( resp );
				}
			}
		} while (resp!=null && !resp.equals("Salir"));  // Repite hasta que se salga con el botón de salir o por escape (cierre de ventana)
		ventana.cerrar(); // Y cierra la ventana al acabar
	}
	
	private static void muestraOfertas() {
		ventana.println( "Estas son nuestras ofertas actuales:" );
		for (Oferta oferta : sistema.getListaOfertas()) {
			ventana.println( "  " + oferta );
		}
	}

	// Gestiona una compra
	private static void gestionCompra( String botonPulsado ) {
		// 1.- Comprobamos a qué producto corresponde el botón pulsado
		Oferta ofertaPedida = null;
		for (Oferta oferta : sistema.getListaOfertas()) {
			if (botonPulsado.equals( oferta.getNombre())) {  // Este es
				ofertaPedida = oferta;
				break; // No hace falta seguir buscando
			}
		}
		if (ofertaPedida == null) return;  // Si hay error en la oferta no se hace nada
		// 2.- Duplicamos el objeto
		Oferta ofertaCompra = (Oferta) ofertaPedida.clone();
		// 3.- Pedimos datos para la compra
		boolean correcto = ofertaCompra.pedirDatos( ventana );  // Método polimórfico (pide distintos datos dependiendo de la oferta)
		// 4.- Si los datos son correctos, realizamos la compra
		if (correcto) {
			ventana.println();
			ventana.println( "Confirmamos la compra de " + ofertaCompra );
			ventana.println( String.format( "Precio total: %4.2f€", ofertaCompra.getPrecioTotal() ) );
			ventana.println();
			// 5.- Metemos la compra en el sistema
			sistema.addCompra( ofertaCompra );
		}
	}
	
	// Muestra información del sistema de compras
	private static void informacion() {
		ventana.clear();
		sistema.muestraInfo( ventana );
	}

}
