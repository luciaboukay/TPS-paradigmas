package queue;

import java.util.ArrayList;
import java.util.List;

public class Queue {
	
    private StatusOfQ currentStateOfQ = StatusOfQ.emptyQueue();
    private List<StatusOfQ> historicalStatesOfQ = new ArrayList<>();
    private List<String> queue = new ArrayList<>();
  
    public Queue() {
    	historicalStatesOfQ.add(currentStateOfQ);
    }
    
    public boolean isEmpty() {
	    return lastHistoricalState().isEmpty();	
	}

	public Queue add( Object  cargo ) {
		currentStateOfQ = StatusOfQ.nonEmptyQueue();
		queue.add((String) cargo);
		historicalStatesOfQ.add(currentStateOfQ);
		return this;
	}

	public Object take() {
		currentStateOfQ = lastHistoricalState();
		historicalStatesOfQ.remove(currentStateOfQ);
		return currentStateOfQ.take(queue);
	}

	public Object head() {
		return currentStateOfQ.head(queue);
	}

	public int size() {
			return queue.size();
		
	}
	private StatusOfQ lastHistoricalState() {
		return historicalStatesOfQ.get(historicalStatesOfQ.size()-1);
	}
}