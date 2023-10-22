package Nemo;

public class SurfaceLevel extends Depth {

	public void liberateCapsule() {
		return;
	}

	public int increaseByOneUnit(Submarine nemo) {
		return nemo.getDepth();
	}

	public int decreaseByOneUnit(Submarine nemo) {
		nemo.getDepthRegister().add(new FirstLevelOfImmersion());
		return nemo.getDepth() - 1;
	}
}
