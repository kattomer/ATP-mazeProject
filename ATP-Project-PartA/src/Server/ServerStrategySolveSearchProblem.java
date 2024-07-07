package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private HashMap<Integer,String> map;

    /**
     * constractor
     */


    public ServerStrategySolveSearchProblem() {
        map=new HashMap<>();
    }

    /**
     *getting the maze from the client and send final solution to him after this function
     * using strategy design so we could select the algorithm in run-time
     * we used the fact that hash code is unique, so we saved him as a key with the right path
     * @param InputFromClient
     * @param OutputToClient
     */

    @Override
    public void serverStrategy(InputStream InputFromClient, OutputStream OutputToClient)  {
        try {
            if (InputFromClient == null){
                throw new Exception("need to insert input");
            }
            ObjectOutputStream toClient=new ObjectOutputStream(OutputToClient);
            ObjectInputStream fromClient=new ObjectInputStream(InputFromClient);
            toClient.flush();
            Maze maze= (Maze) fromClient.readObject();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            int code=maze.hashCode();
            String path=tempDirectoryPath+"/"+code;


            File file=new File(path);
            if (file.exists()||map.containsKey(code)){
                FileInputStream fileInputStream=new FileInputStream(path);
                ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
                Solution sol=(Solution) objectInputStream.readObject();
                toClient.writeObject(sol);
                toClient.flush();
                fromClient.close();
                toClient.close();


            }
            else {

                ISearchingAlgorithm algorithm=Configurations.getSearching();
                SearchableMaze maze1=new SearchableMaze(maze);
                Solution sol1 =algorithm.solve(maze1);
                FileOutputStream fileOut=new FileOutputStream(path);
                ObjectOutputStream objectOut=new ObjectOutputStream(fileOut);
                objectOut.writeObject(sol1);
                objectOut.flush();
                map.put(code,path);
                fileOut.close();
                objectOut.close();

                toClient.writeObject(sol1);
                toClient.flush();




            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}