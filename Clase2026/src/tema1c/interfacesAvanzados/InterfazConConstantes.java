	package tema1c.interfacesAvanzados;

/** Ejemplo de interfaz con constantes
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public interface InterfazConConstantes {
	
	// A un interfaz no se le pueden poner atributos de objeto, pero sí de clase:
	double PI = 3.1416;  // Atributo constante - funciona SIEMPRE como static final (no hay que ponerlo, pero es como si se pusiera)
	
	double area();  // Método normal de interfaz (public, no static)
	
}
