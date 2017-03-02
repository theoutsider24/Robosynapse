package common.utility;

import static client.utility.Constants.ARENA_HEIGHT;
import static client.utility.Constants.ARENA_WIDTH;
import static client.utility.Constants.MAP_SQUARE_SIZE;

import java.io.Serializable;

import org.newdawn.slick.geom.Vector2f;



/**
 * Entity that is used in both client and server.
 * This object is originally sent form the server to the client.
 * The client then unwraps this entity to a more specific object depending on the type
 * specified during construction.
 * @author CodexVII
 *
 */
public class Entity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6883983649295889397L;
	/*public static final int ARENA_WIDTH               = 2000;//The area game makes use of, separating from menu width and height
    public static final int ARENA_HEIGHT               = 1125;
	public static final int MAP_SQUARE_SIZE 			= 50;*/
    
	
	private int id;  
    private String name;	//TODO Decide on system for catagorising stuff e.g 1 = player, 2 = camera
    private Vector2f position;
    private String type;
    private int rotation;
    public boolean[][] visionMask;
    
    public Entity (int id, String name, Vector2f position, String type, float rotation,boolean[][] visionMask) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.type = type;
        this.rotation = (int)rotation;
        this.visionMask=visionMask;
        /*int mapWidth = ARENA_WIDTH / MAP_SQUARE_SIZE;
        int  mapHeight = ARENA_HEIGHT / MAP_SQUARE_SIZE;

        visionMask = new boolean[mapHeight][mapWidth];
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
            	visionMask[x][y]=true;
            }
        }*/
    }
    /**
     * new constructor to match the one on the server
     * 
     * @param id
     * @param name
     * @param rotation
     * @param position
     */
    public Entity (int id, String name, float rotation, Vector2f position, String type,boolean[][] visionMask) {
        this(id,name,position,type,rotation,visionMask);
    }
    public Entity (int id, String name, float rotation, Vector2f position, String type) {
        this(id,name,position,type,rotation,null);
    }
    /**
     * Convenience constructor for entities that don't require the
     * default fields.
     */
    public Entity(){
    	
    }
    

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String toString(){
    	return "Entity Object\n" +
    			"id: " + id +
    			" name: " + name +
    			" position: " + position +
    			" type: " + type +
    			" rotation: " + rotation;
    }
}
