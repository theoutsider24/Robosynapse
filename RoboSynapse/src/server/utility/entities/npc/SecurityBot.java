package server.utility.entities.npc;

import java.sql.Time;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import common.utility.Entity;

import server.utility.common.Constants;
import server.utility.entities.MovableObject;
import server.utility.entities.player.Player;
import server.utility.entities.player.Behaviours.EmptyBehaviour;
import server.utility.entities.player.Behaviours.WillBehave;
import server.utility.entities.player.commands.FireCommand;
import server.utility.entities.player.commands.MoveCommand;
import server.utility.entities.player.commands.PauseCommand;
import server.utility.entities.player.commands.TurnCommand;
import server.utility.entities.player.interfaces.ICommand;
import server.utility.entities.player.interfaces.IBotActions;
import server.utility.entities.player.tank.BotFactory;

public class SecurityBot extends Player {
    protected int currentPatrolPoint;
    protected boolean backtracking;
    private int radarRange;
    private static int idNum=1;
    int speed=10;//TODO get from bot
    
    public SecurityBot(String name) {
        super(name+"#"+idNum++,"security bot");
    }
    
    public SecurityBot(String name, Vector2f position, float rotation, double speed, int strength, int health, int range) {
    	this(name);
    	setPos(position);
    	setRotation(rotation);
    	
    	this.radarRange = range;
    	backtracking = false;
    	currentPatrolPoint = 0;
    	
    	getBehaviour().onSpawn();
    }
    
  /*  public void playerScanned(MovableObject player) {
    	float degreesToTarget = Constants.degreesToFacePoint(getPosition(), getRotation(), player.getPosition());
    	instructions.clear();
    	if (degreesToTarget > 5){
    		//rotate to face it if not TODO decide if 5 degrees is acceptable error
    		turn(degreesToTarget);
    	} 
    	fire();
    }*/

    /*@Override
    public void update() {
        if (hasCommand()) {
        	actions.get(0).execute();
            if (actions.get(0).isFinished()) {
            	actions.remove(0);
            }
        } else {
        	//check if you've reached target point
        	if (Constants.getDistance(getPosition(), patrolPath.get(currentPatrolPoint)) < (speed * 2)) {
        		// if yes, set new point
        		if (backtracking) {
        			if (currentPatrolPoint == 0) {
        				backtracking = false;
        				currentPatrolPoint = 1;
        			} else {
        				currentPatrolPoint--;
        			}        			
        		} else {
        			if(currentPatrolPoint == (patrolPath.size() - 1)) {
        				backtracking = true;
        				currentPatrolPoint = patrolPath.size() - 2;
        			} else {
        				currentPatrolPoint++;
        			}
        		}
        	}        	
        	//check if facing said point
        	float degreesToTarget = Constants.degreesToFacePoint(getPosition(), getRotation(), patrolPath.get(currentPatrolPoint));
        	if (Math.abs(degreesToTarget) > 2){
        		//rotate to face it if not TODO decide if 5 degrees is acceptable error
        		turn(degreesToTarget);
        	} 
        	//move towards it
        	double tempe = Constants.getDistance(getPosition(), patrolPath.get(currentPatrolPoint));
        	move(Constants.getDistance(getPosition(), patrolPath.get(currentPatrolPoint)));
        }
    }*/

    private void targetPlayer(MovableObject player) {
        Float rotationToTarget = (float) Math.toDegrees(Constants.radsToFacePoint(getPosition(), getRotation(), player.getPosition()));
        turn(rotationToTarget);
        fire();
    }
    @Override
    public Entity toEntity()
    {
    	return new Entity(getId(), getName(), getRotation(), getPosition(), getName(),null);
    }
}
