package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    Random rand = new Random();

    @Override
    public Maze generate(int row, int col) {
        if(row == 0 && col == 0){
            return null;
        }
        EmptyMazeGenerator emptyMaze = new EmptyMazeGenerator();
        Maze maze = emptyMaze.generate(row, col);

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j = j+2) {
                maze.addWall(i, j);
            }
            // breaks randomly walls
            int rndNum = rand.nextInt(col);
            maze.addEmptyCell(i, rndNum);
        }
        maze.addEmptyCell(row -1, col-1);
        return maze;
    }
}
