package tema1b.resueltos.ej1b9;

import javax.swing.JOptionPane;

public class Cancion extends Multimedia {
	private String cantante;

	public Cancion(String nombre, String cantante) {
		super(nombre);
		this.cantante = cantante;
	}

	public String getCantante() {
		return cantante;
	}

	public void setCantante(String cantante) {
		this.cantante = cantante;
	}
	
	@Override
	public void pedir() {
		super.pedir();
		cantante = JOptionPane.showInputDialog( "Introduce cantante:", cantante );
	}

	@Override
	public String toString() {
		return "Canción " + getNombre() + " de " + cantante;
	}

}
