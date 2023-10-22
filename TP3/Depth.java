package Nemo;

public abstract class Depth {
	private int depth;

	public abstract int increaseByOneUnit(Submarine nemo);

	public abstract int decreaseByOneUnit(Submarine nemo);

	public abstract void liberateCapsule();

	public int getDepthValue() {
		return depth;
	}

	public Depth setValue(int value) {
		this.depth = value;
		return this;
	}
}
