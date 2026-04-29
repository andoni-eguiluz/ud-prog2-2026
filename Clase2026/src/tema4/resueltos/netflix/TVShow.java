package tema4.resueltos.netflix;

import java.io.Serializable;

public class TVShow extends Programa implements Serializable{

	private int temporadas;

	public TVShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TVShow(String titulo, String director, String pais, int anno, String rating, int temporadas) {
		super(titulo, director, pais, anno, rating);
		this.temporadas = temporadas;
	}

	public int getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(int temporadas) {
		this.temporadas = temporadas;
	}
	@Override
	public String toString() {
		return "TVShow [temporadas=" + temporadas + ", getTitulo()=" + getTitulo() + ", getDirector()=" + getDirector()
				+ ", getPais()=" + getPais() + ", getAnno()=" + getAnno() + ", getRating()=" + getRating() + "]";
	}
	
	
}
