package tema1b.ejemplos.ofertas;

import java.util.ArrayList;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;

/** Clase contenedora del ejemplo de Ofertas
 * Contiene la lista de todas las ofertas en curso
 * y la lista de todas las ofertas compradas
 * Permite operatividad esencial sobre las ofertas
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class OfertasProg2 {
	// Ofertas en curso
	ArrayList<Oferta> listaOfertas;
	// Lista de compras realizadas 
	ArrayList<Oferta> listaCompras;

	/** Crea un objeto de ofertas con los datos de ofertas y compras vacíos
	 */
	public OfertasProg2() {
		listaOfertas = new ArrayList<>();
		listaCompras = new ArrayList<>();
	}

	/** Devuelve la lista de ofertas disponibles
	 * @return	Ofertas en curso
	 */
	public ArrayList<Oferta> getListaOfertas() {
		return listaOfertas;
	}

	/** Devuelve la lista de compras realizadas
	 * @return	Compras realizadas
	 */
	public ArrayList<Oferta> getListaCompras() {
		return listaCompras;
	}

	/** Añade una compra al sistema
	 * @param ofertaComprada	Oferta comprada
	 */
	public void addCompra( Oferta ofertaComprada ) {
		listaCompras.add( ofertaComprada );  // Añade la compra a la lista de compras
	}

	/** Muestra la información histórica de ventas
	 * @param ventana	Ventana en la que mostrar la información
	 */
	public void muestraInfo( VentanaConsolaConBotones ventana ) {
		ventana.println();
		ventana.println( "Resumen de ventas realizadas:" );
		ventana.println( listaCompras.toString() );
		ventana.println();
	}

}
