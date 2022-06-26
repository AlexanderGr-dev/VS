package simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            System.out.println("Warte auf Verbindungen...");
         while(true) {
              try {
                  //System.out.println("Warte auf Verbindungen...");
                  Socket s = serverSocket.accept();
                  System.out.println("Neue Verbindung von " + s.toString());

                  InputStream in = s.getInputStream();
                  BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                  OutputStream out = s.getOutputStream();
                  PrintWriter writer = new PrintWriter(out);

                  String antwort = reader.readLine();
                  System.out.println(antwort);
                  writer.println("Ich habe vom empfangen: #" + antwort + "#");
                  writer.flush();

                 /* reader.close();
                  writer.close();

                  s.close();*/



              } catch (IOException e) {
                  e.printStackTrace();
              }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
