import java.net.*;
import java.io.*;

public class server{
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(4999);
        Socket s = ss.accept();
        InputStreamReader ip = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(ip);
        String str = bf.readLine();
        System.out.println("Client says: " + str);

    }
}