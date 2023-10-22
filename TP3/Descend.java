package Nemo;

public class Descend extends Command {
	public static String commandID = "d";

	public void executeCommand(Submarine nemo) {
		nemo.setDepth(nemo.lastDepthRegisterFromSubmarine().decreaseByOneUnit(nemo));
	}

	public String getCommand() {
		return commandID;
	}
}
