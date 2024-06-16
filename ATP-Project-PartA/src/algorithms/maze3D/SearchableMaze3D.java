package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;
import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    private final Maze3D maze;

    public SearchableMaze3D(Maze3D maze) throws IllegalArgumentException {
        if (maze == null)
            throw new IllegalArgumentException("cant handle null maze");
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return new Maze3DState(null, maze.getStartPosition(), 0);
    }
    @Override
    public AState getGoalState() {
        return new Maze3DState(null, maze.getGoalPosition(), 0);
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();
        if (state == null)
            return possibleStates;
        Position3D currentPosition = (Position3D) state.getCurrentState();

        // UP
        if (maze.isValidPosition(currentPosition.getUpPosition()) && !maze.isWall(currentPosition.getUpPosition()))
            possibleStates.add(new Maze3DState(state, currentPosition.getUpPosition(), 0));
        // RIGHT
        if (maze.isValidPosition(currentPosition.getRightPosition()) && !maze.isWall(currentPosition.getRightPosition()))
            possibleStates.add(new Maze3DState(state, currentPosition.getRightPosition(), 0));
        // DOWN
        if (maze.isValidPosition(currentPosition.getDownPosition()) && !maze.isWall(currentPosition.getDownPosition()))
            possibleStates.add(new Maze3DState(state, currentPosition.getDownPosition(), 0));
        // LEFT
        if (maze.isValidPosition(currentPosition.getLeftPosition()) && !maze.isWall(currentPosition.getLeftPosition()))
            possibleStates.add(new Maze3DState(state, currentPosition.getLeftPosition(), 0));
        // IN
        if (maze.isValidPosition(currentPosition.getInPosition()) && !maze.isWall(currentPosition.getInPosition()))
            possibleStates.add(new Maze3DState(state, currentPosition.getInPosition(), 0));
        // OUT
        if (maze.isValidPosition(currentPosition.getOutPosition()) && !maze.isWall(currentPosition.getOutPosition()))
            possibleStates.add(new Maze3DState(state, currentPosition.getOutPosition(), 0));

        return possibleStates;
    }
}
