package server;

import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;






import org.newdawn.slick.geom.Vector2f;

import server.utility.Command;
import server.utility.CommandRunner;
import server.utility.common.Game;
import server.utility.common.JsonReader;
import server.utility.entities.player.Player;
import server.utility.entities.player.PlayerManager;
import server.utility.map.pathfinder.AStarPathFinder;
import client.RoboSynapseClient;
import client.utility.Constants;


/**
 * Currently there are three threads at play.
 *  1) This console
 *  2) The game server
 *  3) The networking class
 *
 * This console is meant to server as a way to help admins control what the server is doing
 * The server is ran in another thread which allows constant use of this console.
 * @author Ian
 *
 */
public class AdminConsole {
    private static final Logger   log  = Logger.getLogger(RoboSynapseClient.class.getName());
    private static boolean server_running = false;
    private static RoboSynapseServer server;
    
    public static TreeMap<String, Command> commands = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);
    static{
    	CommandRunner.registerCommand(new Command("start","Start game"){
	    		@Override
	    		public void run(String[] args){
	    			System.out.println("Starting server..");
	                try{
	                	startServer();
	                } catch (Exception e) {
	                }
	    		}
    		});
    	CommandRunner.registerCommand(new Command("stop","Stop game"){
    		@Override
    		public void run(String[] args){
    			System.out.println("Stopping server..");
            	stopServer();
    		}
		});
    	CommandRunner.registerCommand(new Command("restart","Restart game"){
    		@Override
    		public void run(String[] args){
    			System.out.println("Restarting server..");                
            	stopServer();
            	startServer();                	
               
    		}
		});
    	CommandRunner.registerCommand(new Command("addSecurityBot","Takes two coordinates (floats) and spawns a security bot at that point"){
    		@Override
    		public void run(String[] args){
				Vector2f v = new Vector2f(Float.parseFloat(args[0]),Float.parseFloat(args[1]));
				Game.getInstance().securitySystem.addSecurityBot(v, 0f);
    		}
		});
    	CommandRunner.registerCommand(new Command("pause","Pause the game loop"){
    		@Override
    		public void run(String[] args){
				Game.getInstance().togglePause();
    		}
		});
    	CommandRunner.registerCommand(new Command("help","Print all available commands"){
    		@Override
    		public void run(String[] args){
				System.out.println(CommandRunner.getAvailableCommandsString());
    		}
		});
    	CommandRunner.registerCommand(new Command("kill","Kill the specified player"){
    		@Override
    		public void run(String[] args){
				Game.getInstance().getPlayerManager().killPlayer(args[0]);
    		}
		});
    	CommandRunner.registerCommand(new Command("playerInfo","Print player names and positions"){
    		@Override
    		public void run(String[] args){
				System.out.println(Game.getInstance().getPlayerManager().getAllPlayerData());
    		}
		});
    	CommandRunner.registerCommand(new Command("addPlayer","Add player [name:string] with [bot:string] at [spawnpoint:int]"){
    		@Override
    		public void run(String[] args){
    	    	Game.getInstance().addPlayer(new Player(args[0], args[1]), Integer.parseInt(args[2]));
    		}
		});
    }
    public static void startServer()
    {
    	 if(!server_running){
         	server = new RoboSynapseServer();
             server.start();
             server_running = true;
         }else System.out.println("Server is already running!");
    }
    public static void stopServer()
    {
    	if(server_running){
            server.stopServer();
            server_running = false;
            server=null;
        }else System.out.println("Server is not running!");
    }
    public static void main(String[] args){    	
        log.setLevel(Constants.DEBUG_LEVEL);
        Scanner input = new Scanner(System.in);
        
        //log.log(Level.INFO, CommandRunner.getAvailableCommandsString());
        System.out.println(CommandRunner.getAvailableCommandsString());
        while(true){
        	CommandRunner.processInput(input.nextLine());
        }
    }
}