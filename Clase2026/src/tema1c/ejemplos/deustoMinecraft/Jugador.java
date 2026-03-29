package tema1c.ejemplos.deustoMinecraft;

public class Jugador extends Entidad implements Comparable<Jugador>, Movible {
    private int experiencia;
    private Inventario inventario;

    public Jugador() {
        super();
        this.experiencia = 0;
        this.inventario = new Inventario();
    }

    public Jugador(String nombre, int x, int y, int experiencia) {
        super(nombre, x, y);
        this.experiencia = experiencia;
        this.inventario = new Inventario();
    }

    @Override
    public int compareTo(Jugador o) {
        // Ordenación de mayor a menor experiencia
        return Integer.compare(o.getExperiencia(), this.experiencia);
    }

    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }
    public Inventario getInventario() { return inventario; }
    public void setInventario(Inventario inventario) { this.inventario = inventario; }

    @Override
    public String toString() {
        return super.toString() + " | XP: " + experiencia;
    }
    
	// Implementación de Movible para jugadores
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
