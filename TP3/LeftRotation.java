package Nemo;

public class LeftRotation extends Command {
	public static String commandID = "l";

	public void executeCommand(Submarine nemo) {
		nemo.setDirection((nemo.getDirection()).leftrotation());
		;
	}

	public String getCommand() {
		return commandID;
	}
}