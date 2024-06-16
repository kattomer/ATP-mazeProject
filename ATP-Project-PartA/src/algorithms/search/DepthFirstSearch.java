package algorithms.search;

import java.util.*;

// DepthFirstSearch class implements the depth-first search algorithm for solving search problems
public class DepthFirstSearch extends ASearchingAlgorithm {
    protected Stack<AState> openList; // Stack to manage open list of states

    // Constructor initializing the open list as a stack
    public DepthFirstSearch() {
        super();
        this.openList = new Stack<>();
    }

    // Solve method to find the solution for the given searchable item
    @Override
    public Solution solve(ISearchable item) {
        if (item == null)
            return null; // Return null if the item is null

        HashMap<String, AState> closedSet = new HashMap<>(); // Closed set to track visited states
        AState startState = item.getStartState(); // Get the start state
        AState goalState = item.getGoalState(); // Get the goal state

        openList.push(startState); // Add the start state to the open list

        while (!openList.isEmpty()) {
            AState currentState = openList.pop(); // Get and remove the top state from the open list
            numberOfNodesEvaluated++; // Increment the count of evaluated nodes

            if (currentState.equals(goalState)) {
                return new Solution(currentState); // Return solution if the goal state is reached
            }
            closedSet.put(currentState.toString(), currentState); // Add the current state to the closed set

            // Get all possible states from the current state
            ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                // If the neighbor is not in the closed set and not in the open list
                if (!closedSet.containsKey(neighbor.toString()) && !openList.contains(neighbor)) {
                    neighbor.setFrom(currentState); // Set the parent of the neighbor
                    openList.push(currentState); // Push the current state back to the stack
                    openList.push(neighbor); // Push the neighbor to the stack
                }
            }
        }

        // No solution found
        return null;
    }

    // Get the name of the search algorithm
    @Override
    public String getName() {
        return "Depth First Search";
    }
}
