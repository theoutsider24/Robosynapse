package server.utility.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.RoboSynapseClient;
import common.utility.Team;
import server.utility.common.Constants;
import server.utility.entities.player.Player;

public class TCPReceiver extends Thread {
    private static final Logger   log  = Logger.getLogger(RoboSynapseClient.class.getName());
    private boolean is_setup;
    private ServerSocket welcomeSocket;
    private ArrayList<Player> teams = new ArrayList<Player>();
    private ArrayList<InetAddress> IPAddresses = new ArrayList<>(); 
    private boolean keepGoing=true;
    Socket connectionSocket; 
    
    @Override
	public void run(){
        log.setLevel(Constants.DEBUG_LEVEL);
        log.log(Level.INFO, "Thread running..");
        if(is_setup == false){
            setup();
        }

    	
        while(keepGoing){
            receivePacket();
        }
    }
    @Override 
    public void start()
    {
    	super.start();
    }
    public void setup(){
        log.setLevel(Constants.DEBUG_LEVEL);
        try {
            welcomeSocket  = new ServerSocket(Constants.TCP_LISTENING_PORT);
            is_setup = true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating socket for TCPReciever", e);
        }
    }
    
    public void receivePacket(){
        if(is_setup){
            try{
                log.log(Level.INFO, "nowListening!");
                //accept the connection
                connectionSocket = welcomeSocket.accept();
                connectionSocket.getInetAddress();
                connectionSocket.getPort();
                //create an input stream to listen for any new players
                InputStream is = connectionSocket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                
                //take in team object
                //check if team already in the player list
                //if not add to player list.
                
                //take in the data that was sent
                addToPlayerList((Player) ois.readObject());
                
                //add this connection to the client address book if it does not exist already
                addAddress(connectionSocket);
                ois.close();
                
            }catch(SocketException e){
                if("socket closed".equals(e.getMessage())){
                    log.log(Level.SEVERE, "Cannot accept new connections. Socket closed.");
                } else {
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * A way to store IPAddresses and ports to broadcast to
     * 
     */
    private void addAddress(Socket socket){
        boolean exists = false;

        InetAddress address = socket.getInetAddress();
        int port = socket.getPort();
        for(int i=0; i<IPAddresses.size(); i++){
            if(address == IPAddresses.get(i)){
                exists = true;
                break;
            }
        }

        /*
         * If it doesn't exist already then add the IP Address to the list
         */
        if(exists == false){
            IPAddresses.add(address);
        }
    }
    
//  private void assignToCorrectType(ArrayList<Entity> entity){
//      if(entity.size() > 0){
//          switch(entity.get(0).getType()){
//              case "Player":
//                  addToPlayerList(entity, players);
//                  break;
//              default: System.out.println("Nothing here");
//          }
//      }
//  }
    
    /**
     * check if entity already exists in player
     * 
     * @param entity
     * @param player
     */
    private void addToPlayerList(Player player){
        boolean exists = false;
        System.out.println("Working with: " + player.getName() + ". Current list is: "+  teams.toString());
        for(int i = 0; i < teams.size(); i++){
            if(teams.get(i).getName().equals(player.getName())){
                exists = true;
                break;
            }
        }
        
        if(!exists){
            System.out.println("Added!");
            teams.add(player);
            
        }
    }
//  public ArrayList<Entity> getEntities(){
//      return entities;
//  }

    public ArrayList<Player> getTeams(){
        if(teams != null){
            return teams;
        }else{
            return null;
        }
    }
    
    public ArrayList<InetAddress> getIPAddresses(){
        return IPAddresses;
    }/*
    
//  public ArrayList<ClientInfo> getClients(){
//      return clients;
//  }
    public void start(){
        System.out.println("Starting network thread..");
        if(t == null){
            t = new Thread(this);
            t.start();
        }
    }*/
    
    public void disconnect(){
    	System.out.println("trying");
    	/*t.interrupt();
        t = null;*/
    	keepGoing=false;
    	is_setup = false;
        try {
            welcomeSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	try {
    		System.out.println("about to join");
			this.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	System.out.println("joined");
        
    }
    
    public boolean isSetup(){
        if(is_setup){
            return true;
        }else return false;
    }
    
    private void listPlayersToSTD()
    {
        for(Player player : teams)
        {
            System.out.println(player.getName());
        }
    }
}