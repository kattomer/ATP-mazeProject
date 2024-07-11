package algorithms.search;

import java.util.*;

public class BestFirstSearch extends ASearchingAlgorithm {
    class  costComparator implements Comparator<AState>{
        @Override
        public int compare(AState s1, AState s2) {
            return Double.compare(s1.getCost(), s2.getCost());
        }
    }
    protected PriorityQueue<AState> openList;

    public BestFirstSearch() {
        super();
        this.openList = new PriorityQueue<>(( new costComparator()));

    }
    @Override
    public Solution solve(ISearchable item) {
        if(item == null){
            return null;
        }
        HashMap<String,AState> closedSet = new HashMap<>();

        AState startState = item.getStartState();
        AState goalState = item.getGoalState();
        openList.add(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.poll();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            else{
                ArrayList<AState> neighbors = item.getAllPossibleStates(currentState);
                for (AState neighbor : neighbors) {
                    if (!closedSet.containsKey(neighbor.toString()) && !openList.contains(neighbor)) {
                        neighbor.setFrom(currentState);
                        neighbor.setCost(currentState.getCost() + neighbor.getCost());
                        closedSet.put(neighbor.toString(), neighbor);
                        openList.add(neighbor);
                    }
                }
                closedSet.put(currentState.toString(), currentState);
            }
        }
        // No solution found
        return null;
    }

    @Override
    public String getName() { return "Best First Search"; }
}
