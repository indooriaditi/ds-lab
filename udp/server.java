import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
        DatagramSocket s = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        while(true){
            DatagramPacket receivepacket = new DatagramPacket(receive,receive.length);
            s.receive(receivepacket);
            System.out.println("client says: " + data(receive));
            if(data(receive).toString().equals("bye")){
                break;
            }
            receive = new byte[65535];
        }
    }

    public static StringBuilder data(byte[] a){
        if(a==null){
            return null;
        }
        StringBuilder str = new StringBuilder();
        int i=0;
        while(a[i]!=0){
            str.append((char) a[i]);
            i++;
        }
        return str;
    }
}