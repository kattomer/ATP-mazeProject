package algorithms.search;

import java.util.*;

// BreadthFirstSearch class implements the breadth-first search algorithm for solving search problems
public class BreadthFirstSearch extends ASearchingAlgorithm {
    private LinkedList<AState> openList; // Queue to manage open list of states

    // Constructor initializing the open list as a linked list
    public BreadthFirstSearch(){
        super();
        this.openList = new LinkedList<>();
    }

    // Solve method to find the solution for the given searchable item
    @Override
    public Solution solve(ISearchable item) {
        if (item == null)
            return null; // Return null if the item is null

        HashMap<String, AState> closedSet = new HashMap<>(); // Closed set to track visited states
        AState startState = item.getStartState(); // Get the start state
        AState goalState = item.getGoalState(); // Get the goal state

        openList.add(startState); // Add the start state to the open list

        while (!openList.isEmpty()) {
            AState currentState = openList.remove(); // Get and remove the first state from the open list
            numberOfNodesEvaluated++; // Increment the count of evaluated nodes

            if (currentState.equals(goalState)) {
                return new Solution(currentState); // Return solution if the goal state is reached
            } else {
                // Get all possible states from the current state
                ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
                for (AState neighbor : neighbors) {
                    // If the neighbor is not in the closed set and not in the open list
                    if (!closedSet.containsKey(neighbor.toString()) && !openList.contains(neighbor)) {
                        neighbor.setFrom(currentState); // Set the parent of the neighbor
                        closedSet.put(neighbor.toString(), neighbor); // Add the neighbor to the closed set
                        openList.add(neighbor); // Add the neighbor to the open list
                    }
                }
                closedSet.put(currentState.toString(), currentState); // Add the current state to the closed set
            }
        }

        // No solution found
        return null;
    }

    // Get the name of the search algorithm
    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
