package queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class QueueTest {

	private static String justATextAddedSecond = "Second";
	private static String justATextAddedFirst = "First";
	private static String justAText = "Something";

	@Test
	public void test01QueueShouldBeEmptyWhenCreated() {
		assertTrue(new Queue().isEmpty());
	}

	@Test
	public void test02AddElementsToTheQueue() {
		assertFalse(queuewithJustAText().isEmpty());
	}

	@Test
	public void test03AddedElementsIsAtHead() {
		assertEquals(justAText, queuewithJustAText().head());
	}

	@Test
	public void test04TakeRemovesElementsFromTheQueue() {
		Queue queue = queuewithJustAText();
		queue.take();
		assertTrue(queue.isEmpty());
	}

	@Test
	public void test05TakeReturnsLastAddedObject() {
		assertEquals(justAText, queuewithJustAText().take());
	}

	@Test
	public void test06QueueBehavesFIFO() {
		Queue queue = queueWithJustATextAddedFirstAndJustATextAddedSecond();

		assertEquals(queue.take(), justATextAddedFirst);
		assertEquals(queue.take(), justATextAddedSecond);
		assertTrue(queue.isEmpty());
	}

	@Test
	public void test07HeadReturnsFirstAddedObject() {
		assertEquals(queueWithJustATextAddedFirstAndJustATextAddedSecond().head(), justATextAddedFirst);
	}

	@Test
	public void test08HeadDoesNotRemoveObjectFromQueue() {
		Queue queue = queuewithJustAText();
		assertEquals(1, queue.size());
		queue.head();
		assertEquals(1, queue.size());
	}

	@Test
	public void test09SizeRepresentsObjectInTheQueue() {
		assertEquals(2, queueWithJustATextAddedFirstAndJustATextAddedSecond().size());
	}

	@Test
	public void test10CanNotTakeWhenThereAreNoObjectsInTheQueue() {
		assertThrowsLike(() -> new Queue().take(), EmptyStatusOfQ.emptyQueueError);
	}

	@Test
	public void test09CanNotTakeWhenThereAreNoObjectsInTheQueueAndTheQueueHadObjects() {
		Queue queue = queuewithJustAText();
		queue.take();
		assertThrowsLike(() -> queue.take(), EmptyStatusOfQ.emptyQueueError);
	}

	@Test
	public void test10CanNotHeadWhenThereAreNoObjectsInTheQueue() {
		assertThrowsLike(() -> new Queue().head(), EmptyStatusOfQ.emptyQueueError);
	}

	private Queue queuewithJustAText() {
		Queue queue = new Queue();
		queue.add(justAText);
		return queue;
	}

	private Queue queueWithJustATextAddedFirstAndJustATextAddedSecond() {
		Queue queue = new Queue();
		queue.add(justATextAddedFirst);
		queue.add(justATextAddedSecond);
		return queue;
	}

	private void assertThrowsLike(Executable executable, String message) {
		assertEquals(message, assertThrows(Exception.class, executable).getMessage());
	}
}