package client.utility;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.RoboSynapseClient;
import common.utility.Entity;

public class UDPReceiver implements Runnable{
    private static final Logger log = Logger.getLogger( RoboSynapseClient.class.getName() );
    private int port;
    private int data_size;
    private byte[] receiveData;
    private boolean is_setup;
    private static UDPReceiver receiver;
        
    DatagramSocket clientSocket;
    DatagramPacket receivePacket;
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<InetAddress> IPAddresses = new ArrayList<InetAddress>();
    private Thread t;
    
    public static UDPReceiver getReceiver() {
    	if (receiver == null) {
    		receiver = new UDPReceiver();
    	}
    	return receiver;
    }
    
    @Override
    public void run(){
        log.setLevel(Constants.DEBUG_LEVEL);
        log.log(Level.INFO, "Thread running..");
        if(is_setup == false){
            setup();
        }
        
        /*
         * can t be set to null while in an infinite loop?
         * --------
         * yes because this is being ran from another thread, so it should work.... I believe 
         */
        while(t != null){
            receivePacket();
        }
    }
    
//  public UDPReceiver(int port, int data_size){
//      this.port = port;
//      this.data_size = data_size;
//  }
    
    public void setup(){
        port = Constants.SERVER_LISTENING_PORT;
        data_size = Constants.DATA_SIZE;
        try {
            clientSocket = new DatagramSocket(port);
            receiveData = new byte[data_size];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            is_setup = true;
            log.log(Level.INFO, "Successfully setup! Port: " + port);
        } catch (Exception e) {
            log.log(Level.SEVERE, "UDPReciever setup() failed", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void receivePacket(){
        try{
            clientSocket.receive(receivePacket);
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    receivePacket.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);

            // take the bot object from the packet and store it inside our local
            entities = (ArrayList<Entity>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            if("socket closed".equals(e.getMessage())){
                System.err.println("Failed to read incomming object. Socket closed.");
            }else{
                e.printStackTrace();
            }
        }
    }
        
    public ArrayList<Entity> getEntities(){
        return entities;
    }

    public ArrayList<InetAddress> getIPAddresses(){
        return IPAddresses;
    }
    public void start(){
        System.out.println("Starting network thread..");
        if(t == null){
            t = new Thread(this);
            t.start();
        }
    }
    
    public void disconnect(){
        clientSocket.close();
        t = null;
    }
    
    public boolean isSetup(){
        if(is_setup){
            return true;
        }else return false;
    }
}