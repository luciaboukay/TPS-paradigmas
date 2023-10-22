package Nemo;

import java.util.Objects;

public class Coordinate {
	private int x;
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate add(Coordinate coordinate) {
		return new Coordinate(x + coordinate.getXValue(), y + coordinate.getYValue());
	}

	public boolean equals(Object o) {
		return (this == o)
				|| (o != null && getClass() == o.getClass() && (x == ((Coordinate) o).x && y == ((Coordinate) o).y));
	}

	public int hashCode() {
		return Objects.hash(x, y);
	}

	public int getXValue() {
		return x;
	}

	public int getYValue() {
		return y;
	}
}
