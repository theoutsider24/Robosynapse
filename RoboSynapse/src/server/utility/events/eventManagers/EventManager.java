/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.events.eventManagers;

import java.util.ArrayList;

import server.utility.common.Game;
import server.utility.entities.npc.SecuritySystem;
import server.utility.entities.player.Player;
import server.utility.entities.player.PlayerManager;
import server.utility.map.Map;
import server.utility.map.WayPoint;
import server.utility.map.pathfinder.AStarPathFinder;

public class EventManager {
	public static final String ENEMY_SCANNED_EVENT = "Enemy scanned";
	
	
	public static void processEvents()
	{
		detectScannedObjects();
		detectCollision();
		detectIdle();
	}
	private static void detectScannedObjects()
	{
		//Player Scanning
		ArrayList<Player> allPlayers = Game.getInstance().getPlayerManager().getAllPlayers();
		for(Player p1:allPlayers)//detector
		{
			for(Player p2:allPlayers)//subject
			{
				if(p1!=p2)
				{
					int[] cellPosition = AStarPathFinder.getCellIndexFromPos(p2.getPosition());
					if(p1.getVisionMask()[cellPosition[0]][cellPosition[1]])
						p1.getBehaviour().onScanPlayer(p2);
				}
			}
		}
		
		//Waypoint scanning
		for(Player p:allPlayers)//detector
		{
			for(WayPoint wp:Map.wayPoints)//subject
			{
				int[] cellPosition = AStarPathFinder.getCellIndexFromPos(wp.getPosition());
				if(p.getVisionMask()[cellPosition[0]][cellPosition[1]])
					p.getBehaviour().onScanWayPoint(wp);
				
			}
		}
	}
	
	private static void detectCollision()
	{
		//Collision with walls
		ArrayList<Player> allPlayers = Game.getInstance().getPlayerManager().getAllPlayers();
		for(Player p:allPlayers)
		{
            if (Game.getInstance().mapManager.insideWall(p.getCollisionShape())) {
            	p.getBehaviour().onCollision();
            	p.undoMove();
            }
		}
	}
	
	private static void detectIdle()
	{
		ArrayList<Player> allPlayers = Game.getInstance().getPlayerManager().getAllPlayers();
		for(Player p:allPlayers)
		{
            if (!p.hasActions())
            	p.getBehaviour().onIdle();
		}
	}
	
	public static void fireOnSpawnEvent(Player p)
	{
		p.getBehaviour().onSpawn();
	}
}
