package utils.ventanas.ejemplosSwing.dibujo;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/** Ejemplo de dibujo directo en panel (cubo de rubik)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class EjemploDibujadoDirectoCuboRubik {
	
	private static JFrame v;
	private static PanelDibujo pDibujo;
	public static void main(String[] args) {
		calculaPuntosCubo();
		try {
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
					v = new JFrame();
					v.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
					v.setSize( 1000, 800 );
					pDibujo = new PanelDibujo();
					v.getContentPane().add( pDibujo, BorderLayout.CENTER );
					v.setVisible( true );
				}
			});
		} catch (Exception e) {}
	}

	
	
	private static Point[][][] ptosCubo;

	private static void calculaPuntosCubo() {
		int xIni = 100;
		int yIni = 200;
		int offsetX = 100;
		int offsetY = 100;
		int offsetDiagX = 70;
		int offsetDiagY = -40;
		ptosCubo = new Point[4][4][4];
		for (int x=0; x<4; x++) {
			for (int y=0; y<4; y++) {
				for (int z=0; z<4; z++) {
					ptosCubo[x][y][z] = new Point( xIni + x*offsetX + z*offsetDiagX, yIni + y*offsetY + z*offsetDiagY );
				}
			}
		}
	}
	
	private static void dibujaCubo(Graphics2D g2) {
		// 1. Dibuja cubo general
		g2.setColor( Color.black );
		// Frontal
		g2.drawLine( ptosCubo[0][0][0].x, ptosCubo[0][0][0].y, ptosCubo[3][0][0].x, ptosCubo[3][0][0].y );
		g2.drawLine( ptosCubo[3][0][0].x, ptosCubo[3][0][0].y, ptosCubo[3][3][0].x, ptosCubo[3][3][0].y );
		g2.drawLine( ptosCubo[3][3][0].x, ptosCubo[3][3][0].y, ptosCubo[0][3][0].x, ptosCubo[0][3][0].y );
		g2.drawLine( ptosCubo[0][3][0].x, ptosCubo[0][3][0].y, ptosCubo[0][0][0].x, ptosCubo[0][0][0].y );
		// Superior
		g2.drawLine( ptosCubo[0][0][0].x, ptosCubo[0][0][0].y, ptosCubo[3][0][0].x, ptosCubo[3][0][0].y );
		g2.drawLine( ptosCubo[3][0][0].x, ptosCubo[3][0][0].y, ptosCubo[3][0][3].x, ptosCubo[3][0][3].y );
		g2.drawLine( ptosCubo[3][0][3].x, ptosCubo[3][0][3].y, ptosCubo[0][0][3].x, ptosCubo[0][0][3].y );
		g2.drawLine( ptosCubo[0][0][3].x, ptosCubo[0][0][3].y, ptosCubo[0][0][0].x, ptosCubo[0][0][0].y );
		// Frontal
		g2.drawLine( ptosCubo[3][0][0].x, ptosCubo[3][0][0].y, ptosCubo[3][3][0].x, ptosCubo[3][3][0].y );
		g2.drawLine( ptosCubo[3][3][0].x, ptosCubo[3][3][0].y, ptosCubo[3][3][3].x, ptosCubo[3][3][3].y );
		g2.drawLine( ptosCubo[3][3][3].x, ptosCubo[3][3][3].y, ptosCubo[3][0][3].x, ptosCubo[3][0][3].y );
		g2.drawLine( ptosCubo[3][0][3].x, ptosCubo[3][0][3].y, ptosCubo[3][0][0].x, ptosCubo[3][0][0].y );
		// Rayas frontales
		g2.drawLine( ptosCubo[0][1][0].x, ptosCubo[0][1][0].y, ptosCubo[3][1][0].x, ptosCubo[3][1][0].y );
		g2.drawLine( ptosCubo[0][2][0].x, ptosCubo[0][2][0].y, ptosCubo[3][2][0].x, ptosCubo[3][2][0].y );
		g2.drawLine( ptosCubo[1][0][0].x, ptosCubo[1][0][0].y, ptosCubo[1][3][0].x, ptosCubo[1][3][0].y );
		g2.drawLine( ptosCubo[2][0][0].x, ptosCubo[2][0][0].y, ptosCubo[2][3][0].x, ptosCubo[2][3][0].y );
		// Rayas superiores
		g2.drawLine( ptosCubo[0][0][1].x, ptosCubo[0][0][1].y, ptosCubo[3][0][1].x, ptosCubo[3][0][1].y );
		g2.drawLine( ptosCubo[0][0][2].x, ptosCubo[0][0][2].y, ptosCubo[3][0][2].x, ptosCubo[3][0][2].y );
		g2.drawLine( ptosCubo[1][0][0].x, ptosCubo[1][0][0].y, ptosCubo[1][0][3].x, ptosCubo[1][0][3].y );
		g2.drawLine( ptosCubo[2][0][0].x, ptosCubo[2][0][0].y, ptosCubo[2][0][3].x, ptosCubo[2][0][3].y );
		// Rayas laterales
		g2.drawLine( ptosCubo[3][0][1].x, ptosCubo[3][0][1].y, ptosCubo[3][3][1].x, ptosCubo[3][3][1].y );
		g2.drawLine( ptosCubo[3][0][2].x, ptosCubo[3][0][2].y, ptosCubo[3][3][2].x, ptosCubo[3][3][2].y );
		g2.drawLine( ptosCubo[3][1][0].x, ptosCubo[3][1][0].y, ptosCubo[3][1][3].x, ptosCubo[3][1][3].y );
		g2.drawLine( ptosCubo[3][2][0].x, ptosCubo[3][2][0].y, ptosCubo[3][2][3].x, ptosCubo[3][2][3].y );
		
		// 2. Dibuja los puntos (para verlos - esto habría que quitarlo seguramente)
		// Dibuja caritas frontales
		g2.setColor( Color.magenta );
		for (int x=0; x<=3; x++) {
			for (int y=0; y<=3; y++) {
				g2.drawOval( ptosCubo[x][y][0].x-2, ptosCubo[x][y][0].y-2, 4, 4 );
			}
		}
		// Dibuja superior
		for (int x=0; x<=3; x++) {
			for (int z=0; z<=3; z++) {
				g2.drawOval( ptosCubo[x][0][z].x-2, ptosCubo[x][0][z].y-2, 4, 4 );
			}
		}
		// Dibuja lateral
		for (int y=0; y<=3; y++) {
			for (int z=0; z<=3; z++) {
				g2.drawOval( ptosCubo[3][y][z].x-2, ptosCubo[3][y][z].y-2, 4, 4 );
			}
		}

		Random r = new Random();  // Pone colores aleatorios
		
		// 3. Dibuja caritas internas
		// Hay dos maneras. Tipo = 1 la más fácil (2 la más difícil)
		int tipo = 2;
		if (tipo==1) {
			// Dibuja caritas frontales
			g2.setColor( Color.blue );
			for (int x=0; x<3; x++) {
				for (int y=0; y<3; y++) {
					g2.setColor( new Color( r.nextInt(256), r.nextInt(256), r.nextInt(256) ) );
					dibujaCarita( g2, ptosCubo[x][y][0].x, ptosCubo[x][y][0].y,
								ptosCubo[x+1][y][0].x, ptosCubo[x+1][y][0].y,
								ptosCubo[x+1][y+1][0].x, ptosCubo[x+1][y+1][0].y,
								ptosCubo[x][y+1][0].x, ptosCubo[x][y+1][0].y );
				}
			}
			// Dibuja superior
			for (int x=0; x<3; x++) {
				for (int z=0; z<3; z++) {
					g2.setColor( new Color( r.nextInt(256), r.nextInt(256), r.nextInt(256) ) );
					dibujaCarita( g2, ptosCubo[x][0][z].x, ptosCubo[x][0][z].y,
							ptosCubo[x+1][0][z].x, ptosCubo[x+1][0][z].y,
							ptosCubo[x+1][0][z+1].x, ptosCubo[x+1][0][z+1].y,
							ptosCubo[x][0][z+1].x, ptosCubo[x][0][z+1].y );
				}
			}
			// Dibuja lateral
			for (int y=0; y<3; y++) {
				for (int z=0; z<3; z++) {
					g2.setColor( new Color( r.nextInt(256), r.nextInt(256), r.nextInt(256) ) );
					dibujaCarita( g2, ptosCubo[3][y][z].x, ptosCubo[3][y][z].y,
							ptosCubo[3][y+1][z].x, ptosCubo[3][y+1][z].y,
							ptosCubo[3][y+1][z+1].x, ptosCubo[3][y+1][z+1].y,
							ptosCubo[3][y][z+1].x, ptosCubo[3][y][z+1].y );
				}
			}
		} else {
			// Dibuja caritas frontales
			int oIx = 5;  // Pixels internos a la propia línea divisoria de la cara
			int oIy = 5;  // Pixels internos a la propia línea divisoria de la cara
			g2.setColor( Color.blue );
			for (int x=0; x<3; x++) {
				for (int y=0; y<3; y++) {
					g2.setColor( new Color( r.nextInt(256), r.nextInt(256), r.nextInt(256) ) );
					dibujaCarita( g2, ptosCubo[x][y][0].x+oIx, ptosCubo[x][y][0].y+oIy,
							ptosCubo[x+1][y][0].x-oIx, ptosCubo[x+1][y][0].y+oIy,
							ptosCubo[x+1][y+1][0].x-oIx, ptosCubo[x+1][y+1][0].y-oIy,
							ptosCubo[x][y+1][0].x+oIx, ptosCubo[x][y+1][0].y-oIy );
				}
			}
			// Dibuja superior
			// Los offsets cambian por la perspectiva
			int oX = 7;
			oIy = -4;  // Y el offset y va al revés además de estar en perspectiva
			for (int x=0; x<3; x++) {
				for (int z=0; z<3; z++) {
					g2.setColor( new Color( r.nextInt(256), r.nextInt(256), r.nextInt(256) ) );
					dibujaCarita( g2, ptosCubo[x][0][z].x+oIx+oX, ptosCubo[x][0][z].y+oIy,
							ptosCubo[x+1][0][z].x-oIx+oX, ptosCubo[x+1][0][z].y+oIy,
							ptosCubo[x+1][0][z+1].x-oIx-oX, ptosCubo[x+1][0][z+1].y-oIy,
							ptosCubo[x][0][z+1].x+oIx-oX, ptosCubo[x][0][z+1].y-oIy );
				}
			}
			// Dibuja lateral
			// Los offsets cambian por la perspectiva
			int oY = 5;
			oIy = 3;
			for (int y=0; y<3; y++) {
				for (int z=0; z<3; z++) {
					g2.setColor( new Color( r.nextInt(256), r.nextInt(256), r.nextInt(256) ) );
					dibujaCarita( g2, ptosCubo[3][y][z].x+oIx, ptosCubo[3][y][z].y+oIy, 
							ptosCubo[3][y+1][z].x+oIx, ptosCubo[3][y+1][z].y-oIy-oY,
							ptosCubo[3][y+1][z+1].x-oIx, ptosCubo[3][y+1][z+1].y-oIy,
							ptosCubo[3][y][z+1].x-oIx, ptosCubo[3][y][z+1].y+oIy+oY );
				}
			}
		}
	}
	
		private static void dibujaCarita( Graphics2D g2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4 ) {
			Polygon p = new Polygon();
			p.addPoint( x1, y1 ); p.addPoint( x2, y2 ); p.addPoint( x3, y3 ); p.addPoint( x4, y4 );
			// g2.drawPolygon( p );  // Si se quisieran solo dibujar los bordes
			g2.fillPolygon( p );
		}
	
	private static class PanelDibujo extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			dibujaCubo((Graphics2D)g);
		}
	}
	
}
