package tema1b.ejercicios.ventas;

import java.awt.Color;
import java.awt.Font;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase lanzadora del programa de ventas
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class MainVentasMovil {
	
/* Descomenta todo este código para que funcione el ejercicio completo

	public static void main(String[] args) {
		Tienda tienda = new Tienda( "Deustienda" );
		// Datos de tienda
		tienda.addTrabajador( new Trabajador( "Inés", 0.9 ) );
		tienda.addTrabajador( new Trabajador( "Alberto", 0.4 ) );
		tienda.addTrabajador( new Trabajador( "Ana", 2.0 ) );
		for (Producto producto : PRODS) {
			tienda.addProducto( producto );
		}
		simulaProcesoTienda( tienda );
	}
	
	private static final Producto[] PRODS = {
		new Movil( "Lo nuevo de Samsung", 1359.00, "/tema2/ejercicios/ventas/s20.png", "Samsung", "Galaxy S20" ),
		new Servicio( "Instalación apps" ),
		new Movil( "Móvil sin Coltán", 450.00, "/tema2/ejercicios/ventas/fairphone3.png", "Fairphone", "3" ),
		new Movil( "El 11 de Apple", 1329.00, "/tema2/ejercicios/ventas/iphone11.png", "Apple", "IPhone 11 Pro" ),
		new Servicio( "Limpieza virus" ),
	};

	// Simulación del proceso de tienda hasta que se cierra (la ventana)
	private static void simulaProcesoTienda( Tienda tienda ) {
		VentanaConsolaConBotones vent = new VentanaConsolaConBotones( "Simulación Deustienda" );
		vent.init();
		boolean tiendaAbierta = true;
		while (tiendaAbierta) {
			sacaInfo( vent, tienda );
			pideAccionAlCliente( vent, tienda );
			tiendaAbierta = !vent.estaCerrada();
		}
	}

	// Visualizar la info de la tienda
	private static void sacaInfo( VentanaConsolaConBotones vent, Tienda tienda ) {
		vent.clear();
		vent.println( "Bienvenido a Deustienda" );
		vent.println( "Nuestros productos son:" );
		for (int i=0; i<tienda.getListaProductos().size(); i++) {
			Producto prod = tienda.getListaProductos().get(i);
			vent.println( " - " + prod );
		}
		vent.println( "Información de nuestros trabajadores: " + tienda.getInfoTrabajadores(), Color.ORANGE );
		vent.println( "¿Qué desea? Pulse el botón correspondiente", Color.GREEN );
	}

	// Proceso de elección de acción de cliente
	private static void pideAccionAlCliente( VentanaConsolaConBotones vent, Tienda tienda ) {
		String[] botones = new String[ tienda.getListaProductos().size()];  // Nota: un botón por cada producto
		for (int i=0; i<tienda.getListaProductos().size(); i++) {
			Producto prod = tienda.getListaProductos().get(i);
			botones[i] = prod.getNombre();  // Un botón por cada producto con el nombre del producto
		}
		String respuesta = vent.sacaBotonesYEsperaRespuesta( botones );
		if (respuesta!=null) {  // Hay compra de cliente. Veamos qué
			for (int i=0; i<tienda.getListaProductos().size(); i++) {
				Producto prod = tienda.getListaProductos().get(i);
				if (prod.getNombre().equals(respuesta)) {  // Este es el producto comprado por el cliente
					procesoVenta( vent, tienda, prod );
					break;
				}
			}
		}
	}

	// Proceso de venta del producto
	private static void procesoVenta( VentanaConsolaConBotones vent, Tienda tienda, Producto prod ) {
		// 1.- Veamos si hay operario libre
		tienda.pasaElTiempo(); // Actualizamos el tiempo que haya pasado en la tienda
		Trabajador trabajador = tienda.getTrabajadorLibre();
		while (trabajador==null) {
			vent.println( "No hay trabajadores libres! " + tienda.getInfoTrabajadores() + ". En espera...", Color.RED );
			vent.espera( 5000 ); // Espera 5 segundos...
			tienda.pasaElTiempo();
			trabajador = tienda.getTrabajadorLibre();
		}
		// 2.- Cuando ya hay trabajador libre, se le asigna el trabajo
		vent.println( "Su dependiente asignado es " + trabajador, Color.GREEN );
		if (prod instanceof Servicio) {  // Solo si es servicio, hay que calcular el precio y el tiempo
			Servicio serv = (Servicio) prod;
			if (serv.getNombre().equals( "Instalación apps" )) { // Si es instalación de apps hay que preguntárselo al cliente
				vent.println( "¿Cuántas apps desea instalar?" );
				int numero = 0;
				do {
					numero = vent.leeInt();
				} while (numero<=0 || numero==Integer.MAX_VALUE);
				serv.calcInstalacionApps( numero );
			} else if (serv.getNombre().equals( "Limpieza virus" )) {
				serv.calcTiempoLimpiezaVirus();
			}
		}
		tienda.pasaElTiempo(); // Actualiza el tiempo pasado en la tienda
		trabajador.setTiempoPendiente( prod.getTiempoTrabajo() );  // Nota: para esto da igual que sea venta o servicio (ambos saben su tiempo de trabajo)
		if (prod instanceof Movil) {  // Solo si es móvil: vistazo a la compra
			VentanaGrafica vg = new VentanaGrafica( 500, 600, "Información de venta" );
			vg.dibujaTexto( 10, 10, "Venta en proceso. Atendido por " + trabajador.getNombre(), new Font( "Arial", Font.PLAIN, 16 ), Color.BLUE );
			vg.dibujaImagen( ((Movil)prod).getImagen(), 250, 300, 1.0, 0.0, 1.0f );
			vg.setMensaje( "Cierra la ventana para continuar." );
			while (!vg.estaCerrada()) {  // Bucle de espera al cierre
				vg.espera(1000);
			}
			tienda.pasaElTiempo();
		}
	}

*/
	
}
