package client.utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.utility.entities.player.Player;
import client.RoboSynapseClient;
import common.utility.Team;

public class TCPBroadcaster implements Runnable{
    private static final Logger log = Logger.getLogger( RoboSynapseClient.class.getName() );
    static Socket socket;
    static String host;
    static int dest_port;
    static Team team;
    Thread t;
    static Player playerToSend;
    boolean is_setup = false;
    private static TCPBroadcaster broadcaster;
    int heart_beat;
    
    @Override
    public void run(){
        log.setLevel(Constants.DEBUG_LEVEL);
        //TODO choosing new timing mechanism, Timer, or scheduler
//        Clock clock = new Clock();
//        Time heart_beat;
        
//      registerPlayer();
        
        /**
         * Attempt to re-register the bot at a set interval of time
         */
        do{
            heart_beat++;// = clock.getElapsedTime();
            if(heart_beat >= Constants.BEAT){
//              registerPlayer();
                if(playerToSend != null)
                {
                    System.out.println("player found");
                    registerPlayer(playerToSend);
                    System.out.println("Player Sent");
                    playerToSend = null;
                }
//                clock.restart();
            }
        }while(t != null);
    }
    
    public static TCPBroadcaster getBroadcaster() {
    	if (broadcaster == null) {
    		broadcaster = new TCPBroadcaster("localhost", Constants.CLIENT_DESTINATION_PORT, RoboSynapseClient.getTeam());
    	}
    	return broadcaster;
    }
    	
    
    private TCPBroadcaster(String host, int dest_port, Team team){
        this.host = host;
        this.dest_port = dest_port;
        this.team = team;
    }
    
    public static void registerPlayer(final Player player){
        try {
            //initialize the socket
            socket = new Socket(host, dest_port);
        }catch(SocketException e){
            if("Connection refused: connect".equals(e.getMessage())){
                log.log(Level.SEVERE, "Server unreachable. TCP socket not initialized.");
            }else{
                e.printStackTrace();
            }
        }catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        if(socket != null){ 
            try{
        
                //create an output stream that will accommodate sending objects over the network
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                
                oos.writeObject(player);
                //if a connection is made, the object written on this line will be sent over
                //to the server
                log.log(Level.INFO, "Registered!");
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                
            }   
        }
        
    }
    
    public void terminate(){
        t = null;
    }
    
    public void start(){
        System.out.println("Starting TCP broadcaster thread for client..");
        if(t == null){
            t = new Thread(this);
            t.start();
        }
    }
    
    public static void addPlayerToSend(final Player player)
    {
        System.out.println("Player added to queue");
        playerToSend = player;
    }
}