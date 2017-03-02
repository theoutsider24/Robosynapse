/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank;

import static server.utility.common.Constants.*;

import java.util.TreeMap;

import org.json.simple.JSONObject;

import server.utility.common.JsonReader;



public class BotFactory {
	static TreeMap<String, JSONObject> definitions = new TreeMap<String, JSONObject>();
	
	public static void addDefinition(JSONObject obj)
	{
		String name = JsonReader.getString(obj, "name", DEFAULT_NAME);
		if(!definitions.containsKey(name))
		{
			definitions.put(name,obj);
		}		
	}
	public static Bot buildBot(String name)
	{
		JSONObject obj = definitions.get(name);
		if(obj==null)
		{
			System.out.println("Attempted to build Bot: \""+name+"\"\nThis bot does not exist");
			throw(new NullPointerException());
		}
		return new Bot(obj);
	}
}
