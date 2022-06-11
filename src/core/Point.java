package core;

import java.util.Objects;

public record Point (double x, double y) {
	public static Point fromPolaire (double direction, double vitesse) {
		return new Point(Math.cos(direction) * vitesse, Math.sin(direction) * vitesse);
	}
	public Point add(double value) {
		return new Point(x + value, y + value);
	}
	public Point sub(double value) {
		return new Point(x - value, y - value);
	}
	public Point mul(double value) {
		return new Point(x * value, y * value);
	}
	public Point div(double value) {
		return new Point(x / value, y / value);
	}
	public Point add(Point p) {
		Objects.requireNonNull(p);
		return new Point(x + p.x, y + p.y);
	}
	public Point sub(Point p) {
		Objects.requireNonNull(p);
		return new Point(x - p.x, y - p.y);
	}
	public Point mul(Point p) {
		Objects.requireNonNull(p);
		return new Point(x * p.x, y * p.y);
	}
	public Point div(Point p) {
		Objects.requireNonNull(p);
		return new Point(x / p.x, y / p.y);
	}
	public double distancePow2From (Point p) {
		Objects.requireNonNull(p);
		return Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2);
	}
	public double distanceFrom (Point point) {
		Objects.requireNonNull(point);
		return Math.sqrt(this.distancePow2From(point));
	}
	public double distanceFrom0() {
		return Math.sqrt(x * x + y * y);
	}
}
