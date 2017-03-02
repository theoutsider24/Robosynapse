package server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.util.Timer;

import client.RoboSynapseClient;
import static server.utility.common.Constants.*;
import common.utility.Entity;
import server.utility.common.Game;
import server.utility.common.JsonReader;
import server.utility.entities.player.Behaviours.Behaviour;
import server.utility.map.pathfinder.AStarPathFinder;
//import server.utility.map.SpawnPoint;
import server.utility.network.Database;
import server.utility.network.TCPReceiver;
import server.utility.network.UDPBroadcaster;

public class RoboSynapseServer extends Thread{
    private static final Logger   log  = Logger.getLogger(RoboSynapseClient.class.getName());
    private static Game game;
    private static UDPBroadcaster broadcaster;
    private static ArrayList<InetAddress> IPAddresses;
    private TCPReceiver receiver;
    private static boolean running;
//    private static Clock tickClock;
    
    private static void update() {
//        if(tickClock.getElapsedTime().asMilliseconds() > 5){
            game.update();
//            tickClock.restart();
//        }
    }

    public void updateClientInfo(){
        IPAddresses = receiver.getIPAddresses();
    }
    
    private static void init() throws Exception {
		JsonReader.readGameDefinition("game.json");  
		AStarPathFinder.init();
        game = Game.getInstance();
        
        //temporary address. should be obtained via receiver
        IPAddresses = new ArrayList<>();
        IPAddresses.add(InetAddress.getByName("localhost"));
        broadcaster = new UDPBroadcaster();
//        tickClock = new Clock();
    }
    
    public  void listen(){
        receiver = new TCPReceiver();
        receiver.start();
    }   
    
    /*public void updateSpawn() {
        spawn_points = Database.getSpawnPoints();
        Collections.sort(spawn_points);
        log.log(Level.INFO, "New Spawn Points: " + spawn_points.toString());
    }*///TODO make this update the map's spawnpoint list
    @Override
    public void run() {
        System.out.println("Server thread now running..");
        try {
            init();
            listen(); //starts tcpReciever that will listen for new players being added
            
            //timer tickClock will always be 0?
            Timer timer = new Timer();
            timer.reset();
            float secondsPerFrame=1/FPS_LIMIT;
            while(running){
            	timer.reset();
                //check for new player
                update();
                //pass any new player to gaem
                broadcast(Game.getInstance().getEntities());
                
                timer.tick();
                while(timer.getTime()<secondsPerFrame)
                {
                	timer.tick();
                }
            }

            Game.destroyInstance();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }
    
    public static void addPlayer(String name, int spawnZone, Behaviour behaviour) {
        //TODO get tank parts list from db given team name        
    }
    
    @Override
    public void start() {
        log.setLevel(DEBUG_LEVEL);
        System.out.println("Starting server thread..");
        running = true;
        super.start();
    }
    
    public void stopServer() {
        running = false;
        try {
            this.join();
            receiver.disconnect();
            System.out.println("Server Stopped");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void broadcast(ArrayList<Entity> entities){
        log.log(Level.FINER, "Broadcasting entities");
        broadcaster.broadcastPacket(entities, IPAddresses);
    }
}