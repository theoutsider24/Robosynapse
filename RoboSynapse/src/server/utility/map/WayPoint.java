/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.map;

import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.MovableObject;
import server.utility.entities.player.Player;

public class WayPoint {
	private boolean[] directions;//N,S,E,W
	
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	private static float[] angles = new float[]{0,180,90,270};
	
	Vector2f position;
	public WayPoint(Vector2f v, int... dirs){
		directions=new boolean[4];		
		for(int d:dirs)
		{
			directions[d]=true;
		}
		position=v;
	}
	public Vector2f getPosition()
	{
		return position;
	}
	public float getDirection(int i)
	{
		return angles[i];
	}
	public boolean hasDirection(int i)
	{
		return directions[i];
	}
	public static float getRequiredRotation(MovableObject tank,float desiredRotation)
	{
		float currentRotation = tank.getRotation();
		
		float angle=desiredRotation-currentRotation;
		
		if(Math.abs(angle)>180)
		{
			if(angle<0) angle+=360;
			else angle-=360;		
		}
		
		return angle;
	}
}
