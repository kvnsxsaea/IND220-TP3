package com.kansysaea.ind220_tp3s.Server;

import com.kansysaea.ind220_tp3s.TCPServer;
import com.kansysaea.ind220_tp3s.UDPServer;

public class ServerApp {
    public static void main(String[] args) {
        try {
            TCPServer tcpServer = new TCPServer(12345);
            UDPServer udpServer = new UDPServer(12345);

            Thread tcpThread = new Thread(tcpServer);
            Thread udpThread = new Thread(udpServer);

            tcpThread.start();
            udpThread.start();
            tcpThread.join();
            udpThread.join();

        } catch (InterruptedException e) {
            System.err.println("Error sending data: " + e.getMessage());
        }
    }
}