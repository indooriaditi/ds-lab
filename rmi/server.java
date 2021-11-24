import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class server extends ImplExample{

    public server() { }
    public static void main(String[] args) {
        try{
            ImplExample obj = new ImplExample();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj,0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello",stub);
            System.err.println("Server ready");
        }
        catch(Exception e){
            System.err.println("Server Exception: "+e.toString());
            e.printStackTrace();
        }
    }
}