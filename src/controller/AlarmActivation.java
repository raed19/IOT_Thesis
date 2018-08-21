package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class AlarmActivation {

    public static int ALARM_PORT = 10004;
    private InetAddress ip;
    private Socket s;
    private ObjectInputStream dis;

    public AlarmActivation() {
        try {

            // getting localhost ip
            ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, Server.SERVER_PORT, ip, ALARM_PORT);

            // obtaining input
//            DataInputStream dis = new DataInputStream(s.getInputStream());
            dis = new ObjectInputStream(s.getInputStream());
            if (s.isConnected()) {
                System.out.println("Alarm Sensor has established connection with Server");
                // the following loop performs the exchange of
                // information between client and client handler
//                while (true) {
//                    String fromServer;
//                    fromServer = dis.readUTF();
//                    System.out.println(fromServer);

//                Message message = (Message) dis.readObject();
//                System.out.println(message.getMessage());
//                }
            } else {
                System.out.println("Alarm Sensor has not established connection with Server");
                // closing resources
                dis.close();
            }
        } catch (IOException e) {
        }
    }
}
