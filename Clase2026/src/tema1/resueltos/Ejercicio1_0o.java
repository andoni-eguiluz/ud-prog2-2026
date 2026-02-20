package tema1.resueltos;

import java.math.BigInteger;

public class Ejercicio1_0o {
	
    public static void main(String[] args) {
        // Calcular Fibonacci(100)
        BigInteger fibonacci100 = fibonacci(100);
        System.out.println("Fibonacci(100): " + fibonacci100 );
        BigInteger fib500000 = fibonacci(500000);
        System.out.println("Fibonacci(500000): " + fib500000 );
        System.out.println( "  (" + (""+fib500000).length()  + " dígitos!) \n" );
        	// Más de 100.000 dígitos - Hasta con doubles un número así sería ya INFINITY)

        // Calcular 50!
        BigInteger factorial = factorial(50);
        System.out.println("50! = " + factorial + "\n" );
        
        // Comparación con los fibonacci calculados con tipos primitivos
        Ejercicio1_0j.main( null );
        System.out.println( "Fibonacci BigInteger:" );
        System.out.print( "1 1 ");
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ONE;
        BigInteger temp;
        for (int i = 3; i <= Ejercicio1_0j.TAMANYO_FIBS; i++) {
            temp = a.add(b);
            a = b;
            b = temp;
            System.out.print( b + " " );
        }
        System.out.println();
    }

    /** Calcula el número de fibonacci usando BigInteger
     * @param n	Número de fibonacci a calcular (2 o mayor)
     * @return	Resultado
     */
    public static BigInteger fibonacci(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger temp;
        for (int i = 2; i <= n; i++) {
            temp = a.add(b);
            a = b;
            b = temp;
        }
        return b;
    }

    /** Cálculo de factorial usando BigInteger
     * @param n	Número del que calcular el factorial
     * @return	Cálculo de factorial de n!
     */
    public static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
