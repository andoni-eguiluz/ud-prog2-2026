package tema1b.resueltos.ej1b9;

import javax.swing.JOptionPane;

public class Pelicula extends Multimedia {
	private String director_a;

	public Pelicula(String nombre, String cantante) {
		super(nombre);
		this.director_a = cantante;
	}

	public String getCantante() {
		return director_a;
	}

	public void setCantante(String cantante) {
		this.director_a = cantante;
	}
	
	@Override
	public void pedir() {
		super.pedir();
		director_a = JOptionPane.showInputDialog( "Introduce director/a:", director_a );
	}
	
	@Override
	public String toString() {
		return "Película " + getNombre() + " de " + director_a;
	}
	
}
