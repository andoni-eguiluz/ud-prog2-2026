package tema1c.resueltos.brickbreaker;

import java.awt.Color;
import java.awt.Font;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class BloqueRompible extends Bloque implements ObjetoVivo, Chocable, Animable, Puntuable {

	/** Tipo de letra visual de la vida en el bloque
	 */
	public static final Font FONT_TEXTO = new Font( "Arial", Font.BOLD, 18 );

	/** Milisegundos de animación de desaparición cuando se acaba la vida
	 */
	public static final int TIEMPO_ANIMACION = 500;

	protected int vida;
	protected Color colorTexto;
	protected long tiempoRestanteAnimacionMuerte = -1;
	protected Puntuacion puntuacion;
	
	/** Crea un bloque rompible nuevo sin movimiento (con velocidad 0)
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param ancho	Píxels de ancho
	 * @param alto	Píxels de alto
	 * @param color	Color de borde
	 * @param colorFondo	Color de fondo
	 * @param vida	Valor de vida del bloque (positivo)
	 */
	public BloqueRompible(double x, double y, double ancho, double alto, Color color, Color colorFondo, int vida ) {
		this( x, y, ancho, alto, 0.0, 0.0, color, colorFondo, vida );
	}
	
	/** Crea un bloque rompible nuevo
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param ancho	Píxels de ancho
	 * @param alto	Píxels de alto
	 * @param velX	Velocidad horizontal en píxels/segundo (positivo hacia la derecha, negativo hacia la izquierda)
	 * @param velY	Velocidad vertical en píxels/segundo (positivo hacia la derecha, negativo hacia la izquierda)
	 * @param color	Color de borde
	 * @param colorFondo	Color de fondo
	 * @param vida	Valor de vida del bloque (positivo)
	 */
	public BloqueRompible(double x, double y, double ancho, double alto, double velX, double velY, Color color, Color colorFondo, int vida) {
		super( x, y, ancho, alto, velX, velY, color, colorFondo );
		setColorTexto();
		this.vida = vida;
	}
	
	/** Crea un bloque aleatorio
	 * De vida entre 1 y 100
	 * Todo el bloque dentro de la ventana recibida
	 * Ancho y alto entre 10 y 50 pixels
	 * Velocidad x,y entre 100 y 300 pixels / seg, positiva o negativa
	 * Color de borde y fondo aleatorios entre los colores amarillo, rojo, verde, azul, cyan, magenta y negro
	 * @param v	Ventana de referencia para la creación (se toma su tamaño)
	 */
	public BloqueRompible(VentanaGrafica v) {
		super(v);
		vida = random.nextInt(100) + 1;
		setColorTexto();
	}

	// Pone el color de texto contrastado con el fondo, blanco o negro
	private void setColorTexto() {
		if (colorFondo.getRed()>128 || colorFondo.getGreen()>128 || colorFondo.getBlue()>128) {  // Pone negro en general
		// if (colorFondo.getRed() + colorFondo.getGreen() + colorFondo.getBlue() > 128*3) {  // 50% de blanco o negro, pero visualmente no funciona demasiado bien con colores vivos
			colorTexto = Color.BLACK;
		} else {  // y pone blanco si es muy oscuro
			colorTexto = Color.WHITE;
		}
	}
	
	@Override
	public void setColorFondo(Color colorFondo) {
		super.setColorFondo(colorFondo);
		setColorTexto();
	}

	@Override
	public void dibujar( VentanaGrafica v ) {
		if (tiempoRestanteAnimacionMuerte>=0) {  // Animación de desaparición
			double porcentajeMuerte = 1.0 * tiempoRestanteAnimacionMuerte / TIEMPO_ANIMACION;
			int transparencia = (int) Math.round( 255 * porcentajeMuerte );
			v.dibujaRect( x-ancho/2, y-alto/2, ancho, alto, 2.0f, new Color( colorBorde.getRed(), colorBorde.getGreen(), colorBorde.getBlue(), transparencia), 
					new Color( colorFondo.getRed(), colorFondo.getGreen(), colorFondo.getBlue(), transparencia) );
		} else if (vida > 0) {
			super.dibujar(v);
			v.dibujaTextoCentrado( x-ancho/2, y-alto/2, ancho, alto, ""+vida, FONT_TEXTO, colorTexto, true );
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BloqueRompible)) {
			return false;
		}
		BloqueRompible bloque2 = (BloqueRompible) obj;
		return x==bloque2.x&& y==bloque2.y && ancho==bloque2.ancho && alto==bloque2.alto;
	}

	@Override
	public String toString() {
		return super.toString() + "-{Vida " + vida + "}";
	}
	
	// Interfaz ObjetoConVida
	
	/** Devuelve la vida del bloque
	 * @return	Vida actual
	 */
	@Override
	public int getVida() {
		return vida;
	}

	/** Decrementa la vida del bloque (en una unidad)
	 */
	@Override
	public void decVida() {
		if (vida>0 && puntuacion!=null) {  // Incrementa la puntuación si procede
			puntuacion.incPuntos(1);
		}
		vida--;
		compruebaMuerte();
	}	

	/** Modifica la vida del bloque
	 * @param alto	Nueva vida (positiva
	 */
	@Override
	public void setVida(int vida) {
		this.vida = vida;
		compruebaMuerte();
	}
	
	// Lanza animación si el objeto deja de vivir
	private void compruebaMuerte() {
		if (vida <= 0 && tiempoRestanteAnimacionMuerte<0) {
			tiempoRestanteAnimacionMuerte = TIEMPO_ANIMACION;
		}
	}

	@Override
	public void mover(double milis) {
		super.mover(milis);
		if (tiempoRestanteAnimacionMuerte>0) {  // Animación de desaparición
			tiempoRestanteAnimacionMuerte -= milis;
			if (tiempoRestanteAnimacionMuerte < 0) {
				tiempoRestanteAnimacionMuerte = 0;
			}
		}
	}
	
	/** Informa si el objeto está vivo (vida > 0)
	 * @return	true si el objeto está vivo, false si no
	 */
	@Override
	public boolean estaVivo() {
		return vida>0;
	}	

	@Override
	public boolean esChocable() {
		return estaVivo();  // Es chocable solo si está vivo
	}

	@Override
	public boolean estaEnAnimacion() {
		return tiempoRestanteAnimacionMuerte > 0;
	}
	
	@Override
	public TipoAnimacion getAnimacionEnCurso() {
		if (tiempoRestanteAnimacionMuerte > 0) {
			return TipoAnimacion.MUERTE;
		}
		return null;
	}

	@Override
	public void setPuntuacion(Puntuacion puntuacion) {
		this.puntuacion = puntuacion;
	}
	
}
