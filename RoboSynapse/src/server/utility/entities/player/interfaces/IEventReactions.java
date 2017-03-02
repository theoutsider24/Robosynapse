package server.utility.entities.player.interfaces;

import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.player.Player;
import server.utility.events.ScannedEvent;
import server.utility.map.WayPoint;

public interface IEventReactions {
	public void onScan(ScannedEvent ev);
	
	public void onScanPlayer(Player p);
	
	public void onScanWayPoint(WayPoint p);
	
	public void onBulletHit(int ownerId, String ownerName, Vector2f bulletPosition, float bulletRotation, int bulletStrength);

	public void onAlarmTriggered();

	public void onHeatIncrease();
	
	public void onCollision();
	
	public void onIdle();
	
	public void onSpawn();
}
