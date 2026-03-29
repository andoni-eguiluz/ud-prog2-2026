package tema1c.ejemplos.deustoMinecraft;

import java.util.ArrayList;
import java.util.Collections;

public class MinecraftMain {
    public static void main(String[] args) {
        Mundo mundo = new Mundo();

        // Crear Jugadores
        Jugador j1 = new Jugador("Eva", 50, 70, 80);
        j1.getInventario().agregarObjeto(new Objeto("Espada de Diamante", Rareza.LEGENDARIO));
        j1.getInventario().agregarObjeto(new Objeto("Pico de Madera", Rareza.COMUN));

        Jugador j2 = new Jugador("Alex", 150, 20, 160);
        j2.getInventario().agregarObjeto(new Objeto("Manzana Dorada", Rareza.LEGENDARIO));

        // Crear Mobs
        Mob m1 = new Mob("Slime", 21, 21, true, 20.0);
        Mob m2 = new Mob("Murciélago", 55, 105, true, 20.0);
        Mob m3 = new Mob("Esqueleto", 25, 50, false, 10.0);
        Mob m4 = new Mob("Orco", 205, 80, false, 10.0);

        // Añadir a Mundo
        mundo.agregarEntidad(j1);
        mundo.agregarEntidad(j2);
        mundo.agregarEntidad(m1);
        mundo.agregarEntidad(m2);
        mundo.agregarEntidad(m3);
        mundo.agregarEntidad(m4);

        // Mover a un jugador
        System.out.println("Posición inicial de Steve: (" + j1.getX() + "," + j1.getY() + ")");
        j1.derecha();
        j1.arriba();
        System.out.println("Nueva posición de Steve: (" + j1.getX() + "," + j1.getY() + ")");

        // Ordenar jugadores por experiencia
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        for (Entidad e : mundo.getListaEntidades()) {
            if (e instanceof Jugador) {
                listaJugadores.add((Jugador) e);
            }
        }
        
        Collections.sort(listaJugadores);
        
        System.out.println("\nJugadores ordenados por XP (Mayor a Menor):");
        for (Jugador j : listaJugadores) {
            System.out.println(j);
        }

        // Probar métodos de consulta
        System.out.println("\nMobs hostiles en el mundo: " + mundo.countHostileMobs());
        System.out.println("Entidad en (21,21): " + mundo.getNombreEntidadEnPosicion(21, 21));
        System.out.println("Objetos legendarios de Eva: " + mundo.getObjetosLegendarios("Eva"));
    }
}