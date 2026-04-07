package tema3.ejercicios;

import java.util.*;

/** Clase contenedora para el ejercicio 3.8
 * @author andoni.eguiluz @ ingenieria.deusto.es
 *
 */
public class GestorAgentes {
	
	// Estructura de datos
	// TODO pendiente de definir
	
	/** Crea un gestor de agentes comerciales
	 */
	public GestorAgentes() {
		// TODO
	}
	
	/** Añade datos de un agente al gestor
	 * @param datosAgente	Datos del agente en formato "pp#localidad#agente#dni" donde pp es el código numérico de la provincia (entero de 1 a 52, según el código postal: https://es.wikipedia.org/wiki/Anexo:Provincias_de_Espa%C3%B1a_por_c%C3%B3digo_postal)
	 * 						Si el dato es incorrecto, no se añade nada.
	 */
	public void anyadirAgente( String datosAgente ) {
		// TODO
	}
	
	/** Devuelve la colección de códigos de provincias donde hay agentes comerciales, sin orden
	 * @return	Colección de códigos enteros
	 */
	public Collection<Integer> getProvincias() {
		// TODO
		return null;
	}
	
	/** Devuelve el conjunto de ciudades de una provincia en orden alfabético 
	 * @param provincia	Código de provincia (de 1 a 52)
	 * @return	Conjunto de ciudades de esa provincia, vacío si no hay ninguna ciudad en esa provincia
	 */
	public Set<String> getCiudades( int provincia ) {
		// TODO
		return null;
	}
	
	/** Devuelve la colección de agentes de una provincia y ciudad, en orden
	 * @param provincia	Código de provincia
	 * @param ciudad	Nombre de ciudad
	 * @return	Colección de agentes de esa ciudad y provincia, en orden alfabético de nombre de agente. Vacío si no hay agentes en esa ciudad y provincia
	 */
	public Collection<Agente> getAgentes( int provincia, String ciudad ) {
		// TODO
		return null;
	}

	/** Saca a consola la lista de agentes agrupada por provincia y ordenada por ciudad en el formato:
	 * pp:	Ciudad-1:  agente 1, agente 2, agente 3...
	 * 		Ciudad-2:  agente 1, agente 2...
	 * 		...
	 * pp:	Ciudad-1:  agente 1, agente 2...
	 */
	public void informeAgentes() {
		// TODO
	}
	
}
