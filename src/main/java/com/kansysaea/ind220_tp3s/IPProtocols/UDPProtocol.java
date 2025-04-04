// UDPProtocol.java
    package com.kansysaea.ind220_tp3s.IPProtocols;

import com.kansysaea.ind220_tp3s.Controllers.SocketUIController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

    public class UDPProtocol implements IPProtocol {
        private final String ipAddress;
        private final int port;
        private DatagramSocket socket;
        private InetAddress address;
        private SocketUIController controller;

        public UDPProtocol(String ipAddress, int port, SocketUIController controller) {
            this.ipAddress = ipAddress;
            this.port = port;
            this.controller = controller;
            createSocket();
            startListening();
        }

        private void createSocket() {
            try {
                this.socket = new DatagramSocket();
                this.address = InetAddress.getByName(ipAddress);
            } catch (IOException e) {
                System.err.println("I/O Error: " + e.getMessage());
            }
        }

        @Override
        public void sendData(String data) {
            controller.printMsgBoard(data);
            try {
                byte[] buffer = data.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                System.err.println("Error sending data: " + e.getMessage());
            }
        }

        @Override
        public String receiveData() {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received (UDP): " + message);
                return message;
            } catch (IOException e) {
                System.err.println("Error receiving data: " + e.getMessage());
            }
            return null;
        }

        public void startListening() {
            Thread listenerThread = new Thread(() -> {
                while (true) {
                    String data = receiveData();
                    if (data != null) {
                        controller.receiveMessage(data);
                    }
                }
            });
            listenerThread.start();
        }

        public void close() {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }