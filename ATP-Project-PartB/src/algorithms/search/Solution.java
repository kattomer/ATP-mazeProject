package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Solution implements Serializable {
    private AState state;

    public Solution(AState state) {
        this.state = state;
    }

    public ArrayList<AState> getSolutionPath() {
        ArrayList<AState> path = new ArrayList<>();

        if(this.state == null){
            return null;
        }

        while (state != null) {
            path.add(state);
            state = state.getFrom();
        }

        // Reverse the path to start from the beginning
        ArrayList<AState> solutionPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            solutionPath.add(path.get(i));
        }

        return solutionPath;
    }
}