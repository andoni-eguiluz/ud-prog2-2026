package utils.ideasProyectos.funcion2D;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import utils.ideasProyectos.funcion2D.FuncionCompuesta.TipoComposicion;
import utils.ideasProyectos.funcion2D.Senoidal.TipoSenoidal;

public class PruebaFunciones2D extends JFrame {
	double PORC_MOVTO_EJES = 1/8d;
	double PORC_AMPL_ZOOM = 1.2d;
	
	private static final long serialVersionUID = 1L;
	static public int ANCHURA_GRAFICO = 2000;
	static public int ALTURA_GRAFICO = 1500;
	boolean ventPreparada = false;
	BufferedImage imagen;
	Graphics2D grImagen;
	JTextField tfEscalaX;
	JTextField tfEscalaY;
	MiPanelDibujo pDibujo;
	double escalaY;  // Valor de la altura en unidades de representaci�n
	double escalaX;  // Valor de la anchura en unidades de representaci�n
	double origenX;  // Pixel donde empieza el 0 de X
	double origenY;  // Pixel donde empieza el 0 de Y
	boolean relAspecto;  // si true, x determina y

	private class FuncionRepr {
		Funcion2D f;
		Color c;
		int trazo;
		public FuncionRepr(Funcion2D f, Color c, int trazo) {
			super(); this.f = f; this.c = c; this.trazo = trazo;
		}
	}
	ArrayList<FuncionRepr> funciones;  // Funciones representadas
	
	/** Indica si la ventana est� preparada para el dibujado
	 * (cuando Swing la activa en pantalla)
	 * @return	true si la ventana est� lista, false en caso contrario
	 */
	public boolean estaPreparada() {
		return ventPreparada;
	}
	
	public PruebaFunciones2D() {
		funciones = new ArrayList<>();
		setSize( 800, 600 );
		escalaX = 40;
		relAspecto = true;
		escalaY = 600; // Da igual se recalcula al ser proporcional
		origenX = 400;  // Aprox
		origenY = 300;  // Aprox
		imagen = new BufferedImage( ANCHURA_GRAFICO, ALTURA_GRAFICO, BufferedImage.TYPE_INT_RGB );
		grImagen = (Graphics2D) imagen.getGraphics();
		borraTablero();
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		pDibujo = new MiPanelDibujo();
		getContentPane().add( pDibujo, BorderLayout.CENTER );
		JPanel pConfig = new JPanel();
		pConfig.add( new JLabel( "Cursores mueven los ejes, Ctrl+Cursor arriba/abajo aleja/acerca, Espacio centra" ));
		getContentPane().add( pConfig, BorderLayout.SOUTH );
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				ventPreparada = true;
				grImagen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				configuraEscala();
			}
		});
		pDibujo.setFocusable( true );
		pDibujo.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pDibujo.requestFocus();
			}
		});
		pDibujo.requestFocus();
		pDibujo.addKeyListener( new KeyAdapter() {
			boolean pulsadaCtrl = false;
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					pulsadaCtrl = false;
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				boolean cambio = false;
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					pulsadaCtrl = true;
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (pulsadaCtrl) {
						escalaX /= PORC_AMPL_ZOOM;
						escalaY /= PORC_AMPL_ZOOM;
					} else {
						origenY += ( pDibujo.getHeight() * PORC_MOVTO_EJES );
					}
					cambio = true;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (pulsadaCtrl) {
						escalaX *= PORC_AMPL_ZOOM;
						escalaY *= PORC_AMPL_ZOOM;
					} else {
						origenY -= ( pDibujo.getHeight() * PORC_MOVTO_EJES );
					}
					cambio = true;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					origenX += ( pDibujo.getWidth() * PORC_MOVTO_EJES );
					cambio = true;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					origenX -= ( pDibujo.getWidth() * PORC_MOVTO_EJES );
					cambio = true;
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					origenX = pDibujo.getWidth()/2;
					origenY = pDibujo.getHeight()/2;
					cambio = true;
				}
				if (cambio) {
					borraTablero();
					dibujaFunciones();
				}
			}
		});
		pDibujo.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				origenX = e.getX();
				origenY = e.getY();
				borraTablero();
				dibujaFunciones();
			}
		});
		pDibujo.addMouseMotionListener( new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				setTitle( String.format( "Coordenada: [%1$,3.2f" + " , " + "%2$,3.2f]",
						pixelXAValor(e.getX()), pixelYAValor(e.getY()) ) );
			}
		});
	}
		private void configuraEscala() {
			int ancho = pDibujo.getWidth();
			int alto = pDibujo.getHeight();
			if (relAspecto) {
				escalaY = escalaX / ancho * alto;
			}
		}
		
		private int valorYAPixel( double valorY ) {
			double valPixel = origenY - valorY / escalaY * pDibujo.getHeight();
			if (valPixel > Integer.MAX_VALUE) return Integer.MAX_VALUE;
			if (valPixel < Integer.MIN_VALUE)  return Integer.MIN_VALUE;
			return (int) Math.round(valPixel);
		}
		@SuppressWarnings("unused")
		private int valorXAPixel( double valorX ) { 
			double valPixel = origenX - valorX / escalaX * pDibujo.getWidth();
			if (valPixel > Integer.MAX_VALUE) return Integer.MAX_VALUE;
			if (valPixel > Integer.MIN_VALUE) return Integer.MIN_VALUE;
			return (int) Math.round(valPixel);
		}
		private double pixelYAValor( int pixelY ) { 
			return (origenY - pixelY) * escalaY / pDibujo.getHeight();
		}
		private double pixelXAValor( int pixelX ) { 
			return (pixelX - origenX) * escalaX / pDibujo.getWidth();
		}

	public void dibujaEjes() {
		// Dibuja ejes en negro
		grImagen.setColor( Color.black );
		grImagen.setStroke( new BasicStroke(3) );
		grImagen.drawLine( 0, (int)Math.round(origenY), ANCHURA_GRAFICO-1, (int)Math.round(origenY) );
		grImagen.drawLine( (int)Math.round(origenX), 0, (int)Math.round(origenX), ALTURA_GRAFICO-1 );
	}
	
	public void representaFuncion( Funcion2D f, Color c ) {
		representaFuncion(f, c, 1);
	}
	public void representaFuncion( Funcion2D f, Color c, int trazo ) {
		funciones.add( new FuncionRepr(f, c, trazo) );
		dibujaFuncion( f, c, trazo );
	}
	public void dibujaFunciones() {
		for (FuncionRepr fr : funciones) {
			dibujaFuncion( fr.f, fr.c, fr.trazo );
		}
	}
	public void borraTablero() {
		grImagen.setColor( Color.white );
		grImagen.fillRect(0, 0, ANCHURA_GRAFICO-1, ALTURA_GRAFICO-1 );
		dibujaEjes();
	}
	public void dibujaFuncion( Funcion2D f, Color c, int trazo ) {
		// Dibuja funci�n
		grImagen.setColor( c );
		grImagen.setStroke( new BasicStroke(trazo) );
		int antX = Integer.MAX_VALUE;
		int antY = -1;
		for( int pixX=0; pixX<ANCHURA_GRAFICO; pixX++ ) { // pixX<pDibujo.getWidth() para hacer solo lo que se ve en pantalla
			double valX = pixelXAValor(pixX);
			double valY = f.f( valX );
			if (!Double.isNaN( valY )) {
				int pixY = valorYAPixel( valY );
				// System.out.println( "(" + pixX + "," + pixY + ") de punto real [" + valX + "," + valY + "]" );
				if (antX!=Integer.MAX_VALUE) {
					grImagen.drawLine(antX, antY, pixX, pixY);
				}
				antX = pixX;
				antY = pixY;
			}
		}
		pDibujo.repaint();
	}

	private class MiPanelDibujo extends JPanel {
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g) {
			if (ventPreparada) {
				Graphics2D gr2 = (Graphics2D) g;
				gr2.setColor( Color.blue );
				gr2.drawImage(imagen, 0, 0, null);
			}
		}
	}
	
	private static PruebaFunciones2D vent;
	public static void main(String[] args) {
		vent = new PruebaFunciones2D();
		vent.setVisible( true );
		try {
			while (!vent.ventPreparada)
				Thread.sleep(100);
		} catch (Exception e) { e.printStackTrace(); }
		Funcion2D seno5 = new Senoidal( 5, TipoSenoidal.SENO );
		vent.representaFuncion( seno5, Color.blue );
		Funcion2D coseno4 = new Senoidal( 4, TipoSenoidal.COSENO );
		vent.representaFuncion( coseno4, Color.green );
		Funcion2D ln = new Logaritmica( 1, 1, 0 );
		vent.representaFuncion( ln, Color.gray );
		FuncionConRangos compuesta = new FuncionConRangos( 
				new FuncionCompuesta(seno5, 0.5, 0), -Math.PI, Math.PI );
		compuesta.add( new Recta2Puntos( Math.PI, 0, 7, 9 ), Math.PI, 7 );
		compuesta.add( new Recta2Puntos( 7, 9, 11, 0 ), 7, 11 );
		compuesta.add( new Exponencial( 0.1, -11, 0, 2 ), 11, Double.POSITIVE_INFINITY );
		compuesta.add( new Exponencial( 1, +Math.PI, 0, 3 ), -2*Math.PI, -Math.PI );
		compuesta.add( new FuncionCompuesta( seno5, coseno4, TipoComposicion.COMP, 2, 0.5 )
			, Double.NEGATIVE_INFINITY, -2*Math.PI );
		vent.representaFuncion( compuesta, Color.magenta, 2 );
		FuncionDeDatos datos = new FuncionDeDatos( new double[] { 1, 7, 3, -4, 10, 0, 2, 4 }, 0.0, 0.5, true );
		vent.representaFuncion( datos, Color.cyan, 2 );
	}
	
}
