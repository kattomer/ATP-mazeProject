package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import java.io.*;
import java.util.Objects;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] argArr = (int[]) fromClient.readObject();
            if (argArr.length != 2){
                throw new Exception("the array need to include 2 arguments!");
            }
            Configurations configurations = Configurations.getInstance();
            String genAlgorithm = configurations.getMazeGenerating();
            Maze maze = null;

            if (Objects.equals(genAlgorithm, "EmptyMazeGenerator")){
                maze = new EmptyMazeGenerator().generate(argArr[0],argArr[1] );
            }
            if (Objects.equals(genAlgorithm, "MyMazeGenerator")){
                maze = new MyMazeGenerator().generate(argArr[0],argArr[1] );
            }
            if (Objects.equals(genAlgorithm, "SimpleMazeGenerator")){
                maze = new SimpleMazeGenerator().generate(argArr[0],argArr[1] );
            }

            MyCompressorOutputStream compressorMaze = new MyCompressorOutputStream(new ByteArrayOutputStream());
            if (maze!=null){
                compressorMaze.write(maze.toByteArray());
                compressorMaze.flush();
                toClient.writeObject(((ByteArrayOutputStream)compressorMaze.getOut()).toByteArray());
                toClient.flush();
            }

            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
