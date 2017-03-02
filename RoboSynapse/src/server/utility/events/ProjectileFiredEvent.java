package server.utility.events;

import java.util.EventObject;

import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.MovableObject;

public class ProjectileFiredEvent extends EventObject {

    int      projectileStrength;
    float    direction;
    Vector2f source;
    int ownerId;

    public ProjectileFiredEvent(MovableObject arg0, int strength) {
        super(arg0);
        projectileStrength = strength;
        direction = arg0.getRotation();
        source = arg0.getPosition();
        ownerId = arg0.getId();
    }

    public int getProjectileStrength() {
        return projectileStrength;
    }

    public float getDirection() {
        return direction;
    }

    public Vector2f getSource() {
        return source;
    }
    
    public int getOwnerId(){
        return ownerId;
    }
}
