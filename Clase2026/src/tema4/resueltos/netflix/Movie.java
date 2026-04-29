package tema4.resueltos.netflix;

import java.io.Serializable;

public class Movie extends Programa implements Serializable{
	
	private int duracionMinutos;

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Movie(String titulo, String director, String pais, int anno, String rating, int duracionMinutos) {
		super(titulo, director, pais, anno, rating);
		this.duracionMinutos = duracionMinutos;
	}
	public int getDuracionMinutos() {
		return duracionMinutos;
	}
	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}
	@Override
	public String toString() {
		return "Movie [duracionMinutos=" + duracionMinutos + ", getTitulo()=" + getTitulo() + ", getDirector()="
				+ getDirector() + ", getPais()=" + getPais() + ", getAnno()=" + getAnno() + ", getRating()="
				+ getRating() + "]";
	}
	
}
