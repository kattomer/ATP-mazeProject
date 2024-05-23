package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator{

    // generates maze using iterative DFS
    @Override
    public Maze generate(int row, int col) {
        return DfsMazeGenerator(row, col);
    }

    private Maze DfsMazeGenerator(int rows, int columns) {
        // Arrange
        Random rnd = new Random();
        Maze maze = new Maze(rows, columns);
        Stack<Position> neighbours = new Stack<>();
        ArrayList<Position> neighbourWalls;
//        maze.WallInitialize();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.addWall(i,j);
            }
        }

        // Pushing the first position into the stack
        Position currPosition = maze.getStartPosition();
        maze.SetTransition(currPosition);
        neighbours.push(currPosition);

        // While the neighbours stack is not empty
        while (!neighbours.isEmpty()) {
            currPosition = neighbours.pop();
            neighbourWalls = maze.wallsTwoStepsAway(currPosition);

            if (neighbourWalls.size() != 0) {
                neighbours.push(currPosition);
                Position randNeighbour = neighbourWalls.get(rnd.nextInt(neighbourWalls.size()));
                maze.SetTransition(randNeighbour);
                maze.connectNeighbours(currPosition, randNeighbour);
                neighbours.push(randNeighbour);
            }
        }
        maze.setGoalPosition();
        return maze;
    }
}
}
