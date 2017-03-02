/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.Behaviours;

import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.player.Player;
import server.utility.events.ScannedEvent;
import server.utility.map.WayPoint;

public class EmptyBehaviour extends Behaviour{
	@Override
	public void onScan(ScannedEvent ev) {	}

	@Override
	public void onBulletHit(int ownerId, String ownerName,
			Vector2f bulletPosition, float bulletRotation, int bulletStrength) {	}

	@Override
	public void onAlarmTriggered() {	}

	@Override
	public void onHeatIncrease() {	}

	@Override
	public void onCollision() {	}

	@Override
	public void onSpawn() {	}

	@Override
	public void onScanPlayer(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIdle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScanWayPoint(WayPoint p) {
		// TODO Auto-generated method stub
		
	}

}
