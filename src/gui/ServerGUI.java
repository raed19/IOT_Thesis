/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author smsta
 */
public class ServerGUI extends javax.swing.JFrame  {

    private Server server;
    private static ServerGUI serverGUI;

    public ServerGUI() throws IOException {
        
        initComponents();
        server = null;

    }

    public static synchronized ServerGUI getInstance() throws IOException {
        if (serverGUI == null) {
            serverGUI = new ServerGUI();
        }
        return serverGUI;
    }

    public void appendToTextArea(String str) {
        textArea.append(str);
        textArea.setCaretPosition(textArea.getText().length() - 1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        serverJLabel = new javax.swing.JLabel();
        startServerButton = new javax.swing.JButton();
        textAreaJPanel = new javax.swing.JPanel();
        textAreaScrollPanel = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setAlwaysOnTop(true);
        setResizable(false);

        parentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        serverJLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        serverJLabel.setText("SERVER");

        startServerButton.setText("Start");
        startServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startServerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(serverJLabel)
                .addGap(371, 371, 371))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(serverJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(startServerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        parentPanel.add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 620, 30));

        textAreaJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Messages"));

        textArea.setColumns(20);
        textArea.setRows(5);
        textArea.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        textArea.setEnabled(false);
        textAreaScrollPanel.setViewportView(textArea);

        javax.swing.GroupLayout textAreaJPanelLayout = new javax.swing.GroupLayout(textAreaJPanel);
        textAreaJPanel.setLayout(textAreaJPanelLayout);
        textAreaJPanelLayout.setHorizontalGroup(
            textAreaJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textAreaJPanelLayout.createSequentialGroup()
                .addComponent(textAreaScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        textAreaJPanelLayout.setVerticalGroup(
            textAreaJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textAreaJPanelLayout.createSequentialGroup()
                .addComponent(textAreaScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        parentPanel.add(textAreaJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 52, 630, 430));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerButtonActionPerformed
        // TODO add your handling code here:

        server = new Server(this);
        new ServerRunning().start();
        startServerButton.setEnabled(false);
    }//GEN-LAST:event_startServerButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        try {
//            new ServerGUI().setVisible(true);
//        } catch (IOException ex) {
//            
//        }
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ServerGUI().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel parentPanel;
    private javax.swing.JLabel serverJLabel;
    private javax.swing.JButton startServerButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JPanel textAreaJPanel;
    private javax.swing.JScrollPane textAreaScrollPanel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    public JLabel getServerJLabel() {
        return serverJLabel;
    }

    public void setServerJLabel(JLabel serverJLabel) {
        this.serverJLabel = serverJLabel;
    }

  

    /*
	 * A thread to run the Server
     */
    class ServerRunning extends Thread {

        public void run() {
            try {
                server.start();         // should execute until if fails
            } catch (IOException ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            // the server failed
            server = null;
        }
    }
}
