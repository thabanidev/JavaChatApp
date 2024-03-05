package com.thabanidev.client;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public ChatClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("CONNECTED TO CHAT SERVER: " + socket);

            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String line = "";
            while (!line.equals("/exit")) {
                line = inputConsole.readLine();
                out.println(line);
                System.out.println(in.readLine());
            }

            socket.close();
            inputConsole.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("CLIENT ERROR: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 5000);
    }
}
