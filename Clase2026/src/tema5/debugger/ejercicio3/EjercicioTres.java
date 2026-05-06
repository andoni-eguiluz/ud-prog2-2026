package tema5.debugger.ejercicio3;
/**
 * EJERCICIO 3 - Objetos, NullPointerException y lógica
 * =====================================================
 * Compuesto por dos clases: Estudiante y EjercicioTres (main).
 *
 * ERRORES A ENCONTRAR:
 *   - ERROR 1: El campo "email" nunca se inicializa en el constructor.
 *              Cuando se usa, lanza NullPointerException.
 *   - ERROR 2: calcularMedia() falla con arrays vacíos (division by zero).
 *   - ERROR 3: haAprobado() tiene el umbral incorrecto: un 5.0 exacto debería
 *              considerarse aprobado, pero no lo hace.
 *
 * TÉCNICAS DE DEBUGGER A USAR:
 *   - Breakpoint en e1.getResumen(). En la vista Variables, expande el objeto
 *     "e1" haciendo clic en la flecha: verás todos sus campos y sus valores.
 *   - Usa F5 (Step Into) para entrar dentro de getResumen() y ver exactamente
 *     qué línea lanza la excepción.
 *   - Pon un breakpoint en calcularMedia() y pasa el ratón sobre "notas"
 *     para ver el array completo (hover debug).
 *   - Para el error 3: crea un estudiante con media exactamente 5.0 y comprueba
 *     qué devuelve haAprobado().
 *
 * RESULTADO ESPERADO:
 *   Ana García | ana@ejemplo.com | Media: 6.3
 *   ¿Ha aprobado Ana? true
 *   Luis Pérez | sin-email@ejemplo.com | Media: 0.0
 *   ¿Ha aprobado Luis? false
 *   Carlos Ruiz | carlos@ejemplo.com | Media: 5.0
 *   ¿Ha aprobado Carlos? true   <-- con exactamente 5.0 debe ser true
 */

class Estudiante {
    private String nombre;
    private double[] notas;
    private String email;

    public Estudiante(String nombre, double[] notas) {
        this.nombre = nombre;
        this.notas = notas;
    }

    public Estudiante(String nombre, double[] notas, String email) {
        this.nombre = nombre;
        this.notas = notas;
        this.email = email;
    }

    public double calcularMedia() {
        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.length;
    }

    public String getResumen() {
        return nombre + " | " + email.toLowerCase() + " | Media: " + calcularMedia();
    }

    public boolean haAprobado() {
        return calcularMedia() > 5.0;
    }
}

public class EjercicioTres {
    public static void main(String[] args) {
        double[] notas1 = {6.5, 7.0, 4.5, 8.0, 5.5};
        double[] notasVacias = {};
        double[] notasCinco  = {5.0, 5.0, 5.0};

        Estudiante e1 = new Estudiante("Ana García",  notas1,      "ana@ejemplo.com");
        Estudiante e2 = new Estudiante("Luis Pérez",  notasVacias);          // usará constructor sin email
        Estudiante e3 = new Estudiante("Carlos Ruiz", notasCinco,  "carlos@ejemplo.com");

        System.out.println(e1.getResumen());
        System.out.println("¿Ha aprobado Ana? " + e1.haAprobado());

        System.out.println(e2.getResumen());
        System.out.println("¿Ha aprobado Luis? " + e2.haAprobado());

        System.out.println(e3.getResumen());
        System.out.println("¿Ha aprobado Carlos? " + e3.haAprobado());
    }
}
