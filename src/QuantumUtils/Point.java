package QuantumUtils;

public class Point {
	public int x;
	public int y;
	public Point(int x, int y) { this.x = x; this.y = y; }
	public boolean equals(Object o) { return this.x == ((Point)o).x && this.y == ((Point)o).y ;}
	public Point clone() { return new Point(this.x, this.y); }
}