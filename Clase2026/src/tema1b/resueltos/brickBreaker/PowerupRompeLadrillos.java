package tema1b.resueltos.brickBreaker;

import java.awt.Color;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Powerup que rompe ladrillos cuando se le toca, con un tiempo de vida (desaparece cuando se acabe su vida) 
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PowerupRompeLadrillos extends Bola {

	protected int vida;  // En milisegundos
	protected ArrayList<BloqueRompible> bloquesAfectados;  // Bloques afectados por el powerup
	protected ArrayList<ObjetoMovil> listaToquesEnCurso;
	protected boolean powerUpFuncionando = false;
	protected int cambioColor = 10;  // Cambia el color subiendo o bajando estas unidades en R,G,B
	
	/** Crea un powerup nuevo
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param radio Píxels de radio
	 * @param vida	Valor de vida del bloque (positivo)
	 */
	public PowerupRompeLadrillos(double x, double y, double radio, int vida ) {
		super( x, y, radio, Color.BLUE, Color.BLACK );
		this.vida = vida;
		listaToquesEnCurso = new ArrayList<>();
	}
	
	/** Define los bloques a los que afecta este powerup (a los que "mata" una unidad de vida cuando se activa) 
	 * @param bloquesAfectados
	 */
	public void setBloquesAfectados(ArrayList<BloqueRompible> bloquesAfectados) {
		this.bloquesAfectados = bloquesAfectados;
	}

	@Override
	public void dibujar( VentanaGrafica v ) {
		if (estaVivo()) {  // Solo se dibuja mientras esté vivo
			super.dibujar(v);
			v.dibujaImagen( "doublearrow.png", x, y, (int) radio*2, (int) radio*2, -1.0, 0.0, 1.0f );
			if (powerUpFuncionando) {
				v.dibujaRect( 0, y, v.getAnchura(), 10, 2f, Color.YELLOW, Color.RED );
			}
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "-{Vida " + vida + "}";
	}
	
	// Interfaz ObjetoConVida
	
	/** Devuelve la vida del bloque
	 * @return	Vida actual
	 */
	public int getVida() {
		return vida;
	}

	/** Decrementa la vida del bloque (en una unidad)
	 */
	public void decVida() {
		vida--;
	}	

	/** Modifica la vida del bloque
	 * @param alto	Nueva vida (positiva
	 */
	public void setVida(int vida) {
		this.vida = vida;
	}
	
	@Override
	public void mover(double milis) {
		super.mover(milis);
		vida -= milis;
		// Animación de coloreo
		int componenteColor = colorFondo.getRed();
		componenteColor += cambioColor;
		if (componenteColor < 0) {
			componenteColor = 0;
			cambioColor = -cambioColor;
		} else if (componenteColor > 255) {
			componenteColor = 255;
			cambioColor = -cambioColor;
		}
		setColorFondo( new Color( componenteColor, componenteColor, componenteColor ) );
	}
	
	/** Informa si el objeto está vivo (vida > 0)
	 * @return	true si el objeto está vivo, false si no
	 */
	public boolean estaVivo() {
		return vida>0;
	}	

	@Override
	public boolean esChocable() {
		return false;  // Nunca es chocable pero sí es tocable: cuando se busca su choque, si es con una bola activa su "disparo"
	}
	
	@Override
	public boolean chocaCon(ObjetoMovil objeto2) {
		boolean seTocan = super.chocaCon(objeto2);
		if (estaVivo() && objeto2 instanceof Bola) {  // Si el powerup está vivo y choca con una bola entonces se activa
			if (seTocan) { // Si hay toque
				if (!listaToquesEnCurso.contains(objeto2)) {  // Si ya estaba registrado, está en el mismo toque - no se hace nada
					// Si no estaba registrado el choque, se activa y se registra
					// System.out.println( "Activación!" );
					listaToquesEnCurso.add( objeto2 );
					powerUpFuncionando = true;
					// Efecto sobre los bloques afectados
					if (bloquesAfectados!=null) {
						for (BloqueRompible bloque : bloquesAfectados) {
							if (bloque.estaVivo()) {
								bloque.decVida();
							}
						}
					}
				}
			} else {
				if (listaToquesEnCurso.contains(objeto2)) {
					listaToquesEnCurso.remove( objeto2 );
					if (listaToquesEnCurso.isEmpty()) {
						powerUpFuncionando = false;
					}
				}
			}
		}
		return seTocan;
	}
	
}

