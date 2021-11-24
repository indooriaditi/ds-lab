import java.rmi.registry.*;

public class client{

    private client() { }
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry(null);
            Hello stub = (Hello) registry.lookup("Hello");
            stub.printMsg();
        }
        catch(Exception e){
            System.err.println("Client Exception: "+e.toString());
            e.printStackTrace();
        }
        
    }
}