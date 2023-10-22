package Nemo;

public class Ascend extends Command {
	public static String commandID = "u";

	public void executeCommand(Submarine nemo) {
		nemo.setDepth(nemo.lastDepthRegisterFromSubmarine().increaseByOneUnit(nemo));
	}

	public String getCommand() {
		return commandID;
	}
}
