package tema3.ejemplos;

import java.util.*;

public class RepasoMapas {
	
    public static void main(String[] args) {
        // Mapa: como el arraylist pero con clave en lugar de índice
        // arraylist l --> l.get(2)
        // mapa m --> m.get(clave) --> null si la clave no existe en ese mapa, valor si existe
        // mapa optimizado para buscar por clave - siempre es inmediato
        // mapa no optimizado para los valores - si no sabes la clave hay que recorrerlo entero
        // clave tiene que ser única
        // Métodos:
        //    mapa.put( k, v )
        //    mapa.get( k ) --> v
        //    mapa.remove( k ) 
        //    mapa.containsKey( k ) --> true o false
        //    mapa.keySet() --> conjunto de claves
        // Combinaciones: como quieras
        // Ejemplo: jugadores de futbol (dorsal) cada uno con acciones y varias inversiones en cada acción
        // ArrayList<HashMap<String,ArrayList<Inversion>>>
        // Ejemplo: personas (dni con letra) cada uno con acciones y varias inversiones en cada acción
        // HashMap<String,HashMap<String,ArrayList<Inversion>>>
        // EJemplo: nº de habitantes de ciudades en provincias en autonomías en países
        // HashMap<String,HashMap<String,HashMap<String,HashMap<String,Integer>>>>
        // Ejemplo: saldo de personas sabiendo su nombre, apellido1
        // HashMap<String-nombre,HashMap<String-apellido,Double>>
        // HashMap<String-apellido,HashMap<String-nombre,Double>>
        // Los mapas se suelen esconder en los objetos. Por ejemplo país-ciudad
        // HashMap<String,HashMap<String,Ciudad>>
        // HashMap<String,Pais>  ... Pais --> HashMap<String,Ciudad>
    	
    }

}
