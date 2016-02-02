import java.io.*;
import java.util.*;


public class Puzzle {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		
		// First, we need to input the game's starting state.
		ArrayList<Integer> input = new ArrayList<Integer>();
		BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.flush();
		for(int i = 0; i < 6; i++) {
			//Read in a line of input.
			String inputLine = standardInput.readLine();
			String[] chars = inputLine.split(" ", -1);
			System.out.println(Arrays.toString(chars));
			for (int j = 0; j < chars.length; j += 2) {
				int temp;
				if (chars[j].isEmpty()) {
					temp = -1;
				}
				else {
					temp = Integer.parseInt(chars[j]);
				}
				
				input.add(temp);
			}
			
		}
		// Now we create a node with a state that is the starting state.
		open.add(new Node(new State(input)));
//		open.get(0).getState().printBoard();
		String listString = "";

		for (int s : open.get(0).getState().getBoard())
		{
		    listString += s + " ";
		}
		System.out.println(listString);
		
	}

}
