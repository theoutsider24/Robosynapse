/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank;

import server.utility.common.JsonReader;
import server.utility.entities.MovableObject;
import server.utility.entities.player.tank.PartFactory;
import static server.utility.common.Constants.*;

import org.json.simple.JSONObject;

import server.utility.entities.player.tank.parts.*;

public class Bot extends MovableObject{
	private int GUN=0,
				ARMOUR=1,
				WHEELS=2,
				COOLING=3,
				AMMO=4,
				TECHHOLDER=5,		
				length=6; //used to get number of values	
		
	String name;	
	private Part[] parts;

	public Bot(JSONObject obj) {
		super(JsonReader.getString(obj, "name", DEFAULT_NAME));
		parts = new Part[length];
		
		name = super.name;
		parts[GUN] = PartFactory.getPart(Gun.class,JsonReader.getString(obj, "gun", "cannon")); //creates gun based on "gun" field in bot json definition, defaults to "cannon"
		parts[AMMO] = PartFactory.getPart(Ammo.class,JsonReader.getString(obj, "ammo", "bullet"));
		parts[COOLING] = PartFactory.getPart(Cooling.class,JsonReader.getString(obj, "cooling", "Hand-Held Fan"));
		parts[ARMOUR] = PartFactory.getPart(Armour.class,JsonReader.getString(obj, "armour", "Paper"));
		parts[WHEELS] = PartFactory.getPart(Wheels.class,JsonReader.getString(obj, "wheels", "Trolley Wheel"));
	}
	
	public String toString()
	{
		String result="";
		result+="Name: "+name+"\n";
		
		result+="\n--GUN--\n";
		result+=parts[GUN].toString();
		
		result+="\n--AMMO--\n";
		result+=parts[AMMO].toString();
		
		result+="\n--COOLING--\n";
		result+=parts[COOLING].toString();
		
		result+="\n--ARMOUR--\n";
		result+=parts[ARMOUR].toString();			
		
		result+="\n--WHEELS--\n";
		result+=parts[WHEELS].toString();		
		
		return result;
	}
	
	public void update()
	{
		for(Part p:parts)
		{
			if(p!=null)p.update();
		}
	}
}
