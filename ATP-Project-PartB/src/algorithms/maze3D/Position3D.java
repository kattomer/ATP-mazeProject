package algorithms.maze3D;

public class Position3D {
    private int depth;
    private int row;
    private int column;

    public Position3D(int depth, int row, int column) {
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    public int getDepthIndex() {
        return depth;
    }
    public int getRowIndex() {
        return row;
    }
    public int getColumnIndex() {
        return column;
    }

    public Position3D getUpPosition() {
        return new Position3D(depth, row - 1, column);
    }
    public Position3D getDownPosition() {
        return new Position3D(depth, row + 1, column);
    }
    public Position3D getLeftPosition() {
        return new Position3D(depth, row, column - 1);
    }
    public Position3D getRightPosition() {
        return new Position3D(depth, row, column + 1);
    }
    public Position3D getInPosition() {
        return new Position3D(depth - 1, row, column);
    }
    public Position3D getOutPosition() {
        return new Position3D(depth + 1, row, column);
    }

    @Override
    public String toString() {
        return "{" + this.depth + "," + this.row + "," + this.column + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position3D that = (Position3D) obj;
        return depth == that.depth && row == that.row && column == that.column;
    }
}
