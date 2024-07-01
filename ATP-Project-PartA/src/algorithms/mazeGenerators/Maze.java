package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private int[][] maze; // 2D array to represent the maze
    private Position start; // Starting position of the maze
    private Position goal; // Goal position of the maze
    private int rowMaze;
    private int colMaze;

    // Constructor to initialize the maze with given dimensions
    public Maze(int row, int col) {
        maze = new int[row][col];
        this.rowMaze = row;
        this.colMaze = col;
        this.start = new Position(0, 0); // Default start position
        this.goal = new Position(row - 1, col - 1); // Default goal position
    }
    
     public Maze(byte[] bytes) {
        int rows = 0;
        int col = 0;
        int startRow = 0;
        int startCol = 0;
        int goalRow = 0;
        int goalCol = 0;
        int index = 0;
        // row
        while (bytes[index] == (byte) 255) {
            rows += 255;
            index++;
        }
        rows += bytes[index];
        this.rowMaze = rows;
        index++;

        // col
        while (bytes[index] == (byte) 255) {
            col += 255;
            index++;
        }
        col += bytes[index];
        this.colMaze = col;
        index++;

        // init maze
        maze = new int[rowMaze][colMaze];
        for (int i = 0; i < rowMaze; i++) {
            for (int j = 0; j < colMaze; j++) {
                if (bytes[index]==(byte) 0) {
                    addEmptyCell(i,j);
                }
                else {
                    addWall(i,j);
                }
                index++;
            }
        }
        // start point
        while (bytes[index] == (byte) 255) {
            startRow += 255;
            index++;
        }
        startRow += bytes[index];
        index++;
        while (bytes[index] == (byte) 255) {
            startCol += 255;
            index++;
        }
        startCol += bytes[index];
        index++;
        this.start =  new Position(startRow,startCol);

        // goal point
        while (bytes[index] == (byte) 255) {
            goalRow += 255;
            index++;
        }
        goalRow += bytes[index];
        index++;
        while (bytes[index] == (byte) 255) {
            goalCol += 255;
            index++;
        }
        goalCol += bytes[index];
        this.goal =  new Position(goalRow,goalCol);
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

    public byte[] toByteArray(){
        ArrayList<Byte> byteArray = new ArrayList<>();
        double cellsForColumns = Math.ceil(this.colMaze / 255);
        double cellsForRows = Math.ceil(this.rowMaze / 255);

        // Add number of rows
        for(int i = 0; i < cellsForRows; i++){
            byteArray.add(Byte.MAX_VALUE);
        }
        byteArray.add((byte) (this.rowMaze % 255) );

        // Add number of cols
        for(int i = 0; i < cellsForColumns; i++){
            byteArray.add(Byte.MAX_VALUE);
        }
        byteArray.add((byte) (this.colMaze % 255) );

        // Add data maze
        for(int i = 0; i < this.rowMaze; i++){
            for (int k = 0; k < this.colMaze; k++){
                byteArray.add((byte) this.maze[i][k]);
            }
        }
        // Add start position
        int startRows = (int) Math.ceil(start.getRowIndex()/ 255);
        int startCols = (int) Math.ceil(start.getColumnIndex()/ 255);
        for(int i = 0; i < startRows; i++){
            byteArray.add((byte) 255 );
        }
        byteArray.add((byte) (startRows % 255) );
        for(int i = 0; i < startCols; i++){
            byteArray.add((byte) 255 );
        }
        byteArray.add((byte) (startCols % 255) );

        // Add goal position
        int goalRows = (int) Math.ceil(goal.getRowIndex() / 255);
        int goalCols = (int) Math.ceil(goal.getColumnIndex()/ 255);
        for(int i = 0; i < goalRows; i++){
            byteArray.add((byte) 255 );
        }
        byteArray.add((byte) (goalRows % 255) );
        for(int i = 0; i < goalCols; i++){
            byteArray.add((byte) 255 );
        }
        byteArray.add((byte) (goalCols % 255) );

        // convert to bytes array
        byte[] mazeToByteArr = new byte[byteArray.size()];
        for(int i = 0; i < byteArray.size(); i++){
            mazeToByteArr[i] = byteArray.get(i);
        }
        return mazeToByteArr;
    }
}

    
    
        
