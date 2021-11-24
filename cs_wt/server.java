import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class server {

  public static void main(String[] args) throws IOException {
    ServerSocket ss = new ServerSocket(5056);
    while (true) {
      Socket s = null;
      try {
        s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        Thread t = new ClientHandler(s, dis, dos);
        t.start();
      } catch (Exception e) {
        s.close();
        e.printStackTrace();
      }
    }
  }
}

class ClientHandler extends Thread {

  DateFormat fordate = new SimpleDateFormat("YYYY/MM/DD");
  DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
  final DataInputStream dis;
  final DataOutputStream dos;
  final Socket s;

  public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
    this.s = s;
    this.dis = dis;
    this.dos = dos;
  }

  @Override
  public void run() {
    String toReturn;
    String received;
    while (true) {
      try {
        dos.writeUTF("Select one [Date|Time]..Enter 'exit' to exit");
        received = dis.readUTF();
        if (received.equals("exit")) {
          System.out.println("Client " + this.s + " sends exit...");
          System.out.println("Closing this connection.");
          this.s.close();
          System.out.println("Connection closed");
          break;
        }
        Date date = new Date();
        switch (received) {
          case "Date":
            toReturn = fordate.format(date);
            dos.writeUTF(toReturn);
            break;
          case "Time":
            toReturn = fortime.format(date);
            dos.writeUTF(toReturn);
            break;
          default:
            dos.writeUTF("Invalid");
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      this.dis.close();
      this.dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
