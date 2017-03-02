package server.utility.map;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import server.utility.common.Constants;
import static server.utility.map.WayPoint.*;

public class Map implements MapInterface{

//    private static ArrayList<WallSegment> walls;
    private static ArrayList<Rectangle> walls;
    private static ArrayList<Rectangle> spawnpoints;
    private static int[][] mazeWalls;
    public static ArrayList<WayPoint> wayPoints;
    
    public Map() {      
        init();      
    }

    private void init() {
        walls = new ArrayList<Rectangle>();
        spawnpoints = new ArrayList<Rectangle>();
        wayPoints = new ArrayList<WayPoint>();
        setDefaultSpawnPoints();
        setOuterWall();
        setMazeWall();
    }
    
    private void setOuterWall() {
        Rectangle top = new Rectangle(4, 4, Constants.WINDOW_WIDTH - 8, 42);
        Rectangle right = new Rectangle(Constants.WINDOW_WIDTH - 46, 46, 42, Constants.WINDOW_HEIGHT - 92);
        Rectangle bottom = new Rectangle(4,  Constants.WINDOW_HEIGHT - 46, Constants.WINDOW_WIDTH - 8, 42);
        Rectangle left = new Rectangle(4, 46, 42, Constants.WINDOW_HEIGHT - 92);
        walls.add(top);
        walls.add(right);
        walls.add(bottom);
        walls.add(left);        
    }

    //public ArrayList<WallSegment> getWalls() {
    public ArrayList<Rectangle> getWalls() {
        return walls;
    }
    public void setSpawnPoints (ArrayList<Vector2f> locations) {
        spawnpoints.clear();
        for (Vector2f location : locations) {
            addSpawnPoint(location);
        }
    }
    
    public void addSpawnPoint(Vector2f location) {
        spawnpoints.add(new Rectangle((int)location.x, (int)location.y, Constants.SPAWN_BOX_SIZE, Constants.SPAWN_BOX_SIZE));
    }
    
    private void setDefaultSpawnPoints() {
        // co-ordinates are for top left of box
        addSpawnPoint(new Vector2f(50,50));
        addSpawnPoint(new Vector2f(450,50));
        addSpawnPoint(new Vector2f(1250,50));
        addSpawnPoint(new Vector2f(1850,50));
        addSpawnPoint(new Vector2f(50,950));
        addSpawnPoint(new Vector2f(550,850));
        addSpawnPoint(new Vector2f(1150,950));
        addSpawnPoint(new Vector2f(1850,950));
    }

    private void setMazeWall() {
        int mapWidth = Constants.WINDOW_WIDTH / Constants.MAP_SQUARE_SIZE;
        int mapHeight = Constants.WINDOW_HEIGHT / Constants.MAP_SQUARE_SIZE;
        mazeWalls = new int[mapWidth][mapHeight];
        mazeWalls[0] =  new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[1] =  new int[]{0,2,2,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,2,0,0,0,0,0,0,0,0,0,0,2,2,0};
        mazeWalls[2] =  new int[]{0,2,2,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,2,0,0,0,0,0,0,0,0,0,0,2,2,0};    
        mazeWalls[3] =  new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0};
        mazeWalls[4] =  new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0};
        mazeWalls[5] =  new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[6] =  new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[7] =  new int[]{0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,0};
        mazeWalls[8] =  new int[]{0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,0};
        mazeWalls[9] =  new int[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0};
        mazeWalls[10] = new int[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0};
        mazeWalls[11] = new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0};
        mazeWalls[12] = new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0};
        mazeWalls[13] = new int[]{0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0};
        mazeWalls[14] = new int[]{0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0};
        mazeWalls[15] = new int[]{0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0};
        mazeWalls[16] = new int[]{0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0};
        mazeWalls[17] = new int[]{0,0,0,0,0,0,0,1,1,0,0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[18] = new int[]{0,0,0,0,0,0,0,1,1,0,0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[19] = new int[]{0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,2,2,0,0,0,0,0,0,1,1,0,0,1,1,2,2,0};
        mazeWalls[20] = new int[]{0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,2,2,0,0,0,0,0,0,1,1,0,0,1,1,2,2,0};
        mazeWalls[21] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        for (int y = 0; y < mazeWalls.length; y++) {
            for (int x = 0; x < mazeWalls[y].length; x++) {
                if (mazeWalls[x][y] == 1) {
                    Rectangle maze = new Rectangle(x*Constants.MAP_SQUARE_SIZE, y*Constants.MAP_SQUARE_SIZE, Constants.MAP_SQUARE_SIZE, Constants.MAP_SQUARE_SIZE);
                  walls.add(maze);
                } 
            }
        }
        
        wayPoints.add(new WayPoint(new Vector2f(100, 300),NORTH,WEST));
        
        wayPoints.add(new WayPoint(new Vector2f(300, 100),WEST,SOUTH));
        wayPoints.add(new WayPoint(new Vector2f(300, 300),NORTH,SOUTH,EAST,WEST));
        wayPoints.add(new WayPoint(new Vector2f(300, 500),NORTH,WEST));
        
        wayPoints.add(new WayPoint(new Vector2f(500, 300),SOUTH,EAST,WEST));
        wayPoints.add(new WayPoint(new Vector2f(500, 500),NORTH,SOUTH,EAST));
        
        wayPoints.add(new WayPoint(new Vector2f(700, 100),EAST,SOUTH));
        wayPoints.add(new WayPoint(new Vector2f(700, 300),NORTH,WEST));
        
        wayPoints.add(new WayPoint(new Vector2f(900, 100),SOUTH,EAST,WEST));
        wayPoints.add(new WayPoint(new Vector2f(900, 300),NORTH,EAST));
        
        wayPoints.add(new WayPoint(new Vector2f(500, 700),NORTH,EAST,WEST));
        
        wayPoints.add(new WayPoint(new Vector2f(1000, 500),NORTH,EAST,WEST));
    }
    /*
     * returns the bounding rectangle of a given spawnpoint
     */
	public Rectangle getSpawnZone(int spawnZone) {
	    //TODO check for off by one error. If player input spawn zone 1 - 8, will have to convert for 0 - 7
	    if (spawnZone > spawnpoints.size()) {
	        spawnZone = spawnZone%spawnpoints.size();
	    } 
	    return spawnpoints.get(spawnZone);	    
	}
	
	/*
	 * returns the centre point of a given spawnpoint
	 */
	public Vector2f getSpawnPoint(int spawnZone) {
	    return new Vector2f(spawnpoints.get(spawnZone).getMinX() + (spawnpoints.get(spawnZone).getWidth() / 2), spawnpoints.get(spawnZone).getMinY() + (spawnpoints.get(spawnZone).getHeight() / 2));
	}
            
}
