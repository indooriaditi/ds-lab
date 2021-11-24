package com.praneeth;


/*
 * Author:        Stephen Christensen
 * Class Purpose: implements a main frame for GUI client interface.
 *                Contains main method for program
 */



import javax.swing.JFrame;

public class FTP_App {

    /*
     * main method for program
     * Creates frame for GUI client
     */
    public static void main(String[] args) {
        FTP_Client frame = new FTP_Client();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}