package Nemo;

public class East extends Direction {

	public East() {
		type = "East";
	}

	public Coordinate forward(Coordinate coordinate) {
		return coordinate.add(new Coordinate(1, 0));
	}

	public Direction rightrotation() {
		return new South();
	}

	public Direction leftrotation() {
		return new North();
	}
}