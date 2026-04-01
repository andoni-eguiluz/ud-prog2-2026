package utils.ideasProyectos.funcion2D;

/** Interfaz y paquete que permite tratar a las funciones 2D como objetos
 * Podr�a utilizarse para un proyecto de visualizaci�n matem�tica de funciones,
 * de representaci�n de valores funcionales, etc...
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public interface Funcion2D {
	/** Devuelve el valor y de una funci�n matem�tica determinada 
	 * de una sola variable: y = f(x)
	 * @param x	Valor (x) de la variable independiente
	 * @return	Valor (y) de la variable dependiente seg�n la funci�n
	 */
	double f( double x );
}
