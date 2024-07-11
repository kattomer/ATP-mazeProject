package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    private LinkedList<AState> openList;

    public BreadthFirstSearch(){
        super();
        this.openList = new LinkedList<>();

    }
    @Override
    public Solution solve(ISearchable item) {
        if(item == null)
            return null;
        HashMap<String,AState> closedSet = new HashMap<>();

        AState startState = item.getStartState();
        AState goalState = item.getGoalState();
        openList.add(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.remove();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            else{
                ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
                for (AState neighbor : neighbors) {
                    if (!closedSet.containsKey(neighbor.toString()) && !openList.contains(neighbor)) {
                        neighbor.setFrom(currentState);
                        closedSet.put(neighbor.toString(),neighbor);
                        openList.add(neighbor);
                    }
                }
                closedSet.put(currentState.toString(),currentState);
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
