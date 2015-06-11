import static java.lang.System.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author Nathan Dias {@literal <nathanxyzdias@gmail.com>}
 */
public class Client {
    
    static String address;
    static{
        try{address= InetAddress.getLocalHost().getHostAddress();}
        catch(Exception e){}
    }
    
    
    public static void main(String[] args)throws IOException{
        
        final Scanner yo = new Scanner(System.in);
        System.out.println("IP address");
        
        String ip = yo.nextLine();
        
        System.out.println("port");
        
        int port = Integer.valueOf(yo.nextLine());
        
        final DatagramSocket receive = new DatagramSocket(port);
        
        final DatagramSocket send  = new DatagramSocket();
        
        System.out.println("username");
        
        final String username=yo.nextLine();
        
        final String uuid = username+address;
        
        new Thread(()->{
            
            while(true){
                String s = yo.nextLine();
                try{
                send(s,ip,port,send);
                }catch(Exception e){
                    System.out.println("NETWORK ERROR");
                }
            }
            
            
        }).start();
        
        new Thread(()->{
            DatagramPacket p = new DatagramPacket(new byte[512],512);
            while(true){
                try{
                receive.receive(p);
                }catch(Exception e){}
                handle(p,uuid);
                
                
                
            }
            
        }).start();
        
        
    }
    
    
    static void send(String s,String ip,int port,DatagramSocket st)throws Exception{
        DatagramPacket p = new DatagramPacket(s.getBytes(),s.length(),
                                                    InetAddress.getByName(ip),port);
        st.send(p);
    }
    
    
    static void handle(DatagramPacket p,String uuid){
        String message = new String(p.getData());
        
        String[] split = message.split(" ");
        
        if(split[0].equals(uuid))
            return;
        
        System.out.println(message.substring(message.indexOf(" ")+1));
    }
    
}
