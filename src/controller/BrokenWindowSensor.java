package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class BrokenWindowSensor {

    public static int WINDOW_SENSOR_PORT = 10001;
    private Scanner scan = null;
    private Socket s;
    private InetAddress ip;
    private ObjectInputStream dis = null;
    private ObjectOutputStream dos = null;

    public BrokenWindowSensor() {
        try {
            scan = new Scanner(System.in);
            // getting localhost ip
            ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, Server.SERVER_PORT, ip, 10001);

            // obtaining input and out streams
            dis = new ObjectInputStream(s.getInputStream());
            dos = new ObjectOutputStream(s.getOutputStream());
            if (s.isConnected()) {
                System.out.println("Broken Window Sensor has established connection with Server");
                /*
                todo green the button text and make it disable
                 */
                // the following loop performs the exchange of
                // information between client and client handler
//                while (true) {
//                    String toSend;
//                    System.out.println("Enter y or Y to send report to server of Broken Window");
//                    toSend = scan.next();
//                    if (toSend.equalsIgnoreCase("y")) {
//                        Message message = new Message(1, "window is broken");
//                        dos.writeObject(message);
//                        dos.flush();
//                    }
//                }
            } else {
                System.out.println("Broken Window Sensor has not established connection with Server");
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
            Message message = new Message(1, "window is broken");
            try {
                dos.writeObject(message);
                dos.flush();
            } catch (IOException ex) {
            }
        }
    }
}
