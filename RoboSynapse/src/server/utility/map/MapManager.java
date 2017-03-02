package server.utility.map;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.MovableObject;


/*
 * Mapmanager should move all the sprites around and deal with any timed events
 */
public class MapManager implements MapInterface{

    private Map map;
    
    public MapManager() {
        map = new Map();
    }
    
    public void checkForCollisions(ArrayList<MovableObject> entities) {
        for(MovableObject e : entities) {
            if (Circle.class.isInstance(e.getCollisionShape())) { //it's a circle
                insideWall(e.getCollisionShape());
            } else { //else assume it's a rectangle
                insideWall(e.getCollisionShape().transform(Transform.createRotateTransform(e.getRotation())));
            }
            
        }
    }

    // TODO add another method to see if intersects wall, see commented out chuck of code
    public boolean insideWall(Shape collisionShape) {
        for (Rectangle wall : map.getWalls()) {            
            if (collisionShape.intersects(wall) || wall.contains(collisionShape)) {
                return true;
            }            
        }        
        return false;
    }

    @Override
    public ArrayList<Rectangle> getWalls() {
        return map.getWalls();
    }

    @Override
    public Rectangle getSpawnZone(int spawnZone) {
        return map.getSpawnZone(spawnZone);
    }

    @Override
    public Vector2f getSpawnPoint(int spawnZone) {
        return map.getSpawnPoint(spawnZone);
    }
    
    public boolean isLineOfSightClear(Vector2f p1, Vector2f p2){
        Line2D line1 = new Line2D.Float(p1.x, p1.y, p2.x, p2.y);
        for(Line2D line2 : getWallLines()) {
            if (line1.intersectsLine(line2)) {
                return false;
            }
        }
        return true;
    }    
    
    private ArrayList<Line2D> getWallLines() {
        ArrayList<Line2D> lines = new ArrayList<Line2D>();
        //TODO if only return walls near player that could possibly block line of sight, it may be more efficient
        for (Rectangle wall : getWalls()) {
            lines.add(new Line2D.Float(wall.getMinX(), wall.getMinY(), wall.getWidth(), wall.getMinY()));
            lines.add(new Line2D.Float(wall.getMinX() + wall.getWidth(), wall.getMinY(), wall.getMinX() + wall.getWidth(), wall.getMinY() + wall.getHeight()));
            lines.add(new Line2D.Float(wall.getMinX() + wall.getWidth(), wall.getMinY() + wall.getHeight(), wall.getMinX(), wall.getMinY() + wall.getHeight()));
            lines.add(new Line2D.Float(wall.getMinX(), wall.getMinY() + wall.getHeight(), wall.getMinX(), wall.getMinY()));
        }
        return lines;
    }
}
