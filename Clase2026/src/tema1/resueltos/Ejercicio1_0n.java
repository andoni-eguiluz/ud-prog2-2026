package tema1.resueltos;

import java.awt.Rectangle;

public class Ejercicio1_0n {
	
    public static void main(String[] args) {
        // 1. Crear el primer rectángulo
        Rectangle rect1 = new Rectangle(10, 10, 50, 100);
        
        // 2. Asignar rect1 a rect2 (aliasing)
        Rectangle rect2 = rect1;
        
        // 3. Imprimir dimensiones antes del cambio
        System.out.println("Dimensión de rect1 antes del cambio: " + rect1.width + "x" + rect1.height);
        System.out.println("Dimensión de rect2 antes del cambio: " + rect2.width + "x" + rect2.height);
        
        // 4. Modificar rect2
        rect2.setSize(200, 400);
        
        // 5. Imprimir dimensiones después del cambio
        System.out.println("Dimensión de rect1 después del cambio: " + rect1.width + "x" + rect1.height);
        System.out.println("Dimensión de rect2 después del cambio: " + rect2.width + "x" + rect2.height);
        
        // 6. Explicación
        System.out.println("Ambos rectángulos son el mismo objeto en memoria.");
    }
    
}