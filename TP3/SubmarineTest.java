package Nemo;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class SubmarineTest {

	@Test
	public void test01SubmarineStartsAtSurface() {
		assertEquals(0, new Submarine().getDepth());
	}

	@Test
	public void test02SubmarineStartsFacingNorth() {
		assertEquals(new North(), new Submarine().getDirection());
	}

	@Test
	public void test03SubmarineStartsAtOriginWithNotAnyStartingCoordinates() {
		assertEquals(new Submarine().getLocation(), origin());
	}

	@Test
	public void test04SubmarineStartsAtSelectedCoordinates() {
		assertEquals(submarineAt1X2Y().getLocation(), new Coordinate(1, 2));
	}

	@Test
	public void test05SelectedCoordinatesDoNotChangeInitialDepthNorDirection() {
		assertDepthAndDirection(submarineAt1X2Y(), 0, new North());
	}

	@Test
	public void test06SubmarineStaysInTheSamePositionAndDirectionToEmptyString() {
		assertSubmarineConditions(nemoAfterCommand(""), origin(), 0, new North());

	}

	@Test
	public void test07SubmarineDescendsDepth() {
		assertSubmarineConditions(nemoAfterCommand("d"), origin(), -1, new North());
	}

	@Test
	public void test08SubmarineStaysAt0WhenTryingToAscendInSurfaceLevel() {
		assertEquals(0, nemoAfterCommand("u").getDepth());
	}

	@Test
	public void test09SubmarineAscendsCorrectlyWhenTryingToAscendNotInSurfaceLevel() {
		Submarine nemo = nemoAfterCommand("dd");
		assertEquals(-2, nemo.getDepth());
		nemo.move("u");
		assertEquals(-1, nemo.getDepth());
	}

	@Test
	public void test09MovingOneTimeDownAndOneTimeUpIsNeutralToDepthButNotOtherwise() {
		Submarine nemo = nemoAfterCommand("du");
		assertEquals(0, nemo.getDepth());
		Submarine nemo2 = nemoAfterCommand("ud");
		assertNotEquals(0, nemo2.getDepth());
	}

	@Test
	public void test10SubmarineFacesEastAfterRightRotation() {
		assertSubmarineConditions(nemoAfterCommand("r"), origin(), 0, new East());
	}

	@Test
	public void test11SubmarineFacesSouthAfterTwoRightRotation() {
		assertSubmarineConditions(nemoAfterCommand("rr"), origin(), 0, new South());
	}

	@Test
	public void test12SubmarineFacesWestAfterThreeRightRotation() {
		assertSubmarineConditions(nemoAfterCommand("rrr"), origin(), 0, new West());
	}

	@Test
	public void test13RotatingFourTimesToTheLeftTakesTheSubmarineToOriginalDirection() {
		assertSubmarineConditions(nemoAfterCommand("rrrr"), origin(), 0, new North());
	}

	@Test
	public void test14SubmarineFacesWestAfterLeftRotation() {
		assertSubmarineConditions(nemoAfterCommand("l"), origin(), 0, new West());
	}

	@Test
	public void test15SubmarineFacesSouthAfterTwoLeftRotations() {
		assertSubmarineConditions(nemoAfterCommand("ll"), origin(), 0, new South());
	}

	@Test
	public void test16SubmarineFacesSouthAfterThreeLeftRotations() {
		assertSubmarineConditions(nemoAfterCommand("lll"), origin(), 0, new East());
	}

	@Test
	public void test17RotatingFourTimesToTheLeftTakesTheSubmarineToOriginalDirection() {
		assertSubmarineConditions(nemoAfterCommand("llll"), origin(), 0, new North());
	}

	@Test
	public void test18RotatingOneTimeLeftAndOneTimeRightDoesNotAffectFinalDirection() {
		assertSubmarineConditions(nemoAfterCommand("lr"), origin(), 0, new North());
	}

	@Test
	public void test19RotatingThreeTimesLeftEqualsRotatingOneTimeRight() {
		Submarine nemo = nemoAfterCommand("r");
		assertSubmarineConditions(nemoAfterCommand("lll"), nemo.getLocation(), nemo.getDepth(), nemo.getDirection());
	}

	@Test
	public void test20RotatingSubmarineAndChangingDepthPerformCorrectly() {
		assertDepthAndDirection(nemoAfterCommand("dlu"), 0, new West());
	}

	@Test
	public void test21SubmarineMovesOneYUnitFowardWhileFacingNorth() {
		assertSubmarineConditions(nemoAfterCommand("f"), new Coordinate(0, 1), 0, new North());
	}

	@Test
	public void test23SubmarineMovesOneXUnitFowardWhileFacingEast() {
		assertSubmarineConditions(nemoAfterCommand("rf"), new Coordinate(1, 0), 0, new East());
	}

	@Test
	public void test24SubmarineMovesOneYUnitBackwardsWhileFacingSouth() {
		assertSubmarineConditions(nemoAfterCommand("rrf"), new Coordinate(0, -1), 0, new South());
	}

	@Test
	public void test25SubmarineMovesOneXUnitBackwardsWhileFacingWest() {
		assertSubmarineConditions(nemoAfterCommand("lf"), new Coordinate(-1, 0), 0, new West());
	}

	@Test
	public void test26ChangingDepthDoesNotAffectThePerformanceOfMovingForward() {
		assertSubmarineConditions(nemoAfterCommand("df"), new Coordinate(0, 1), -1, new North());
	}

	@Test
	public void test27CommandsAreExecutedInOrderOfStringEntry() {
		Submarine nemo = nemoAfterCommand("rurfdl");
		Submarine nemo2 = nemoAfterCommand("ldfrur");
		assertNotEquals(nemo.getLocation(), nemo2.getLocation());
		assertNotEquals(nemo.getDepth(), nemo2.getDepth());
	}

	@Test
	public void test28LiberatingCapsuleAtSurfaceLevelDoesNotDestroySubmarine() {
		nemoAfterCommand("m");
	}

	@Test
	public void test29LiberatingCapsuleAtFirtsLevelOfInmersionDoesNotDestroySubmarine() {
		nemoAfterCommand("dm");
	}

	@Test
	public void test30LiberatingCapsuleAtSurfaceLevelDoesNotAffectSubmarine() {
		assertSubmarineConditions(nemoAfterCommand("m"), origin(), 0, new North());
	}

	@Test
	void test31CapsuleIsLiberatedCorrectlyAfterTheExcecutionOfOtherCommands() {
		nemoAfterCommand("rllrflrddduuulfrdm");
	}

	@Test
	void test32SubmarineIsDestroyedAfterBeingCommandedLiberateCapsuleAtDeepLevelOfInmersion() {
		assertThrowsLike(() -> nemoAfterCommand("dd").move("m"));
	}

	@Test
	void test33SubmarineIsDestroyedCorrecltyAtDeepLevelOfInmersion() {
		Submarine nemo = nemoAfterCommand("lfflrrlddddlrfuud");
		assertEquals(-3, nemo.getDepth());
		assertThrowsLike(() -> nemo.move("m"));
	}

	private Coordinate origin() {
		return new Coordinate(0, 0);
	}

	private Submarine submarineAt1X2Y() {
		return new Submarine(1, 2);
	}

	private Submarine nemoAfterCommand(String command) {
		Submarine nemo = new Submarine();
		nemo.move(command);
		return nemo;
	}

	private void assertDepthAndDirection(Submarine nemo, int targetDepth, Direction targetDirection) {
		assertEquals(targetDepth, nemo.getDepth());
		assertEquals(targetDirection, nemo.getDirection());
	}

	private void assertSubmarineConditions(Submarine nemo, Coordinate targetLocation, int targetDepth,
			Direction targetDirection) {
		assertEquals(nemo.getLocation(), targetLocation);
		assertDepthAndDirection(nemo, targetDepth, targetDirection);
	}

	private void assertThrowsLike(Executable executable) {
		assertEquals(DeeperLevelOfImmersion.SubmarinoDestruidoException,
				assertThrows(Exception.class, executable).getMessage());
	}
}