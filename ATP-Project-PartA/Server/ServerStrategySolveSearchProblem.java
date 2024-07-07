package Server;
import algorithms.search.*;
import algorithms.mazeGenerators.Maze;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private final File folderDir = new File(System.getProperty("java.io.tmpdir"));

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        try (ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
             ObjectOutputStream toClient = new ObjectOutputStream(outToClient)) {
            boolean solFound = false;
            Maze maze = (Maze) fromClient.readObject();
            Solution solution = null;
            Solution isExistedSol = ReadObjectFromFile(maze);
            if (isExistedSol != null) {
                solFound = true;
                solution = isExistedSol;
            }
//            // Read existing solution from files, each file contain different solution
//            File[] directoryListing = folderDir.listFiles();//                    ((dir, name) -> name.contains("Solution"));
//            if (directoryListing != null) {
//                for (File file : directoryListing) {
//                    if(file.getName().contains("Solution")){
//                        try (FileInputStream fileIn = new FileInputStream(file);
//                             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
//
//                            Object[] objFromFile = (Object[]) objectIn.readObject();
//                            Maze mazeFromFile = (Maze) objFromFile[0];
//                            String searchName = (String) objFromFile[2];
//
//                            if (Arrays.equals(mazeFromFile.toByteArray(), maze.toByteArray()) &&
//                                    searchName.equals(Configurations.getInstance().getSearchAlgorithmName())) {
//                                // extract exist solution from the file
//                                solution = (Solution) objFromFile[1];
//                                break;
//                            }
//                        }
//                    }
//                }
//                // Send the solution back to the client
////                toClient.writeObject(solution);
////                toClient.flush();
////                fromClient.close();
////                toClient.close();
//            }


            // Solve the maze if no existing solution was found
            if (solFound == false) {
                String algorithmName = Configurations.getInstance().getSearchAlgorithmName();
                ASearchingAlgorithm searchAlgorithm = null;

                if (Objects.equals(algorithmName, "BestFirstSearch")){
                    searchAlgorithm = new BestFirstSearch();
                }
                if (Objects.equals(algorithmName, "BreadthFirstSearch")){
                    searchAlgorithm = new BreadthFirstSearch();
                }
                if (Objects.equals(algorithmName, "DepthFirstSearch")){
                    searchAlgorithm = new DepthFirstSearch();
                }

                if(searchAlgorithm != null) {
                    SearchableMaze searchableMaze = new SearchableMaze(maze);
                    solution = searchAlgorithm.solve(searchableMaze);
                    // Write the new solution to file
                    String fileUniqueID = UUID.randomUUID().toString();
                    File newFile = new File(folderDir, "Solution_" + fileUniqueID);
                    try (FileOutputStream fileOut = new FileOutputStream(newFile);
                         ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                        objectOut.writeObject(new Object[]{maze, solution, Configurations.getInstance().getSearchAlgorithmName()});
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            // Send the solution back to the client
            toClient.writeObject(solution);
            toClient.flush();
//            fromClient.close();
//            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public Solution ReadObjectFromFile(Maze maze) {
        Solution existSol = null;
        try {
            File[] directoryListing = folderDir.listFiles();
            if (directoryListing != null) {
                for (File file : directoryListing) {
                    if(file.getName().contains("Solution")) {
                        try (FileInputStream fileIn = new FileInputStream(file);
                             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                            Object[] objFromFile = (Object[]) objectIn.readObject();
                            Maze mazeFromFile = (Maze) objFromFile[0];
                            String SearchName = (String) objFromFile[2];
                            if (Arrays.equals(mazeFromFile.toByteArray(), maze.toByteArray()) && SearchName.equals(Configurations.getInstance().getSearchAlgorithmName())) {
                                existSol = (Solution) objFromFile[1];
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return existSol;
    }


}
