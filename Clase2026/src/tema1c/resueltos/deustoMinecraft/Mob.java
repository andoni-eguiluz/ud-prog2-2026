package tema1c.resueltos.deustoMinecraft;

import java.awt.Color;
import java.io.Serializable;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Mob extends Entidad implements Movible, ConInteligencia, Chocable, Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean hostil;
    protected double puntosVida;

    public Mob() {
        super();
        this.hostil = false;
        this.puntosVida = 20.0;
    }

    public Mob(String nombre, int x, int y, boolean hostil, double puntosVida) {
        super(nombre, x, y);
        this.hostil = hostil;
        this.puntosVida = puntosVida;
    }
    
    // Constructor factoría indirecto para poder crear subclases
    
    public static Mob crearMob(String nombre, int x, int y, boolean hostil, double puntosVida, VentanaGrafica v) {
    	Mob m = null;
    	if ("Slime".equals(nombre)) {
    		m = new Slime( nombre, x, y, hostil, puntosVida, v );
    	} else if ("Esqueleto".equals(nombre)) {
    		m = new Esqueleto( nombre, x, y, hostil, puntosVida, v );
    	} else if ("Murciélago".equals(nombre)) {
    		m = new Murcielago( nombre, x, y, hostil, puntosVida, v );
    	} else if ("Orco".equals(nombre)) {
    		m = new Orco( nombre, x, y, hostil, puntosVida, v );
    	} else {
    		m = new Mob( "Otro", x, y, hostil, puntosVida );
    		m.setVentana( v );
    	}
    	return m;
    }
    
    public boolean isHostil() { return hostil; }
    public void setHostil(boolean hostil) { this.hostil = hostil; }
    public double getPuntosVida() { return puntosVida; }
    public void setPuntosVida(double puntosVida) { this.puntosVida = puntosVida; }

    @Override
    public String toString() {
        return super.toString() + " | Hostil: " + hostil + " | Vida: " + puntosVida;
    }
    
	// Implementación de Movible para mobs
	@Override
	public void arriba() {
		this.y++;
	}

	@Override
	public void abajo() {
		this.y--;
	}

	@Override
	public void izquierda() {
		this.x--;
	}

	@Override
	public void derecha() {
		this.x++;
	}

	// Interfaz Visualizable
	private int radio = 25;
	
	public void setRadio(int radio) {
		this.radio = radio;
	}
	
	public int getRadio() {
		return radio;
	}
	
	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		getVentana().dibujaCirculo( x, y, radio, 1.0f, Color.GREEN, Color.CYAN );  // Dibujado estándar de un mob no orco, ni esqueleto, ni otra subclase
// No hace falta alternativas en cuanto hay subclases
//		int dibujadoXMob = -1;
//		switch (nombre) {
//			case "Slime":
//				dibujadoXMob = 0;
//				break;
//			case "Murciélago":
//				dibujadoXMob = 125;
//				break;
//			case "Esqueleto":
//				dibujadoXMob = 250;
//				break;
//			case "Orco":
//				dibujadoXMob = 375;
//				break;
//		}
//		if (dibujadoXMob == -1) {
//			getVentana().dibujaCirculo( x, y, radio, 1.0f, Color.GREEN, Color.CYAN );
//		} else {
//			getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, radio*2, radio*2, 1.0, 0.0, 1.0f, dibujadoXMob, dibujadoXMob+124, 125, 249 );
//		}
	}

	// ConInteligencia
	
	@Override
	public void mover( Jugador j ) {
		// El mob por defecto no se mueve
	}
	
	// Interfaz Chocable
	@Override
	public boolean chocaCon(Entidad e) {
		if (e instanceof Mob) {
			return false;
		} else if (e instanceof Jugador || e instanceof Piedra) {
			int distX = e.x - this.x;
			int distY = e.y - this.y;
			double dist = Math.sqrt( distX*distX + distY*distY );
			return dist <= radio;
		} else {
			return false;
		}
	}

	@Override
	public void actuaConChoque(Entidad e) {
		if (e instanceof Piedra) {
			puntosVida--;
		}
	}
	
	
}