package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMaze3DGenerator{

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column){
        long startTime = System.currentTimeMillis();
        generate(depth, row, column);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
