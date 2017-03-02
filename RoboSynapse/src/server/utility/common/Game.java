package server.utility.common;

import java.time.Clock;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.npc.SecurityBot;
import server.utility.entities.npc.SecuritySystem;
import server.utility.entities.player.Player;
import server.utility.entities.player.PlayerManager;
import server.utility.entities.player.Behaviours.Behaviour;
import server.utility.entities.player.tank.BotFactory;
import server.utility.entities.player.tank.Bullet;
import server.utility.events.EventHolder;
import server.utility.events.ProjectileFiredEvent;
import server.utility.events.ProjectileFiredEventListener;
import server.utility.events.ScannedEvent;
import server.utility.events.eventManagers.EventManager;
import server.utility.map.MapManager;
import server.utility.map.pathfinder.AStarPathFinder;
import client.RoboSynapseClient;
import common.utility.Entity;
import server.utility.entities.player.Behaviours.*;

public class Game implements ProjectileFiredEventListener {
	private final Logger log = Logger.getLogger(RoboSynapseClient.class.getName());
	private Vector2f speed;
	// private ArrayList<Player> activePlayers;
	private ArrayList<Entity> entities;
	private ArrayList<Bullet> activeBullets;
	private ArrayList<Bullet> deadBullets;
	public SecuritySystem securitySystem;
	ArrayList<SecurityBot> nPCsVisibleToPlayer;
	public MapManager mapManager;
	private Clock gameClock; // currently pulled out all time related stuff,
								// just getting it to work on update
	private double gameTurn;
	private static Game gameInstance;
	private boolean paused = false;
	private PlayerManager playerManager;

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public void togglePause() {
		paused = !paused;
	}

	public static Game getInstance() {
		if (gameInstance == null) {
			gameInstance = new Game();
			gameInstance.init();
		}
		return gameInstance;
	}

	public static void destroyInstance() {
		System.out.println("destroying");
		gameInstance = null;
	}

	private Game() {
		System.out.println("creating");
		log.setLevel(Constants.DEBUG_LEVEL);
		entities = new ArrayList<Entity>();

	}

	private void init() {
		mapManager = new MapManager();
		playerManager = new PlayerManager();

		securitySystem = new SecuritySystem(this);
		activeBullets = new ArrayList<Bullet>();
		deadBullets = new ArrayList<Bullet>();
		nPCsVisibleToPlayer = new ArrayList<SecurityBot>();
		gameTurn = 0;

		addPlayer(new Player("player", "will"), 2);
	}

	public void addPlayer(Player newPlayer, int spawnPoint) {
		// TODO have a method in map that with return x,y cords of chosen spawn
		// points
		newPlayer.setPos(mapManager.getSpawnPoint(spawnPoint));

		getPlayerManager().addPlayer(newPlayer);

	}

	private void spawnProjectile(String name, int owner, int strength, Vector2f startPosition, float facingAsDegree) {
		activeBullets.add(new Bullet(name, owner, strength, startPosition, facingAsDegree));
	}

	public void update() {
		if (!paused) {
			// Move everything
			for (Bullet b : activeBullets) {
				b.update();
			}

			getPlayerManager().updatePlayers();

			// Check for collisions and Create events
			// Check for collisions with walls
			/*
			 * for (Player p : activePlayers) { if
			 * (mapManager.insideWall(p.getTank().getCollisionShape())) {
			 * p.getBehaviour().onCollision(); p.undoMove(); } } for (Player
			 * enemy : securitySystem.getNPCEntities()){ if
			 * (mapManager.insideWall(enemy.getCollisionShape())) {
			 * enemy.getBehaviour().onCollision(); enemy.undoMove(); } }
			 */

			for (Bullet b : activeBullets) {
				if (mapManager.insideWall(b.getCollisionShape())) {
					deadBullets.add(b);
				}
			}
			// clearing bullets that have hit a wall
			// could move where this is done so a bullet an hit a wall and
			// player at the same time (unlikely)
			// could use deadBullet list to kick off explosion animations
			for (Bullet db : deadBullets) {
				activeBullets.remove(db);
			}

			EventManager.processEvents();

			// Check for player collisions with other players, entities
			// TODO put in loop for collision with other players
			/*
			 * for (Player p : activePlayers) { for (SecurityBot e :
			 * securitySystem.getNPCEntities()) { if
			 * (p.getCollisionShape().intersects(e.getCollisionShape())) { //
			 * TODO Entity needs to be updated to // have a collisionRect
			 * (IntRect) System.out.println("Player collision with npc"); //
			 * have player and entity undo their last move, // add collision
			 * event } } }
			 */

			// Check for bullet collisions with players and entities
			/*
			 * for (Bullet bill : activeBullets) { for (Player p :
			 * activePlayers) { // check owner so they don't shoot themselves //
			 * TODO update this collision logic, currently just checks if center
			 * of bullet is inside player if (!(bill.getOwner()==p.getId()) &&
			 * (p.getCollisionShape().contains(bill.getCollisionShape()) ||
			 * p.getCollisionShape().intersects(bill.getCollisionShape()))) {
			 * p.bullethit(bill.getStrength() * 3); //TODO sort out what info
			 * they get from being shot id or name of shooter?
			 * p.getBehaviour().onBulletHit(bill.getOwner(), bill.getName(),
			 * bill.getPosition(), bill.getRotation(), bill.getStrength()); } }
			 * for (SecurityBot npc : securitySystem.getNPCEntities()) { //
			 * check owner so they don't shoot themselves // TODO update this
			 * collision logic, currently just checks if center of bullet is
			 * inside npc if (!(bill.getOwner()==npc.getId()) &&
			 * (npc.getCollisionShape().contains(bill.getCollisionShape()) ||
			 * npc.getCollisionShape().intersects(bill.getCollisionShape()))) {
			 * 
			 * } } }
			 */

			// check sightings //TODO refine this, add ability for NPCs to spot
			// players
			// TODO look at his if optimisation is a problem
			/*
			 * for (Player p : activePlayers) { // if in range // if in FOV //
			 * if not blocked by wall for (SecurityBot npc :
			 * securitySystem.getNPCEntities()) { double distance =
			 * Constants.getDistance(p.getPosition(), npc.getPosition()); if
			 * (distance < Constants.PLAYER_RADAR_RANGE) { if
			 * (Constants.isInFieldOfView(p.getTank(), npc.getTank(),
			 * Constants.PLAYER_RADAR_FOV) &&
			 * mapManager.isLineOfSightClear(p.getPosition(),
			 * npc.getPosition())) { nPCsVisibleToPlayer.add(npc); } //players
			 * have longer range than npcs, checking here rather than looping
			 * again through npc list //TODO put in targeting if (distance < 150
			 * && Constants.isInFieldOfView(p.getTank(), npc.getTank(),
			 * Constants.PLAYER_RADAR_FOV) &&
			 * mapManager.isLineOfSightClear(p.getPosition(),
			 * npc.getPosition())){ //TODO Move getRange to MovableObject
			 * 
			 * } } } if (!nPCsVisibleToPlayer.isEmpty()) { // sort list so
			 * closest is picked SecurityBot closestNpc =
			 * nPCsVisibleToPlayer.get(0); for (SecurityBot closeNpc :
			 * nPCsVisibleToPlayer) { if (Constants.getDistance(p.getPosition(),
			 * closeNpc.getPosition()) < Constants.getDistance(p.getPosition(),
			 * closestNpc.getPosition())) { closestNpc = closeNpc; } } if
			 * (!eventManager.eventExists(p.getId(), closestNpc.getId())) {
			 * ScannedEvent scan = new ScannedEvent(p, closestNpc.getTank());
			 * eventManager.addEvent(new EventHolder(scan, p.getId(),
			 * Constants.EVENT_LIFE, gameTurn)); p.getBehaviour().onScan(scan);
			 * } nPCsVisibleToPlayer.clear(); }
			 * 
			 * // p.getBehaviour().onEnemyScanned(new Event); }
			 */

			// process the events
			/*
			 * Notes game checks for circumstances that should trigger events
			 * e.g. player scans npc, passes onScanEvent(contains subset of
			 * Event info) to the players behaviour while keeping track of the
			 * Event in Event manager. When onScanEvent triggered again, game
			 * checks there isn't a live Event for the player and npc already If
			 * an npc goes out of sight, then the event should die and a new
			 * onScanned event should be triggered players will have to cater
			 * for their behaviour repeatedly seeing and unseeing an npc
			 */

			// gameClock.restart();
			gameTurn++;
		}
	}

	public synchronized ArrayList<Entity> getEntities() {
		entities.clear();
		entities.addAll(getPlayerManager().getEntities());
		for (Bullet b : activeBullets) {
			log.log(Level.FINEST, "Added bullet to entity list with id: " + b.getId());
			Entity TEM = new Entity(b.getId(), b.getName(), b.getRotation(), b.getPosition(), b.getName());
			entities.add(new Entity(b.getId(), b.getName(), b.getRotation(), b.getPosition(), b.getName()));
		}
		// entities.add(fog);

		return entities;
	}

	@Override
	public void registerNewProjectile(ProjectileFiredEvent e) {
		activeBullets
				.add(new Bullet("bullet", e.getOwnerId(), e.getProjectileStrength(), e.getSource(), e.getDirection()));
	}
}
