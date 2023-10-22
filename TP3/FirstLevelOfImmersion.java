package Nemo;

public class FirstLevelOfImmersion extends Depth {

	public void liberateCapsule() {
		return;
	}

	public int increaseByOneUnit(Submarine nemo) {
		nemo.getDepthRegister().remove(nemo.getDepthRegister().size() - 1);
		return 0;
	}

	public int decreaseByOneUnit(Submarine nemo) {
		nemo.getDepthRegister().add(new DeeperLevelOfImmersion());
		return -2;
	}
}
