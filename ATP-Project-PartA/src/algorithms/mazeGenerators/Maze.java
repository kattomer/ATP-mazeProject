package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private int[][] maze; // 2D array to represent the maze
    private Position start; // Starting position of the maze
    private Position goal; // Goal position of the maze

    // Constructor to initialize the maze with given dimensions
    public Maze(int row, int col) {
        maze = new int[row][col];
        this.start = new Position(0, 0); // Default start position
        this.goal = new Position(row - 1, col - 1); // Default goal position
    }
    
    public Maze(byte[] byteArray) {
        int rows = byteArray[0] & 0xFF; // Convert to unsigned int
        int cols = byteArray[1] & 0xFF; // Convert to unsigned int
        maze = new int[rows][cols];

        int index = 2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = byteArray[index++] & 0xFF; // Convert to unsigned int
            }
        }

        this.start = new Position(byteArray[index++] & 0xFF, byteArray[index++] & 0xFF);
        this.goal = new Position(byteArray[index++] & 0xFF, byteArray[index] & 0xFF);
    }

    // Get the number of columns in the maze
    private int getMazeCols() {
        return maze[0].length;
    }

    // Get the number of rows in the maze
    private int getMazeRows() {
        return maze.length;
    }

    // Add an empty cell (path) at the given row and column
    public void addEmptyCell(int row, int col) {
        maze[row][col] = 0;
    }

    // Add a wall at the given row and column
    public void addWall(int row, int col) {
        maze[row][col] = 1;
    }

    // Check if the given position is a wall
    public boolean IsWall(Position position) {
        if (isValidPosition(position)) {
            return maze[position.getRowIndex()][position.getColumnIndex()] == 1;
        }
        return false;
    }

    // Get the start position
    public Position getStartPosition() {
        return start;
    }

    // Get the goal position
    public Position getGoalPosition() {
        return goal;
    }

    // Set a cell as a path (transition) at the given position
    public void SetTransition(Position position) {
        if (position.getRowIndex() >= 0 && position.getColumnIndex() >= 0) {
            this.maze[position.getRowIndex()][position.getColumnIndex()] = 0;
        }
    }

    // Check if the given position is valid within the maze boundaries
    public boolean isValidPosition(Position position) {
        if (position == null) {
            return false;
        }

        int row = position.getRowIndex();
        int col = position.getColumnIndex();
        return row >= 0 && row < this.getMazeRows() && col >= 0 && col < this.getMazeCols();
    }

    // Get a list of unvisited neighbour positions two steps away
    public ArrayList<Position> doubleSteps(Position position) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (position != null) {
            Position up = position.getUpPosition().getUpPosition();
            if (this.isValidPosition(up) && IsWall(up)) wallsList.add(up);

            Position right = position.getRightPosition().getRightPosition();
            if (this.isValidPosition(right) && IsWall(right)) wallsList.add(right);

            Position down = position.getDownPosition().getDownPosition();
            if (this.isValidPosition(down) && IsWall(down)) wallsList.add(down);

            Position left = position.getLeftPosition().getLeftPosition();
            if (this.isValidPosition(left) && IsWall(left)) wallsList.add(left);
        }
        return wallsList;
    }

    // Connect two neighbouring positions by removing the wall between them
    public void connectNeighbours(Position currentPosition, Position neighbour) throws IllegalArgumentException {
        if (!this.isValidPosition(currentPosition) || !this.isValidPosition(neighbour)) {
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

    // Color codes for printing the maze
    public static final String RED = "\033[0;31m"; // RED
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String RESET = "\033[0m"; // Text Reset

    // Print the maze with start and goal positions highlighted
    public void print() {
        for (int i = 0; i < this.getMazeRows(); i++) {
            System.out.print("{");
            for (int j = 0; j < this.getMazeCols(); j++) {
                if (this.start.equals(new Position(i, j))) {
                    System.out.print(RED + " S" + RESET);
                } else if (this.goal.equals(new Position(i, j))) {
                    System.out.print(GREEN + " E" + RESET);
                } else {
                    System.out.print(" " + this.maze[i][j]);
                }
            }
            System.out.println(" }");
        }
    }

    // Set a random valid goal position on the maze boundary
    public void setGoalPosition() {
        Random rnd = new Random();
        ArrayList<Position> goalPositionOptions = new ArrayList<>();
        int columnsSize = this.getMazeCols();
        int rowSize = this.getMazeRows();

        // Check boundary cells
        for (int i = 0; i < columnsSize; i++) {
            if (this.maze[0][i] == 0) goalPositionOptions.add(new Position(0, i));
            if (this.maze[rowSize - 1][i] == 0) goalPositionOptions.add(new Position(rowSize - 1, i));
        }

        for (int i = 0; i < rowSize; i++) {
            if (this.maze[i][0] == 0) goalPositionOptions.add(new Position(i, 0));
            if (this.maze[i][columnsSize - 1] == 0) goalPositionOptions.add(new Position(i, columnsSize - 1));
        }

        if (goalPositionOptions.size() <= 1) {
            throw new RuntimeException("No valid goal position found.");
        }

        // Select a random goal position that is different from the start position
        do {
            this.goal = goalPositionOptions.get(rnd.nextInt(goalPositionOptions.size()));
        } while (this.getStartPosition().equals(this.getGoalPosition()));
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // dimensions of the maze - rows and columns
        byteStream.write(getMazeRows());
        byteStream.write(getMazeCols());

        // maze data 
        for (int i = 0; i < getMazeRows(); i++) {
            for (int j = 0; j < getMazeCols(); j++) {
                byteStream.write(maze[i][j]);
            }
        }

        // start position 
        byteStream.write(start.getRowIndex());
        byteStream.write(start.getColumnIndex());

        // goal position 
        byteStream.write(goal.getRowIndex());
        byteStream.write(goal.getColumnIndex());

        return byteStream.toByteArray();

    }
}

    
    
        
