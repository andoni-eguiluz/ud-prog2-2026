package tema1c.ejemplos.ajedrez.datos;

/**
 * Contrato para elementos visuales que pueden ser colocados en una
 * casilla del tablero por el jugador.
 *
 * Lo implementan:
 *   - Pieza (y todas sus subclases): movimiento normal de ajedrez.
 *   - Mina:  el jugador la coge de la zona lateral y la deposita
 *            en cualquier casilla libre del tablero.
 *
 * El Escudo NO implementa Movible: solo puede activarse desde su
 * posición fija en la zona lateral, no colocarse en el tablero.
 *
 * La distinción Seleccionable / Movible permite al bucle principal
 * preguntar: "¿puedo mover esto?" de forma polimórfica, sin instanceof.
 */
public interface Movible {

    /**
     * Mueve el elemento a la casilla indicada del tablero.
     *
     * @param fila    fila destino (0-7)
     * @param columna columna destino (0-7)
     */
    void moverA(int fila, int columna);

    /**
     * Indica si el elemento puede moverse a la casilla indicada.
     *
     * @param fila    fila destino (0-7)
     * @param columna columna destino (0-7)
     * @return true si el movimiento es legal para este elemento
     */
    boolean puedeMoverA(int fila, int columna);
}