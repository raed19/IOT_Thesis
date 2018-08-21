/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;

/**
 *
 * @author Raed
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public int getID() {
        return ID;
    }

    public String getMessage() {
        return message;
    }
    private int ID;
    private String message;

    public Message(int ID, String message) {
        this.ID = ID;
        this.message = message;
    }
    /*
    public void set( String message) {
       
        this.message = message;
    }*/
   public void ConnectionStatus ( int ID , String message )
   {
       
   }
}
