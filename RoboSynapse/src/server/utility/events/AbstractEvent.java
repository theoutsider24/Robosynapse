package server.utility.events;


import org.newdawn.slick.geom.Vector2f;

import server.utility.common.Constants;
import server.utility.entities.MovableObject;
import server.utility.entities.player.Player;

public abstract class AbstractEvent implements EventI {
    protected Player actor;
    protected MovableObject subject;
	protected Vector2f position;
  	
    public Vector2f getPosition() {
        return subject.getPosition();
    }
    
    public float getRotation() {
        return subject.getRotation();
    }
     
    public int getId() {
        return subject.getId();
    }	
    
    public double getDistance() {
        return Constants.getDistance(actor.getPosition(), subject.getPosition());
    }
    
    public abstract String toString();
}
