package tema4.resueltos.netflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class NetxflixProgramacion {

	private ArrayList<Programa> listaProgramas = new ArrayList<>();
	
	
	public NetxflixProgramacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Programa> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(ArrayList<Programa> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	/***
	 * Ejercicio 1
	 * **/
	
	public void cargarDatosProgramas () {
		String nombreFichero = JOptionPane.showInputDialog("inserte el nombre del fichero");
		File file = new File(nombreFichero); //"programasclear.csv"
		try { 
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				String linea = scanner.nextLine();
				String[] datos = linea.split(",");
				Programa programa = null;
				if(datos[6].equals("Movie"))
					programa = new Movie(datos[0], datos[1], datos[2], Integer.valueOf(datos[3]), datos[4], Integer.valueOf(datos[5]));
				else programa = new TVShow(datos[0], datos[1], datos[2], Integer.valueOf(datos[3]), datos[4], Integer.valueOf(datos[5]));
				listaProgramas.add(programa);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * Ejercicio 2
	 * ***/
	
	public HashMap<String, Integer> cantidadPorTipos() {
		HashMap<String, Integer> mapa = new HashMap<>();
		for (Programa pr : listaProgramas) {
			if(pr instanceof Movie) {
				if(!mapa.containsKey("Movie"))
					mapa.put("Movie", 1);
				else mapa.put("Movie", mapa.get("Movie") + 1);
			} else {
				if(!mapa.containsKey("TV Show"))
					mapa.put("TV Show", 1);
				else mapa.put("TV Show", mapa.get("TV Show") + 1);
			}
		}
		return mapa;
	}
	
	/***
	 * Ejercicio 3
	 * **/
	
	public HashMap<String, ArrayList<Programa>> clasificacionPaises (){
		HashMap<String, ArrayList<Programa>> mapaClasificacion = new HashMap<>();
		for (Programa pr : listaProgramas) {
			if(!mapaClasificacion.containsKey(pr.getPais())){
				ArrayList<Programa> lista = new ArrayList<>();
				lista.add(pr);
				mapaClasificacion.put(pr.getPais(), lista);
			} else {
				mapaClasificacion.get(pr.getPais()).add(pr);
			}
		}
		return mapaClasificacion;
	} 
	
	/**
	 * Ejercicio 4
	 * **/
	
	public LinkedList<Programa> pilaProgramasAnno (int anno){
		LinkedList<Programa> pila = new LinkedList<>();
		for (Programa pr : listaProgramas) {
			if(pr.getAnno() == anno) {
				pila.addFirst(pr);  // push
			}
		}
		return pila;
	}
	
	
	/**
	 * Ejercicio 5
	 * **/
	public int annoMasProgramas () {
		HashMap<Integer, Integer> annoCantidadProgramas = new HashMap<>(); // clave: anno y valor: cantidad programas de un anno
		for (int i = 0; i < listaProgramas.size(); i++) {
			if (!annoCantidadProgramas.containsKey(listaProgramas.get(i).getAnno()))
				annoCantidadProgramas.put(listaProgramas.get(i).getAnno(), 1);
			else annoCantidadProgramas.put(listaProgramas.get(i).getAnno(), annoCantidadProgramas.get(listaProgramas.get(i).getAnno())+1);
		}
		
		System.out.println(annoCantidadProgramas);
		
		int anno = 0;
		int cantidadMayor = 0;
		for (Map.Entry<Integer, Integer> entry : annoCantidadProgramas.entrySet()) {
			Integer key = entry.getKey();
			Integer val = entry.getValue();
			if(val > cantidadMayor) {
				cantidadMayor = val;
				anno = key;
			}
		}
		return anno;
	}
	
	/***
	 * Ejercicio 6
	 * ***/
	public void guardarFicheroPaises () {
		HashMap<String, ArrayList<Programa>> mapaClasificacion = clasificacionPaises();
		String nombreFichero = JOptionPane.showInputDialog("Inserte el nombre del fichero de datos donde va a guardar la información");
		try {
			FileOutputStream fos = new FileOutputStream(nombreFichero);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(mapaClasificacion);
			oos.close();
			fos.close();
			JOptionPane.showMessageDialog(null, "Los datos se han guardado correctamente");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
