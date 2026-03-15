package tema1c.ejercicios.registros;

import java.util.Date;

public class SesionUsuario extends Registro {
	
	/** Crea una nueva sesión partiendo de una línea de texto con todos los datos en orden, separados por tabulador
	 * Ejemplo de línea: "DuracionSesion	18/03/2022 12:10	andoni	25500"
	 * @param linea	Línea de texto de la que extraer los datos
	 * @return	nuevo objeto con esos datos, null si hay algún error
	 */
	public static SesionUsuario crearDeLinea( String linea ) {
		if (!linea.startsWith("DuracionSesion\t")) {
			return null;
		}
		String[] partes = linea.split( "\t" );
		Date fecha =  Registro.getFechaFromString( partes[1] );
		int duracion = Integer.parseInt( partes[3] );
		SesionUsuario nueva = new SesionUsuario( fecha, partes[2], duracion );
		return nueva;
	}
	
	protected String usuario;
	int duracionSesionMilis;
	public SesionUsuario(Date fecha, String usuario, int duracionSesionMilis) {
		super(fecha);
		this.usuario = usuario;
		this.duracionSesionMilis = duracionSesionMilis;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getDuracionSesionMilis() {
		return duracionSesionMilis;
	}
	public void setDuracionSesionMilis(int duracionSesionMilis) {
		this.duracionSesionMilis = duracionSesionMilis;
	}

	@Override
	public String toString() {
		return "{" + super.toString() + " " + usuario + " - " + duracionSesionMilis/1000 + " sgs.}";
	}
	
}
