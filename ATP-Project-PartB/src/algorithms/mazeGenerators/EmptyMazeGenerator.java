package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int row, int col) {
        if(row == 0 && col == 0){
            return null;
        }

        Maze maze = new Maze(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze.addEmptyCell(i,j);
            }
        }
        return maze;
    }

}
