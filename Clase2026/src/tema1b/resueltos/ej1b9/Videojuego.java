package tema1b.resueltos.ej1b9;

import javax.swing.JOptionPane;

public class Videojuego extends Multimedia {
	private boolean registrado;

	public Videojuego(String nombre, boolean registrado) {
		super(nombre);
		this.registrado = registrado;
	}

	public boolean isRegistrado() {
		return registrado;
	}

	public void setRegistrado(boolean registrado) {
		this.registrado = registrado;
	}

	@Override
	public void pedir() {
		super.pedir();
		Object resp = JOptionPane.showInputDialog( null, "¿Está registrado?", "Elige opción", JOptionPane.QUESTION_MESSAGE, null,
				new Object[] { "SÍ", "NO" }, "SÍ" );
		if (resp!=null) {
			registrado = "SÍ".equals((String) resp);
		}
	}
	
	@Override
	public String toString() {
		return "Videojuego " + getNombre() + (registrado ? " REGISTRADO" : " NO REGISTRADO");
	}
	
}
