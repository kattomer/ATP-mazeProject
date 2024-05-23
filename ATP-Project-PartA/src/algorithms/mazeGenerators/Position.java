package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    private int row;
    private int col;

    public Position(int row, int col){
        if (row < 0 || col < 0){
            // Prevent Illegal Argument Exceptions.
            this.row = 0;
            this.col = 0;
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public Position getUpPosition() {
        return new Position(row -1, col);
    }
    public Position getDownPosition(){
        return new Position(row +1, col);
    }
    public Position getRightPosition(){
        return new Position(row, col +1);
    }
    public Position getLeftPosition(){
        return new Position(row, col -1);
    }
}
