package de.othr.vs;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Start {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Anmelden");
        frame.setLocation(800,100);
        frame.setLayout(new GridLayout(3,0));
        frame.setSize(420,420);
        //frame.setPreferredSize(new Dimension(200,200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel user = new JPanel();
        //user.setLayout(new GridLayout(0,2));
        user.add(new JLabel("Username:"));
        JTextField username = new JTextField();
        username.setColumns(10);
        user.add(username);
        frame.add(user);

        JPanel pw = new JPanel();
        //pw.setLayout(new GridLayout(0,2));
        pw.add(new JLabel("Password:"));
        JTextField password = new JTextField();
        password.setColumns(10);
        pw.add(password);
        frame.add(pw);

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Exit"));
        buttons.add(new JButton("Login"));
        frame.add(buttons);

       /* JPanel panel1 = new JPanel();
        JLabel user = new JLabel("Nutzer:");
        JTextField username = new JTextField();
        user.setVerticalAlignment(JLabel.TOP);
        user.setHorizontalAlignment(JLabel.LEFT);
        panel1.setLayout(new BorderLayout());
        panel1.add(user);
        panel1.add(username);

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Passwort:"));

        frame.add(panel1);
        frame.add(panel2);*/
        frame.pack();

        frame.setVisible(true);
    }

}
