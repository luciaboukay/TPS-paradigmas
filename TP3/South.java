package Nemo;

public class South extends Direction {

	public South() {
		type = "South";
	}

	public Coordinate forward(Coordinate coordinate) {
		return coordinate.add(new Coordinate(0, -1));
	}

	public Direction rightrotation() {
		return new West();
	}

	public Direction leftrotation() {
		return new East();
	}
}
