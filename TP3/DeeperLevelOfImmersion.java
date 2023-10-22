package Nemo;

public class DeeperLevelOfImmersion extends Depth {

	public static String SubmarinoDestruidoException = "Submarino destruido";

	public void liberateCapsule() {
		throw new RuntimeException(SubmarinoDestruidoException);
	}

	public int increaseByOneUnit(Submarine nemo) {
		nemo.getDepthRegister().remove(nemo.getDepthRegister().size() - 1);
		return nemo.getDepth() + 1;
	}

	public int decreaseByOneUnit(Submarine nemo) {
		nemo.getDepthRegister().add(new DeeperLevelOfImmersion());
		return nemo.getDepth() - 1;
	}

}
