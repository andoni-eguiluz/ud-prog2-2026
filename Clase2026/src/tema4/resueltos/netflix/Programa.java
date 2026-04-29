package tema4.resueltos.netflix;

public abstract class Programa {

	private String titulo;
	private String director;
	private String pais;
	private int anno;
	private String rating;
	public Programa() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Programa(String titulo, String director, String pais, int anno, String rating) {
		super();
		this.titulo = titulo;
		this.director = director;
		this.pais = pais;
		this.anno = anno;
		this.rating = rating;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Programa [titulo=" + titulo + ", director=" + director + ", pais=" + pais + ", anno=" + anno
				+ ", rating=" + rating + "]";
	}
	
	
	
}
