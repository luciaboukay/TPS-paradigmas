package Nemo;

import java.util.Objects;

public abstract class Direction {
	public String type;

	public abstract Coordinate forward(Coordinate coordinate);

	public abstract Direction leftrotation();

	public abstract Direction rightrotation();

	public int hashCode() {
		return Objects.hash(type);
	}

	public boolean equals(Object obj) {
		return Objects.equals(type, ((Direction) obj).type);
	}

}
