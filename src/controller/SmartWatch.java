package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import gui.SmartWatchGUI;
import javax.swing.JOptionPane;

public class SmartWatch {

    public static int SMART_WATCH_PORT = 10003;
    private static int count = 1;
    //  static HashSet<String> set = new HashSet<String>();
    static boolean check = true;
    private InetAddress ip;
    private Socket s;
    private ObjectInputStream dis;
    private ObjectOutputStream dos;

    private SmartWatchGUI smartWatchGUI;

    private String choice = "";

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public SmartWatch(SmartWatchGUI smartWatchGUI) {
        this.smartWatchGUI = smartWatchGUI;
        try {
            // getting localhost ip
            ip = InetAddress.getByName("localhost");
            // establish the connection with server port 5056
            s = new Socket(ip, Server.SERVER_PORT, ip, SMART_WATCH_PORT);
            // obtaining input and out streams
            dis = new ObjectInputStream(s.getInputStream());
            dos = new ObjectOutputStream(s.getOutputStream());
            if (s.isConnected()) {
                System.out.println("Smart Watch has established connection with Server");
                display("Smart Watch has established connection with Server");
            }
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }

    public boolean isConnected() {
        return s.isConnected();
    }

    public void start() throws SQLException {
        try {
            while (isConnected()) {

                Message fromServer;
                fromServer = (Message) dis.readObject();
                switch (fromServer.getID()) {
                    case 1: {
                        smartWatchGUI.smartWatchLEDColors(false, false, true, false,false);
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        int input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Broken Window", JOptionPane.YES_NO_OPTION);
                        PassDataToDBSmartWatch("Notifying from the server for  +" +fromServer.getMessage());
                        if (input == 0) {

                            Message message = new Message(3, "call the police for broken window");
                            //   PassDataToDBSmartWatch("call the police for broken window ");
                            display("Smart Watch:: yes," + message.getMessage());
                            dos.writeObject(message);
                            PassDataToDBSmartWatch("user agree to call the police for  +" +fromServer.getMessage());
                            dos.flush();

                        } else {
                            System.out.println("Calling police is canceled for " + fromServer.getMessage());
                            display("Calling police is canceled for broken window");
                            Message message = new Message(10, "call the police for broken window");
                           PassDataToDBSmartWatch("user denied to call the police for  +" +fromServer.getMessage());
                        }

                        fromServer = (Message) dis.readObject();
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        System.out.println(fromServer.getMessage() + " (yes|no)");

                        input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Broken Window", JOptionPane.YES_NO_OPTION);
                         
                        if (input == 0) {
                            Message message = new Message(4, "notify the alarm for broken window");                   
                            display("Smart Watch:: yes," + message.getMessage());
                            dos.writeObject(message);
                            PassDataToDBSmartWatch("user agree to active the alarm for  +" +fromServer.getMessage());
                            dos.flush();
                        } else { // Thread.sleep(70000);
                            System.out.println("notifying the alarm is canceled");
                            display("notifying the alarm is canceled");
                             Message message = new Message(10, "turning on the alaram for broken window");
                            PassDataToDBSmartWatch("user denied to active the alarm for  +" +fromServer.getMessage());
                        }
                        smartWatchGUI.smartWatchLEDColors(false, false, false, false,false);
                        break;
                    }
                    case 2: {
                        smartWatchGUI.smartWatchLEDColors(true, false, false, false,false);
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        int input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Water Leakage", JOptionPane.YES_NO_OPTION);
                          PassDataToDBSmartWatch("Notifying from the server for  +" +fromServer.getMessage());
                        if (input == 0) {
                            Message message = new Message(3, "call the manager for water leakage");
                            display("Smart Watch:: yes," + message.getMessage());
                            System.err.println("Smart Watch:: yes," + message.getMessage());
                            
                            dos.writeObject(message);
                             PassDataToDBSmartWatch("user agree to call the manager for  +" +fromServer.getMessage());
                            dos.flush();
                            //  PassDataToDBSmartWatch("calling police is canceled for " + fromServer.getMessage() );
                        } else {
                            System.out.println("Calling manager is canceled for ");
                            display("Calling manager is canceled for  water leakage");
                             Message message = new Message(10, "calling  the police for broken window");
                            PassDataToDBSmartWatch("user denied to call the manager for  +" +fromServer.getMessage());
                        }
                        fromServer = (Message) dis.readObject();
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Water Leakage", JOptionPane.YES_NO_OPTION);
                        if (input == 0) {
                            Message message = new Message(4, "notify the alarm for water leakage");
                            display("Smart Watch:: yes," + message.getMessage());
                            System.err.println("Smart Watch:: yes," + message.getMessage());
                            PassDataToDBSmartWatch("user agree to active the alarm for  +" +fromServer.getMessage());
                            dos.writeObject(message);
                            dos.flush();
                        } else {
                            System.out.println("notifying the alarm is canceled");
                            display("notifying the alarm is canceled");
                             Message message = new Message(10, "turning on the alaram for water leakage window");
                           PassDataToDBSmartWatch("user denied to active the alarm for  +" +fromServer.getMessage());
                        }
                        smartWatchGUI.smartWatchLEDColors(false, false, false, false,false);
                        break;
                    }
                    case 5: {
                        smartWatchGUI.smartWatchLEDColors(false, true, false, false,false);
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        int input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Smoke Detected", JOptionPane.YES_NO_OPTION);
                         PassDataToDBSmartWatch("Notifying from the server for  +" +fromServer.getMessage());
                        if (input == 0) {
                            Message message = new Message(8, "call the fire department for smoking detection");
                            display("Smart Watch:: yes," + message.getMessage());
                            PassDataToDBSmartWatch("user agree to call the fire department for  +" +fromServer.getMessage());
                            dos.writeObject(message);
                            dos.flush();
                        } else { // Thread.sleep(70000);

                            System.out.println("Calling fire department is canceled");
                            display("Calling fire department is canceled");
                             Message message = new Message(10, "call the fire department for smoke detection");
                            PassDataToDBSmartWatch("user denied to call the fire department for  +" +fromServer.getMessage());
                        }
                        fromServer = (Message) dis.readObject();
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Smoke Detected", JOptionPane.YES_NO_OPTION);
                        if (input == 0) {
                            Message message = new Message(4, "notify the alarm for smoke detection");
                            display("Smart Watch:: yes," + message.getMessage());
                            PassDataToDBSmartWatch("user agree to active the alarm for  +" +fromServer.getMessage());
                            dos.writeObject(message);
                            dos.flush();
                            
                        } else { // Thread.sleep(70000);
                            System.out.println("notifying the alarm is canceled");
                            display("notifying the alarm is canceled");
                             Message message = new Message(10, "turning on the alaram for smoke detection");
                            PassDataToDBSmartWatch("user denied to active the alarm for  +" +fromServer.getMessage());
                        }
                        smartWatchGUI.smartWatchLEDColors(false, false, false, false,false);
                        break;
                    }
                    case 6: {
                        smartWatchGUI.smartWatchLEDColors(false, false, false, true,false);
                        System.out.println(fromServer.getMessage());
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        int input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Motion Detected", JOptionPane.YES_NO_OPTION);
                          PassDataToDBSmartWatch("Notifying from the server for  +" +fromServer.getMessage());
                        if (input == 0) {
                            System.out.println("camera will stop taking pictures ");
                            Message message = new Message(8, "pictures are the same");
                            display("Smart Watch:: yes," + message.getMessage());
                            dos.writeObject(message);
                            PassDataToDBSmartWatch("user agree that pictures are the same, so no notifcation from the camera to start recording ");
                            dos.flush();
                            // PassDataToDBSmartWatch("camera will stop taking pictures" );
                        } else {
                            Message message = new Message(7, "notify camera to start recording ");
                            PassDataToDBSmartWatch("user agree that pictures are different, so notify camera to start recording  ");
                            display("Smart Watch:: " + message.getMessage());
                            dos.writeObject(message);
                            dos.flush();
                        }
                        smartWatchGUI.smartWatchLEDColors(false, false, false, false,false);
                        break;
                    }
                    case 7: {
                        smartWatchGUI.smartWatchLEDColors(false, false, false, false,true);
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        int input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "Smoke Detected", JOptionPane.YES_NO_OPTION);
                         PassDataToDBSmartWatch("Notifying from the server for  +" +fromServer.getMessage());
                        if (input == 0) {
                            Message message = new Message(8, "call the fire department for gas detection");
                            display("Smart Watch:: yes," + message.getMessage());
                           PassDataToDBSmartWatch("user agree to call the fire department for  +" +fromServer.getMessage());
                            dos.writeObject(message);
                            dos.flush();
                        } else { // Thread.sleep(70000);

                            System.out.println("Calling fire department is canceled");
                            display("Calling fire department is canceled");
                             Message message = new Message(10, "call the police for gas detection");
                           PassDataToDBSmartWatch("user denied to call the fire department for  +" +fromServer.getMessage());
                        }
                        fromServer = (Message) dis.readObject();
                        System.out.println(fromServer.getMessage() + " (yes|no)");
                        display("Server:: " + fromServer.getMessage() + " (yes|no)");
                        input = JOptionPane.showConfirmDialog(smartWatchGUI,
                                fromServer.getMessage(), "gas Detected", JOptionPane.YES_NO_OPTION);
                        if (input == 0) {
                            Message message = new Message(4, "notify the alarm for gas detection");
                            display("Smart Watch:: yes," + message.getMessage());
                            PassDataToDBSmartWatch("user agree to active the alarm for  +" +fromServer.getMessage());
                            dos.writeObject(message);
                            dos.flush();               
                        } else { // Thread.sleep(70000);
                            System.out.println("notifying the alarm is canceled");
                            display("notifying the alarm is canceled");
                             Message message = new Message(10, "turning on the alaram for gas detection");
                            PassDataToDBSmartWatch("user denied to active the alarm for  +" +fromServer.getMessage());
                        }
                        smartWatchGUI.smartWatchLEDColors(false, false, false, false,false);
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
        }
    }

    private void display(String str) {
        if (smartWatchGUI != null) {
            smartWatchGUI.appendToTextArea(str + "\n");
        }
    }

    public static void PassDataToDBSmartWatch(String message) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull",
                "root",
                "RaedRaed14");
        if (con != null) {
            System.out.println(" connection has been established ");
        }

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
        PreparedStatement pre = con.prepareStatement("insert into iot.smart_watch_data (notification_name,date_and_time)values (?,?)");
       // pre.setInt(1, count++);
        pre.setString(1, "The smart watch says: " + message);
        pre.setTimestamp(2, sqlTime);
        pre.executeUpdate();

        //--------- close connection
        if (pre != null) {
            try {
                pre.close();
                System.out.println("data saved in the database");
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
