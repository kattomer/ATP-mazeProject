package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;

public class Maze3DState extends AState {
    public Maze3DState(AState prevState, Position3D state, int cost){
        super(prevState,state,cost);
    }
}
