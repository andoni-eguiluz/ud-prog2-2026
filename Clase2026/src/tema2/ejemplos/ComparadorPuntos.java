package tema2.ejemplos;

import java.awt.Point;
import java.util.Comparator;

public class ComparadorPuntos implements Comparator<Point> {
	@Override
	public int compare(Point o1, Point o2) {
		double dist1 = o1.distance(0, 0);
		double dist2 = o2.distance(0, 0);
		if (dist1 < dist2) {
			return -1;
		} else if (dist1 == dist2) {
			return 0;
		} else {
			return +1;
		}
	}
}
