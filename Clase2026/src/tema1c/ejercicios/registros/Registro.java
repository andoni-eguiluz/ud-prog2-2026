package tema1c.ejercicios.registros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Registro {
	
	public static final SimpleDateFormat FORMATEADOR_FECHA = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
	public static Date getFechaFromString( String fechaEnString ) {
		try {
			Date fecha =  Registro.FORMATEADOR_FECHA.parse( fechaEnString );
			return fecha;
		} catch (ParseException e) {
			return null;
		}
		
	}

	/** Crea un nuevo registro partiendo de una línea de texto con todos los datos en orden, separados por tabulador
	 * El objeto que se crea depende de la línea y se instancia alguna de las clases hijas (PrecioCarburante, PrecioElectricidad, etc.)
	 * @param linea	Línea de texto de la que extraer los datos
	 * @return	nuevo registro con esos datos, null si hay algún error
	 */
	public static Registro crearDeLinea( String linea ) {
		Registro registro = PrecioCarburante.crearDeLinea( linea );
		if (registro!=null) {
			return registro;
		}
		registro = PrecioElectricidad.crearDeLinea( linea );
		if (registro!=null) {
			return registro;
		}
		registro = SesionUsuario.crearDeLinea( linea );
		if (registro!=null) {
			return registro;
		}
		registro = Consulta.crearDeLinea( linea );
		if (registro!=null) {
			return registro;
		}
		return null;
	}

	
	protected Date fecha;
	public Registro(Date fecha) {
		super();
		this.fecha = fecha;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		return FORMATEADOR_FECHA.format( fecha );
	}
	
}
