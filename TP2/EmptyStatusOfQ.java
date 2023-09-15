package queue;

import java.util.List;

public class EmptyStatusOfQ extends StatusOfQ {

	public static String emptyQueueError = "Queue is empty";

	public boolean isEmpty() {
		return true;
	}

	public String take(List<String> queue) {
		throw new RuntimeException(emptyQueueError);
	}

	public String head(List<String> queue) {
		throw new RuntimeException(emptyQueueError);
	}
}