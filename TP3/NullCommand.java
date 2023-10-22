package Nemo;

public class NullCommand extends Command {
	public static String commandID = "";

	public void executeCommand(Submarine nemo) {
	}

	public String getCommand() {
		return commandID;
	}
}
