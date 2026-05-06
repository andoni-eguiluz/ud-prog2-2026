package tema5.debugger.ejercicio4;
import java.util.*;

/**
 * EJERCICIO 4 - Colecciones, HashMap y lógica de negocio
 * =======================================================
 * Sistema de inventario para una tienda de informática.
 *
 * ERRORES A ENCONTRAR:
 *   - ERROR 1: agregarProducto() reemplaza el stock en lugar de sumarlo.
 *              "Teclado" debería tener 13 unidades (10+3), pero tiene 3.
 *   - ERROR 2: vender() permite stock negativo. Vender 8 ratones cuando
 *              solo hay 5 debería rechazarse, pero se acepta.
 *   - ERROR 3: productoMasVendido() usa getOrDefault con valor inicial 1
 *              en lugar de 0, inflando el conteo de cada producto.
 *   - ERROR 4: productoMasVendido() lanza excepción si historialVentas está vacío.
 *
 * TÉCNICAS DE DEBUGGER A USAR:
 *   - Watchpoint: clic derecho sobre "inventario" en Variables → Toggle Watchpoint.
 *     Eclipse parará CADA VEZ que se modifique el mapa. Muy útil para el error 1.
 *   - Breakpoint condicional en vender(): clic derecho sobre el breakpoint →
 *     Breakpoint Properties → Condition: "cantidad > stockActual"
 *     Así solo para cuando hay un problema real de stock.
 *   - Ejecuta productoMasVendido() paso a paso y observa el mapa "conteo"
 *     crecer con valores erróneos.
 *
 * RESULTADO ESPERADO:
 *   Teclado añadido: 10 unidades
 *   Ratón añadido: 5 unidades
 *   Teclado actualizado: 13 unidades (10 + 3)
 *   Venta OK: Teclado x2 → quedan 11
 *   Venta RECHAZADA: Ratón x8 (solo hay 5)
 *   Venta OK: Teclado x2 → quedan 9
 *   Venta RECHAZADA: Monitor (no existe)
 *   Inventario: {Teclado=9, Ratón=5}
 *   Más vendido: Teclado
 */
public class EjercicioCuatro {

    static Map<String, Integer> inventario = new HashMap<>();
    static List<String> historialVentas    = new ArrayList<>();

    public static void agregarProducto(String nombre, int cantidad) {
        inventario.put(nombre, cantidad);
        System.out.println(nombre + " → stock actual: " + inventario.get(nombre));
    }

    public static boolean vender(String nombre, int cantidad) {
        if (!inventario.containsKey(nombre)) {
            System.out.println("RECHAZADA: producto no encontrado: " + nombre);
            return false;
        }
        int stockActual = inventario.get(nombre);
        inventario.put(nombre, stockActual - cantidad);
        historialVentas.add(nombre);
        System.out.println("OK: vendidos " + cantidad + " x " + nombre
                + " | quedan: " + inventario.get(nombre));
        return true;
    }

    public static String productoMasVendido() {
        Map<String, Integer> conteo = new HashMap<>();
        for (String venta : historialVentas) {
            conteo.put(venta, conteo.getOrDefault(venta, 1) + 1);
        }
        return Collections.max(conteo.entrySet(),
                Map.Entry.comparingByValue()).getKey();
    }

    public static void main(String[] args) {
        agregarProducto("Teclado", 10);
        agregarProducto("Ratón", 5);
        agregarProducto("Teclado", 3);   // debería acumular → 13 total

        System.out.println("---");
        vender("Teclado", 2);
        vender("Ratón", 8);              // debería rechazarse (solo hay 5)
        vender("Teclado", 2);
        vender("Monitor", 1);            // no existe

        System.out.println("---");
        System.out.println("Inventario final: " + inventario);
        System.out.println("Producto más vendido: " + productoMasVendido());
    }
}
