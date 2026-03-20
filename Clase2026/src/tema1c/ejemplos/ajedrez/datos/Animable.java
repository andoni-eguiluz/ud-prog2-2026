package tema1c.ejemplos.ajedrez.datos;

/**
 * Contrato para elementos que se actualizan solos cada fotograma,
 * independientemente de la interacción del usuario.
 *
 * Lo implementan:
 *   - Alfil:  baile continuo de rotación izquierda-derecha.
 *   - Reloj:  avance del segundero y minutero cada fotograma.
 *
 * El bucle principal llama a actualizar(deltaSeg) sobre todos los
 * ElementoVisual que implementen este interfaz, sin necesidad de
 * conocer su clase concreta.
 */
public interface Animable {

    /**
     * Actualiza el estado interno del elemento en función del tiempo
     * transcurrido desde el fotograma anterior.
     *
     * @param deltaSeg segundos transcurridos desde el último fotograma
     */
    void actualizar(double deltaSeg);
}