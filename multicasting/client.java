import java.io.*;
import java.net.*;

public class client implements Runnable {

  public static void main(String[] args) {
    client c = new client();
    Thread t = new Thread(c);
    t.start();
  }

  public void receiveMsg(String ip, int port) throws IOException {
    MulticastSocket c = new MulticastSocket(4321);
    byte[] receive = new byte[1024];
    InetAddress group = InetAddress.getByName("230.0.0.0");
    c.joinGroup(group);
    while (true) {
      System.out.println("Waiting for message..");
      DatagramPacket receivepacket = new DatagramPacket(receive,receive.length);
      c.receive(receivepacket);
      String msg = new String(receivepacket.getData(),receivepacket.getOffset(),receivepacket.getLength());
      System.out.println("Multicast message: " + msg);
      if (msg.equals("OK")) {
        System.out.println("Exiting.. ");
        break;
      }
    }
    c.leaveGroup(group);
    c.close();
  }

  @Override
  public void run() {
    try {
      receiveMsg("230.0.0.0", 4321);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
