package tema1c.ejemplos.ajedrez.datos;

import java.awt.Point;

/**
 * Contrato para elementos visuales que pueden ser seleccionados
 * mediante un click del ratón.
 *
 * Lo implementan:
 *   - Pieza (y todas sus subclases): selección para mover.
 *   - Mina:   selección para coger y colocar en el tablero.
 *   - Escudo: selección para activar protección durante un turno.
 *
 * El bucle principal puede iterar sobre cualquier List<Seleccionable>
 * sin conocer la clase concreta de cada elemento.
 */
public interface Seleccionable {

    /**
     * Indica si el click (en coordenadas de píxel de la ventana) cae
     * dentro del área visual de este elemento.
     *
     * @param click coordenadas del click en píxeles
     * @return true si el click está sobre este elemento
     */
    boolean contieneClick(Point click);

    /**
     * Marca o desmarca este elemento como seleccionado.
     *
     * @param seleccionado true para seleccionar, false para deseleccionar
     */
    void setSeleccionado(boolean seleccionado);

    /**
     * Devuelve si el elemento está actualmente seleccionado.
     *
     * @return true si está seleccionado
     */
    boolean isSeleccionado();
}