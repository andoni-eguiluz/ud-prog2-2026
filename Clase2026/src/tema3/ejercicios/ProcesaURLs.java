package tema3.ejercicios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

/** Clase de utilidad para proceso sencillo de páginas web a través de sus URLs públicas
 * Utilizada en ejercicio SacarEquiposFutbol
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class ProcesaURLs {

	/** Ejemplo de uso de la utilidad con equipos de fútbol de la web de Marca
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		// Hemos explorado la web www.marca.com (marzo de 2019) y vemos que
		// En la página https://www.marca.com/futbol/primera-division/calendario.html
		// está el calendario de la liga, y en él hay muchas líneas con la forma
		// <img src="https://e00-marca.uecdn.es/assets/sports ... alt="Girona"/>
		// y así con todos los equipos.
		// Y el charset es iso-8859-15   (<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-15"/>)
		// Hipótesis: buscamos todas esas líneas y encontraremos los equipos
		ArrayList<String> lineas = ProcesaURLs.buscaEnWeb( "https://www.marca.com/futbol/primera-division/calendario.html", 
				"<img src=\"https://e00-marca.uecdn.es/assets/", 
				"iso-8859-15" );
		for (String linea : lineas) {
			System.out.println( linea );
		}
	}
	
	/** Busca en una dirección web un substring determinado, y devuelve todas las líneas que lo contengan
	 * @param url	Dirección web
	 * @param busqueda	String a buscar
	 * @param charset	Juego de caracteres (para tildes y caracteres especiales)
	 * @return	Lista de todas las líneas que contienen ese String (vacía si no hay ninguna)
	 */
	public static ArrayList<String> buscaEnWeb( String url, String busqueda, String charset ) {
		ArrayList<String> lineas = devuelveTodasLasLineasSinExc( url, charset );
		ArrayList<String> lineasBuscadas = new ArrayList<>();
		for (String linea : lineas) {
			if (linea.contains( busqueda )) {
				lineasBuscadas.add( linea );
			}
		}
		return lineasBuscadas;
	}
	
	/** Devuelve todas las líneas de texto de una página web
	 * @param url	Dirección web
	 * @param charset	Juego de caracteres (para las tildes)
	 * @return	Lista de líneas de esa dirección (en texto)
	 * @throws MalformedURLException	Error si la URL es incorrecta
	 * @throws IOException	Error al abrir la conexión
	 * @throws UnknownHostException	Error de servidor inexistente
	 * @throws FileNotFoundException	Error de acceso a página inexistente (en algunos servidores)
	 * @throws ConnectException	Error de timeout
	 */
	private static ArrayList<String> devuelveTodasLasLineas( String url, String charset )
	throws MalformedURLException, IOException, UnknownHostException, FileNotFoundException, ConnectException
	{
		ArrayList<String> ret = new ArrayList<>();
		BufferedReader input = null;
		InputStream inStream = null;
		URLConnection connection = (new URL(url)).openConnection();
		connection.setDoInput(true);
		inStream = connection.getInputStream();
		input = new BufferedReader(new InputStreamReader(inStream,charset));
		String line = "";
		while ((line = input.readLine()) != null) {
			ret.add( line );
		}
		inStream.close();
		input.close();
		return ret;
	}
	
	// Versión sin excepciones (ver capítulo 6)
	private static ArrayList<String> devuelveTodasLasLineasSinExc( String url, String charset ) {
		try {
			return devuelveTodasLasLineas(url,charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
