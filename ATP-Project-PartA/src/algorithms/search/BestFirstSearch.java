package algorithms.search;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch {

    @Override
    public Solution solve(ISearchable item) {

        class  costComparator implements Comparator<AState>{
            @Override
            public int compare(AState s1, AState s2) {
                return Double.compare(s1.getCost(), s2.getCost());
            }
        }

        PriorityQueue<AState> openList = new PriorityQueue<>(new costComparator());
        Set<AState> closedSet = new HashSet<>();

        AState startState = item.getStartState();
        AState goalState = item.getGoalState();

        startState.setCost(0);
        openList.add(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.poll();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            closedSet.add(currentState);

            ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor) && !openList.contains(neighbor)) {
                    neighbor.setFrom(currentState);
                    neighbor.setCost(currentState.getCost() + neighbor.getCost());
                    openList.add(neighbor);
                }
            }
        }
        // No solution found
        return null;
    }

    @Override
    public String getName() { return "Best First Search"; }
}
