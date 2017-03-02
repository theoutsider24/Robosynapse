package server.utility.events;

import org.newdawn.slick.geom.Vector2f;


public interface EventI {
    /*
     * An event will have an Actor and a Subject
     * the Subject is what a player will want to get information about
     * Player scans an npc, Player is the Actor 
     * npc is the subject
     * Player collides with wall, Player = Actor, wall = Subject
     * Player gets shot Player is still the actor, bullet = Subject
     * getPosition and getRotation should return the position and rotation of the subject
     */
    public Vector2f getPosition();
    
    public float getRotation();
    
    public String toString();
    
    public double getDistance();
    
    public int getId();
}
