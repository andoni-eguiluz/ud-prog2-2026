package tema4.ejemplos.objetosATexto;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// TAREAS!!!
// TODO 1 - Solucionar error de compilación
// TODO 2 - Solucionar excepción de letra de DNI

public class PruebaFicherosTexto {

	private static ArrayList<Empleado> listaEmpleados = new ArrayList<>();
	private static ArrayList<Cliente> listaClientes = new ArrayList<>();
	private static ArrayList<Persona> listaPersonas = new ArrayList<>();
	
	public static void main(String[] args) {
		crearDatosDePrueba( listaEmpleados, listaClientes );
		listaPersonas.addAll( listaEmpleados ); 
		listaPersonas.addAll( listaClientes ); 
		listaPersonas.sort( new Comparator<Persona>() {
			@Override
			public int compare(Persona o1, Persona o2) {
				return o1.getDNI() - o2.getDNI();
			}
		} );
		System.out.println( "Empleados: " + listaEmpleados ); 
		System.out.println( "Clientes: " + listaClientes ); 
		System.out.println( "Todos:         " + listaPersonas ); 
		// TODO 3 - Guardar a fichero de texto la lista de empleados
		// TODO 3b - Mirar fichero de empleados. ¿Sería posible leerlo correctamente?
		// TODO 3c - Por qué entonces no es una buena práctica usar toString?
		// TODO 4 - Guardar a fichero de texto la lista de empleados con un método especializado aLinea() en una línea de texto
		// TODO 4b - ¿Qué separador debemos usar para que pueda haber una lectura posterior viable?
		// TODO 4c - ¿En qué casos no funcionaría bien la lectura posterior?
		// TODO 5 - Guardar con el mismo criterio la lista de clientes
		// TODO 6 - Guardar con el mismo criterio la lista de personas. 
		// TODO 6b - ¿Qué problema hay?
		// TODO 6c - ¿Cómo lo solucionas?
		// TODO 6d - Soluciónalo
		// TODO 7 - Carga la lista de personas del fichero de texto y comprueba que se ha cargado bien 
		//          con métodos constructores crearDesde( String[] ) en cada clase ¿qué errores pueden ocurrir?
		// TODO 8 - Y si hubiera composición, por ejemplo si Persona incluyera una lista de Reuniones a las que ha asistido?  (hay muchas personas en cada reunión)
		//          (Imaginemos una clase Reunion que tiene id, fecha, tema, duración, resumen, lista de personas participantes)
	}

	public static void crearDatosDePrueba(List<Empleado> empleados, List<Cliente> clientes) {
		// EMPLEADOS
		empleados.add(new Empleado(12345678, 'Z', "Ana", "López", 1983, "Gerente"));
		empleados.add(new Empleado(56789012, 'L', "Elena", "Martín", 1997, "RRHH"));  
		empleados.add(new Empleado(23456789, 'D', "Luis Alejandro", "Pérez", 1988, "Analista"));
		empleados.add(new Empleado(45678901, 'S', "Carlos", "Ruiz", 2005, "Tester")); 
		empleados.add(new Empleado(67890123, 'B', "Javier", "Sánchez", 2002, "Soporte"));
		empleados.add(new Empleado(34567890, 'V', "Marta", "García", 1999, "Programador"));
		// CLIENTES
		clientes.add(new Cliente(66666666, 'Q', "Cliente", "Seis", 1993, 6, 430.20));
		clientes.add(new Cliente(11111111, 'H', "Cliente", "Uno", 1991, 1, 1200.50));
		clientes.add(new Cliente(55555555, 'K', "Cliente", "Cinco", 1977, 5, 0.0));
		clientes.add(new Cliente(22222222, 'J', "Cliente", "Dos", 1983, 2, 350.75));
		clientes.add(new Cliente(33333333, 'P', "Cliente", "Tres", 1968, 3, -50.00));
		clientes.add(new Cliente(44444444, 'A', "Cliente", "Cuatro", 2001, 4, 9999.99));
	}	
	
}
