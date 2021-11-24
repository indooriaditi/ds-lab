import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3999);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str="", str2="";
        while(!str.equals("stop")) {
            str = din.readUTF();
            System.out.println("Client: "+ str);
            str2 = bf.readLine();
            dout.writeUTF(str2);
            dout.flush();
        }

        din.close();
        s.close();
        ss.close();
    }
}