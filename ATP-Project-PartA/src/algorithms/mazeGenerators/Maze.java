package algorithms.mazeGenerators;

import java.util.ArrayList;

public class Maze {
    private int[][] maze;
    private Position start;
    private Position goal;
    public Maze(int row, int col) {
        maze = new int[row][col];
        this.start = new Position(0,0);
        this.goal = new Position(row,col);
    }

    private int getMazeNumOfCols() {
        return maze[0].length;
    }
    private int getMazeNumOfRows() {
        return maze.length;
    }

    public void addEmptyCell(int row, int col){
        maze[row][col] = 0;
    }

    public void addWall(int row, int col){
        maze[row][col] = 1;
    }

    public Position getStartPosition(){
        return start;
    }

    public void SetTransition(Position position) {
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            this.maze[position.getRow()][position.getColumn()] = 0;
    }

    public ArrayList<Position> wallsTwoStepsAway(Position position) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (position != null) {
            Position up = position.getUpPosition().getUpPosition();
            if (this.validMazePosition(up) && IsWall(up))
                wallsList.add(up);
            Position right = position.getRightPosition().getRightPosition();
            if (this.validMazePosition(right) && IsWall(right))
                wallsList.add(right);
            Position down = position.getDownPosition().getDownPosition();
            if (this.validMazePosition(down) && IsWall(down))
                wallsList.add(down);
            Position left = position.getLeftPosition().getLeftPosition();
            if (this.validMazePosition(left) && IsWall(left))
                wallsList.add(left);
        }
        return wallsList;
    }

    private boolean validMazePosition(Position position) {
        if (position == null) { return false;}

        int row = position.getRow();
        int col = position.getColumn();
        return row >= 0 && row < this.getMazeNumOfRows() &&
                col >= 0 && col < this.getMazeNumOfCols();
    }


    //--------------------------------------------------------------------//
    public void connectNeighbours(Position currentPosition, Position neighbour) throws IllegalArgumentException {
        if (!this.validMazePosition(currentPosition)) {
            throw new IllegalArgumentException();
        }
        if (currentPosition.getColumn() == neighbour.getColumn()) {
            this.SetTransition(new Position(Math.min(neighbour.getRow(), currentPosition.getRow()) + 1, currentPosition.getColumn()));
        } else if (currentPosition.getRow() == neighbour.getRow()) {
            this.SetTransition(new Position(currentPosition.getRow(), Math.min(neighbour.getColumn(), currentPosition.getColumn()) + 1));
        }
    }

}

