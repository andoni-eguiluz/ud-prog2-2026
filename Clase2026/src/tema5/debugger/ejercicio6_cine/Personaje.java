package tema5.debugger.ejercicio6_cine;
/**
 * EJERCICIO 6 - Cine de Acción: Sistema de Combate
 * =================================================
 * Temática: Simulación de personajes de películas de acción en combate.
 *
 * CLASES DEL EJERCICIO:
 *   - Personaje.java      (esta clase)
 *   - Equipo.java
 *   - Combate.java
 *   - EjercicioSeis.java  (main)
 *
 * ERRORES EN ESTA CLASE:
 *   - ERROR 1: recibirDanio() 
 *   - ERROR 2: curar() 
 *   - ERROR 3: calcularPoderTotal() 
 */
public class Personaje {

    private String nombre;
    private String pelicula;
    private int    vida;
    private int    vidaMaxima;
    private int    ataque;
    private int    defensa;
    private int    nivel;
    private int    habilidades;    // puntos extra de habilidades especiales
    private boolean estaVivo;

    public Personaje(String nombre, String pelicula,
                     int vidaMaxima, int ataque, int defensa, int nivel) {
        this.nombre      = nombre;
        this.pelicula    = pelicula;
        this.vidaMaxima  = vidaMaxima;
        this.vida        = vidaMaxima;
        this.ataque      = ataque;
        this.defensa     = defensa;
        this.nivel       = nivel;
        this.habilidades = nivel * 5;
        this.estaVivo    = true;
    }

    /**
     * Aplica daño al personaje. El daño efectivo se reduce con la defensa.
     * Fórmula: dañoEfectivo = max(1, danio - defensa)
     * Si vida llega a 0 o menos, el personaje muere.
     *
     * 
     */
    public void recibirDanio(int danio) {
        int danoEfectivo = Math.max(1, danio - defensa);
        vida -= danoEfectivo;
        if (vida < 0) {
            estaVivo = false;
        }
    }

    /**
     * Cura al personaje en la cantidad indicada.
     * La vida no puede superar vidaMaxima.
     *
     * 
     */
    public void curar(int cantidad) {
        vida += cantidad;
    }

    /**
     * El poder total combina el nivel base con las habilidades especiales.
     * Fórmula: poderTotal = (ataque + defensa) * nivel + habilidades
     *
     * 
     */
    public int calcularPoderTotal() {
        
        return (ataque + defensa) * nivel * habilidades;
    }

    public void subirNivel() {
        nivel++;
        habilidades += 5;
        vidaMaxima  += 20;
        vida        = vidaMaxima;    // recupera vida al subir nivel
        ataque      += 3;
        defensa     += 2;
    }

    // --- Getters ---
    public String  getNombre()     { return nombre; }
    public String  getPelicula()   { return pelicula; }
    public int     getVida()       { return vida; }
    public int     getVidaMaxima() { return vidaMaxima; }
    public int     getAtaque()     { return ataque; }
    public int     getDefensa()    { return defensa; }
    public int     getNivel()      { return nivel; }
    public boolean estaVivo()      { return estaVivo; }

    @Override
    public String toString() {
        String estado = estaVivo ? "VIVO [" + vida + "/" + vidaMaxima + " HP]" : "ELIMINADO";
        return String.format("%-20s | %-20s | Nv.%d | ATQ:%d DEF:%d | Poder:%d | %s",
                nombre, pelicula, nivel, ataque, defensa, calcularPoderTotal(), estado);
    }
}
