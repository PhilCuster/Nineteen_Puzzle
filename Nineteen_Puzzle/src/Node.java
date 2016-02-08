
public class Node implements Denumerable, Comparable<Node> {
	private State state; //State held in this Node
	private int g; // Path Distance from Start.
	private int f; // g + h;
	private Node parentNode; // Null if start.
	public boolean explored; // If the node is explored or not.
	private int key;
	public int move;
	
	public Node(State _state, int _g, Node _parentNode) {
		state = _state;
		g = _g;
		f = g + state.getH();
        parentNode = _parentNode;
        explored = false;
	}
	
	public State getState() {
		return state;
	}

	public int getF() {
		return f;
	}

	@Override
	public int getNumber() {
		return key;
	}

	@Override
	public void setNumber(int x) {
		key = x;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node _parentNode) {
		parentNode = _parentNode;
	}

	public int getG() {
		return g;
	}

    public void setG(int _g) {
        g = _g;
    }

	public void setMove(int _move) {
		move = _move;
	}

    public void recalcF() {
        f = g + state.getH();
    }

	public State[] generateMoves() {
		State[] states = new State[4];
		int count = 0;
		int blankX = 0;
		int blankY = 0;
        int[][] newboard;
		for(int k = 0; k < 6; k++) {
			for(int l = 0; l < 6; l++) {
				if(state.getBoard()[k][l] == 0) {
					blankX = k;
					blankY = l;
				}
			}
		}
		//  Check the squares around the current one.
        // Order is up, left, right, down.
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
                newboard = copyState(state.getBoard());
				if (Math.abs(i) == Math.abs(j)) {
					continue;
				}
				else if(blankX + i > 5 || blankX + i < 0 || blankY + j > 5 || blankY + j < 0) {
                    count++;
					continue;
				}
				// Generate a state where the blank is swapped with the designated tile.
				int temp = newboard[blankX+i][blankY+j];
				if(temp == -1) {
                    states[count] = null;
                    count++;
					continue;
				}
				newboard[blankX+i][blankY+j] = 0;
				newboard[blankX][blankY] = temp;

				// Generate a new state.
				states[count] = new State(newboard);
				count++;
			}
		}
		return states;
	}

	@Override
	public int compareTo(Node o) {
		if(f < o.getF()) {
			return -1;
		}
		else if(f == o.getF()) {
			return 0;
		}
		else {
			return 1;
		}
	}

    private int[][] copyState(int[][] oldState) {
        int[][] newArray = new int[6][6];
        for(int i = 0; i < oldState.length; i++) {
            for(int j = 0; j < oldState[i].length; j++) {
                newArray[i][j] = oldState[i][j];
            }
        }
        return newArray;
    }
}
