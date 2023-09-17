package queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class QueueTest {

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
		assertEquals("Something", queuewithJustAText().head());
	}

	@Test
	public void test04TakeRemovesElementsFromTheQueue() {
		Queue queue = queuewithJustAText();
		queue.take();
		assertTrue(queue.isEmpty());
	}

	@Test
	public void test05TakeReturnsLastAddedObject() {
		assertEquals("Something", queuewithJustAText().take());
	}

	@Test
	public void test06QueueBehavesFIFO() {
		Queue queue = queueWithJustATextAddedFirstAndJustATextAddedSecond();

		assertEquals(queue.take(), "First");
		assertEquals(queue.take(), "Second");
		assertTrue(queue.isEmpty());
	}

	@Test
	public void test07HeadReturnsFirstAddedObject() {
		assertEquals(queueWithJustATextAddedFirstAndJustATextAddedSecond().head(), "First");
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
	public void test11CanNotTakeWhenThereAreNoObjectsInTheQueueAndTheQueueHadObjects() {
		Queue queue = queuewithJustAText();
		queue.take();
		assertThrowsLike(() -> queue.take(), EmptyStatusOfQ.emptyQueueError);
	}

	@Test
	public void test12CanNotHeadWhenThereAreNoObjectsInTheQueue() {
		assertThrowsLike(() -> new Queue().head(), EmptyStatusOfQ.emptyQueueError);
	}

	private Queue queuewithJustAText() {
		Queue queue = new Queue();
		queue.add("Something");
		return queue;
	}

	private Queue queueWithJustATextAddedFirstAndJustATextAddedSecond() {
		Queue queue = new Queue();
		queue.add("First");
		queue.add("Second");
		return queue;
	}

	private void assertThrowsLike(Executable executable, String message) {
		assertEquals(message, assertThrows(Exception.class, executable).getMessage());
	}
}