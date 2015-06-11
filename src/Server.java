
import java.io.IOException;
import java.util.*;
import java.net.*;

/**
 *
 * @author Nathan Dias {@literal <nathanxyzdias@gmail.com>}
 */
public class Server {
    
    
    public static void main(String[] args){
        Scanner yo = new Scanner(System.in);
        System.out.println("Port: ");
        int port = yo.nextInt();
        System.out.println("starting server");
        
        DatagramSocket so=null;
        
        try{
            so = new DatagramSocket(port);
        }catch(IOException e){
            System.out.println("could not create socket");
            System.exit(1);
        }
        
        final DatagramSocket s = so;
        System.out.println("server initialized");
        
        Set<SocketAddress> l = new HashSet<>();
        
        new Thread(()->{
            while(true){
                DatagramPacket p = new DatagramPacket(new byte[512], 512);
                
                try{
                    s.receive(p);
                }catch(IOException e){}
                
                process(p,l,s);
                
                l.add(p.getSocketAddress());
             
            }
            
        }).start();
        
        System.out.println("server started");
    }
    
    static void process(DatagramPacket p, Set<SocketAddress> l,DatagramSocket s){
        System.out.println(new String(p.getData()));
        try{
            for(SocketAddress i:l){
                DatagramPacket pa = new DatagramPacket(
                                        p.getData(), p.getData().length, i);
                s.send(pa);
            }
        }catch(IOException e){}
    }
    
}
