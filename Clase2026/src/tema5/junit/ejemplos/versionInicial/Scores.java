package tema5.junit.ejemplos.versionInicial;

import java.util.*;

public class Scores {
	
	private HashMap<String,ArrayList<Integer>> mapaPunts;

//	Constructor de un nuevo gestor de Scores
	public Scores() {
		mapaPunts = new HashMap<>();
	}
	
	public void addUsuario( String nick ) {
		if (mapaPunts.containsKey(nick)) {
			// Nada
		} else {
			mapaPunts.put( nick, new ArrayList<>() );
		}
	}
	
	public void addScore( String nick, int score ) {
		if (!mapaPunts.containsKey(nick)) {
			mapaPunts.put( nick, new ArrayList<>() );
		}
		mapaPunts.get(nick).add( score );
	}
	
	/** Devuelve las puntuaciones almacenadas para un usuario
	 * @param nick	Usuario a consultar
	 * @return	Lista de sus puntuaciones, null si el usuario no existe
	 */
	public List<Integer> getScores( String nick ) {
		return mapaPunts.get(nick);
	}
	
	public Set<String> getUsuarios() {
		return mapaPunts.keySet();
	}
	
	/** Devuelve los usuarios ordenados por el número de partidas jugadas, de más a menos
	 * @return	Lista de los usuarios ordenados por ese criterio. Si tienen la misma fidelidad, se ordenan por orden alfabético
	 */
	public List<String> getUsuariosFieles() {
		ArrayList<String> l = new ArrayList<>();
		for (String claves : mapaPunts.keySet()) {
			l.add( claves );
		}
		l.sort( new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						int long1 = getScores(o1).size();
						int long2 = getScores(o2).size();
						int comp = long2-long1;  // -(long1-long2)
						if (comp==0) {
							comp = o1.compareTo(o2);
						}
						return comp;
					}
				}
		);
		return l;
	}

}
