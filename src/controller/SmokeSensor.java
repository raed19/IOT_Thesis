/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author smsta
 */
public class SmokeSensor {

    public static int Door_SENSOR_PORT = 10007;
    Scanner scan = null;
    Socket s;

    ObjectInputStream dis = null;
    ObjectOutputStream dos = null;

    public SmokeSensor() {
        try {
            scan = new Scanner(System.in);
            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, Server.SERVER_PORT, ip, Door_SENSOR_PORT);

            // obtaining input and out streams
            dis = new ObjectInputStream(s.getInputStream());
            dos = new ObjectOutputStream(s.getOutputStream());
            if (s.isConnected()) {
                System.out.println("Smoke Sensor has established connection with Server");
            } else {
                System.out.println("Smoke Sensor has not established connection with Server");
                // closing resources
                scan.close();
                dis.close();
                dos.close();
            }
        } catch (IOException e) {
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
            Message message = new Message(5, "smoke has been detected");
            try {
                dos.writeObject(message);
                dos.flush();
            } catch (IOException ex) {
            }
        }
    }
}
