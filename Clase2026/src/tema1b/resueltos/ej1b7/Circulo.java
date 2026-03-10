package tema1b.resueltos.ej1b7;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase Circulo para juegos de clicker
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Circulo extends Figura {

	private static float grosor = 1.0f; // grosor del círculo, en píxeles
	public static final int RADIO_MINIMO = 20; // Radio mínimo de círculo aleatorio
	public static final int RADIO_MAXIMO = 50; // Radio máximo de círculo aleatorio
	public static final int X_MAXIMA = 500; // X máxima de círculo aleatorio
	public static final int Y_MAXIMA = 300; // Y máxima de círculo aleatorio
	public static final int VEL_MINIMA = 100; // vel. mínima de círculo aleatorio (tanto en horizontal como en vertical)
	public static final int VEL_MAXIMA = 300; // vel. máxima de círculo aleatorio (tanto en horizontal como en vertical)
	private static final Random random = new Random();

	public static long tiempoInicioDesaparicion = 1500;  // msg en los que el fondo empieza a desaparecer
	public static long tiempoDesaparicion = 2000;  // msg en los que el fondo ya es transparente
	
	public static float getGrosor() {
		return grosor;
	}

	public static void setGrosor(float grosor) {
		Circulo.grosor = grosor;
	}

	
	// NO STATIC
	
	private int radioEnPixels;  // radio del círculo, en píxeles
	private Color colorBorde;
	private Color colorRelleno;
	
	/** Crea un círculo nuevo
	 * @param radioEnPixels	Radio del círculo en píxeles. Debe ser positivo
	 * @param xCentro	Coordenada x del centro del círculo, en píxeles (de izquierda a derecha)
	 * @param yCentro	Coordenada y del centro del círculo, en píxeles (de arriba abajo)
	 * @param colorBorde	Color del borde
	 * @param colorRelleno	Color de rellenoe
	 */
	public Circulo(int radioEnPixels, int xCentro, int yCentro, Color colorBorde, Color colorRelleno) {
		super( xCentro, yCentro );
		this.radioEnPixels = radioEnPixels;
		this.colorBorde = colorBorde;
		this.colorRelleno = colorRelleno;
	}
	
	/** Crea un círculo nuevo de color azul y relleno amarillo
	 * @param radioEnPixels	Radio del círculo en píxeles. Debe ser positivo
	 * @param xCentro	Coordenada x del centro del círculo, en píxeles (de izquierda a derecha)
	 * @param yCentro	Coordenada y del centro del círculo, en píxeles (de arriba abajo)
	 */
	public Circulo(int radioEnPixels, int xCentro, int yCentro) {
		this( radioEnPixels, xCentro, yCentro, Color.BLUE, Color.YELLOW );
	}
	
	/** Crea un círculo nuevo de coordenadas, radio, color y velocidades aleatorias.
	 * El radio estará entre {@link #RADIO_MINIMO} y {@link #RADIO_MAXIMO} pixels
	 * La posición será una coordenada válida para la ventana indicada 
	 * La velocidad será entre {@link #VEL_MINIMA} y {@value #VEL_MAXIMA} en cada uno de los dos ejes
	 * @param ventana	Ventana que marca el espacio visible en el que localizar al círculo
	 */
	public Circulo( VentanaGrafica ventana ) {
		super( 0, 0 ); // Inicializamos y luego lo cambiamos
		radioEnPixels = RADIO_MINIMO + random.nextInt( RADIO_MAXIMO-RADIO_MINIMO+1 );
		// Se podría tb hacer con seters en lugar de protected:
		// setxCentro( random.nextInt( ventana.getAnchura() - radioEnPixels*2 ) + radioEnPixels ); 
		// setyCentro( random.nextInt( ventana.getAltura() - radioEnPixels*2 ) + radioEnPixels );
		this.xCentro = random.nextInt( ventana.getAnchura() - radioEnPixels*2 ) + radioEnPixels; 
		this.yCentro = random.nextInt( ventana.getAltura() - radioEnPixels*2 ) + radioEnPixels;
		colorBorde = new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256) );  // Una manera de generar un color aleatorio cualquiera
		Color[] colores = { Color.BLUE, Color.PINK, Color.CYAN, Color.GREEN };  // Otra manera, partiendo de una lista cerrada
		colorRelleno = colores[ random.nextInt( colores.length ) ];
		// Velocidades aleatorias
		velX = random.nextInt(VEL_MAXIMA-VEL_MINIMA+1) + VEL_MINIMA;
		velY = random.nextInt(VEL_MAXIMA-VEL_MINIMA+1) + VEL_MINIMA;
	}
	
	public Color getColorBorde() {
		return colorBorde;
	}

	public void setColorBorde(Color colorBorde) {
		this.colorBorde = colorBorde;
	}

	public Color getColorRelleno() {
		return colorRelleno;
	}

	public void setColorRelleno(Color colorRelleno) {
		this.colorRelleno = colorRelleno;
	}

	public int getRadioEnPixels() {
		return radioEnPixels;
	}

	/** Convierte el círculo en string en formato "(x,y) - radio"
	 */
	public String toString() {
		return super.toString() + String.format( " - %d", radioEnPixels );
	}

	/** Dibuja el círculo, con un color de fondo que va desapareciendo desde el milisegundo {@link #tiempoInicioDesaparicion} hasta el {@value #tiempoDesaparicion}
	 * @param v	Ventana en la que dibujar el círculo
	 */
	public void dibujar( VentanaGrafica v ) {
		int opacidad = 255;
		if (getTiempoVida() > tiempoInicioDesaparicion) {
			long maxDesaparicion = tiempoDesaparicion - tiempoInicioDesaparicion;
			long miDesaparicion = getTiempoVida() - tiempoInicioDesaparicion;
			if (miDesaparicion > maxDesaparicion) miDesaparicion = maxDesaparicion;
			opacidad = 255 - (int) (miDesaparicion * 255 / maxDesaparicion);   // Convertir a escala 255,0 (255 es opaco, 0 transparente)
		}
		v.dibujaCirculo( xCentro, yCentro, radioEnPixels, 2.0f, colorBorde );
		v.dibujaImagen( "dr_strange.png", xCentro, yCentro, (int)radioEnPixels*2, (int)radioEnPixels*2, 1.0, 0.0, opacidad/255f );
	}

	/** Informa si el círculo contiene un punto de la ventana
	 * @param punto	Punto a consultar
	 * @return	true si ese punto está dentro del círculo (incluyendo su borde), false si no lo está
	 */
	public boolean contienePunto( Point punto ) {
		double distCentroAPunto = Math.sqrt( (xCentro-punto.x)*(xCentro-punto.x) + (yCentro-punto.y)*(yCentro-punto.y) );
		return distCentroAPunto <= radioEnPixels;
	}
	
	/** Determina si el círculo choca con el borde vertical del espacio de dibujado
	 * @param v	Ventana de dibujado
	 * @return	true si el círculo toca con el borde inferior o superior, false en caso contrario
	 */
	public boolean chocaBordeVertical( VentanaGrafica v ) {
		return yCentro-radioEnPixels <= 0 || yCentro+radioEnPixels >= v.getAltura();
	}
	
	/** Determina si el círculo choca con el borde horizontal del espacio de dibujado
	 * @param v	Ventana de dibujado
	 * @return	true si el círculo toca con el borde izquierdo o derecho, false en caso contrario
	 */
	public boolean chocaBordeHorizontal( VentanaGrafica v ) {
		return xCentro-radioEnPixels <= 0 || xCentro+radioEnPixels >= v.getAnchura();
	}

}
