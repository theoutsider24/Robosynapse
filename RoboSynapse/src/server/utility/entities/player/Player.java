package server.utility.entities.player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import common.utility.Entity;
import server.utility.common.Constants;
import server.utility.entities.Scannable;
import server.utility.entities.player.Behaviours.Behaviour;
import server.utility.entities.player.commands.FireCommand;
import server.utility.entities.player.commands.GoToPointCommand;
import server.utility.entities.player.commands.MoveCommand;
import server.utility.entities.player.commands.PauseCommand;
import server.utility.entities.player.commands.TurnCommand;
import server.utility.entities.player.commands.TurnToFaceCommand;
import server.utility.entities.player.interfaces.ICommand;
import server.utility.entities.player.interfaces.IBotActions;
import server.utility.entities.player.tank.Bot;
import server.utility.entities.player.tank.BotFactory;
import server.utility.events.ProjectileFiredEventListener;
import server.utility.map.pathfinder.AStarPathFinder;

public class Player implements IBotActions, Scannable {
    private String                playerName;           // the team's name
    private Bot                  playerTank;           // the team's tank
    /*
     * plan to use collisionRect as a way of detecting if more detailed collision detection needs to be done Assuming the detection of a
     * transformable object is more intensive than a Rectangle will only check for collision between two transforms if their bounding boxes
     * collide collisionRect should be set so it's sides are the length of the diagonal of transform
     */
    public ArrayList<ICommand>   actions;
    public ArrayList<ICommand>   storedActions;
    
    private Behaviour             playerBehaviour;
    private int                   id;
    private int                   port;     // used to detect when new command has been issued
    private boolean[][]			  visionMask;
    private boolean 			  alive;
    
    public Player(String teamName, String tankName) {
    	this.alive=true;
        this.playerName = teamName;
        this.playerTank = BotFactory.buildBot(tankName);
        this.id = Constants.getIdCount();
        this.playerBehaviour = Behaviour.getBehaviour(tankName+"behave");
        this.playerBehaviour.setPlayer(this);
        actions = new ArrayList<ICommand>();
        storedActions = new ArrayList<ICommand>();
        update();
    }
    
    private ICommand lastActionExecuted;
    public void update() {
    	if(hasActions())
    	{
    		if(actions.get(0)!=lastActionExecuted)
    		{
    			lastActionExecuted= actions.get(0); 
    			actions.get(0).init();
    		}
    		
	        actions.get(0).execute();
	        
	        if(actions.get(0).isFinished()) {
	            actions.remove(0);
	        }
    	}
        playerTank.update();
        
        visionMask=AStarPathFinder.getVisionMask(playerTank, 500);//TODO hard-coded vision range
    }

    public Shape getCollisionShape() {
        return playerTank.getCollisionShape();
    }


    public boolean hasActions() {
        return actions.size() > 0;
    }

    private void logLastMoveInfo() {
        // TODO need a record of the last move they made so it can be undone in
        // the event of collision
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return playerName;
    }

    public Vector2f getPosition() {
        return playerTank.getPosition();
    }

    public void setPos(Vector2f pos) {
        playerTank.setPosition(pos);
    }

    public float getRotation() {
        return playerTank.getRotation();
    }
    
    public void setRotation(float rotation) {
    	playerTank.setRotation(rotation);
    }
    
    public Bot getTank() {
        return playerTank;
    }
    
    public void bullethit(int healthLost) {
        this.playerTank.hurt(healthLost);
    }
    
    public Behaviour getBehaviour() {
        return playerBehaviour;
    }
    
    public void undoMove() {
    	try{
    		playerTank.getUndo().execute();
    	}catch(Exception e){}
    }

    @Override
    public void move(double distance) {
        actions.add(new MoveCommand(playerTank, distance));
    }

    @Override
    public void turn(float degrees) {
        actions.add(new TurnCommand(playerTank, degrees));
    }
    
    @Override
    public void fire() {
        actions.add(new FireCommand(playerTank));
    }
    
    @Override
    public void goToPoint(Vector2f p) {
        actions.add(new GoToPointCommand(playerTank,p));
    }
    
    @Override
	public void turnToFace(float f) {
		actions.add(new TurnToFaceCommand(playerTank, f));
	}
    
	@Override
	public void pause(double turnsToWait) {
		actions.add(new PauseCommand(playerTank, turnsToWait));		
	}
    
    public HashMap getProjectileInfo() {
        HashMap firingInfo = new HashMap();
        //TODO this needs to query the tank parts, find the strength, etc of the tanks ammo
        firingInfo.put("owner", getName());
        firingInfo.put("strength", 3);
        firingInfo.put("origin", getPosition()); //TODO calculate the front center
        firingInfo.put("direction", getRotation());
        return firingInfo;        
    }

    @Override
    public HashMap scanned() {
        HashMap info = new HashMap();
        info.put("name", playerName);
        info.put("position", getPosition());
        info.put("rotation", getRotation());        
        return info;
    }
    
    
    public void clearCommands() {
    	actions.clear();
    }
    public void storeCommands() {
    	storedActions.clear();
    	if(!actions.isEmpty())
    		storedActions.addAll(actions);
    }
    public void resumeStoredCommands() {
    	if(!storedActions.isEmpty())
    	{
        	clearCommands();
    		actions.addAll(storedActions);
    		storedActions.clear();
    	}
    }
    
    public boolean[][] getVisionMask()
    {
    	return visionMask;
    }
    
    public void RegisterProjectileFiredEventListener(ProjectileFiredEventListener listener) {
        playerTank.RegisterProjectileFiredEventListener(listener);
    }
    public Entity toEntity()
    {
    	return new Entity(getId(), getName(), getRotation(), getPosition(), getName(),getVisionMask());
    }
    public void kill()
    {
    	alive=false;
    }

	
}
