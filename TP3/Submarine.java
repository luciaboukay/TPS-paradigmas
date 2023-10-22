package Nemo;

import java.util.ArrayList;
import java.util.Arrays;

public class Submarine {

	private Depth depth;
	private Coordinate location;
	private Direction direction;
	public ArrayList<Depth> depthRegister = new ArrayList<Depth>();

	public Submarine(int x, int y) {
		depth = new SurfaceLevel();
		location = new Coordinate(x, y);
		direction = new North();
		depthRegister.add(new SurfaceLevel());
	}

	public Submarine() {
		depth = new SurfaceLevel();
		location = new Coordinate(0, 0);
		direction = new North();
		depthRegister.add(new SurfaceLevel());
	}

	public void move(String commands) {
		Arrays.stream(commands.split("")).forEach(attemptedCommandID -> {
			Command.getListOfCommands().stream().filter(iteratedCommand -> iteratedCommand.matches(attemptedCommandID))
					.toList().get(0).executeCommand(this);
		});
	}

	public Depth lastDepthRegisterFromSubmarine() {
		return depthRegister.get(depthRegister.size() - 1);
	}

	public int getDepth() {
		return depth.getDepthValue();
	}

	public Coordinate getLocation() {
		return location;
	}

	public Direction getDirection() {
		return direction;
	}

	public ArrayList<Depth> getDepthRegister() {
		return depthRegister;
	}

	public void setDepth(int depthValue) {
		depth = depth.setValue(depthValue);
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
