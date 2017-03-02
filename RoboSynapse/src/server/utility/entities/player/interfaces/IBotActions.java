package server.utility.entities.player.interfaces;

import org.newdawn.slick.geom.Vector2f;

public interface IBotActions {
	
	void move(double distance);
	
	void turn(float degrees);
	
	void fire();
	
	void pause(double turnsToWait);
	
	void goToPoint(Vector2f p);
	
	void turnToFace(float f);
}