package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class WaterLeakageSensor {

    public static int WATER_LEAKAGE_PORT = 10002;
    private Scanner scan;
    private InetAddress ip;
    private Socket s;
    private ObjectInputStream dis;
    private ObjectOutputStream dos;

    public WaterLeakageSensor() {
        try {

            scan = new Scanner(System.in);
            // getting localhost ip
            ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, Server.SERVER_PORT, ip, WATER_LEAKAGE_PORT);
            // obtaining input and out streams

            dis = new ObjectInputStream(s.getInputStream());
            dos = new ObjectOutputStream(s.getOutputStream());
            if (s.isConnected()) {
                System.out.println("Water Leakage Sensor has established connection with Server");
                // the following loop performs the exchange of
                // information between client and client handler

//                while (true) {
//                    String toSend;
//                    System.out.println("Enter y or Y to send report to server of water leakage");
//                    toSend = scan.next();
//                    if (toSend.equalsIgnoreCase("y")) {
//                        Message message = new Message(2, "water is leaking");
//                        dos.writeObject(message);
//                        dos.flush();
//                    }
//                }
            } else {
                System.out.println("water leakage Sensor has not established connection with Server");
                // closing resources
                scan.close();
                dis.close();
                dos.close();
            }
        } catch (Exception e) {
        }

    }

    /**
     * whether the socket is still connected to the server or not
     *
     * @return
     */
    public boolean isConnected() {
        return s.isConnected();
    }

    public void sendMessage(String str) {
        if (str.equalsIgnoreCase("yes")) {
            Message message = new Message(2, "water is leaking");
            try {
                dos.writeObject(message);
                dos.flush();
            } catch (IOException ex) {
            }
        }
    }
}
