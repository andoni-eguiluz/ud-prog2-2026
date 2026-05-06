package tema5.debugger.ejercicio6_cine;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Equipo — parte del Ejercicio 6.
 *
 * Representa un equipo de personajes de acción que combate unido.
 *
 * ERROR 4: getMiembroMasFuerte() 
 * ERROR 5: calcularPoderEquipo() 
 */
public class Equipo {

    private String          nombre;
    private List<Personaje> miembros;

    public Equipo(String nombre) {
        this.nombre   = nombre;
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Personaje p) {
        miembros.add(p);
    }

    /**
     * Devuelve el miembro con mayor poder total.
     *
     *
     */
    public Personaje getMiembroMasFuerte() {
        if (miembros.isEmpty()) return null;
        Personaje masFuerte = miembros.get(0);
        for (Personaje p : miembros) {
            if (p.calcularPoderTotal() < masFuerte.calcularPoderTotal()) {
                masFuerte = p;
            }
        }
        return masFuerte;
    }

    /**
     * Suma el poder de TODOS los miembros vivos del equipo.
     *
     *
     */
    public int calcularPoderEquipo() {
        int total = 0;
        for (Personaje p : miembros) {
            // ERROR 5: falta: if (p.estaVivo())
            total += p.calcularPoderTotal();
        }
        return total;
    }

    /**
     * Devuelve cuántos miembros siguen vivos.
     */
    public int contarVivos() {
        int vivos = 0;
        for (Personaje p : miembros) {
            if (p.estaVivo()) vivos++;
        }
        return vivos;
    }

    /**
     * Aplica curación a todos los miembros vivos del equipo.
     */
    public void curarEquipo(int cantidad) {
        for (Personaje p : miembros) {
            if (p.estaVivo()) {
                p.curar(cantidad);
            }
        }
    }

    // --- Getters ---
    public String          getNombre()   { return nombre; }
    public List<Personaje> getMiembros() { return miembros; }

    public void imprimirEstado() {
        System.out.println("--- Equipo: " + nombre
                + " | Vivos: " + contarVivos() + "/" + miembros.size()
                + " | Poder total: " + calcularPoderEquipo() + " ---");
        for (Personaje p : miembros) {
            System.out.println("  " + p);
        }
    }
}
