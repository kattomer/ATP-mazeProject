package Server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
//import IO.SimpleCompressorOutputStream;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.BestFirstSearch;

import static Server.Configurations.getSearching;


public class ServerStrategyGenerateMaze implements IServerStrategy{
    /**
     * *getting the maze from the client and send final solution to him after this function
     * using strategy design so we could select the algorithm in run-time
     * sending the right generator to the client
     * @param InputFromClient
     * @param OutputToClient
     * @throws IOException
     */
    @Override
    public void serverStrategy(InputStream InputFromClient, OutputStream OutputToClient) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(InputFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(OutputToClient);
            int[] argArray = (int[]) fromClient.readObject();
            if(argArray.length != 2){
                throw new Exception("must have 2 arguments");
            }
            Configurations configurations = Configurations.getInstance();
            String genAlgorithm = configurations.getMazeGenerating();
            Maze maze = null;

            if (Objects.equals(genAlgorithm, "MyMazeGenerator")){
                maze=new MyMazeGenerator().generate(argArray[0],argArray[1] );
            }

            if (Objects.equals(genAlgorithm, "EmptyMazeGenerator")){
                maze=new EmptyMazeGenerator().generate(argArray[0],argArray[1] );
            }

            if (Objects.equals(genAlgorithm, "SimpleMazeGenerator")){
                maze=new SimpleMazeGenerator().generate(argArray[0],argArray[1] );
            }

            MyCompressorOutputStream compressor = new MyCompressorOutputStream(new ByteArrayOutputStream());
            if (maze!=null){
                compressor.write(maze.toByteArray());
                compressor.flush();
                toClient.writeObject(((ByteArrayOutputStream)compressor.getOut()).toByteArray());
                toClient.flush();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}