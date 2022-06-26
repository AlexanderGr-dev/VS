package simple;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class Client {

    private static String host = "localhost";

    public static void main(String[] args){
        try {
            Socket s = new Socket(host, 1234);

            InputStream in = s.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            OutputStream out = s.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            System.out.println("Bitte geben Sie eine Nachricht ein:");
            Scanner scanner = new Scanner(System.in);
            String eingabe = scanner.nextLine();
            writer.println(eingabe);
            writer.flush();

            String eingang = reader.readLine();
            System.out.println("Antwort von simple.Server: " + eingang);

            s.close();
            reader.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
