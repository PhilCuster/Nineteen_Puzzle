// Describes the move in the game.
public class Move {
	private String direction;
	private int number;
	
	public Move(String _direction, int _number)  {
		direction = _direction;
		number = _number;
	}
	
	public String getMove() {
		String combinedMove = number + direction;
		return combinedMove;
	}
}
