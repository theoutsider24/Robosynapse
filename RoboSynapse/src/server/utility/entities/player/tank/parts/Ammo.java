/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank.parts;

import static server.utility.common.Constants.*;

import org.json.simple.JSONObject;

import server.utility.common.JsonReader;

public class Ammo extends Part 
{	
	
	private static final int DEFAULT_CAPACITY = 0;//TODO put in constants
	int capacity;

	public Ammo(JSONObject obj) 
	{
		super(obj);
		
		setCapacity(JsonReader.getInt(obj,"capacity",DEFAULT_CAPACITY));
	}

	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString(){
		String result=super.toString();
		
		result+="Capacity: "+capacity +"\n";	
		
		return result;
	}
}
