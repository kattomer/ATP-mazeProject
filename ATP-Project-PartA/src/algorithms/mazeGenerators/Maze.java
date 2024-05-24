package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Maze {
    private int[][] maze;
    private Position start;
    private Position goal;
    public Maze(int row, int col) {
        maze = new int[row][col];
        this.start = new Position(0,0);
        this.goal = new Position(row-1, col-1);
    }

    private int getMazeCols() {
        return maze[0].length;
    }
    private int getMazeRows() {
        return maze.length;
    }
    public void addEmptyCell(int row, int col){
        maze[row][col] = 0;
    }
    public void addWall(int row, int col){
        maze[row][col] = 1;
    }
    public boolean IsWall(Position position) {
        if (isValidPosition(position)) {
            return maze[position.getRowIndex()][position.getColumnIndex()] == 1;
        }
        return false;
    }

    public Position getStartPosition(){
        return start;
    }
    public Position getGoalPosition(){
        return goal;
    }

    public void SetTransition(Position position) {
        if (position.getRowIndex() >= 0 && position.getColumnIndex() >= 0)
            this.maze[position.getRowIndex()][position.getColumnIndex()] = 0;
    }

    public boolean isValidPosition(Position position) {
        if (position == null) { return false;}

        int row = position.getRowIndex();
        int col = position.getColumnIndex();
        return row >= 0 && row < this.getMazeRows() &&
                col >= 0 && col < this.getMazeCols();
    }
    public ArrayList<Position> doubleSteps(Position position) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (position != null) {
            Position up = position.getUpPosition().getUpPosition();
            if (this.isValidPosition(up) && IsWall(up))
                wallsList.add(up);
            Position right = position.getRightPosition().getRightPosition();
            if (this.isValidPosition(right) && IsWall(right))
                wallsList.add(right);
            Position down = position.getDownPosition().getDownPosition();
            if (this.isValidPosition(down) && IsWall(down))
                wallsList.add(down);
            Position left = position.getLeftPosition().getLeftPosition();
            if (this.isValidPosition(left) && IsWall(left))
                wallsList.add(left);
        }
        return wallsList;
    }

    public void connectNeighbours(Position currentPosition, Position neighbour) throws IllegalArgumentException {
        if (!this.isValidPosition(currentPosition)) {
            throw new IllegalArgumentException();
        }
        if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
            int row = Math.min(neighbour.getRowIndex(), currentPosition.getRowIndex()) + 1;
            int col = currentPosition.getColumnIndex();
            this.SetTransition(new Position(row, col));
        } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
            int row = currentPosition.getRowIndex();
            int col = Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1;
            this.SetTransition(new Position(row, col));
        }
    }

    public static final String RED = "\033[0;31m";      // RED
    public static final String GREEN = "\033[0;32m";    // GREEN
    public static final String RESET = "\033[0m";       // Text Reset

    public void print() {
        for (int i = 0; i < this.getMazeRows(); i++) {
            System.out.print("{");
            for (int j = 0; j < this.getMazeCols(); j++) {
                if (this.start.equals(new Position(i, j)))
                    System.out.print(RED + " S" + RESET);
                else if (this.goal.equals(new Position(i, j)))
                    System.out.print(GREEN + " E" + RESET);
                else
                    System.out.print(" " + this.maze[i][j]);
            }
            System.out.println(" }");
        }
    }

    public void setGoalPosition() {
        Random rnd = new Random();
        ArrayList<Position> goalPositionOptions = new ArrayList<>();
        int columnsSize = this.getMazeCols();
        int rowSize = this.getMazeRows();

        for (int i = 0; i < columnsSize; i++) {
            if (this.maze[0][i] == 0)
                goalPositionOptions.add(new Position(0, i));
            if (this.maze[rowSize - 1][i] == 0)
                goalPositionOptions.add(new Position(rowSize - 1, i));
        }

        for (int i = 0; i < rowSize; i++) {
            if (this.maze[i][0] == 0)
                goalPositionOptions.add(new Position(i, 0));
            if (this.maze[i][columnsSize - 1] == 0)
                goalPositionOptions.add(new Position(i, columnsSize - 1));
        }
        if (goalPositionOptions.size() <= 1)
            throw new RuntimeException();

        do
            this.goal = (goalPositionOptions.get(rnd.nextInt(goalPositionOptions.size())));
        while (this.getStartPosition().equals(this.getGoalPosition()));
    }
}

