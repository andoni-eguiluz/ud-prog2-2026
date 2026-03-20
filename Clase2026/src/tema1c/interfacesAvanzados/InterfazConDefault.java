package tema1c.interfacesAvanzados;

/** Ejemplo de interfaz con método default
 * @author andoni.eguiluz @ ingenieria.deusto.es
 *
 */
public interface InterfazConDefault {
	void comportamiento1();  // Método normal de interfaz
	default int comportamiento2() {  // Método por defecto - observa que este sí tiene código, pero no puede depender de atributos de objeto (no se pueden definir atributos de objeto), solo de métodos/atributos estáticos o de otros métodos que a su vez serán abstractos
		comportamiento1(); // Sí se puede llamar a un método abstracto
		return -1;
	}
}

class Ejemplo implements InterfazConDefault {
	@Override
	public void comportamiento1( ) {
		// algo
	}
}
