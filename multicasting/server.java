import java.io.*;
import java.net.*;

public class server {

    public static void sendMsg(String msg, String ip, int port) throws IOException {
        DatagramSocket s = new DatagramSocket();
        byte[] send = new byte[1024];
        InetAddress group = InetAddress.getByName(ip);
        send = msg.getBytes();
        DatagramPacket sendpacket = new DatagramPacket(send,send.length,group,port);
        s.send(sendpacket);
        s.close();
    }

    public static void main(String[] args) throws IOException {
        sendMsg("This is message1","230.0.0.0",4321);
        sendMsg("This is message2","230.0.0.0",4321);
        sendMsg("This is message3","230.0.0.0",4321);
        sendMsg("OK","230.0.0.0",4321);

    }
}