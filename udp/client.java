import java.io.*;
import java.net.*;
import java.util.Scanner; 

public class client {
    public static void main(String[] args) throws IOException {
        DatagramSocket c = new DatagramSocket();
        byte[] send = new byte[65535];
        InetAddress ip = InetAddress.getLocalHost();
        Scanner sc = new Scanner(System.in);
        while(true) {
            String str = sc.nextLine();
            send=str.getBytes();
            DatagramPacket sendpacket = new DatagramPacket(send,send.length,ip,1234);
            c.send(sendpacket);
            if(str.equals("bye")) {
                break;
            }
        }
    }
}