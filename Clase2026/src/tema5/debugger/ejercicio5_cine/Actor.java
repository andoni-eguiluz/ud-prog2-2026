package tema5.debugger.ejercicio5_cine;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Actor — parte del Ejercicio 5.
 *
 * 
 */
public class Actor {

    private String       nombre;
    private int          edad;
    private List<Pelicula> filmografia;
    private double       cachePorPelicula;   // millones €

    public Actor(String nombre, int edad, double cachePorPelicula) {
        this.nombre           = nombre;
        this.edad             = edad;
        this.cachePorPelicula = cachePorPelicula;
        this.filmografia      = new ArrayList<>();
    }

    public void agregarPelicula(Pelicula p) {
        filmografia.add(p);
    }

    /**
     * Devuelve cuántas películas de acción tiene el actor en su filmografía.
     *
     * 
     */
    public int contarPeliculasDeAccion() {
        int contador = 0;
        for (Pelicula p : filmografia) {
            if (p.getGenero().equals("acción")) {   // ERROR 3
                contador++;
            }
        }
        return contador;
    }

    /**
     * Calcula el total ganado por el actor considerando solo películas exitosas.
     * (Solo cobra caché completo si la película es un éxito.)
     */
    public double calcularGananciasExitos() {
        double total = 0;
        for (Pelicula p : filmografia) {
            if (p.esExito()) {
                total += cachePorPelicula;
            } else {
                total += cachePorPelicula * 0.5;   // cobra la mitad si fracasa
            }
        }
        return total;
    }

    /**
     * Devuelve la película con mayor recaudación de su filmografía.
     * Devuelve null si no tiene películas.
     */
    public Pelicula getMejorPelicula() {
        if (filmografia.isEmpty()) return null;
        Pelicula mejor = filmografia.get(0);
        for (Pelicula p : filmografia) {
            if (p.getRecaudacion() > mejor.getRecaudacion()) {
                mejor = p;
            }
        }
        return mejor;
    }

    // --- Getters ---
    public String          getNombre()          { return nombre; }
    public int             getEdad()            { return edad; }
    public double          getCachePorPelicula() { return cachePorPelicula; }
    public List<Pelicula>  getFilmografia()     { return filmografia; }

    @Override
    public String toString() {
        return String.format("Actor: %s (%d años) | Caché: %.1fM€ | Películas: %d | De acción: %d",
                nombre, edad, cachePorPelicula, filmografia.size(), contarPeliculasDeAccion());
    }
}
