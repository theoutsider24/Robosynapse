package server.utility.map;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public interface MapInterface {
    public ArrayList<Rectangle> getWalls();
    public Rectangle getSpawnZone(int spawnZone);
    public Vector2f getSpawnPoint(int spawnZone);
}
