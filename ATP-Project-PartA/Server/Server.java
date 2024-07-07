package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
        int numOfThreadsPool = Configurations.getInstance().getNumOfThreadsPool();
        this.threadPool = Executors.newFixedThreadPool(numOfThreadsPool);
    }

    public void start() {
        new Thread(this::run).start();
    }

    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.submit(()->handleClient(clientSocket));
//                    // This thread will handle the new Client
//                    new Thread(() ->
//                        handleClient(clientSocket)).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket.close();
            threadPool.shutdown();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stop(){
        stop = true;
    }
}

