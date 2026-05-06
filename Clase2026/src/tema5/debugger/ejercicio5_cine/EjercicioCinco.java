package tema5.debugger.ejercicio5_cine;
/**
 * EJERCICIO 5 - Cine de Acción: Sistema de Taquilla
 * ==================================================
 * CLASE PRINCIPAL — ejecuta este archivo.
 *
 * ERRORES DISTRIBUIDOS EN LAS CLASES (usa F5 para navegar entre ellas):
 *
 *   ERROR 1 → Pelicula.calcularPuntuacion()   
 *   ERROR 2 → Pelicula.esExito()              
 *   ERROR 3 → Actor.contarPeliculasDeAccion() 
 *   ERROR 4 → Taquilla.peliculaMasRentable() 
 *   ERROR 5 → Taquilla.calcularRecaudacionTotal() 
 *
 * ESTRATEGIA DE DEBUGGING:
 *   1. Ejecuta primero en modo normal y observa qué resultados son incorrectos.
 *   2. Lanza el debugger. Pon un breakpoint en cada llamada sospechosa en main().
 *   3. Usa F5 para ENTRAR en el método afectado y recorrerlo línea a línea.
 *   4. Usa F7 para volver al main cuando hayas visto suficiente.
 *   5. En Variables, expande los objetos para ver el estado completo.
 *
 * RESULTADO ESPERADO TRAS CORREGIR TODOS LOS ERRORES:
 *   === PELÍCULAS ===
 *   'Misión Imposible 7' (2023) | 300M€ presup. | 567M€ recaud. | puntuacion: 18.9
 *   'Fast & Furious 10' (2023) | 340M€ presup. | 704M€ recaud. | puntuacion: 20.7
 *   'John Wick 4' (2023) | 100M€ presup. | 440M€ recaud. | puntuacion: 44.0
 *   'Aquaman 2' (2023) | 205M€ presup. | 127M€ recaud. | puntuacion: 6.2
 *
 *   === TAQUILLA ===
 *   Película más rentable: 'John Wick 4'
 *   Películas exitosas (recaudan 2x): 3
 *   Recaudación total (sin pérdidas): 1711.0M€
 *
 *   === ACTORES ===
 *   Tom Cruise | Películas de acción: 2 | Ganancias: 40.0M€
 *   Vin Diesel | Películas de acción: 1 | Ganancias: 25.0M€
 *   Keanu Reeves | Películas de acción: 1 | Ganancias: 20.0M€
 */
public class EjercicioCinco {

    public static void main(String[] args) {

        // --- Crear películas ---
        Pelicula mi7    = new Pelicula("Misión Imposible 7", "Acción", 300, 567, 163, 2023);
        Pelicula ff10   = new Pelicula("Fast & Furious 10",  "Acción", 340, 704, 141, 2023);
        Pelicula jw4    = new Pelicula("John Wick 4",        "Acción", 100, 440, 169, 2023);
        Pelicula aq2    = new Pelicula("Aquaman 2",          "Acción", 205, 127, 124, 2023);
        Pelicula avatar = new Pelicula("Avatar 2",           "Ciencia Ficción", 460, 2320, 192, 2022);

        // --- Mostrar películas ---
        System.out.println("=== PELÍCULAS ===");
        System.out.println(mi7);
        System.out.println(ff10);
        System.out.println(jw4);
        System.out.println(aq2);
        System.out.println();

        // --- Taquilla ---
        Taquilla cinePalacio = new Taquilla("Cine Palacio", 9.50);
        cinePalacio.agregarPelicula(mi7);
        cinePalacio.agregarPelicula(ff10);
        cinePalacio.agregarPelicula(jw4);
        cinePalacio.agregarPelicula(aq2);

        System.out.println("=== TAQUILLA ===");
        Pelicula masRentable = cinePalacio.peliculaMasRentable();
        System.out.println("Película más rentable: '" + masRentable.getTitulo() + "'");

        int numExitosas = cinePalacio.getPeliculasExitosas().size();
        System.out.println("Películas exitosas (recaudan 2x presupuesto): " + numExitosas);

        double recaudTotal = cinePalacio.calcularRecaudacionTotal();
        System.out.println("Recaudación total (sin películas con pérdidas): " + recaudTotal + "M€");
        System.out.println("Entradas para cubrir John Wick 4: "
                + cinePalacio.entradasParaCubrir(jw4));
        System.out.println();

        // --- Taquilla vacía (para reproducir ERROR 4) ---
        System.out.println("=== TEST TAQUILLA VACÍA ===");
        Taquilla cineSinCartelera = new Taquilla("Cine Fantasma", 8.00);
        try {
            cineSinCartelera.peliculaMasRentable(); // ERROR 4 explota aquí
        } catch (Exception e) {
            System.out.println("EXCEPCIÓN capturada: " + e.getClass().getSimpleName()
                    + " → " + e.getMessage());
        }
        System.out.println();

        // --- Actores ---
        Actor tomCruise   = new Actor("Tom Cruise",   61, 20.0);
        Actor vinDiesel   = new Actor("Vin Diesel",   56, 20.0);
        Actor keanuReeves = new Actor("Keanu Reeves", 59, 15.0);

        tomCruise.agregarPelicula(mi7);
        tomCruise.agregarPelicula(avatar);  // no es de acción → no debe contar
        vinDiesel.agregarPelicula(ff10);
        vinDiesel.agregarPelicula(avatar);  // no es de acción → no debe contar
        keanuReeves.agregarPelicula(jw4);

        System.out.println("=== ACTORES ===");
        System.out.println(tomCruise);
        System.out.printf("  Ganancias totales: %.1fM€%n", tomCruise.calcularGananciasExitos());
        System.out.printf("  Mejor película: %s%n", tomCruise.getMejorPelicula().getTitulo());
        System.out.println();

        System.out.println(vinDiesel);
        System.out.printf("  Ganancias totales: %.1fM€%n", vinDiesel.calcularGananciasExitos());
        System.out.println();

        System.out.println(keanuReeves);
        System.out.printf("  Ganancias totales: %.1fM€%n", keanuReeves.calcularGananciasExitos());
    }
}
