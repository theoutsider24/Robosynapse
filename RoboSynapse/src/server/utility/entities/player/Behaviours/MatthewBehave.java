package server.utility.entities.player.Behaviours;


import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.player.Player;
import server.utility.events.ScannedEvent;
import server.utility.map.WayPoint;
/*
 * A test behavior class for seeing how stuff is working out.
 */
public class MatthewBehave extends Behaviour {
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
		turn(100);
		move(500);
	}

	@Override
	public void onSpawn() {
		System.out.println("spawn");
		goToPoint(new Vector2f(300,500));
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
		move(-1);
		turn(1);
		move(20);
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
			//turn(1);
		}
	}

	@Override
	public void onScanWayPoint(WayPoint wp) {
		if(status.equals("idle_fghf"))
		{
			status="goingToWaypoint";
			clearCommands();
			followWayPointDirection(wp, wp.SOUTH);
			move(200);
			System.out.println(wp.getPosition());
		}
	}
}