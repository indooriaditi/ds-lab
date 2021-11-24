import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class client {
    public static void main(String[] args) throws IOException {
        try {
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip,5056);
        Scanner sc = new Scanner(System.in);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        while(true) {
            System.out.println(dis.readUTF());
            String toSend = sc.nextLine();
            dos.writeUTF(toSend);
            if(toSend.equals("exit")){
                s.close();
                break;
            }
            String receive = new String();
            receive = dis.readUTF();
            System.out.println("Received: "+ receive);
        } 
        sc.close();
        dis.close();
        dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}