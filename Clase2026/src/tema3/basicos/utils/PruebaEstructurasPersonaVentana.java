package tema3.basicos.utils;

import java.util.*;

/** Prueba combinada de distintas estructuras de datos y distintos tamaños
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class PruebaEstructurasPersonaVentana {

	public static void main(String[] args) {
		String[] pruebas = { "ArrayList", "LinkedList", "HashSet", "TreeSet" };
		ArrayList<ProcesoProbable> procs = new ArrayList<ProcesoProbable>();
		procs.add( new AccesoAPersonasAL() );
		procs.add( new AccesoAPersonasLL() );
		procs.add( new AccesoAPersonasHS() );
		procs.add( new AccesoAPersonasTS() );
		VentanaBancoDePruebas vent = new VentanaBancoDePruebas();
		vent.setProcesos( pruebas, procs );
		vent.setTamanyos( 0, 20000, 1000 );
		vent.setVisible( true );
	}

	private static class AccesoAPersonasAL implements ProcesoProbable {

		private ArrayList<Persona> l;

		@Override
		public void init(int tamanyoTest) {
			l = new ArrayList<>();
			for (int i=0; i<tamanyoTest; i++) {
				l.add( new Persona( i*2+1, "Nombre " + i, "Apellido " + i ));
			}
		}

		public int cont;  // Se hace el contador atributo para que la actualización del contador del test no pueda ser optimizada (y eliminada) por el compilador
		@Override
		public Object test() {
			cont = 0;
			for (int i=0; i<l.size(); i++) {
				if (l.contains( new Persona(i,"","") )) cont++;
			}
			// System.out.println( "Número personas encontradas: " + cont );
			return l;
		}

	}

	private static class AccesoAPersonasHS implements ProcesoProbable {

		private HashSet<Persona> l;
		
		@Override
		public void init(int tamanyoTest) {
			l = new HashSet<>();
			for (int i=0; i<tamanyoTest; i++) {
				l.add( new Persona( i*2+1, "Nombre " + i, "Apellido " + i ));
			}
		}

		public int cont;  // Se hace el contador atributo para que la actualización del contador del test no pueda ser optimizada (y eliminada) por el compilador
		@Override
		public Object test() {
			cont = 0;
			for (int i=0; i<l.size(); i++) {
				if (l.contains( new Persona(i,"","") )) cont++;
			}
			// System.out.println( "Número personas encontradas: " + cont );
			return l;
		}

	}

	private static class AccesoAPersonasLL implements ProcesoProbable {

		private LinkedList<Persona> l;
		
		@Override
		public void init(int tamanyoTest) {
			l = new LinkedList<>();
			for (int i=0; i<tamanyoTest; i++) {
				l.add( new Persona( i*2+1, "Nombre " + i, "Apellido " + i ));
			}
		}

		public int cont;  // Se hace el contador atributo para que la actualización del contador del test no pueda ser optimizada (y eliminada) por el compilador
		@Override
		public Object test() {
			cont = 0;
			for (int i=0; i<l.size(); i++) {
				if (l.contains( new Persona(i,"","") )) cont++;
			}
			// System.out.println( "Número personas encontradas: " + cont );
			return l;
		}

	}

	private static class AccesoAPersonasTS implements ProcesoProbable {

		private TreeSet<Persona> l;
		
		@Override
		public void init(int tamanyoTest) {
			l = new TreeSet<>();
			for (int i=0; i<tamanyoTest; i++) {
				l.add( new Persona( i*2+1, "Nombre " + i, "Apellido " + i ));
			}
		}

		public int cont;  // Se hace el contador atributo para que la actualización del contador del test no pueda ser optimizada (y eliminada) por el compilador
		@Override
		public Object test() {
			cont = 0;
			for (int i=0; i<l.size(); i++) {
				if (l.contains( new Persona(i,"","") )) cont++;
			}
			// System.out.println( "Número personas encontradas: " + cont );
			return l;
		}

	}

	
	private static class Persona implements Comparable<Persona> {
		private int dni;
		private char letraDni;
		private String nombre;
		private String apellidos;

			private static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";
			/**
			 * Devuelve un NIF completo a partir de un DNI. Es decir, añade la letra del NIF<p>
			 * Algoritmo obtenido de http://es.wikibooks.org/wiki/Algoritmo_para_obtener_la_letra_del_NIF#Java
			 * @param dni dni al que se quiere añadir la letra del NIF
			 * @return NIF completo.
			 */
			public static char getLetraDNI(int dni) {
				return NIF_STRING_ASOCIATION.charAt(dni % 23);
			}
		  
		public Persona( int dni, String nombre, String apellidos ) {
			this.dni = dni;
			this.letraDni = getLetraDNI(dni);
			this.nombre = nombre;
			this.apellidos = apellidos;
		}
		
		public int getDni() {
			return dni;
		}
		public void setDni(int dni) {
			this.dni = dni;
		}
		public char getLetraDni() {
			return letraDni;
		}
		public void setLetraDni(char letraDni) {
			this.letraDni = letraDni;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellidos() {
			return apellidos;
		}
		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}

		@Override
		public String toString() {
			return dni + "-" + letraDni + ": " + nombre + " " + apellidos;
		}

		@Override
		public boolean equals( Object o ) {
			if (o instanceof Persona) {
				return dni==((Persona)o).dni;
			} else
				return false;
		}

		// PASO 3
		@Override
		public int hashCode() {
			return dni;  // El hashcode debe ser coherente con el equals
		}

		@Override
		public int compareTo(Persona o) {
			return dni-o.dni;
		}
		
	}
	
}
