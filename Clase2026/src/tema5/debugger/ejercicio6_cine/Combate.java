package tema5.debugger.ejercicio6_cine;
import java.util.Random;

/**
 * Clase Combate — parte del Ejercicio 6.
 *
 * Gestiona los enfrentamientos entre personajes y entre equipos.
 *
 * ERROR 6: determinarGanador()
 * ERROR 7: simularRonda() 
 */
public class Combate {

    private Equipo   equipoA;
    private Equipo   equipoB;
    private int      ronda;
    private Random   rng;

    public Combate(Equipo equipoA, Equipo equipoB) {
        this.equipoA = equipoA;
        this.equipoB = equipoB;
        this.ronda   = 0;
        this.rng     = new Random(42);   // semilla fija para reproducibilidad
    }

    /**
     * Simula un ataque de atacante sobre defensor.
     * El daño tiene una variación aleatoria de ±20%.
     */
    public void atacar(Personaje atacante, Personaje defensor) {
        if (!atacante.estaVivo() || !defensor.estaVivo()) return;

        double variacion = 0.8 + (rng.nextDouble() * 0.4);   // entre 0.8 y 1.2
        int danioBase    = (int)(atacante.getAtaque() * variacion);

        System.out.printf("  %s ataca a %s con %d de daño%n",
                atacante.getNombre(), defensor.getNombre(), danioBase);

        defensor.recibirDanio(danioBase);   

        if (!defensor.estaVivo()) {
            System.out.println("  *** " + defensor.getNombre() + " ha sido eliminado ***");
        } else {
            System.out.printf("  %s tiene %d HP restantes%n",
                    defensor.getNombre(), defensor.getVida());
        }
    }

    /**
     * Simula una ronda completa: el miembro más fuerte de cada equipo ataca
     * al miembro más fuerte del equipo contrario.
     */
    public void simularRonda() {
        ronda++;
        System.out.println("\n=== RONDA " + ronda + " ===");

        Personaje campeA = equipoA.getMiembroMasFuerte();  // aquí puede manifestarse ERROR 4
        Personaje campeB = equipoB.getMiembroMasFuerte();

        if (campeA == null || campeB == null) {
            System.out.println("Un equipo no tiene miembros — ronda cancelada.");
            return;
        }

        // El equipo A ataca primero
        if (campeA.estaVivo()) atacar(campeA, campeB);
        // Luego el equipo B contraataca si sigue vivo
        if (campeB.estaVivo()) atacar(campeB, campeA);

        System.out.println();
        equipoA.imprimirEstado();
        equipoB.imprimirEstado();
    }

    /**
     * Determina el equipo ganador.
     *
     * 
     */
    public Equipo determinarGanador() {
        int vivosA = equipoA.contarVivos();
        int vivosB = equipoB.contarVivos();

        if (vivosA > vivosB) return equipoA;
        if (vivosB > vivosA) return equipoB;
        return equipoA;  
    }

    /**
     * Simula el combate completo hasta que un equipo quede sin miembros vivos.
     * Máximo 20 rondas para evitar combates eternos.
     */
    public void simularCombateCompleto() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     COMBATE: " + equipoA.getNombre()
                + " vs " + equipoB.getNombre());
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
        equipoA.imprimirEstado();
        equipoB.imprimirEstado();

        while (equipoA.contarVivos() > 0 && equipoB.contarVivos() > 0 && ronda < 20) {
            simularRonda();
        }

        System.out.println("\n=== FIN DEL COMBATE ===");
        Equipo ganador = determinarGanador();
        if (ganador == null) {
            System.out.println("¡EMPATE! Ambos equipos han sido eliminados.");
        } else {
            System.out.println("🏆 ¡GANADOR: " + ganador.getNombre() + "!");
            System.out.println("Miembros supervivientes: " + ganador.contarVivos());
            System.out.println("Poder del equipo: " + ganador.calcularPoderEquipo());
        }
    }
}
