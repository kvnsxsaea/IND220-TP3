package com.kansysaea.ind220_tp3s.IPProtocols;


import com.kansysaea.ind220_tp3s.Controllers.SocketUIController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * As per documentation, les Socket offrent une communication TCP.
 **/

public class TCPProtocol implements IPProtocol {
    private final SocketUIController controller;
    private final String ipAddress;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private Scanner in;

    private Thread listenerThread;
    private volatile boolean listening;

    public TCPProtocol(String ipAddress, int port, SocketUIController controller) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.controller = controller;
        createSocket();
        startListening();
    }

    private void createSocket() {
        try {
            this.socket = new Socket(ipAddress, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("I/O Error : " + ipAddress);
        }
    }

    @Override
    public void sendData(String data) {
        if (out != null) {
            out.println(data);
            controller.printMsgBoard(data); // On envoie le message au msgBoard
        }
    }

    @Override
    public String receiveData() {
        if (in.hasNextLine()) {
            String data = in.nextLine();
            System.out.println("Received (TCP): " + data);
            return data;
        }
        return "";
    }

    // Pour continuellement écouter les données, on crée un thread qui écoute sur le côté
    public void startListening() {
        listening = true;
        listenerThread = new Thread(() -> {
            System.out.println("Thread démarré!");
            while (listening) {
                String data = receiveData();
                controller.receiveMessage(data); // On envoie le message au msgBoard
            }
        });
        listenerThread.start();
    }

    // Pour arrêter l'écoute
    public void stopListening() {
        listening = false;
        if (listenerThread != null) {
            try {
                // .join() attend que le thread se termine
                listenerThread.join();
            } catch (InterruptedException e) {
                // On interrmpt le thread
                Thread.currentThread().interrupt();
            }
        }
    }

    // Pour proprement fermer le socket
    public void close() {
        stopListening();
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}