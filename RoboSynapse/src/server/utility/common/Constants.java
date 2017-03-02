package server.utility.common;

import java.util.logging.Level;

import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.MovableObject;

public class Constants {
	//matthew
	public final static String ROOT_DIRECTORY=System.getProperty("user.dir");
	public final static String DEFINITIONS_DIRECTORY=ROOT_DIRECTORY+"\\defs";

	public final static String DEFAULT_NAME="NOT_NAMED";
	public final static int DEFAULT_NOISE=10;
	public final static int DEFAULT_WEIGHT=10;
	public final static int DEFAULT_DAMAGE=2;
	public final static int DEFAULT_RELOAD_SPEED=10;
	public final static int DEFAULT_ACCURACY=10;
	public final static int DEFAULT_HEAT=10;
	public final static int DEFAULT_RANGE=10;
	//end
	
	public static enum ACTION_TYPE{MOVE, TURN, FIRE, WAIT, TECHON, TECHOFF};
	public static double BASESPEED = 2;
	public static final int WINDOW_WIDTH = 2000;
	public static final int WINDOW_HEIGHT = 1125;
	public static final int PLAYER_RADAR_RANGE = 200;
	public static final int PLAYER_RADAR_FOV = 90;
	public static final float CELL_SIZE = 4.0f;
	public static final float ZOOM = 1f;
	public static final short VERTICAL = 1;
	public static final short HORIZONTAL = 2;
	public static final int WALL_THICKNESS = 20;
	public static final int DOOR_WALL_REDUCTION = 20;
	public static final int SPAWN_BOX_SIZE = 50;
	public static final int MAP_SQUARE_SIZE = 50;
	public static final int EVENT_LIFE = 100;
	public static Level DEBUG_LEVEL = Level.ALL;
	public static final float DEFAULT_COLLISION_CIRCLE_RADIUS = 1f;
	private static int idCount = -1;// Starts at -1 as it is incremented in the getId

	
	public static float FPS_LIMIT		= 60;
	//network
	public static final int TCP_LISTENING_PORT = 9877;
	
	public synchronized static int getIdCount() {
		idCount++;
		return idCount;
	}
	
	public static double getDistance(Vector2f p1, Vector2f p2) {
        return Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
    }
	
	public static boolean isInRange(Vector2f p1, Vector2f p2, int range) {
        return ((int)Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y))) < range);
    }
	
	public static boolean isInFieldOfView(MovableObject entity, MovableObject target, int degOfFOV){
	    return checkIsInFieldOfView(entity.getPosition(), entity.getRotation(), target.getPosition(), (float)degOfFOV);
	}
	
	public static boolean isInFieldOfView(Vector2f entityPos, float entityRotation, Vector2f targetPos){
	    return checkIsInFieldOfView(entityPos, entityRotation, targetPos, PLAYER_RADAR_FOV);
	}
	
	// Caution this returns the positive angle to the right fixedPoint must rotate e.g 0->360
	public static float radsToFacePoint(Vector2f fixedPoint, float p1Rotation, Vector2f point2) {
	    float rotationInRadians = (float) Math.toRadians(p1Rotation);
        Vector2f point1 = new Vector2f((float)(fixedPoint.x + Math.sin(rotationInRadians)),
                (float) (fixedPoint.y + -Math.cos(rotationInRadians)));
        float angle1 = (float) Math.atan2(point1.y - fixedPoint.y, point1.x - fixedPoint.x);
        float angle2 = (float) Math.atan2(point2.y - fixedPoint.y, point2.x - fixedPoint.x);
        float angleInRadians = angle2-angle1;
        return angleInRadians;
	}
	
	public static float degreesToFacePoint(Vector2f p1, float p1Rotation, Vector2f p2) {
	    float degreesToTarget = (float) Math.toDegrees(radsToFacePoint(p1, p1Rotation, p2));
	    if (degreesToTarget > 180) { 
            degreesToTarget -= 360;
        }
        return degreesToTarget;
	}
	
	// Let's hope the maths is correct http://stackoverflow.com/questions/26076656/calculating-angle-between-two-points-java
	private static boolean checkIsInFieldOfView(Vector2f p1, float p1Rotation, Vector2f p2, float fieldOfView) {
	    float halfFieldOfViewInRadians = (float) Math.toRadians(fieldOfView/2);
	    float rotationInRadians = (float) Math.toRadians(p1Rotation);
        Vector2f rotationAsVector = new Vector2f((float)(p1.x + Math.sin(rotationInRadians)),
                (float) (p1.y + -Math.cos(rotationInRadians)));
        float angle1 = (float) Math.atan2(rotationAsVector.y - p1.y, rotationAsVector.x - p1.x);
        float angle2 = (float) Math.atan2(p2.y - p1.y, p2.x - p1.x);
        float angleInRadians = angle1-angle2;
	    return angleInRadians < halfFieldOfViewInRadians;
	}
}
