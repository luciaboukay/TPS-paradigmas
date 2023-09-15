package queue;

import java.util.List;

public class NonEmptyStatusOfQ extends StatusOfQ {

	public boolean isEmpty() {
		return false;
	}

	public String take(List<String> queue) {
		String elementTaken = queue.get(0);
		queue.remove(elementTaken);
		return elementTaken;

	}

	public String head(List<String> queue) {
		return queue.get(0);
	}

}