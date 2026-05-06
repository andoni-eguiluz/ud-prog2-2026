package tema5.debugger.ejercicio5_cine;
/**
 * EJERCICIO 5 - Cine de Acción: Sistema de Taquilla
 * ==================================================
 * Temática: Gestión de películas de acción y sus recaudaciones.
 *
 * CLASES DEL EJERCICIO:
 *   - Pelicula.java       (esta clase)
 *   - Actor.java
 *   - Taquilla.java
 *   - EjercicioCinco.java (main)
 *
 * ERRORES A ENCONTRAR (navega entre clases con F5 / Step Into):
 *   - ERROR 1 (Pelicula): calcularPuntuacion()
 *   - ERROR 2 (Pelicula): esExito() 
 *   - ERROR 3 (Actor):    contarPeliculasDeAccion() 
 *   - ERROR 4 (Taquilla): peliculaMasRentable() 
 *   - ERROR 5 (Taquilla): calcularRecaudacionTotal()
 *                        
 *
 * TÉCNICAS A USAR:
 *   - F5 (Step Into) para saltar entre clases durante la ejecución.
 *   - F7 (Step Return) para volver al método llamante.
 *   - Expandir objetos en Variables para ver todos sus campos.
 *   - Hover sobre una variable para ver su valor sin parar.
 */
public class Pelicula {

    private String titulo;
    private String genero;
    private double presupuesto;     // en millones €
    private double recaudacion;     // en millones €
    private int    duracionMinutos;
    private int    anio;

    public Pelicula(String titulo, String genero, double presupuesto,
                    double recaudacion, int duracionMinutos, int anio) {
        this.titulo            = titulo;
        this.genero            = genero;
        this.presupuesto       = presupuesto;
        this.recaudacion       = recaudacion;
        this.duracionMinutos   = duracionMinutos;
        this.anio              = anio;
    }

    /**
     * Puntuación = (recaudacion / presupuesto) * 10
     * Una película con recaudacion == presupuesto debería puntuar 10.
     * Una que recauda el doble, 20. Una que pierde dinero, menos de 10.
     *
     * 
     */
    public double calcularPuntuacion() {
        return (presupuesto / recaudacion) * 10;
    }

    /**
     * Una película es un éxito si recauda al menos el doble de su presupuesto.
     *
     * 
     */
    public boolean esExito() {
        return recaudacion >= presupuesto * 1.5;
    }

    public double getBeneficio() {
        return recaudacion - presupuesto;
    }

    // --- Getters ---
    public String  getTitulo()          { return titulo; }
    public String  getGenero()          { return genero; }
    public double  getPresupuesto()     { return presupuesto; }
    public double  getRecaudacion()     { return recaudacion; }
    public int     getDuracionMinutos() { return duracionMinutos; }
    public int     getAnio()            { return anio; }

    @Override
    public String toString() {
        return String.format("'%s' (%d) | %.0fM€ presup. | %.0fM€ recaud. | puntuacion: %.1f",
                titulo, anio, presupuesto, recaudacion, calcularPuntuacion());
    }
}
