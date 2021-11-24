import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",3999);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str = "", str2 = "";
        while(!str.equals("stop")) {
            str = bf.readLine();
            dout.writeUTF(str);
            dout.flush();
            str2 = din.readUTF();
            System.out.println("Server: "+ str2);
        }

        dout.close();
        s.close();
    } 
}