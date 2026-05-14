package tema5.junit.ejemplos.versionInicial;

public class MCD {
	
    /** Calcula y devuelve el máximo común divisor de dos números
     * Si los números son negativos, se consideran como si fueran positivos.
     * Si algún número es cero, el MCD es el otro número en positivo.
     * Si los dos números son cero, el MCD se considera +1.
     * @param num1	Primer número
     * @param num2	Segundo número
     * @return	Devuelve el mayor entero positivo que divide exactamente a ambos
     */
    public static int mcd( int num1, int num2 ) {
    	// Intento de implementación mediante el algoritmo de Euclides
        int mayor = Math.abs( Math.max( num1, num2 ) );
        int menor = Math.abs( Math.min( num1, num2 ) );
        do {
            int restoMayorEntreMenor = mayor % menor;
            if (restoMayorEntreMenor == 0) {
                return menor;
            }
            mayor = menor;
            menor = restoMayorEntreMenor; 
        } while (true);
    } 
    
}
