/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.common;

import static server.utility.common.Constants.*;
import server.utility.entities.player.tank.BotFactory;
import server.utility.entities.player.tank.PartFactory;
import server.utility.entities.player.tank.parts.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JsonReader {
	public static JSONObject readFile(String fileName)
	{
		String path = System.getProperty("user.dir") + "\\defs";
		
		JSONParser parser = new JSONParser();

    	String fullPath = DEFINITIONS_DIRECTORY+"\\"+fileName;
        try { 
            Object obj = parser.parse(new FileReader(fullPath));            
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not find "+fullPath);
        }
        return null;
	}
	public static int getInt(JSONObject obj,String name, int defaultValue)
	{
		int val=0;
		if(obj.get(name)==null)
			val=defaultValue;
		else
			val=(int)((long)obj.get(name));
		
		return val;
	}
	public static String getString(JSONObject obj,String name, String defaultValue)
	{
		String val="";
		if(obj.get(name)==null)
			val=defaultValue;
		else
			val=(String)obj.get(name);
		
		return val;
	}
	public static void readBotDefinition(JSONObject jsonObject)
	{
		if(jsonObject!=null)
		{
			BotFactory.addDefinition(jsonObject);
		}		
	}
	public static void readPartDefinition(Class type,JSONObject jsonObject)
	{
		if(jsonObject!=null)
		{
			PartFactory.getFactory(type).addDefinition(jsonObject);
		}		
	}
	public static void readGameDefinition(String fileName)
	{
		JSONObject jsonObject = readFile(fileName);
		
		JSONArray defs;
		
		String[] tags={"guns","ammo","cooling","armour","wheels"};
		Class[] classes={Gun.class,Ammo.class,Cooling.class,Armour.class,Wheels.class};
		
		for(int i=0;i<tags.length;i++)
		{
			defs = (JSONArray)jsonObject.get(tags[i]);
			for(Object s:defs)
			{
				readPartDefinition(classes[i],(JSONObject)s);
			}
		}		
		
		defs = (JSONArray)jsonObject.get("bots");
		for(Object s:defs)
		{
			readBotDefinition((JSONObject)s);
		}
	}
}