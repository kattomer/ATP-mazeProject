package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {
    protected Stack<AState> openList;

    public DepthFirstSearch() {
        super();
        this.openList = new Stack<>();

    }
    @Override
    public Solution solve(ISearchable item) {
        if(item == null)
            return null;
        HashMap<String,AState> closedSet = new HashMap<>();

        AState startState = item.getStartState();
        AState goalState = item.getGoalState();
        openList.push(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.pop();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            closedSet.put(currentState.toString(), currentState);

            ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                if (!closedSet.containsKey(neighbor.toString()) && !openList.contains(neighbor)) {
                    neighbor.setFrom(currentState);
                    openList.push(currentState);
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