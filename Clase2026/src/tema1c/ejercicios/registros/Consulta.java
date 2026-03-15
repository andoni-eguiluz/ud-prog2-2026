package tema1c.ejercicios.registros;

import java.util.Date;

public class Consulta extends Registro {

	/** Crea una nueva consulta partiendo de una línea de texto con todos los datos en orden, separados por tabulador
	 * Ejemplo de línea: "Consulta	18/03/2022 12:00	andoni	Consulta de operaciones	9	92500"
	 * @param linea	Línea de texto de la que extraer los datos
	 * @return	nuevo objeto con esos datos, null si hay algún error
	 */
	public static Consulta crearDeLinea( String linea ) {
		if (!linea.startsWith("Consulta\t")) {
			return null;
		}
		String[] partes = linea.split( "\t" );
		Date fecha =  Registro.getFechaFromString( partes[1] );
		int valoracion = Integer.parseInt( partes[4] );
		int duracion = Integer.parseInt( partes[5] );
		Consulta nueva = new Consulta( fecha, partes[2], partes[3], valoracion, duracion );
		return nueva;
	}
	
	protected String usuario;
	protected String tipoConsulta;
	protected int gradoSatisfaccion;
	protected long tiempoAtencionMilis;
	public Consulta(Date fecha, String usuario, String tipoConsulta, int gradoSatisfaccion, long tiempoAtencion) {
		super(fecha);
		this.usuario = usuario;
		this.tipoConsulta = tipoConsulta;
		this.gradoSatisfaccion = gradoSatisfaccion;
		this.tiempoAtencionMilis = tiempoAtencion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTipoConsulta() {
		return tipoConsulta;
	}
	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}
	public int getGradoSatisfaccion() {
		return gradoSatisfaccion;
	}
	public void setGradoSatisfaccion(int gradoSatisfaccion) {
		this.gradoSatisfaccion = gradoSatisfaccion;
	}
	public long getTiempoAtencionMilis() {
		return tiempoAtencionMilis;
	}
	public void setTiempoAtencionMilis(long tiempoAtencion) {
		this.tiempoAtencionMilis = tiempoAtencion;
	}

	@Override
	public String toString() {
		return "{" + super.toString() + " " + usuario + " - " + tipoConsulta + " / " + tiempoAtencionMilis/1000 + " sgs.}";
	}
}
