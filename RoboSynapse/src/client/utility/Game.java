package client.utility;

import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;

import server.utility.common.Constants;
import server.utility.common.JsonReader;
import client.RoboSynapseClient;
import client.utility.drawables.Drawable;
import client.utility.drawables.DrawableEntity;
import client.utility.drawables.Map;
import common.utility.Entity;

public class Game {
	private static final Logger log = Logger.getLogger(RoboSynapseClient.class.getName());
	private SpriteSheet playerSheet;
	private SpriteSheet bulletSheet;
	private SpriteSheet enemySheet;
	private Image fog_texture;
	private Map map;
	private Image mask;
	private Clock gameclock;

	
	private int range = 500;
	private double angleRange = 45d;
	
	private ArrayList<DrawableEntity> gameEntities;

	public Game() {  	
		log.setLevel(Constants.DEBUG_LEVEL);
//		gameclock = new Clock();
		gameEntities = new ArrayList<DrawableEntity>();
		map = new Map();
		loadResourcesFromFiles();
	}

	public void update(int millSinceLastUpdate) {
		
		//focus on the main player
		DrawableEntity focused_player = null; 
		
		/**
		 * treating the first enemy bot as our player bot
		 * for testing purposes
		 */
		if(gameEntities.size() > 0){
			focused_player = gameEntities.get(0);
			float temp = gameEntities.get(0).getRotation();
		}

		
		// Taking care of any logic necessary for animation
		for (DrawableEntity e : gameEntities) {
			e.update(millSinceLastUpdate);
		}
	}
	
	public synchronized void updateEntitiesList(ArrayList<Entity> entities) {
		gameEntities.clear();
		for (Entity e : entities) {
			gameEntities.add(createDrawableEntityFromEntity(e));
		}
	}
	
	public void loadResourcesFromFiles() {
		try {
			playerSheet = new SpriteSheet("res/playerSheet.png", 50, 50);
			bulletSheet = new SpriteSheet("res/bulletSheet.png", 8, 8);
			enemySheet = new SpriteSheet("res/standIn5050.png", 50, 63);
		} catch (SlickException e) {
            e.printStackTrace();
        }
	}
	
	public DrawableEntity createDrawableEntityFromEntity(Entity entity) {
		SpriteSheet texture = null;
		Point frameSize = null;
		Point sheetSize = null;
		int millisecondsPerFrame = 0;
		
		if(entity.getType().contains("player")){
			texture = playerSheet;
			frameSize = new Point(50, 50);
			sheetSize = new Point(5, 2);
			millisecondsPerFrame = 9000;
			map.setVisionMask(entity.visionMask);
		}
		else if(entity.getType().contains("bullet")){
			texture = bulletSheet;
			frameSize = new Point(8, 8);
			sheetSize = new Point(5, 2);
			millisecondsPerFrame = 90;
		}
		else if(entity.getType().contains("blankEnemy")){
			texture = enemySheet;
			frameSize = new Point(50, 63);
			sheetSize = new Point(1, 1);
			millisecondsPerFrame = 90000;
		}
		else
		{
			log.log(Level.SEVERE, "No switch entry for given entity: "+entity.getType(), entity);
		}
		

		return new DrawableEntity(entity.getId(), entity.getName(),
				entity.getType(), entity.getPosition(), entity.getRotation(),
				texture, frameSize, sheetSize, millisecondsPerFrame);
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		draw(map);
		for(DrawableEntity entity : gameEntities){
			draw(entity);
		}		
	}
	
	public void draw(Drawable d)
	{
		d.draw();
	}
}
