package algorithms.search;
import algorithms.mazeGenerators.Position;


public class MazeState extends AState {

    public MazeState(AState prevState,Position state, int cost){
        super(prevState,state,cost);
    }

}
