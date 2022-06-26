package Webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ClientRequestHandler implements Runnable{

    private final Socket clientRequest;

    public ClientRequestHandler(Socket clientRequest) {
        this.clientRequest = clientRequest;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientRequest.getInputStream()));
            PrintWriter writer = new PrintWriter(clientRequest.getOutputStream());


            String request = reader.readLine();
            String[] requestParts = request.split(" ");
            //String fileName = requestParts[1];
            String fileName = "index.html";

            System.out.println("Angeforderte Datei: " + fileName);

            // Alle Zeilen der Datei einlesen:
            // (Relativer Pfad in NetBeans-Projekten immer von Projektverzeichnis aus)
            List<String> linesInFile = Files.readAllLines(Paths.get("./src" , fileName));

            // Alle Zeilen plus Header-Zeilen in Socket schreiben:
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html");
            writer.print("Content-Length: ");
            // Lambda-Ausdruck (seit Java 8 möglich): zählt hier die Zeichen im File
            // (geht auch herkömmlich mit Iteration über Zeilen und sum += zeile.length()+1)
            writer.println("" + linesInFile.stream().mapToInt( line -> line.length()+1 ).sum());
            // Leere Zeile zwischen Header und Body wichtig
            writer.println("");
            // Inhalt der Datei zeilenweise in Socket schreiben
            for(String zeile : linesInFile ) {
                writer.println(zeile);
            }
            writer.flush();
            clientRequest.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
