package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable item) {
        if(item == null)
            return null;
        Stack<AState> openList = new Stack<>();
        Set<String> closedSet = new HashSet<>();

        AState startState = item.getStartState();
        AState goalState = item.getGoalState();

        openList.push(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.pop();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            closedSet.add(currentState.toString());

            ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor.toString()) && !openList.contains(neighbor)) {
                    neighbor.setFrom(currentState);
                    openList.push(neighbor);
                }
            }
        }
        // No solution found
        return null;
    }

    @Override
    public String getName() {
        return "Depth First Search";
    }
}