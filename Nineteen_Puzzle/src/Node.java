
public class Node {
	private State state; //State held in this Node
	private int pathToStart; // G-value
	private Node parentNode; // Null if start.
	private boolean explored; // If the node is explored or not.
	
	public Node(State _state) {
		state = _state;
	}
	
	public State getState() {
		return state;
	}
}
