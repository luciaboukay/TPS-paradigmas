package Nemo;

public class RightRotation extends Command {
	public static String commandID = "r";

	public void executeCommand(Submarine nemo) {
		nemo.setDirection((nemo.getDirection()).rightrotation());
	}

	public String getCommand() {
		return commandID;
	}
}
