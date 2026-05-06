package tema5.debugger.ejercicio1;
/**
 * EJERCICIO 1 - Bucle infinito y error de tipos
 * ==============================================
 * Objetivo: Encontrar errores en bucles y operaciones aritméticas.
 *
 * ERRORES A ENCONTRAR (usa el debugger, no los busques leyendo el código):
 *   - ERROR 1: El bucle while provoca una excepción. ¿Cuál? ¿En qué iteración?
 *   - ERROR 2: El resultado de "media" no es el esperado. Calcula a mano cuánto
 *              debería valer y compáralo con lo que imprime el programa.
 *
 * TÉCNICAS DE DEBUGGER A USAR:
 *   - Pon un breakpoint en la línea "suma += numeros[i]"
 *   - Usa F6 (Step Over) y observa la variable "i" en la vista Variables
 *   - Observa la variable "suma" acumularse en cada iteración
 *   - Cuando encuentres el error 1, corrígelo y busca el error 2
 *
 * RESULTADO ESPERADO:
 *   Suma total: 174
 *   Media: 24.857142857142858
 *   Máximo: 62
 */
public class EjercicioUno {

    public static void main(String[] args) {
        int[] numeros = {10, 25, 3, 47, 8, 19, 62};
        int suma = 0;
        int i = 0;

        while (i <= numeros.length) {
            suma += numeros[i];
            i++;
        }

        System.out.println("Suma total: " + suma);

        double media = suma / numeros.length;
        System.out.println("Media: " + media);

        int maximo = numeros[0];
        for (int j = 1; j < numeros.length; j++) {
            if (numeros[j] > maximo) {
                maximo = numeros[j];
            }
        }
        System.out.println("Máximo: " + maximo);
    }
}
