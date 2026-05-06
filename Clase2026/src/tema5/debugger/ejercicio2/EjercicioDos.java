package tema5.debugger.ejercicio2;
/**
 * EJERCICIO 2 - Recursividad y lógica incorrecta
 * ================================================
 * Objetivo: Depurar métodos recursivos usando la vista de pila de llamadas.
 *
 * ERRORES A ENCONTRAR:
 *   - ERROR 1: factorial(0) lanza StackOverflowError. ¿Por qué no para la recursión?
 *   - ERROR 2: sumaDigitos(1234) devuelve un resultado incorrecto.
 *              Valor esperado: 1+2+3+4 = 10
 *   - ERROR 3 (bonus): esPrimo() funciona correctamente pero es muy lento
 *              para números grandes. ¿Cómo se podría optimizar?
 *
 * TÉCNICAS DE DEBUGGER A USAR:
 *   - Pon un breakpoint DENTRO de factorial() y observa la vista "Debug"
 *     (la pila de llamadas). Haz clic en cada frame para ver las variables locales.
 *   - Para sumaDigitos(), ejecuta paso a paso con F5 (Step Into) y comprueba
 *     qué valor devuelve cada llamada recursiva.
 *   - Usa la vista "Expressions" para evaluar: n % 10  y  n / 10  con distintos valores.
 *
 * RESULTADO ESPERADO:
 *   Factorial de 0: 1
 *   Factorial de 5: 120
 *   Factorial de 10: 3628800
 *   ¿Es primo 17? true
 *   ¿Es primo 18? false
 *   Suma dígitos de 1234: 10
 *   Suma dígitos de 9999: 36
 */
public class EjercicioDos {

    /**
     * Calcula el factorial de n de forma recursiva.
     * factorial(0) = 1
     * factorial(n) = n * factorial(n-1)
     */
    public static long factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /**
     * Devuelve true si n es un número primo.
     */
    public static boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Suma todos los dígitos de un número entero positivo.
     * Ejemplo: sumaDigitos(1234) = 1 + 2 + 3 + 4 = 10
     */
    public static int sumaDigitos(int n) {
        if (n == 0) return 0;
        return (n / 10) + sumaDigitos(n % 10);
    }

    public static void main(String[] args) {
        System.out.println("Factorial de 0: " + factorial(0));   // Debería ser 1
        System.out.println("Factorial de 5: " + factorial(5));   // Debería ser 120
        System.out.println("Factorial de 10: " + factorial(10)); // Debería ser 3628800

        System.out.println("¿Es primo 17? " + esPrimo(17));     // Debería ser true
        System.out.println("¿Es primo 18? " + esPrimo(18));     // Debería ser false

        System.out.println("Suma dígitos de 1234: " + sumaDigitos(1234)); // Debería ser 10
        System.out.println("Suma dígitos de 9999: " + sumaDigitos(9999)); // Debería ser 36
    }
}
