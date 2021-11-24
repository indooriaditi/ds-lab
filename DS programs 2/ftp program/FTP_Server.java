package com.praneeth;

/*
 * Author:        Stephen Christensen
 * Class Purpose: implements a server to accept a text file from a client.
 *
 */


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FTP_Server {
    final static String transferFilePath = "C:\\Users\\Praneeth\\OneDrive\\Documents\\DS Server\\"; //path to save files

    /*
     * main method for server
     * accepts connection from client and receives a text file from client.
     * Server sends a message back to the client stating the file was successfully transfered.
     */
    public static void main(String[] args) throws IOException {
        File outputFile = null;
        FileWriter writer;
        ServerSocket server = new ServerSocket(FTP_Client.FTP_PORT);
        Socket s = server.accept();
        InetAddress address = s.getInetAddress();
        String addressString = address.toString();
        System.out.println("Connected to " + addressString.substring(1));
        InputStream instream = s.getInputStream();
        OutputStream outstream = s.getOutputStream();
        Scanner in = new Scanner(instream);
        PrintWriter out = new PrintWriter(outstream);
        boolean newFileCreated = false;
        while(true) {
            if(!in.hasNext()) return;
            if (newFileCreated == false) {
                String theFileName = in.next();
                outputFile = new File(transferFilePath + theFileName);
                System.out.println("The transfered file is at: " + transferFilePath + theFileName);
                newFileCreated = true;
            }
            in.useDelimiter("\n");
            writer = new FileWriter(outputFile, true);
            String lineFromClient = in.next();
            if ("donesendingfile".equals(lineFromClient)) {
                System.out.println("File was successfully transferred");
                out.println("success");
                out.flush();
            }
            else {
                if (" ".equals(lineFromClient.substring(0,1)))
                    lineFromClient = lineFromClient.substring(1);
                writer.append(lineFromClient + System.getProperty("line.separator"));
                writer.close();
            }
        }
    }
}