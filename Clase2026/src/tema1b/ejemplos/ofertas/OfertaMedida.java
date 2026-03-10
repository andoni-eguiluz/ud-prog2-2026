package tema1b.ejemplos.ofertas;

import java.io.Serializable;

import utils.ventanas.ventanaConsola.VentanaConsolaConBotones;

/** Oferta medida en tamaño (en dos dimensiones)
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class OfertaMedida extends Oferta implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final double COSTE_ESTANDAR_CORTADO_POR_METRO = 0.50;  // Por metro de corte cuesta 0,50 euros por defecto
	
	private double anchura;  // Ancho que se compra (en m.)
	private double altura;  // Ancho que se compra (en m.)
	private double costeCortado; // Coste de corte por m.
	
	/** Crea una nueva oferta (medida en corte de anchura y altura), con coste estándar {@link #COSTE_ESTANDAR_CORTADO_POR_METRO}
	 * @param nombre	Nombre de producto ofertado
s	 * @param precio	Precio de cada kilogramo
	 * @param anchura	Anchura que se compra (en m.)
	 * @param altura	Altura que se compra (en m.)
	 */
	public OfertaMedida(String nombre, double precio, double anchura, double altura) {
		super(nombre, precio);
		this.anchura = anchura;
		this.altura = anchura;
		costeCortado = COSTE_ESTANDAR_CORTADO_POR_METRO;
	}
	
	/** Crea una nueva oferta (medida en corte de anchura y altura), con altura y anchura 0 de compra, con coste estándar {@link #COSTE_ESTANDAR_CORTADO_POR_METRO}
	 * @param nombre	Nombre de producto ofertado
	 * @param precio	Precio de cada kilogramo
	 */
	public OfertaMedida(String nombre, double precio) {
		this(nombre, precio, 0, 0);  // Reutilizamos un constructor desde el otro
	}

	// Método de copia (nos interesa poder duplicar ofertas de este tipo) - por eso implementa Cloneable
	@Override
	public Object clone() {
		return new OfertaMedida(nombre, precio, anchura, altura);
	}

	public double getAnchura() {
		return anchura;
	}

	public void setAnchura(double anchura) {
		this.anchura = anchura;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	/** Devuelve el coste de cortado
	 * @return	Coste de cortado de cada metro (en euros)
	 */
	public double getCosteCortado() {
		return costeCortado;
	}

	/** Modifica el coste de cortado
	 * @param costeCortado	Nuevo coste de cortado de cada metro (en euros)
	 */
	public void setCosteCortado(double costeCortado) {
		this.costeCortado = costeCortado;
	}

	@Override
	public double getPrecioTotal() {
		return precio * anchura * altura + getCosteOperacion();
	}

	public double getCosteOperacion() {
		return anchura * altura * costeCortado;
	}

	@Override
	public boolean pedirDatos( VentanaConsolaConBotones ventana ) {
		ventana.println( "Introduce medidas a comprar de " + getNombre() + ". ¿Anchura (en m.)?" );
		double numero = ventana.leeDouble();
		if (numero == Double.MAX_VALUE) {  // Error en la lectura
			return false;
		} else {
			ventana.println( "¿Altura (en m.)?" );
			double numero2 = ventana.leeDouble();
			if (numero2 == Double.MAX_VALUE) {  // Error en la lectura
				return false;
			} else {
				setAnchura( numero );  // Actualiza los atributos
				setAltura( numero2 );
				return true;
			}
		}
	}
	
	@Override
	public String toString() {
		String ret = super.toString() + String.format( "(%4.2f€ por m2)", precio );
		if (anchura == 0.0 || altura == 0.0) {
			return ret;
		} else {
			return ret + String.format( " - compra de %4.2f x %4.2f m.", anchura, altura );
		}
	}
	
}
