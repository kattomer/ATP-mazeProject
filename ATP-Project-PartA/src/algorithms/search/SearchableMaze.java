package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

// SearchableMaze class implements the ISearchable interface for a maze
public class SearchableMaze implements ISearchable {
    private final Maze maze; // The maze to be searched

    // Constructor initializing the maze
    public SearchableMaze(Maze maze) throws IllegalArgumentException {
        if (maze == null)
            throw new IllegalArgumentException("null maze"); // Throw exception if maze is null
        this.maze = maze;
    }

    // Get the start state of the maze
    @Override
    public AState getStartState() {
        return new MazeState(null, maze.getStartPosition(), 0); // Return start position with zero cost
    }

    // Get the goal state of the maze
    @Override
    public AState getGoalState() {
        return new MazeState(null, maze.getGoalPosition(), 0); // Return goal position with zero cost
    }

    // Get all possible states (neighbors) from the given state
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();
        if (state == null)
            return possibleStates; // Return empty list if the state is null

        Position currentPosition = (Position) state.getCurrentState(); // Get current position

        // Check all possible moves and add valid ones to the possible states list

        // Up
        if (maze.isValidPosition(currentPosition.getUpPosition()) && !maze.IsWall(currentPosition.getUpPosition()))
            possibleStates.add(new MazeState(state, currentPosition.getUpPosition(), state.getCost() + 10));

        // Down
        if (maze.isValidPosition(currentPosition.getDownPosition()) && !maze.IsWall(currentPosition.getDownPosition()))
            possibleStates.add(new MazeState(state, currentPosition.getDownPosition(), state.getCost() + 10));

        // Right
        if (maze.isValidPosition(currentPosition.getRightPosition()) && !maze.IsWall(currentPosition.getRightPosition()))
            possibleStates.add(new MazeState(state, currentPosition.getRightPosition(), state.getCost() + 10));

        // Left
        if (maze.isValidPosition(currentPosition.getLeftPosition()) && !maze.IsWall(currentPosition.getLeftPosition()))
            possibleStates.add(new MazeState(state, currentPosition.getLeftPosition(), state.getCost() + 10));

        // Right-Up
        if (maze.isValidPosition(currentPosition.getRightUpPosition()) && !maze.IsWall(currentPosition.getRightUpPosition()) &&
                (!maze.IsWall(currentPosition.getUpPosition()) || !maze.IsWall(currentPosition.getRightPosition())))
            possibleStates.add(new MazeState(state, currentPosition.getRightUpPosition(), state.getCost() + 15));

        // Right-Down
        if (maze.isValidPosition(currentPosition.getRightDownPosition()) && !maze.IsWall(currentPosition.getRightDownPosition()) &&
                (!maze.IsWall(currentPosition.getDownPosition()) || !maze.IsWall(currentPosition.getRightPosition())))
            possibleStates.add(new MazeState(state, currentPosition.getRightDownPosition(), state.getCost() + 15));

        // Left-Up
        if (maze.isValidPosition(currentPosition.getLeftUpPosition()) && !maze.IsWall(currentPosition.getLeftUpPosition()) &&
                (!maze.IsWall(currentPosition.getUpPosition()) || !maze.IsWall(currentPosition.getLeftPosition())))
            possibleStates.add(new MazeState(state, currentPosition.getLeftUpPosition(), state.getCost() + 15));

        // Left-Down
        if (maze.isValidPosition(currentPosition.getLeftDownPosition()) && !maze.IsWall(currentPosition.getLeftDownPosition()) &&
                (!maze.IsWall(currentPosition.getDownPosition()) || !maze.IsWall(currentPosition.getLeftPosition())))
            possibleStates.add(new MazeState(state, currentPosition.getLeftDownPosition(), state.getCost() + 15));

        return possibleStates; // Return all valid possible states
    }
}
