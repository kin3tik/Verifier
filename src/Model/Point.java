package Model;

public class Point {
	/**
	 * Class just used as 'data point' for a signature. i.e. signature == ArrayList<Point>
	 */

private int t, x, y, p;
	
	public Point(int t, int x, int y, int p) {
		this.t = t;
		this.x = x;
		this.y = y;
		this.p = p;
	}
	
	public String toString() {
		return "("+t+", "+x+", "+y+", "+p+")";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getT() {
		return t;
	}

	public int getP() {
		return p;
	}
}
