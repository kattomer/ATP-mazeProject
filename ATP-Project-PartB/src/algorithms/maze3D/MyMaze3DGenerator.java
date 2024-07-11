package algorithms.maze3D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMaze3DGenerator extends AMaze3DGenerator{
    @Override
    public Maze3D generate(int depth, int rows, int columns) {
        if(rows <= 2 && columns <= 2){
            return null;
        }

        Maze3D maze = new Maze3D(depth,rows,columns);
        Stack<Position3D> neighbours = new Stack<>();

        // Initialize the maze with walls
        for (int d = 0; d < depth; d++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    (maze.getMap())[d][r][c] = 1;
                }
            }
        }

        // Start from the initial position
        Position3D startPosition = maze.getStartPosition();
        maze.SetTransition(startPosition);
        neighbours.push(startPosition);

        // While the neighbours stack is not empty
        while (!neighbours.isEmpty()) {
            Position3D currPosition = neighbours.pop();
            ArrayList<Position3D> unvisitedNeighbours = maze.doubleSteps3D(currPosition);

            if (!unvisitedNeighbours.isEmpty()) {
                neighbours.push(currPosition);

                // Choose a random unvisited neighbour
                Random rnd = new Random();
                Position3D randNeighbour = unvisitedNeighbours.get(rnd.nextInt(unvisitedNeighbours.size()));

                // Remove the wall between the current cell and the chosen cell
                maze.connectNeighbours(currPosition, randNeighbour);
                maze.SetTransition(randNeighbour);
                neighbours.push(randNeighbour);
            }
        }

        // Set the goal position
        maze.setGoalPosition();
        return maze;
    }


}
