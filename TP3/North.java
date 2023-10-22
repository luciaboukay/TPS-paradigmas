package Nemo;

public class North extends Direction {

	public North() {
		type = "North";
	}

	public Coordinate forward(Coordinate coordinate) {
		return coordinate.add(new Coordinate(0, 1));
	}

	public Direction rightrotation() {
		return new East();
	}

	public Direction leftrotation() {
		return new West();
	}
}
