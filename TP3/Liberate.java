package Nemo;

public class Liberate extends Command {
	public static String commandID = "m";

	public void executeCommand(Submarine nemo) {
		nemo.lastDepthRegisterFromSubmarine().liberateCapsule();
	}

	public String getCommand() {
		return commandID;
	}
}