package Server;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import static jdk.internal.vm.PostVMInitHook.run;

public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private volatile boolean stop;

    private final ExecutorService threadPool;

    /**
     * constractor
     * @param port
     * @param listeningIntervalMS
     * @param strategy
     */


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        int num_threads = Configurations.getInstance().getNumOfThreads();
        this.stop=false;
        this.threadPool = Executors.newFixedThreadPool(num_threads);

    }

    /**
     * starting the thread
     */

    public void start() {
        new Thread(this::run).start();

    }

    /**
     * this function is runing the server
     */

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.listeningIntervalMS);


            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.submit(()->handleClient(clientSocket));

                } catch (Exception e) {
                    //throw new RuntimeException(e);
                }

            }
            serverSocket.close();
            threadPool.shutdown();



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * handling clients requests with the right strategy
     * @param clientSocket
     */

    private void handleClient(Socket clientSocket) {
        try {
            this.strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());

            clientSocket.close();
        } catch (IOException exception) {
            exception.printStackTrace();

        }

    }

    /**
     * stopping the server from running
     */

    public void stop() {

        this.stop = true;
    }
}