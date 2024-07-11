package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Configurations {
    private static final Properties properties = new Properties();
    private static Configurations instance = null;
    private final String URL = "resources/config.properties";

    private Configurations() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(URL);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public String getMazeGenerating(){
        return properties.getProperty("mazeGeneratingAlgorithm");
    }

    public String getSearchAlgorithmName() {
        return properties.getProperty("mazeSearchingAlgorithm");
    }

    public int getNumOfThreadsPool() {
        return Integer.parseInt(properties.getProperty("threadPoolSize"));
    }

}
