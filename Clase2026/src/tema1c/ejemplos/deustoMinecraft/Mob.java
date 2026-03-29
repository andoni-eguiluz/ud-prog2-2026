package tema1c.ejemplos.deustoMinecraft;

public class Mob extends Entidad implements Movible {
    private boolean hostil;
    private double puntosVida;

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

}