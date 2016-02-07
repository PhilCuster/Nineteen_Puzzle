import java.io.*;
import java.util.*;


public class Puzzle {


    public static HashMap coordBank = new HashMap(19);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
//		ArrayList<Node> open = new ArrayList<Node>(); //  Nodes that need to be evaluated.
//		ArrayList<Node> closed = new ArrayList<Node>(); // Nodes that have already been evaluated.

		/*
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

		*/

		// Now we create a node with a state that is the starting state.
//		open.get(0).getState().printBoard();

        // Hard coded final state.
        int[][] finalStateArray = new int[][]{
                {-1, -1, 0, 1, -1, -1},
                {-1, -1, 2, 3, -1, -1},
                {4, 5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14, 15},
                {-1, -1, 16, 17, -1, -1},
                {-1, -1, 18, 19, -1, -1}
        };
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(finalStateArray[i][j] < 1) {
                    continue;
                }
                else {
                    coordBank.put(finalStateArray[i][j], new int[]{i,j});
                }
            }
        }
        State finalState = new State(finalStateArray);


		// For now we can just hard code the start state.
		int[][] startState = new int[][]{
                {-1, -1, 2, 1, -1, -1},
                {-1, -1, 6, 0, -1, -1},
                {4, 5, 7, 3, 8, 9},
                {10, 11, 12, 13, 14, 15},
                {-1, -1, 16, 17, -1, -1},
                {-1, -1, 18, 19, -1, -1}
        };
        Node startNode = new Node(new State(startState),0,null);
        System.out.println("Start state: ");
        startNode.getState().printBoard();
//        System.out.println(startNode.getState().getH());

        // Create the HashMap
        HashMap hashStates = new HashMap();
        hashStates.put(startNode.getState(),startNode);

        // Create the final path.
        ArrayList<Node> finalPath = new ArrayList<Node>();

        // Create a priority queue.
        MinPQ<Node> queue = new MinPQ<Node>();

        // Add the start node to the queue.
        queue.add(startNode);

        int iteration = 1;
        int maxG = 0;
        System.out.println("Current depth: " + maxG);

        // While the queue is not empty.
        while(!queue.isEmpty()) {
//            System.out.println("Iteration number " + iteration);

            iteration++;
//            System.out.println("Checking a new node!");
            // Set node with lowest f_score to current.
            Node current_node = queue.remove();
//            current_node.getState().printBoard();

            if(current_node.explored) {
//                System.out.println("-------------------------------------------");
                continue;
            }

            // If the current node is the goal than reconstruct the path.
            if(Arrays.deepEquals(current_node.getState().getBoard(), finalState.getBoard())) {
                while(true) {
                    if(current_node.getParentNode() == null) {
                        break;
                    }
                    finalPath.add(current_node);
                    current_node = current_node.getParentNode();

                }
                Collections.reverse(finalPath);
                for(Node curNode: finalPath) {
                    curNode.getState().printBoard();
                    System.out.println();
                }
                return ;
            }

            // Mark the current node as explored.
            current_node.explored = true;

            if(current_node.getG() > maxG) {
                maxG = current_node.getG();
                System.out.println("Current depth: " + maxG);
            }

            // Generate the neighbor nodes of the current node.
            // First, generate all the possible moves, returns an array of states.
            State[] newStates = current_node.generateMoves();
//            System.out.println("New moves generated!");

            // Iterate through the neighbors.
            int count = 0;
            while(count < 4) {
                if(newStates[count] != null) {
//                    System.out.println("Checking neighbor: " + count);
                    // First we check if it already exists.
                    Node neighbor = (Node) hashStates.get(newStates[count]);
                    if (neighbor != null) {
                        // Check to see if we have found a shorter path.
                        if (neighbor.getG() > (current_node.getG() + 1)) {
                            // We need to update the neightbor node.
                            neighbor.setParentNode(current_node);
                            neighbor.setMove(count);
                            neighbor.setG(current_node.getG() + 1);
                            neighbor.recalcF();
                            queue.update(neighbor);
                            // Next, check if it has already been explored.
                            if (neighbor.explored) {
                                neighbor.explored = false;
                            }
                        }
                    }
                    else {
                        // It has not been explored yet, create a new node, adding it to the queue.
                        queue.add(new Node(newStates[count], current_node.getG() + 1, current_node));
                    }
                }
                count++;
            }
//            System.out.println("-------------------------------------------");
        }

        for(Node curNode: finalPath) {
            curNode.getState().printBoard();
        }
    }

}
