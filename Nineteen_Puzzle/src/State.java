import java.util.Arrays;

public class State {
	private int[][] board; // -1's represent unaddressable space.
    private int h;
	
	public State(int[][] _board) {
		board = _board;
        h = calculateH();
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public void printBoard() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				if(board[i][j] == -1) {
					System.out.print("   ");
				}
				else if(board[i][j] == 0) {
					System.out.print("   ");
				}
				else if(board[i][j] >= 10) {
					System.out.print(board[i][j] + " ");
				}
				else {
					System.out.print(board[i][j] + "  ");
				}
			}
			System.out.println();
		}
	}

	public int calculateH() {
        int runningTotal = 0;
        int x1, x2, y1, y2;
        int[] tempArray;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(board[i][j] < 1) {
                    continue;
                }
                else {
                    x1 = i;
                    y1 = j;
                    tempArray = (int[]) Puzzle.coordBank.get(board[i][j]);
                    x2 = tempArray[0];
                    y2 = tempArray[1];
                    runningTotal += Math.abs(x1-x2) + Math.abs(y1-y2);
                }
            }

        }
        return runningTotal;
    }

    public int getH() {
        return h;
    }

	public boolean equals(Object o) {
        if(Arrays.deepEquals(board, ((State)o).getBoard())) {
            return true;
        }
        else return false;
    }

    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
