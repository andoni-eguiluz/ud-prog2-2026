package tema4.basicos.conceptoExcepciones;

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
import java.util.HashSet;

public class EjemploExcepcionesUrl {
	
	public static void main(String[] args) {
		sacarTiempoEnCiudadesDePrimera();
	}
	
	/** Busca en una dirección web un substring determinado, y devuelve todas las líneas que lo contengan
	 * @param url	Dirección web
	 * @param busqueda	String a buscar
	 * @param charset	Juego de caracteres (para tildes y caracteres especiales)
	 * @return	Lista de todas las líneas que contienen ese String (vacía si no hay ninguna)
	 */
	public static ArrayList<String> buscaEnWeb( String url, String busqueda, String charset ) {
		// TODO Pendiente gestionar errores directos con devuelveTodasLasLineas
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
	public static ArrayList<String> devuelveTodasLasLineas( String url, String charset )
	throws MalformedURLException, IOException, UnknownHostException, FileNotFoundException, ConnectException
	{
		ArrayList<String> ret = new ArrayList<>();
		BufferedReader input = null;
		InputStream inStream = null;
		System.setProperty("http.agent", "Chrome");  // Para que la consulta a web no sea identificada como un bot por el servidor
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
	
	// Versión sin excepciones (para probar las diferencias)
	private static ArrayList<String> devuelveTodasLasLineasSinExc( String url, String charset ) {
		try {
			return devuelveTodasLasLineas(url,charset);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	private static void sacarTiempoEnCiudadesDePrimera() {
		// De partida hemos explorado la web www.marca.com y vemos que
		// En la página https://www.marca.com/futbol/primera-division/calendario.html
		// Siempre está el calendario de la liga, y en él hay muchas líneas con la forma
		// <img src="https://e00-marca.uecdn.es/assets/sports ... alt="Girona"/>
		// Y el charset es iso-8859-15   (<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-15"/>)
		// Hipótesis: buscamos todas esas líneas y encontraremos los equipos
		ArrayList<String> lineas = buscaEnWeb( "https://www.marca.com/futbol/primera-division/calendario.html", "<img src=\"https://e00-marca.uecdn.es/assets/sports", "iso-8859-15" );
		ArrayList<String> equipos = new ArrayList<>();
		for (String linea : lineas) {
			int pos1 = linea.indexOf( "alt=" );
			int pos2 = linea.indexOf( "\"", pos1+5 );
			if (pos1!=-1 && pos2!=-1) {
				equipos.add( linea.substring( pos1+5, pos2 ) );
			}
		}
		quitaRepetidos( equipos );
		System.out.println( "Lista de equipos: " + equipos );
		for (String equipo : equipos) {
			visualizaEquipo( equipo );
		}
	}
	
	private static void quitaRepetidos( ArrayList<String> equipos ) {
		HashSet<String> equiposNoRep = new HashSet<>();
		for (String equipo : equipos) equiposNoRep.add( equipo );
		equipos.clear();
		for (String equipo : equiposNoRep) equipos.add( equipo );
	}
	
	private static void visualizaEquipo( String equipo ) {
		String ciudad = dejaMinusculas(equipo); // Quitamos las mayúsculas y ponemos en minúsculas
		int grados = getGradosDeCiudad( ciudad );
		System.out.println( "Equipo " + equipo + " - temperatura " + grados + "º" );
	}
	
	// Quita tildes y deja solo minúsculas
	private static String dejaMinusculas( String texto ) {
		texto = texto.toLowerCase();
		return texto.replaceAll( "á", "a" ).replaceAll( "é", "e" ).replaceAll( "í", "i" ).replaceAll( "ó", "o" ).replaceAll( "ú", "u" );
	}
	
	private static int getGradosDeCiudad( String ciudad ) {
		// De partida hemos explorado la web eltiempo.es y vemos que
		// Suele haber una URL  https://www.eltiempo.es/madrid.html   (con el nombre de la ciudad)
		// Y suele haber una línea que indica los grados:
		//    <span data-temp="9" ... 
		// Y el charset es UTF-8
		// En la web eltiempo.es era esto...
		// ArrayList<String> lineasGrados = buscaEnWeb( "https://www.eltiempo.es/" + ciudad + ".html", "data-temp", "UTF-8" );
		// Ahora (marzo 2022) miramos en otra web, www.tiempo.com:
		// y hemos visto que la temperatura está después de una marca "dato-temperatura changeUnitT"
		ArrayList<String> lineasGrados = buscaEnWeb( "https://www.tiempo.com/" + ciudad + ".htm", "dato-temperatura changeUnitT", "UTF-8" );
		// La primera es la que vale
		String linea = lineasGrados.get(0);
		// Buscamos en el formato que encontramos en la web el sitio de los grados
		// Antes con eltiempo.es era:
		// int primeraComilla = linea.indexOf( "span data-temp=\"" );
		// int segundaComilla = linea.indexOf( '"', primeraComilla+16 );
		// int temperatura = Integer.parseInt( linea.substring( primeraComilla+16, segundaComilla ) );
		// En www.tiempo.com (marzo 2022) es algo así como  <span class="dato-temperatura changeUnitT" data="7.25|0|">
		int marca = linea.indexOf( "dato-temperatura changeUnitT" );
		int posData = linea.indexOf( "data=", marca );
		int posFinal = linea.indexOf( "|", posData );
		int temperatura = (int) Math.round( Double.parseDouble( linea.substring( posData + 6, posFinal) ) );
		return temperatura;
	}
	
}
