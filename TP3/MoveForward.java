package Nemo;

public class MoveForward extends Command {

	public static String commandID = "f";

	public void executeCommand(Submarine nemo) {
		nemo.setLocation((nemo.getDirection()).forward(nemo.getLocation()));

	}

	public String getCommand() {
		return commandID;
	}
}
