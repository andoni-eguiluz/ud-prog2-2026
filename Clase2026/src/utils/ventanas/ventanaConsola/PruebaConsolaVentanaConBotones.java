package utils.ventanas.ventanaConsola;

import java.awt.Color;

public class PruebaConsolaVentanaConBotones {

	/** Método de prueba de la clase ConsolaVentanaConBotones.
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		VentanaConsolaConBotones v = new VentanaConsolaConBotones( "Prueba de ventana de consola" );
		v.init();
		v.println( "Hola. Dame un número entero:" );  // Sin color (negro)
		int dato = v.leeInt();
		if (dato == Integer.MAX_VALUE)
			v.println( "Tu número introducido es INCORRECTO.", Color.red );
		else 
			v.println( "Tu número introducido es " + dato, Color.green );
		v.println( "Dame un número real:", Color.blue );
		double datoDouble = v.leeDouble();
		if (datoDouble == Double.MAX_VALUE)
			v.println( "Tu número introducido es INCORRECTO.", Color.red );
		else 
			v.println( "Tu número introducido es " + datoDouble, Color.green );
		v.println( "Dame un string:", Color.blue );
		String linea = v.leeString();
		v.clear();  // Borra la pantalla
		v.println( "Tu string introducido es \"" + linea + "\"", Color.blue );
		v.println( "Saco otra ventana" );
		VentanaConsolaConBotones v2 = new VentanaConsolaConBotones( "Otra" );
		v2.getJFrame().setLocation( 0, 0 );
		v2.println( "Soy otra ventana" );
		v.println( "Estoy ejecutándome 5 segundos más... y me paro", Color.blue );
		v.espera( 5000 );
		v.cerrar();
		v2.println( "Yo sigo aquí. Tendrás que cerrarme a mano" );
		v2.println( "¿Qué te parece si ahora me contestas con el botón?" );
		String resp = v2.sacaBotonesYEsperaRespuesta( "Opción 1", "Opción 2", "No hay opción", "Opta tú" );
		v2.println( "Botón pulsado: " + resp );
	}	

}
