package Nemo;

public class West extends Direction {

	public West() {
		type = "West";
	}

	public Coordinate forward(Coordinate coordinate) {
		return coordinate.add(new Coordinate(-1, 0));
	}

	public Direction rightrotation() {
		return new North();
	}

	public Direction leftrotation() {
		return new South();
	}
}
