package server.utility.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import common.utility.Entity;

public class UDPBroadcaster {
    //DatagramSocket serverSocket;
    int client_port = 2000;
    int data_size = 10000;
    
	public void broadcastPacket(ArrayList<Entity> entities, ArrayList<InetAddress> IPAddresses){
        try (
        		DatagramSocket serverSocket = new DatagramSocket();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream(data_size);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
			){
            oos.writeObject(entities);
            oos.flush();
            byte[] data = baos.toByteArray();
            
            for(int j = 0; j < IPAddresses.size(); j++){
                DatagramPacket sendPacket = new DatagramPacket(data,
                        data.length, IPAddresses.get(j), client_port);
                serverSocket.send(sendPacket);
            }
            
            oos.close();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /*
    public void terminate(){
        serverSocket.close();
    }*/

}