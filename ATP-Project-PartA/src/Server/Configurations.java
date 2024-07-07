package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class  Configurations {
    private static final Properties properties = new Properties();
    private static Configurations instance = null;

    private Configurations() {
        InputStream inputStream = null;
        try {
            inputStream = getClass().getResourceAsStream("/config.properties");

            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return string that represent the right maze generator
     * @throws Exception
     */



    public String getMazeGenerating() throws Exception {

        return properties.getProperty("mazeGeneratingAlgorithm");
    }

    /**
     *
     * @return searching algorithm from the configuration file
     */


    public static ASearchingAlgorithm getSearching() {
        ASearchingAlgorithm algorithm = null;
        //assert false;
        String p = properties.getProperty("mazeSearchingAlgorithm");
        if (Objects.equals(p, "BestFirstSearch")) {
            algorithm = new BestFirstSearch();
        }
        if (Objects.equals(p, "BreadthFirstSearch")) {
            algorithm = new BreadthFirstSearch();
        }
        if (Objects.equals(p, "DepthFirstSearch")) {
            algorithm = new DepthFirstSearch();
        }
        return algorithm;
    }

    /**
     *
     * @return num of threads
     */

    public int getNumOfThreads() {
        return Integer.parseInt(properties.getProperty("threadPoolSize"));
    }

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }
}