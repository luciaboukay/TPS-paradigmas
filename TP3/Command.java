package Nemo;

import java.util.ArrayList;

public abstract class Command {
	private static final ArrayList<Command> listOfCommands = new ArrayList<>();

	static {
		listOfCommands.add(new RightRotation());
		listOfCommands.add(new LeftRotation());
		listOfCommands.add(new MoveForward());
		listOfCommands.add(new Liberate());
		listOfCommands.add(new NullCommand());
		listOfCommands.add(new Descend());
		listOfCommands.add(new Ascend());
	}

	public boolean matches(String command) {
		return getCommand().equals(command);
	}

	public abstract void executeCommand(Submarine nemo);

	public abstract String getCommand();

	public static ArrayList<Command> getListOfCommands() {
		return listOfCommands;
	}
}
