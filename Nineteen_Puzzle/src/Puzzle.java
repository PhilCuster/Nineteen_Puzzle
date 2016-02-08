import java.io.*;
import java.util.*;


public class Puzzle {

    public static int nodeCount = 0;

    public static HashMap coordBank = new HashMap(19);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

        // Input the starting state from the user.
        String[] strArray;
        int[][] startState = new int[6][6];
        System.out.println("Enter the starting state with commas between squares and whitespace for blanks:");
        Scanner sc = new Scanner(System.in);
        for(int read = 0; read < 6; read++) {
            strArray = sc.nextLine().split(" ");
            for(int p = 0; p < strArray.length; p++) {
                    startState[read][p] = Integer.parseInt(strArray[p]);
            }
        }



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

        Node startNode = new Node(new State(startState),0,null);
        System.out.println("Start state: ");
        startNode.getState().printBoard();

        // Create the HashMap
        HashMap hashStates = new HashMap();
        hashStates.put(startNode.getState(),startNode);

        // Create the final path.
        ArrayList<Node> finalPath = new ArrayList<Node>();

        // Create a priority queue.
        MinPQ<Node> queue = new MinPQ<Node>();

        // Add the start node to the queue.
        queue.add(startNode);
        updateCount();

        // While the queue is not empty.
        while(!queue.isEmpty()) {
            // Set node with lowest f_score to current.
            Node current_node = queue.remove();

            if(current_node.explored) {
                continue;
            }

            // If the current node is the goal than reconstruct the path.
            if(current_node.getState().getH() == 0) {
                int moveCount = 0;
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
                    moveCount++;
                }
                System.out.println("Nodes generated: " + nodeCount);
                System.out.println("Moves required: " + moveCount);
                return ;
            }

            // Mark the current node as explored.
            current_node.explored = true;

            // Generate the neighbor nodes of the current node.
            // First, generate all the possible moves, returns an array of states.
            State[] newStates = current_node.generateMoves();

            // Iterate through the neighbors.
            int count = 0;
            while(count < 4) {
                if(newStates[count] != null) {
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
                        Node newNode = new Node(newStates[count], current_node.getG() + 1, current_node);
                        queue.add(newNode);
                        hashStates.put(newStates[count], newNode);
                        updateCount();
                    }
                }
                count++;
            }
        }

        System.out.println("Solution not found...\n");
    }

    private static void updateCount() {
        nodeCount++;
        if(nodeCount % 50000 == 0) {
            System.out.println(nodeCount);
        }
    }
}
