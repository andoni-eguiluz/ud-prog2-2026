package tema5.debugger.ejercicio5_cine;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Taquilla — parte del Ejercicio 5.
 *
 * Gestiona la cartelera de un cine y calcula estadísticas.
 *
 * ERROR 4: peliculaMasRentable() 
 * ERROR 5: calcularRecaudacionTotal() 
 */
public class Taquilla {

    private String          nombreCine;
    private List<Pelicula>  cartelera;
    private double          precioEntrada;   // €

    public Taquilla(String nombreCine, double precioEntrada) {
        this.nombreCine    = nombreCine;
        this.precioEntrada = precioEntrada;
        this.cartelera     = new ArrayList<>();
    }

    public void agregarPelicula(Pelicula p) {
        cartelera.add(p);
    }

    /**
     * Devuelve la película con mayor recaudación.
     *
     *
     */
    public Pelicula peliculaMasRentable() {
        Pelicula mejor = cartelera.get(0);   // ERROR 4: sin comprobación de vacío
        for (Pelicula p : cartelera) {
            if (p.getRecaudacion() > mejor.getRecaudacion()) {
                mejor = p;
            }
        }
        return mejor;
    }

    /**
     * Suma la recaudación solo de las películas que al menos recuperaron
     * su presupuesto (recaudacion >= presupuesto).
     *
     * 
     */
    public double calcularRecaudacionTotal() {
        double total = 0;
        for (Pelicula p : cartelera) {
        	total += p.getRecaudacion();
        }
        return total;
    }

    /**
     * Devuelve una lista de películas exitosas (según Pelicula.esExito()).
     */
    public List<Pelicula> getPeliculasExitosas() {
        List<Pelicula> exitosas = new ArrayList<>();
        for (Pelicula p : cartelera) {
            if (p.esExito()) {
                exitosas.add(p);
            }
        }
        return exitosas;
    }

    /**
     * Calcula cuántas entradas se necesitan vender para cubrir el presupuesto
     * de una película dada.
     */
    public long entradasParaCubrir(Pelicula p) {
        return (long) Math.ceil((p.getPresupuesto() * 1_000_000) / precioEntrada);
    }

    // --- Getters ---
    public String         getNombreCine()   { return nombreCine; }
    public double         getPrecioEntrada() { return precioEntrada; }
    public List<Pelicula> getCartelera()    { return cartelera; }

    @Override
    public String toString() {
        return String.format("Cine '%s' | %d películas | Entrada: %.2f€",
                nombreCine, cartelera.size(), precioEntrada);
    }
}
