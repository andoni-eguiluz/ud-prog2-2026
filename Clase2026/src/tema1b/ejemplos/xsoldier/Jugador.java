package tema1b.ejemplos.xsoldier;

import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Jugador extends Personaje {
	protected int puntos;
	protected int teclaIzquierda;
	protected int teclaDerecha;
	protected int teclaDisparo;
	protected int potencia;
	protected ArrayList<Disparo> disparos;
	
	public Jugador(int x, int y, int vx, int vy, int r, String imagen, int puntos, int teclaIzquierda, int teclaDerecha,
			int teclaDisparo, int potencia, ArrayList<Disparo> disparos) {
		super(x, y, vx, vy, r, imagen);
		this.puntos = puntos;
		this.teclaIzquierda = teclaIzquierda;
		this.teclaDerecha = teclaDerecha;
		this.teclaDisparo = teclaDisparo;
		this.potencia = potencia;
		this.disparos = disparos;
	}
	
	public Jugador() {
		super();
		this.disparos = new ArrayList<Disparo>();
	}
	
	public Jugador(int x, int y, int teclaIzquierda, int teclaDerecha, int teclaDisparo) {
		super(x, y, 0, 0, 10, "player-up.png");
		this.puntos = 0;
		this.teclaIzquierda = teclaIzquierda;
		this.teclaDerecha = teclaDerecha;
		this.teclaDisparo = teclaDisparo;
		this.potencia = 5;
		this.disparos = new ArrayList<Disparo>();
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getTeclaIzquierda() {
		return teclaIzquierda;
	}

	public void setTeclaIzquierda(int teclaIzquierda) {
		this.teclaIzquierda = teclaIzquierda;
	}

	public int getTeclaDerecha() {
		return teclaDerecha;
	}

	public void setTeclaDerecha(int teclaDerecha) {
		this.teclaDerecha = teclaDerecha;
	}

	public int getTeclaDisparo() {
		return teclaDisparo;
	}

	public void setTeclaDisparo(int teclaDisparo) {
		this.teclaDisparo = teclaDisparo;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public ArrayList<Disparo> getDisparos() {
		return disparos;
	}

	public void setDisparos(ArrayList<Disparo> disparos) {
		this.disparos = disparos;
	}

	@Override
	public String toString() {
		return "Jugador [puntos=" + puntos + ", teclaIzquierda=" + teclaIzquierda + ", teclaDerecha=" + teclaDerecha
				+ ", teclaDisparo=" + teclaDisparo + ", potencia=" + potencia + ", disparos=" + disparos + ", x=" + x
				+ ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", r=" + r + ", imagen=" + imagen + "]";
	}
	
	public void mover(VentanaGrafica v) {
		if (this.x < 0) {
			this.x = 0;
			this.vx = 0;
		} else if (this.x > v.getAnchura()) {
			this.x = v.getAnchura();
			this.vx = 0;
		} else {
			this.x += this.vx;
		}
	}
	
}
