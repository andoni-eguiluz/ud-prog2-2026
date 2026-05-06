package tema5.debugger.ejercicio6_cine;
/**
 * EJERCICIO 6 - Cine de Acción: Sistema de Combate
 * =================================================
 * CLASE PRINCIPAL — ejecuta este archivo.
 *
 * MAPA DE ERRORES (7 errores distribuidos en 3 clases):
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │ ERROR 1 → Personaje.recibirDanio()        							 │
 * │ ERROR 2 → Personaje.curar()             							 │
 * │ ERROR 3 → Personaje.calcularPoderTotal()    					     │
 * │ ERROR 4 → Equipo.getMiembroMasFuerte()   							 │
 * │ ERROR 5 → Equipo.calcularPoderEquipo()                              │
 * │ ERROR 6 → Combate.determinarGanador()                               │
 * │ ERROR 7 → (consecuencia) 	                                         │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 * ESTRATEGIA DE DEBUGGING RECOMENDADA:
 *   PASO 1 — Pon breakpoints en main() antes de cada bloque de test.
 *   PASO 2 — Usa F5 para entrar en cada método y ver la lógica interna.
 *   PASO 3 — En Variables, expande los objetos Personaje para ver vida,
 *             vidaMaxima, estaVivo en tiempo real.
 *   PASO 4 — Usa "Display" (Ctrl+Shift+D sobre código seleccionado) para
 *             evaluar expresiones complejas como p.calcularPoderTotal().
 *   PASO 5 — Observa la pila de llamadas cuando un método llama a otro:
 *             main → simularCombateCompleto → simularRonda → atacar → recibirDanio
 *
 * RESULTADO ESPERADO TRAS CORREGIR TODOS LOS ERRORES:
 *   Test 1 - Personaje con exactamente 0 de vida: MUERTO ✓
 *   Test 2 - Curación sin superar máximo: 100/100 HP ✓
 *   Test 3 - Poder total de John Wick: (50+30)*5 + 25 = 425
 *   Test 4 - Miembro más fuerte del Equipo Héroes: John Wick
 *   Test 5 - Poder equipo con 1 eliminado: solo cuenta los vivos
 *   Test 6 - Combate completo: gana el equipo con más vivos
 */
public class EjercicioSeis {

    public static void main(String[] args) {

        // ── Crear personajes icónicos del cine de acción ──────────────────
        Personaje johnWick    = new Personaje("John Wick",    "John Wick 4",        100, 50, 30, 5);
        Personaje ethanHunt   = new Personaje("Ethan Hunt",   "Misión Imposible",   90,  45, 25, 4);
        Personaje domToretto  = new Personaje("Dom Toretto",  "Fast & Furious",     110, 40, 35, 4);
        Personaje jasonBourne = new Personaje("Jason Bourne", "El Mito de Bourne",  85,  48, 22, 4);

        Personaje terminador  = new Personaje("T-800",        "Terminator",         150, 55, 45, 6);
        Personaje agentSmith  = new Personaje("Agent Smith",  "Matrix",             120, 52, 40, 5);
        Personaje thanosHench = new Personaje("Corvus Glaive","Infinity War",       95,  50, 28, 4);

        // ══════════════════════════════════════════════════════════════════
        // TEST 1: recibirDanio() — ¿muere cuando vida llega exactamente a 0?
        // ══════════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════");
        System.out.println("TEST 1 — Vida llega exactamente a 0");
        System.out.println("═══════════════════════════════════════");
        Personaje cobaya = new Personaje("Cobaya Test", "Test", 50, 10, 0, 1);
        System.out.println("Antes: vida=" + cobaya.getVida() + " | vivo=" + cobaya.estaVivo());
        cobaya.recibirDanio(50);   // defensa=0 → daño efectivo=50 → vida debería ser 0
        System.out.println("Después de recibir 50 daño exacto:");
        System.out.println("  vida=" + cobaya.getVida() + " | vivo=" + cobaya.estaVivo());
        System.out.println("  ¿Correcto? vida=0 y vivo=false → " +
                (cobaya.getVida() == 0 && !cobaya.estaVivo() ? "✓ SÍ" : "✗ ERROR 1 AQUÍ"));
        System.out.println();

        // ══════════════════════════════════════════════════════════════════
        // TEST 2: curar() — ¿respeta vidaMaxima?
        // ══════════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════");
        System.out.println("TEST 2 — Curación sin superar máximo");
        System.out.println("═══════════════════════════════════════");
        Personaje herido = new Personaje("Herido Test", "Test", 100, 10, 0, 1);
        herido.recibirDanio(30);   // queda en 70 HP
        System.out.println("Después de 30 daño: vida=" + herido.getVida());
        herido.curar(9999);
        System.out.println("Después de curar 9999: vida=" + herido.getVida()
                + " | máximo=" + herido.getVidaMaxima());
        System.out.println("  ¿Correcto? vida=100 → " +
                (herido.getVida() == 100 ? "✓ SÍ" : "✗ ERROR 2 AQUÍ (vida=" + herido.getVida() + ")"));
        System.out.println();

        // ══════════════════════════════════════════════════════════════════
        // TEST 3: calcularPoderTotal() — ¿fórmula correcta?
        // Esperado: (50+30)*5 + 25 = 425
        // ══════════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════");
        System.out.println("TEST 3 — Poder total de John Wick");
        System.out.println("═══════════════════════════════════════");
        int poderEsperado = (johnWick.getAtaque() + johnWick.getDefensa()) * johnWick.getNivel()
                + (johnWick.getNivel() * 5);   // habilidades = nivel*5
        int poderReal     = johnWick.calcularPoderTotal();
        System.out.println("  ATQ=" + johnWick.getAtaque() + " DEF=" + johnWick.getDefensa()
                + " Nv=" + johnWick.getNivel() + " Habilidades=" + (johnWick.getNivel()*5));
        System.out.println("  Esperado: " + poderEsperado + " | Obtenido: " + poderReal);
        System.out.println("  ¿Correcto? → " + (poderReal == poderEsperado ? "✓ SÍ" : "✗ ERROR 3 AQUÍ"));
        System.out.println();

        // ══════════════════════════════════════════════════════════════════
        // TEST 4: getMiembroMasFuerte() — ¿devuelve el de mayor poder?
        // ══════════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════");
        System.out.println("TEST 4 — Miembro más fuerte del equipo");
        System.out.println("═══════════════════════════════════════");
        Equipo heroes = new Equipo("Héroes del Cine");
        heroes.agregarMiembro(ethanHunt);
        heroes.agregarMiembro(johnWick);    // debería ser el más fuerte
        heroes.agregarMiembro(domToretto);
        heroes.agregarMiembro(jasonBourne);
        heroes.imprimirEstado();
        Personaje masFuerte = heroes.getMiembroMasFuerte();
        System.out.println("Más fuerte detectado: " + masFuerte.getNombre());
        System.out.println("  ¿Correcto? debería ser John Wick → " +
                (masFuerte.getNombre().equals("John Wick") ? "✓ SÍ" : "✗ ERROR 4 AQUÍ"));
        System.out.println();

        // ══════════════════════════════════════════════════════════════════
        // TEST 5: calcularPoderEquipo() — ¿ignora los eliminados?
        // ══════════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════");
        System.out.println("TEST 5 — Poder equipo ignorando eliminados");
        System.out.println("═══════════════════════════════════════");
        int poderAntes = heroes.calcularPoderEquipo();
        System.out.println("Poder antes de eliminar a Ethan Hunt: " + poderAntes);
        ethanHunt.recibirDanio(9999);   // eliminar a Ethan Hunt
        System.out.println("Ethan Hunt eliminado. estaVivo=" + ethanHunt.estaVivo());
        int poderDespues = heroes.calcularPoderEquipo();
        System.out.println("Poder después: " + poderDespues);
        System.out.println("  ¿Correcto? debe ser MENOR que antes → " +
                (poderDespues < poderAntes ? "✓ SÍ" : "✗ ERROR 5 AQUÍ (contando eliminados)"));
        System.out.println();

        // ══════════════════════════════════════════════════════════════════
        // TEST 6: Combate completo
        // ══════════════════════════════════════════════════════════════════
        Equipo villanos = new Equipo("Villanos del Celuloide");
        villanos.agregarMiembro(terminador);
        villanos.agregarMiembro(agentSmith);
        villanos.agregarMiembro(thanosHench);

        // Recrear héroes sin el eliminado
        Equipo heroesFrescos = new Equipo("Héroes del Cine");
        heroesFrescos.agregarMiembro(new Personaje("John Wick",    "John Wick 4",       100, 50, 30, 5));
        heroesFrescos.agregarMiembro(new Personaje("Dom Toretto",  "Fast & Furious",    110, 40, 35, 4));
        heroesFrescos.agregarMiembro(new Personaje("Jason Bourne", "El Mito de Bourne",  85, 48, 22, 4));

        Combate combate = new Combate(heroesFrescos, villanos);
        combate.simularCombateCompleto();
    }
}
