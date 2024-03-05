package com.thabanidev.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("SERVER RUNNING ON PORT https://localhost:5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("CLIENT CONNECTED: " + clientSocket);
            }
        } catch (IOException e) {
            System.out.println("ERROR SETTING UP SERVER");
        }

    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final List<ClientHandler> clients;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientHandler(Socket clientSocket, List<ClientHandler> clients, PrintWriter out, BufferedReader in) {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String input;

            while ((input = in.readLine()) != null) {
                for (ClientHandler client : clients) {
                    client.out.println(input);
                }
            }
        } catch (IOException e) {
            System.out.println("SERVER ERROR: " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("SERVER ERROR: " + e.getMessage());
            }
        }
    }
}
