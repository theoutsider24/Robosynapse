package server.utility.entities.player.Behaviours;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import com.mysql.fabric.xmlrpc.base.Array;

import server.utility.entities.player.Player;
import server.utility.events.ScannedEvent;
import server.utility.map.WayPoint;
/*
 * A test behavior class for seeing how stuff is working out.
 */
public class WillBehave extends Behaviour {
	public String status = "not stuck";
	
	public void onScan(ScannedEvent ev) {
	    System.out.println("onScan");
		System.out.println(ev.toString()); 
	}

	@Override
	public void onAlarmTriggered() {
		System.out.println("Alarm triggered");
	}

	@Override
	public void onHeatIncrease() {
		System.out.println("Heat increase");
	}

	@Override
	public void onSpawn() {
		System.out.println("spawned");
		//goToPoint(new Vector2f(100,500));
	}

    @Override
    public void onBulletHit(int ownerId, String ownerName, Vector2f bulletPosition, float bulletRotation, int bulletStrength) {
        System.out.println("That scoundrel " + ownerName + " shot me!");
       
    }

	@Override
	public void onCollision() {
		if(status.equals("not stuck"))
		{
			System.out.println("storing "+player.actions.size() + " actions");
			player.storeCommands();
			status = "stuck";
		}
		
		player.clearCommands();
		move(-5);
		turn(1);
	}

	@Override
	public void onScanPlayer(Player p) {
	}
	
	
	
	@Override
	public void onIdle()
	{
		if(status.equals("stuck"))
		{
			System.out.println("resuming "+player.storedActions.size() + " actions");
			status="not stuck";
			player.resumeStoredCommands();
		}
		else
		{
			status="idle";
			turn(1);
		}
	}

	ArrayList<WayPoint> visited = new ArrayList<WayPoint>();
	@Override
	public void onScanWayPoint(WayPoint wp) {
		if(status.equals("idle")&&!visited.contains(wp))
		{
			status="goingToWaypoint";
			clearCommands();
			goToWayPoint(wp);
			visited.add(wp);
		}
	}
}