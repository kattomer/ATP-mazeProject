package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable item) {
        if(item == null)
            return null;
        Queue<AState> openList = new LinkedList<>();
        Set<AState> closedSet = new HashSet<>();

        AState startState = item.getStartState();
        AState goalState = item.getGoalState();

        openList.add(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.poll(); // retrieves and remove the first element - FIFO
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            closedSet.add(currentState);

            ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor) && !openList.contains(neighbor)) {
                    neighbor.setFrom(currentState);
                    openList.add(neighbor);
                }
            }
        }
        // No solution found
        return null;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
