package deeptrip.utils;

import java.util.ArrayList;
public class Point {

	private static final int INITIAL_CAPACITY_X = 1024;
	private static final int INITIAL_CAPACITY_Y = 1024;
	private static final float GROWTH_FACTOR = 0.3f;
	private static final Object locker = new Object();
	private static ArrayList<ArrayList<Point>> points;
	private final int x;
	private final int y;

	static {
		points = new ArrayList<>(INITIAL_CAPACITY_X);
	}

	public static Point of(int x, int y) {
		Point p = null;

		if (x < 0 || y < 0) return p;

		if (x >= points.size()) {
			synchronized (locker) {
				if (x >= points.size()) {
					int growth = (x == 0)? (int)(1 * GROWTH_FACTOR) : (int)(points.size() * GROWTH_FACTOR);
					for (int i = points.size(); i <= x + growth; i++) {
						points.add(null);
					}
					points.set(x, new ArrayList<Point>(INITIAL_CAPACITY_Y));
				}
			}
		}
		if (y >= points.get(x).size()) {
			synchronized (locker) {
				if (y >= points.get(x).size()) {
					int growth = (y == 0)? (int)(1 * GROWTH_FACTOR) : (int)(points.get(x).size() * GROWTH_FACTOR);
					for (int i = points.get(x).size(); i <= y + growth; i++) {
						points.get(x).add(null);
					}
					points.get(x).set(y, null);
				}
			}
		}
		if (points.get(x).get(y) == null) {
			synchronized (locker) {
				if (points.get(x).get(y) == null) {
					points.get(x).set(y, new Point(x, y));
				}
			}
		}
		p = points.get(x).get(y);
		return p;
	}

	private Point(final int x, final int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	
}
