package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;

public class Maze3D {
    private int[][][] map; // 3D map representing the maze
    private Position3D startPosition; // Starting position in the maze
    private Position3D goalPosition; // Goal position in the maze

    // Constructor that initializes the maze with a given map, start, and goal positions
    public Maze3D(int[][][] map, Position3D startPosition, Position3D goalPosition) {
        this.map = map;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    // Constructor that creates an empty maze of given dimensions and sets default start and goal positions
    public Maze3D(int depth, int row, int col) throws IllegalArgumentException {
        if (depth < 2 || row < 2 || col < 2) {
            throw new IllegalArgumentException();
        }
        this.map = new int[depth][row][col];
        this.startPosition = new Position3D(0, 0, 0);
        this.goalPosition = new Position3D(depth - 1, row - 1, col - 1);
    }

    // Getter for the maze map
    public int[][][] getMap() {
        return map;
    }

    // Checks if a given position is a wall (i.e., value is 1)
    public boolean isWall(Position3D position) {
        return this.getMap()[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] == 1;
    }

    // Getter for the start position
    public Position3D getStartPosition() {
        return startPosition;
    }

    // Getter for the goal position
    public Position3D getGoalPosition() {
        return goalPosition;
    }

    // Sets a given position in the maze to be a transition (i.e., value is 0)
    public void SetTransition(Position3D positions3D) {
        if (positions3D.getRowIndex() >= 0 && positions3D.getColumnIndex() >= 0 && positions3D.getDepthIndex() >= 0)
            this.getMap()[positions3D.getDepthIndex()][positions3D.getRowIndex()][positions3D.getColumnIndex()] = 0;
    }

    // Checks if a given position is valid within the maze boundaries
    public boolean isValidPosition(Position3D position) {
        if (position == null) {
            return false;
        }
        int depth = position.getDepthIndex();
        int row = position.getRowIndex();
        int col = position.getColumnIndex();
        return depth >= 0 && depth < this.getMap().length &&
                row >= 0 && row < this.getMap()[0].length &&
                col >= 0 && col < this.getMap()[0][0].length;
    }

    // Returns a list of valid wall positions two steps away from the given position
    public ArrayList<Position3D> doubleSteps3D(Position3D position) {
        ArrayList<Position3D> wallsList = new ArrayList<>();
        if (position != null) {
            Position3D up = position.getUpPosition().getUpPosition();
            if (this.isValidPosition(up) && isWall(up)) wallsList.add(up);

            Position3D right = position.getRightPosition().getRightPosition();
            if (this.isValidPosition(right) && isWall(right)) wallsList.add(right);

            Position3D down = position.getDownPosition().getDownPosition();
            if (this.isValidPosition(down) && isWall(down)) wallsList.add(down);

            Position3D left = position.getLeftPosition().getLeftPosition();
            if (this.isValidPosition(left) && isWall(left)) wallsList.add(left);

            Position3D in = position.getInPosition().getInPosition();
            if (this.isValidPosition(in) && isWall(in)) wallsList.add(in);

            Position3D out = position.getOutPosition().getOutPosition();
            if (this.isValidPosition(out) && isWall(out)) wallsList.add(out);
        }
        return wallsList;
    }

    // Connects a given position with its neighbor by removing the wall between them
    public void connectNeighbours(Position3D currentPosition, Position3D neighbour) throws IllegalArgumentException {
        if (!this.isValidPosition(currentPosition) || !this.isValidPosition(neighbour)) {
            throw new IllegalArgumentException();
        }

        int currentDepth = currentPosition.getDepthIndex();
        int currentRow = currentPosition.getRowIndex();
        int currentCol = currentPosition.getColumnIndex();

        int neighbourDepth = neighbour.getDepthIndex();
        int neighbourRow = neighbour.getRowIndex();
        int neighbourCol = neighbour.getColumnIndex();

        if (currentDepth == neighbourDepth) {   // like 2D maze
            if (currentCol == neighbourCol) {
                int row = Math.min(currentRow, neighbourRow) + 1;
                this.getMap()[currentDepth][row][currentCol] = 0;
            } else if (currentRow == neighbourRow) {
                int col = Math.min(currentCol, neighbourCol) + 1;
                this.getMap()[currentDepth][currentRow][col] = 0;
            }

        } else {    // different depth
            int depth = Math.min(currentDepth, neighbourDepth) + 1;

            if (currentCol == neighbourCol && currentRow == neighbourRow) {
                this.getMap()[depth][neighbourRow][currentCol] = 0;
            } else if (currentRow == neighbourRow) {
                int col = Math.min(currentCol, neighbourCol) + 1;
                this.getMap()[depth][currentRow][col] = 0;
            } else if (currentCol == neighbourCol) {
                int row = Math.min(currentRow, neighbourRow) + 1;
                this.getMap()[depth][row][currentCol] = 0;
            }
        }
    }

    // ANSI color codes for printing the maze
    public static final String RED = "\033[0;31m";      // RED
    public static final String GREEN = "\033[0;32m";    // GREEN
    public static final String RESET = "\033[0m";       // Text Reset

    // Prints the maze to the console with start and goal positions highlighted
    public void print() {
        for (int d = 0; d < this.map.length; d++) {
            System.out.println("Depth " + d + ":");
            for (int i = 0; i < this.map[0].length; i++) {
                System.out.print("{");
                for (int j = 0; j < this.map[0][0].length; j++) {
                    Position3D pos = new Position3D(d, i, j);
                    if (this.startPosition.equals(pos))
                        System.out.print(RED + " S" + RESET);
                    else if (this.goalPosition.equals(pos))
                        System.out.print(GREEN + " E" + RESET);
                    else
                        System.out.print(" " + this.map[d][i][j]);
                }
                System.out.println(" }");
            }
            System.out.println();
        }
    }

    // Randomly sets a valid goal position at the boundary of the maze
    public void setGoalPosition() {
        Random rnd = new Random();
        ArrayList<Position3D> goalPositionOptions = new ArrayList<>();
        int depthSize = this.map.length;
        int rowSize = this.map[0].length;
        int colSize = this.map[0][0].length;

        // Check boundary cells in all depths, rows, and columns
        for (int depth = 0; depth < depthSize; depth++) {
            for (int i = 0; i < colSize; i++) {
                if (this.map[depth][0][i] == 0)
                    goalPositionOptions.add(new Position3D(depth, 0, i));
                if (this.map[depth][rowSize - 1][i] == 0)
                    goalPositionOptions.add(new Position3D(depth, rowSize - 1, i));
            }

            for (int i = 0; i < rowSize; i++) {
                if (this.map[depth][i][0] == 0)
                    goalPositionOptions.add(new Position3D(depth, i, 0));
                if (this.map[depth][i][colSize - 1] == 0)
                    goalPositionOptions.add(new Position3D(depth, i, colSize - 1));
            }
        }

        // Check boundary cells in the depth dimension
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; colSize; col++) {
                if (this.map[0][row][col] == 0)
                    goalPositionOptions.add(new Position3D(0, row, col));
                if (this.map[depthSize - 1][row][col] == 0)
                    goalPositionOptions.add(new Position3D(depthSize - 1, row, col));
            }
        }

        if (goalPositionOptions.size() <= 1)
            throw new RuntimeException("No valid goal position found.");

        do
            this.goalPosition = goalPositionOptions.get(rnd.nextInt(goalPositionOptions.size()));
        while (this.getStartPosition().equals(this.getGoalPosition()));
    }
}
