package tema1.resueltos;

import java.awt.Dimension;

public class Ejercicio1_0m {

	public static void main(String[] args) {
        // 1. Crear un objeto Dimension con valores iniciales
        Dimension pantalla = new Dimension(1920, 1080);
        
        // 2. Mostrar la dimensión inicial
        System.out.println("Dimensión inicial: " + pantalla.width + "x" + pantalla.height);
        System.out.println("¿Es una pantalla grande? " + esPantallaGrande(pantalla));

        // 3. Modificar la dimensión a 1366x768
        pantalla.setSize(1366, 768);

        // 4. Mostrar la nueva dimensión
        System.out.println("Dimensión modificada: " + pantalla.width + "x" + pantalla.height);
        System.out.println("¿Es una pantalla grande? " + esPantallaGrande(pantalla));
    }

    // 5. Método para verificar si la pantalla es grande
    public static boolean esPantallaGrande(Dimension d) {
        return d.width >= 1920 && d.height >= 1080;
    }
    
}
