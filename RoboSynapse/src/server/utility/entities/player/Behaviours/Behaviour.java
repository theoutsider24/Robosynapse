package server.utility.entities.player.Behaviours;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;

import org.newdawn.slick.geom.Vector2f;
import org.reflections.Reflections;

import server.utility.common.CommonFunctions;
import server.utility.common.Constants;
import server.utility.entities.player.Player;
import server.utility.entities.player.interfaces.IBotActions;
import server.utility.entities.player.interfaces.IEventReactions;
import server.utility.map.WayPoint;
import server.utility.map.pathfinder.AStarPathFinder;

public abstract class Behaviour implements IEventReactions, IBotActions {
	public static TreeMap<String, Class> allBehaviours = new TreeMap<String, Class>(String.CASE_INSENSITIVE_ORDER);
	/*static {
		loadBehaviourClasses();
	}*/
	protected Player player;

	public static Behaviour getBehaviour(String s) {

		Behaviour result = null;
		/*loadBehaviourClasses();
		//System.out.println(allBehaviours);

		if (!allBehaviours.containsKey(s)) {
			s = "EmptyBehaviour";
		}

		try {
			result = (Behaviour) allBehaviours.get(s).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			result=loadBehaviourClass(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result==null)
			result=new EmptyBehaviour();
		System.out.println("assigned behaviour - "+result.getClass().getSimpleName());
		return result;
	}

	public final void setPlayer(Player player) {
		this.player = player;
	}

	public final void move(double distance) {
		player.move(distance);
	}

	public final void ahead(double distance) {
		player.move(distance);
	}

	public final void backwards(double distance) {
		player.move(-distance);
	}

	public final void pause(double turnsToWait) {
		player.pause(turnsToWait);
	}

	public final void fire() {
		player.fire();
	}

	public final void clearCommands() {
		player.clearCommands();
	}

	@Override
	public final void turn(float degrees) {
		player.turn(degrees);
	}
	
	@Override
	public final void turnToFace(float degrees) {
		player.turnToFace(degrees);
	}

	public final void goToPoint(Vector2f point) {
		/*ArrayList<Vector2f> path = AStarPathFinder.getPath((int) player.getPosition().x, (int) player.getPosition().y,
				(int) point.x, (int) point.y);
		Collections.reverse(path);
		//if (CommonFunctions.getDistSqr(player.getPosition(), path.get(0)) < 5)
			path.remove(0);
		Vector2f origin = player.getPosition();
		float originAngle = player.getRotation();
		for (Vector2f p : path) {
			float angle = CommonFunctions.getAngle(origin, p);
			float turnAngle = angle - originAngle;
			if (Math.abs(turnAngle) > 180) {
				if (turnAngle < 0)
					turnAngle += 360;
				else
					turnAngle -= 360;
			}
			turn(turnAngle);

			originAngle = angle;
			move(Math.sqrt(CommonFunctions.getDistSqr(origin, p)));
			origin = p;
		}*/
		player.goToPoint(point);
	}

	/*
	 * public final void goToPoint(Vector2f p) { float
	 * angle=CommonFunctions.getAngle(player.getPosition(), p); float turnAngle
	 * = angle-player.getRotation(); if(Math.abs(turnAngle)>180) {
	 * if(turnAngle<0) turnAngle+=360; else turnAngle-=360; } turn(turnAngle);
	 * 
	 * move(Math.sqrt(CommonFunctions.getDistSqr(player.getPosition(), p))); }
	 */
	public final void goToWayPoint(WayPoint wp) {
		goToPoint(wp.getPosition());
	}

	public final boolean followWayPointDirection(WayPoint wp, int direction) {
		if (wp.hasDirection(direction)) {
			if (CommonFunctions.getDistSqr(player.getPosition(), wp.getPosition()) > 20) {
				goToWayPoint(wp);
			}
			turnToFace(wp.getDirection(direction));
			return true;
		} else
			return false;
	}

	protected static final void registerBehaviour(Class c) {
		Behaviour.allBehaviours.put(c.getSimpleName(), c);
		System.out.println("registering:" + c.getSimpleName());
	}

	private static Behaviour loadBehaviourClass(String className) throws InstantiationException, IllegalAccessException {
		Behaviour.allBehaviours.clear();
		Reflections reflections = new Reflections("server.utility.entities.player.Behaviours");
		Set<Class<? extends Behaviour>> allClasses = reflections.getSubTypesOf(Behaviour.class);
		
		for (Class<? extends Behaviour> c : allClasses)
		{		 
			if(c.getSimpleName().toLowerCase().equals(className.toLowerCase()))
				return c.newInstance();
		}
		return null;
	}		
}
