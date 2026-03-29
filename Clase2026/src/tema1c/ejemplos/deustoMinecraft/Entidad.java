package tema1c.ejemplos.deustoMinecraft;

public abstract class Entidad {
	protected int id;
	protected String nombre;
	protected int x;
	protected int y;
	private static int contadorId = 0;

	public Entidad() {
		this.id = ++contadorId;
		this.nombre = "";
		this.x = 0;
		this.y = 0;
	}

	public Entidad(String nombre, int x, int y) {
		this.id = ++contadorId;
		this.nombre = nombre;
		this.x = x;
		this.y = y;
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "ID: " + id + " | Nombre: " + nombre + " | Pos: (" + x + "," + y + ")";
	}

}
