package Webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Webserver {

    private static int port;
    public final int myport = 8080;
    public final int size = 10;
    public final ExecutorService threadPool;

    public Webserver(int myport) {
        this.port = myport;
        this.threadPool = Executors.newFixedThreadPool(size);
        startServer();
    }

    private void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            System.out.println("Warte auf Verbindungen...");
            while(true) {
                    Socket clientRequest = serverSocket.accept();
                    System.out.println("Neuer Request von " + clientRequest.getInetAddress().getHostAddress());

                    Runnable requestHandler = new ClientRequestHandler(clientRequest);
                    this.threadPool.execute(requestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Webserver(port);
    }
}
