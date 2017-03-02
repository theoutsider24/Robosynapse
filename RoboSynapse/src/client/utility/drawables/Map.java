package client.utility.drawables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.StateBasedGame;

import client.RoboSynapseClient;
import static client.utility.Constants.*;

public class Map implements Drawable{
    private static final Logger log = Logger.getLogger(RoboSynapseClient.class.getName());  
    
    private final byte CORNER_WALL_NW_TILE = 1;
    private final byte HORIZONTAL_WALL_TILE = 2;
    private final byte VERTICAL_WALL_TILE = 3;  
    private final byte CORNER_WALL_NE_TILE = 4;
    private final byte CORNER_WALL_SE_TILE = 5;
    private final byte CORNER_WALL_SW_TILE = 6;
    private final byte CORNER_SIDE_NW_TILE = 7;
    private final byte HORIZONTAL_SIDE_TILE = 8;
    private final byte VERTICAL_SIDE_TILE = 9;   
    private final byte CORNER_SIDE_NE_TILE = 10;
    private final byte CORNER_SIDE_SE_TILE = 11;
    private final byte CORNER_SIDE_SW_TILE = 12;
    private final byte BLANK_TILE = 13;
    private final byte WALL_CAP_TOP_TILE = 14;
    private final byte WALL_CAP_RIGHT_TILE = 15;
    private final byte WALL_CAP_BOTTOM_TILE = 16;
    private final byte WALL_CAP_LEFT_TILE = 17;
    private final byte MAZE_WALL_TILE = 18;
    private final byte SPAWN_ZONE_TILE = 19;

    private int mapWidth;
    private int mapHeight;
    private byte[][] mapCells;
    private int[][] mazeWalls;
    public boolean[][] mazeMask;
    private Image maskImage;
    private int maskSizeFactor=1;
    
    private SpriteSheet mapSheet;
    private Image wholeMap;
    private Image background;
    private Image maze;
    private Graphics tileToMapImageGraphics;
    
    public Map() {
        log.setLevel(DEBUG_LEVEL);
        init();        
    }
    
    /*
     * Notes on convention for numbering mapCells
     * 1, 2, 3, 4 are for the four courners topLeft, topRight, bottomRigh, bottomLeft
     * 5 is vertical wall, 6 is horizontal wall,
     * 11 is a blank space
     */
    private void init() {	
        mapWidth = ARENA_WIDTH / MAP_SQUARE_SIZE;
        mapHeight = ARENA_HEIGHT / MAP_SQUARE_SIZE;

    	mazeMask = new boolean[mapWidth][mapHeight];//fog
        mapCells = new byte[mapWidth][mapHeight];
//        spriteOfMapForDrawing.setSize(new Vector2f(mapWidth * Constants.MAP_SQUARE_SIZE, mapHeight * Constants.MAP_SQUARE_SIZE));
//        wholeMap.create(mapWidth * Constants.MAP_SQUARE_SIZE, mapHeight * Constants.MAP_SQUARE_SIZE);
        try {
            mapSheet = new SpriteSheet("res/mapSheet.png", MAP_SQUARE_IMAGE_SIZE, MAP_SQUARE_IMAGE_SIZE);
            maskImage = mapSheet.getSubImage(3, 2);//new Image(MAP_SQUARE_SIZE,MAP_SQUARE_SIZE);//fog
            background = new Image("res/background.png");
            maze = new Image(ARENA_WIDTH, ARENA_HEIGHT);
            tileToMapImageGraphics = maze.getGraphics();
        } catch (SlickException e) {
            e.printStackTrace();
        }
        // TODO hard-code  the layout of the map
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (x == 0 && y == 0) { //NW
                    mapCells[x][y] = CORNER_WALL_NW_TILE;
                } else if (x == (mapWidth - 1) && y == 0) { //NE
                    mapCells[x][y] = CORNER_WALL_NE_TILE;
                } else if (x == (mapWidth - 1) && y == (mapHeight - 1)) { //SE
                    mapCells[x][y] = CORNER_WALL_SE_TILE;
                } else if (x == 0 && y == (mapHeight - 1)) { //SW
                    mapCells[x][y] = CORNER_WALL_SW_TILE;
                } else if (x == 0 || x == (mapWidth - 1)) { //Vertical
                    mapCells[x][y] = VERTICAL_WALL_TILE;
                } else if (y == 0 || y == (mapHeight -1)) { //Horizontal
                    mapCells[x][y] = HORIZONTAL_WALL_TILE;
                } else {
                    mapCells[x][y] = BLANK_TILE;   
                }
            }
        }

        mazeWalls = new int[mapWidth][mapHeight];
        mazeWalls[0] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[1] = new int[]{0,2,2,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,2,0,0,0,0,0,0,0,0,0,0,2,2,0};
        mazeWalls[2] = new int[]{0,2,2,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,2,0,0,0,0,0,0,0,0,0,0,2,2,0};    
        mazeWalls[3] = new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0};
        mazeWalls[4] = new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0};
        mazeWalls[5] = new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[6] = new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
        mazeWalls[7] = new int[]{0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,0};
        mazeWalls[8] = new int[]{0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,0};
        mazeWalls[9] = new int[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0};
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
        
        for (int x = 0; x < mapWidth; x++) {
        	for (int y = 0; y < mapHeight; y++) {            
                if (mazeWalls[x][y] == 2) { //NW
                    mapCells[x][y] = SPAWN_ZONE_TILE;
                } else if (mazeWalls[x][y] == 1) { //NE
                    mapCells[x][y] = MAZE_WALL_TILE;
                }
            }
        } 
        generateWholeMap();
    }
    
    public void generateWholeMap() {
        Image image = null;
        for (int x = 0; x < mapWidth; x++) {
        	for (int y = 0; y < mapHeight; y++) {            
                switch(mapCells[x][y]) {
                case BLANK_TILE:
                    image = mapSheet.getSubImage(0, 2);
                    break;
                case HORIZONTAL_WALL_TILE:
                    image = mapSheet.getSubImage(1, 0);                    
                    break;
                case VERTICAL_WALL_TILE:
                    image = mapSheet.getSubImage(2, 0);          
                    break;
                case CORNER_WALL_NW_TILE:
                    image = mapSheet.getSubImage(0, 0);
                    break;
                case CORNER_WALL_NE_TILE:
                    image = mapSheet.getSubImage(0, 0).getFlippedCopy(true, false);
                    break;
                case CORNER_WALL_SE_TILE:
                    image = mapSheet.getSubImage(0, 0).getFlippedCopy(true, true);
                    break;
                case CORNER_WALL_SW_TILE:
                    image = mapSheet.getSubImage(0, 0).getFlippedCopy(false, true);
                    break;
                case WALL_CAP_TOP_TILE:
                    image = mapSheet.getSubImage(1, 1);
                    break;
                case WALL_CAP_RIGHT_TILE:
                    image = mapSheet.getSubImage(2, 1);
                    break;
                case WALL_CAP_BOTTOM_TILE:
                    image = mapSheet.getSubImage(1, 1).getFlippedCopy(true, false);
                    break;
                case WALL_CAP_LEFT_TILE:
                    image = mapSheet.getSubImage(2, 1).getFlippedCopy(false, true);
                    break;
                case CORNER_SIDE_NW_TILE:
                    image = mapSheet.getSubImage(3, 0);
                    break;
                case CORNER_SIDE_NE_TILE:
                    image = mapSheet.getSubImage(3, 0).getFlippedCopy(true, false);
                    break;
                case CORNER_SIDE_SE_TILE:
                    image = mapSheet.getSubImage(3, 0).getFlippedCopy(true, true);
                    break;
                case CORNER_SIDE_SW_TILE:
                    image = mapSheet.getSubImage(3, 0).getFlippedCopy(false, true);
                    break;
                case MAZE_WALL_TILE:
                    image = mapSheet.getSubImage(1, 2);
                    break;
                case SPAWN_ZONE_TILE:
                    image = mapSheet.getSubImage(2, 2);
                    break;
                }
                tileToMapImageGraphics.drawImage(image, x * MAP_SQUARE_SIZE, y * MAP_SQUARE_SIZE);
                tileToMapImageGraphics.flush();
            }            
        }
    }
    
    //ISSUE #18
    public Image getWholeMap(){
    	return wholeMap;
    }
    public void setVisionMask(boolean[][] mask)
    {
    	maskSizeFactor=mask.length/mazeMask.length;    
    	try{
    		for (int x = 0; x < mazeMask.length; x++) {
    			for (int y = 0; y < mazeMask[0].length; y++) {                
                	mazeMask[x][y]=mask[x*maskSizeFactor][y*maskSizeFactor];
                }
            }
    	}catch(Exception e){e.printStackTrace();}
    }
    @Override
    public void draw()
    {
    	background.draw(BACKGROUND_IMAGE_X_OFFSET, BACKGROUND_IMAGE_Y_OFFSET);
        maze.draw(0, 0);

    	/*for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
            	if(!mazeMask[x][y]||mazeWalls[x][y]==1||y==0||x==0)
            		maskImage.draw( x * MAP_SQUARE_SIZE, y * MAP_SQUARE_SIZE);
            }    
        }*/
    }
}
