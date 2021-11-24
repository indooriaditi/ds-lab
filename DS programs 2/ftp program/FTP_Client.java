package com.praneeth;

/*
 * Author:        Stephen Christensen
 * Class Purpose: implements a GUI client interface to send a text file to a FTP server.
 *                Buttons are used to connect and disconnect to the server.  A button opens
 *                window to select a file to view.  A button sends this file to the FTP server
 *                and responds back to the client that the file was received.  A button displays
 *                the Help for the application.  A button closes the application
 */


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class FTP_Client extends JFrame {
    final static int FTP_PORT = 21; //FTP port
    final static String SERVER = "localhost"; //server name
    boolean currentlyConnected = false; //shows whether client is connected to server
    private Socket s; //socket used for connection
    private File fileObjectToSend = null; //file object that is read to be sent
    private InputStream instream; //input stream for client
    private OutputStream outstream; //output stream for client
    private Scanner in; //scans input for client
    private PrintWriter out; //writes output for client
    private FileReader reader; // //file reader to read selected file
    private String fileSend; //name of file to send

    /*
     * Constructor for FTP client. Contains inner classes for event handling.
     * Adds buttons to GUI window.
     */
    public FTP_Client() {
        class connectToServerListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    connectServer();
                } catch (IOException ex) {
                    Logger.getLogger(FTP_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        class disconnectFromServerListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    disconnectServer();
                } catch (IOException ex) {
                    Logger.getLogger(FTP_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        class chooseFileListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    chooseDisplayFile();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FTP_Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FTP_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        class sendFileListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    sendFile();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FTP_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        class helpListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                showHelp();
            }
        }

        class closeApplicationListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                closeApp();
            }
        }

        setVisible(true);
        setTitle("FTP_Client");
        setLayout(new GridLayout(3,2));
        setSize(400,150);

        //interface to connect to server
        JButton connectToServer = new JButton("Connect to server");
        connectToServer.setToolTipText("Click this button to connect to the FTP server");
        add(connectToServer);
        ActionListener connectToServerListener = new connectToServerListener();
        connectToServer.addActionListener(connectToServerListener);

        //interface to disconnect from server
        JButton disconnectFromServer = new JButton("Disconnect from server");
        disconnectFromServer.setToolTipText("Click this button to disconnect from the FTP server");
        add(disconnectFromServer);
        ActionListener disconnectToServerListener = new disconnectFromServerListener();
        disconnectFromServer.addActionListener(disconnectToServerListener);

        //interface to choose file to display
        JButton chooseFile = new JButton("Choose file to display");
        chooseFile.setToolTipText("Click this button to choose a text file and open it in a new window");
        add(chooseFile);
        ActionListener chooseFileListener = new chooseFileListener();
        chooseFile.addActionListener(chooseFileListener);

        //interface to send file to FTP server
        JButton sendFileToServer = new JButton("Send file to server");
        sendFileToServer.setToolTipText("Click this button to send a copy of the selected file to the FTP server");
        add(sendFileToServer);
        ActionListener sendFileListener = new sendFileListener();
        sendFileToServer.addActionListener(sendFileListener);

        //interface for application Help
        JButton applicationHelp = new JButton("Help for application");
        applicationHelp.setToolTipText("Click this button to view the application Help");
        add(applicationHelp);
        ActionListener helpListener = new helpListener();
        applicationHelp.addActionListener(helpListener);

        //interface to close application
        JButton closeApplication = new JButton("Close application");
        closeApplication.setToolTipText("Click this button to close the application");
        add(closeApplication);
        ActionListener closeApplicationListener = new closeApplicationListener();
        closeApplication.addActionListener(closeApplicationListener);
    }

    /*
     * Event handler for button to connect to server
     * Creates input and output streams
     */
    public void connectServer() throws IOException {
        if (!currentlyConnected) {
            s = new Socket(SERVER, FTP_PORT);
            instream = s.getInputStream();
            outstream = s.getOutputStream();
            in = new Scanner(instream);
            out = new PrintWriter(outstream);
            currentlyConnected = true;
        }
    }

    /*
     * Event handler for button to disconnect from server
     */
    public void disconnectServer() throws IOException {
        if (currentlyConnected) {
            s.close();
            currentlyConnected = false;
        }
    }

    /*
     * Event handler for button to choose file to view and to be sent to the server
     */
    public void chooseDisplayFile() throws FileNotFoundException, IOException {
        JFileChooser fileChooserObject = new JFileChooser("C:\\Select");
        fileChooserObject.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fileChooserObject.showOpenDialog(null);
        if (returnVal == 0) {
            fileObjectToSend = fileChooserObject.getSelectedFile();
        }
        fileSend = fileObjectToSend.getName() + " ";
        JFrame frame = new JFrame("Text Contents");
        frame.setVisible(true);
        frame.setSize(300,200);
        JTextArea fileContents = new JTextArea();
        frame.getContentPane().add(new JScrollPane(fileContents), BorderLayout.CENTER);
        try {
            reader = new FileReader(fileObjectToSend);
            BufferedReader br = new BufferedReader(reader);
            fileContents.read(br, fileObjectToSend);
            br.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * Sends file to server.  Receives message from server stating file was transferred
     */
    public void sendFile() throws FileNotFoundException {
        if (fileObjectToSend != null) {
            out.print(fileSend);
            out.flush();
            FileReader readerSend = new FileReader(fileObjectToSend);
            Scanner inFile = new Scanner(readerSend);
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                out.print(line + "\n");
                out.flush();
            }
            out.print("donesendingfile\n");
            out.flush();
        }
        String lineFromServer = in.nextLine();
        if ("success".equals(lineFromServer)) {
            JFrame frame = new JFrame("Message from server");
            JLabel label = new JLabel("File was successfully transferred");
            frame.add(label);
            frame.setSize(400,100);
            frame.setVisible(true);
        }
    }

    /*
     * Event handler for button to display the Help for the application
     */
    public void showHelp() {
        JFrame frame = new JFrame("Help");
        frame.setVisible(true);
        frame.setSize(550,220);
        JTextArea helpContents = new JTextArea();
        frame.getContentPane().add(new JScrollPane(helpContents), BorderLayout.CENTER);
        helpContents.append("The application allows the user to view a text file and send it to an FTP server.\n\n" +
                "To connect to the FTP server, click the 'Connect to server' button.\n" +
                "To disconnect from the FTP server, click the 'Disconnect from server' button.\n" +
                "To select a file to display, click the 'Choose file to display' button.\n" +
                "          An Open File dialog will appear.  Choose the desired file and click 'Open'\n" +
                "          The contents of the file will appear in a new window.\n" +
                "To send the most recently viewed file to the FTP server, click the 'Send file to server' button.\n" +
                "To view the Help for the application, click the 'Help for application' button.\n" +
                "To close the application, click the 'Close application' button.");
    }

    /*
     * Event handler for button to close the application
     */
    public void closeApp() {
        System.exit(0);
    }
}
