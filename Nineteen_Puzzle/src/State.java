import java.util.*;


public class State {
	private ArrayList<Integer> board; // -1's represent unaccessible space.
	private int h; // Our heuristic measure.
	
	public State(ArrayList<Integer> _board) {
		board = _board;
	}
	
	public ArrayList<Integer> getBoard() {
		return board;
	}
	
	public void printBoard() {
		for(int i = 0; i < board.size(); i++) {
			for(int j = 0; j < 6; j++) {
				System.out.print(board.get(i) + " ");
			}
			System.out.println();
		}
	}
	
}
