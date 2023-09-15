package queue;

import java.util.List;

public abstract class StatusOfQ {
	
	static StatusOfQ emptyQueue () {
		return new EmptyStatusOfQ();
	}
	static StatusOfQ nonEmptyQueue () {
		return new NonEmptyStatusOfQ();
	}
	
	public abstract boolean isEmpty();
	
	public abstract String take(List<String> queue);
	
	public abstract String head(List<String> queue);
	
	
}