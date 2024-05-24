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

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
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
    public Position getRightUpPosition(){
        return new Position(row -1, col +1);
    }
    public Position getRightDownPosition(){
        return new Position(row +1, col +1);
    }
    public Position getLeftUpPosition(){
        return new Position(row -1, col -1);
    }
    public Position getLeftDownPosition(){
        return new Position(row +1, col -1);
    }

    @Override
    public String toString() {
        return "{" + this.row + "," + this.col + "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (this.row == ((Position) obj).row && this.col == ((Position) obj).col);
    }
}
