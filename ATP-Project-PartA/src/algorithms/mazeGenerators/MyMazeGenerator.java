package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator{

    // generates maze using iterative DFS
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        Stack<Position> neighbours = new Stack<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.addWall(i,j);
            }
        }
        // Start from the initial position
        Position startPosition = maze.getStartPosition();
        maze.SetTransition(startPosition);
        neighbours.push(startPosition);

        // While the neighbours stack is not empty
        while (!neighbours.isEmpty()) {
            Position currPosition = neighbours.pop();
            ArrayList<Position> unvisitedNeighbours = maze.doubleSteps(currPosition);
//            maze.print();

            if (unvisitedNeighbours.size() != 0) {
                neighbours.push(currPosition);

                // choose a random unvisited neighbour
                Random rnd = new Random();
                Position randNeighbour = unvisitedNeighbours.get(rnd.nextInt(unvisitedNeighbours.size()));

                // remove the wall between the current cell and the chosen cell
                maze.connectNeighbours(currPosition, randNeighbour);
                maze.SetTransition(randNeighbour);
                neighbours.push(randNeighbour);
            }
        }
        // mark the goal position
//        maze.addEmptyCell(rows-1, columns-1);
        maze.setGoalPosition();
        return maze;
    }
}
