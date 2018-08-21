/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

// Java implementation of  ServerGUI side
// It contains two classes : ServerGUI and ClientHandler
// Save file as ServerGUI.java
import Timer.Timer;
import com.emaraic.recorder.CamRecorder;
import gui.ServerGUI;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
// ServerGUI class

public class Server {

    static int SERVER_PORT = 5056;
    private boolean keepGoing;
    //  static ArrayList clientOutputStreams;
    
    private ServerGUI serverGUI;
    static HashMap clientOutputStreams;
    ArrayList<Object> threads;

    public Server(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
        clientOutputStreams = new HashMap();
        threads = new ArrayList<>();
    }

    private void display(String str) {
        if (serverGUI != null) {
            serverGUI.appendToTextArea(str + "\n");
        }
    }

    public void start() throws IOException {
//         server is listening on port 5056
        keepGoing = true;
        ServerSocket ss = new ServerSocket(SERVER_PORT);

//         running infinite loop for getting
//         client request
        System.out.println(ss);
        display(ss.toString());
        //serverGUI.appendToTextArea(ss.toString() + "\n");
//        serverGUI.appendToTextArea("Server is waiting at port number ::" + SERVER_PORT + "\n");
        display("Server is waiting at port number ::" + SERVER_PORT);
        while (keepGoing) {
            Socket s = null;
            Socket s2 = null;

            // socket object to receive incoming client requests
            s = ss.accept();

            System.out.println("A new client is connected : " + s);
            display("A new client is connected : " + s);
            if (!keepGoing) {
                break;
            }
            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

            clientOutputStreams.put(s.getPort(), dos);
            System.out.println("Assigning new thread for this client");
            display("Assigning new thread for this client");
            Thread listener = new Thread(new ClientHandler(s, dos));
            threads.add(listener);
            listener.start();

        }
        ss.close();
    }

    public Server() throws IOException, ClassNotFoundException, SQLException {

//         server is listening on port 5056
        ServerSocket ss = new ServerSocket(SERVER_PORT);

        clientOutputStreams = new HashMap();
//         running infinite loop for getting
//         client request
        System.out.println(ss);

        while (true) {
            Socket s = null;
            Socket s2 = null;
            try {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

                clientOutputStreams.put(s.getPort(), dos);
                System.out.println("Assigning new thread for this client");
                Thread listener = new Thread(new ClientHandler(s, dos));
                listener.start();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }

    class ClientHandler implements Runnable {

        Socket socket;
        ObjectInputStream reader;
        ObjectOutputStream client;

        public ClientHandler(Socket socket, ObjectOutputStream client) throws IOException {
            this.client = client;
            this.socket = socket;
            reader = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            Message messageRecieved;

            try {
                while (true) {

                    if ((messageRecieved = (Message) reader.readObject()) != null) {

                        switch (messageRecieved.getID()) {
                            case 1: {
                                display("Window Sensor :: " + messageRecieved.getMessage());
                                ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(SmartWatch.SMART_WATCH_PORT);
                                Message message = new Message(1, "do you want to call the police department for broken window");
                               // messageRecieved.set("ahmed");
                                display("Server :: " + message.getMessage());
                                writer.writeObject(message);
                                
                                writer.flush();
                               message = new Message(1, "do you want to activate the alarm for broken window");
                               display("Server :: " + message.getMessage());
                               writer.writeObject(message);
                               // PassDataToDB("Notifying smart watch to active the alarm for  +" +messageRecieved.getMessage() );
                               PassDataToDB("Notifying smart watch to call the police for  +" +messageRecieved.getMessage() );
                              
                                break;
                            }
                            case 2: {
                                display("Water Sensor :: " + messageRecieved.getMessage());
                                ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(SmartWatch.SMART_WATCH_PORT);
                                Message message = new Message(2, "do you want to call the police department for water leakage");
                                display("Server :: " + message.getMessage());
                                writer.writeObject(message);
                               
                                writer.flush();
                                message = new Message(2, "do you want to activate the alarm for water leakage");
                                display("Server :: " + message.getMessage());
                                writer.writeObject(message);
                               // PassDataToDB("Notifying smart watch to active the alarm for  +" +messageRecieved.getMessage() );
                                PassDataToDB("Notifying smart watch to call the police for  +" +messageRecieved.getMessage() );
                                break;
                            }
                            case 3:
                                //makeCall
                                display("Smart Watch :: " + messageRecieved.getMessage());
                                System.out.println(messageRecieved.getMessage());
                                display("Server: calling police");
                                PassDataToDB("make a call for "+messageRecieved.getMessage());
                                break;
                            case 4: {
                                display("Smart Watch :: " + messageRecieved.getMessage());
                                ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(AlarmActivation.ALARM_PORT);
                                Message message = new Message(4, messageRecieved.getMessage());
                                display("Server :: notifiying the alarm");
                                writer.writeObject(message);
                                PassDataToDB("Notifying alarm to turn on "+ messageRecieved.getMessage());
                                writer.flush();
                                break;
                            }
                            case 5: {
                                display("Smoke Detector::" + messageRecieved.getMessage());
                                ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(SmartWatch.SMART_WATCH_PORT);
                                Message message = new Message(5, "do you want to call the fire department for smoking detection");
                                display("Server ::" + message.getMessage());
                                writer.writeObject(message);
                                //PassDataToDB("Notifying smart watch to call the fire department for  +" +messageRecieved.getMessage() );
                                writer.flush();
                                message = new Message(5, "do you want to activate the alarm for smoking detection");
                                display("Server ::" + message.getMessage());
                                writer.writeObject(message);
                                PassDataToDB("Notifying smart watch to active the alarm for  +" +messageRecieved.getMessage() );
                                break;
                            }
                            case 6: {
                                display("Door (camera) :: " + messageRecieved.getMessage());
                                ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(SmartWatch.SMART_WATCH_PORT);
                                Message message = new Message(6, "Are pictures are same?");
                                display("Server ::" + message.getMessage());
                                writer.writeObject(message);
                                PassDataToDB("Notifying smart watch for +" +messageRecieved.getMessage() );
                                writer.flush();
                                break;
                            }
                            case 7: {
                                CamRecorder camera;

                                camera = new CamRecorder();
                                camera.setFrameEnable();

                                display("Door Sensor::" + messageRecieved.getMessage());
                                display("Server ::Recording Started");
                                PassDataToDB("Notifying camera to +" +messageRecieved.getMessage() );
                                Timer timer = new Timer(1, 15) {
                                    @Override
                                    protected void onTick() {
                                    }

                                    @Override
                                    protected void onFinish() {
                                        camera.stopRecording();
                                        camera.setFrameDisable();
                                        display("Camera:: Recording Stopped");
                                    }

                                    @Override
                                    protected void onMinutePassed() {
                                    }
                                };
                                timer.start();
                                camera.startRecording();
                                break;
                            }
                            case 8:
                                System.out.println(messageRecieved.getMessage());
                                display(messageRecieved.getMessage());
                                PassDataToDB(messageRecieved.getMessage());
                                break;
                           case 9: {
                                display("Gas Detector::" + messageRecieved.getMessage());
                                ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(SmartWatch.SMART_WATCH_PORT);
                                Message message = new Message(7, "do you want to call the fire department for gas detection");
                                display("Server ::" + message.getMessage());
                                writer.writeObject(message);
                                //PassDataToDB("Notifying smart watch to call the fire department for  +" +messageRecieved.getMessage() );
                                writer.flush();
                                message = new Message(7, "do you want to activate the alarm for gas detection");
                                display("Server ::" + message.getMessage());
                                writer.writeObject(message);
                                PassDataToDB("Notifying smart watch to active the alarm for  +" +messageRecieved.getMessage() );
                                break;
                            }
                            case 10:
                                //canceling
                                display("Smart Watch :: " + messageRecieved.getMessage());
                                System.out.println(messageRecieved.getMessage());
                                display("Server: canceling");
                                PassDataToDB(" the server canceled: "+messageRecieved.getMessage());
                                break;
                            default:
                                break;
                        }

                    }
                }
            } catch (IOException | ClassNotFoundException e) {
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void PassDataToDB(String message) throws ClassNotFoundException, SQLException {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull",
                    "root",
                    "RaedRaed14");
         
            System.out.println("ahmed");
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
            PreparedStatement pre = null ;
          
             pre = con.prepareStatement("insert into iot.server_data (notification_name,date_and_time) values (?,?)");
            //pre.setInt(1, count++);
            pre.setString(1, "The server says: " + message);
            pre.setTimestamp(2, sqlTime);
            pre.executeUpdate();
            
 if (con != null) {
   
     System.out.println(" connection has been established ");
            }
            //--------- close connection
            if (pre != null) {
                try {
                    pre.close();
                    //System.out.println("data saved in the database");
                } catch (SQLException e) {
                    /* ignored */
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }

        }

    }
}
