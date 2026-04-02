package utils.ventanas.ejemplosSwing.dibujo;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

/** Clase de prueba de gráficos en Java. Pintado directo y cambiado para escalado.
 * Paneles con gráficos de fondo uno dentro de otro
 * Acceso a recursos vs acceso a ficheros
 * @author andoni.eguiluz.moran
 */
public class PruebaVariosPanelesConFondo {
	/** Método de prueba de gráficos
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame prueba = new JFrame();
		prueba.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		prueba.setSize( 600, 500 );
		URL recursoImgURL = PruebaVariosPanelesConFondo.class.getResource( "/tema1b/ejemplos/xsoldier/img/player-up.png" );  // Path desde la clase
		JPanel panelFondo1 = new JPanelFondo( recursoImgURL );
		File ficheroImg = new File( "src/utils/ventanas/ejemplosSwing/componentesPersonalizados/coche.png" );  // Path de fichero desde el proyecto
		JPanel panelFondo2 = new JPanelFondo( ficheroImg );
		panelFondo2.setOpaque( false );
		Icon grafNormal = new ImageIcon( recursoImgURL );  // Imagen normal (no escalada)
		JLabel lGrafNormal = new JLabel( grafNormal );
		panelFondo1.setLayout( new BorderLayout() );
		prueba.setContentPane( panelFondo1 );
		JButton botonPrueba = new JButton( "Botón de prueba encima de panel con imagen" );
		botonPrueba.setOpaque( false );
		botonPrueba.setContentAreaFilled( false );
		botonPrueba.setBorderPainted( false );
		panelFondo1.add( panelFondo2, BorderLayout.CENTER );
		panelFondo1.add( botonPrueba, BorderLayout.SOUTH );
		panelFondo1.add( lGrafNormal, BorderLayout.EAST );
		prueba.setVisible( true );
	}
	
	private static class JPanelFondo extends JPanel {
		private static final long serialVersionUID = 1L;
		private BufferedImage imagenOriginal;
		/** Crea un nuevo panel con fondo escalado al tamaño del panel
		 * @param imgURL
		 */
		public JPanelFondo( URL imgURL ) {
			try {
				imagenOriginal = ImageIO.read( imgURL );
			} catch (Exception e) {
				System.out.println( "Recurso " + imgURL + " no encontrado");
			}
		}
		public JPanelFondo( File imgFile ) {
			try {
				imagenOriginal = ImageIO.read( imgFile );
			} catch (Exception e) {
				System.out.println( "Fichero " + imgFile.getAbsolutePath() + " no encontrado");
			}
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Rectangle espacio = g.getClipBounds();  // espacio de dibujado del panel
			Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
			// Código para que el dibujado se reescale al área disponible
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(imagenOriginal, 0, 0, (int)espacio.getWidth(), 
			             (int)espacio.getHeight(), null);
			g2.setColor( Color.green );
			g2.setStroke( new BasicStroke( 1.5f ) );
			g2.drawLine( 0, 0, 100, 100 );
		}
	}
	
}

