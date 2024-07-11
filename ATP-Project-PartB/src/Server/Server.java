package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService executor;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;

        int numOfThreadsPool = Configurations.getInstance().getNumOfThreadsPool();
        executor = Executors.newFixedThreadPool(numOfThreadsPool);
    }

    public void start() {
        new Thread(this::run).start();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());

//                    executor.submit(() -> {
//                        try {
//                            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } finally {
//                            try {
//                                clientSocket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                    System.out.println("Client accepted: " + clientSocket);

                    executor.submit(() -> handleClient(clientSocket));
                } catch (SocketTimeoutException e) {
                    // Ignore the timeout exception and continue accepting connections
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Gracefully shutdown the executor after all tasks have completed
            executor.shutdown();
        }
    }
    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void stop() {
        stop = true;
    }
}
