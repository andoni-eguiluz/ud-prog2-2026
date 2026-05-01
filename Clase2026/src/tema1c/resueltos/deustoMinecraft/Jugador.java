package tema1c.resueltos.deustoMinecraft;

public class Jugador extends Entidad implements Comparable<Jugador>, Movible, Chocable {
	private static final long serialVersionUID = 1L;
	
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

	
	// Interfaz Visualizable
	private static int RADIO_JUGADORES = 25;
	
	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, RADIO_JUGADORES*2, RADIO_JUGADORES*2, 1.0, 0.0, 1.0f, 0, 124, 0, 124 );
	}

	// Interfaz Chocable
	@Override
	public boolean chocaCon(Entidad e) {
		if (e instanceof Mob) {
			return ((Mob)e).chocaCon(this);
		} else {
			return false;
		}
	}
	
	@Override
	public void actuaConChoque(Entidad e) {
		if (e instanceof Mob) {
			if (((Mob)e).isHostil()) {  // Resta experiencia solo si es hostil
				experiencia -= 10;
			}
		}
	}
	
}
