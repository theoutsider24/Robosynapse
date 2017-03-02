package server.utility.entities;

import java.sql.Time;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import server.utility.common.Constants;
import server.utility.entities.player.interfaces.ICommand;
import server.utility.events.ProjectileFiredEvent;
import server.utility.events.ProjectileFiredEventListener;


/*
 * By default Movable object will use a circle to calculate collisions
 * 
 */
public abstract class MovableObject{
    protected String name;
    protected int id;
    protected ICommand undoMove;
    protected double speed;
    protected int strength;
    protected int health;
    protected ProjectileFiredEventListener listener;
    protected Shape objectShape; //Stores position, bounding shape for collisions
    private float rotation;
    
    /*
     * TODO move the animation logic from client to server
     * client is sent a new list of objects every broadcast
     * client needs to be told what animation frame it was on
     */
    public MovableObject(String name) {
        this.id = Constants.getIdCount();
        this.name = name;
        speed = Constants.BASESPEED;
        strength = 2;
        setRotation(0);
        this.objectShape = new Circle(0, 0, Constants.DEFAULT_COLLISION_CIRCLE_RADIUS);
    }
    
    public MovableObject(String name, Vector2f position, float rotation, double speed, int strength, int health) {
        this.id = Constants.getIdCount();
        this.objectShape = new Circle(position.x, position.y, Constants.DEFAULT_COLLISION_CIRCLE_RADIUS);
        this.name = name;
        setRotation(rotation);
        this.speed = speed;
        this.strength = strength;
        this.health = health;
    }
    
    public abstract void update();
    
    public void setPosition(Vector2f position){
        objectShape.setCenterX(position.x);
        objectShape.setCenterY(position.y);
    }
    
    public void setPosition(float x, float y){
        objectShape.setCenterX(x);
        objectShape.setCenterY(y);
    }
    
    public Vector2f getPosition() {
        return new Vector2f(objectShape.getCenterX(), objectShape.getCenterY());
    }
    
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        if (rotation > 0) {
            this.rotation = (rotation % 360);
        } else if(rotation < 0) {
            rotation = rotation % 360;
            this.rotation = 360 - rotation;
        }
        // if rotation == 0 then there's no need to change it
    }

    public void RegisterProjectileFiredEventListener(ProjectileFiredEventListener listener) {
        this.listener = listener;
    }
        
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    
    // TODO check this is working as expected, origin is set to the center of the tank which is why half the width/height is used as an
    // offset when setting position
    // Also change collisionRect to a class variable so not spaming new objects
    public Shape getCollisionShape() {
        return objectShape;
    }
    
    public void setCollisionShape(Shape shape) {
        this.objectShape = shape;
    }
    
    public void fire() {
        //TODO have logic so gun has to cool down
        listener.registerNewProjectile(new ProjectileFiredEvent(this, 2)); //TODO change this to getStrength of gun
    }    
    
    public void setUndo(ICommand reverseOfLastMoveCommand) {
        this.undoMove = reverseOfLastMoveCommand;
    }
    
    public ICommand getUndo() {
        return undoMove;
    }
    
    public double getSpeed() {
        return speed;
    }

    public double getGrip() {
        return 1;
    }
    
    public void hurt(int damage) {
    	health -= damage;
    }
    
    public void heal(int medicine) {
    	health += medicine;
    }

    public void move(Vector2f move) {
        setPosition(getPosition().x + move.x, getPosition().y + move.y);
    }

    public void rotate(float degrees) {
        setRotation(getRotation() + degrees);
    }
    
    //Use to set collision shape to a circle
    public void setSize(float radius) {
        objectShape = new Circle(getPosition().x, getPosition().y, radius);
    }
    
    //Use to set a colision shape to a rectangle
    public void setSize(float width, float height) {
        objectShape = new Rectangle(getPosition().x, getPosition().y, width, height);
    }
}
