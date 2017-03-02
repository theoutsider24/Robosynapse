/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank;

import server.utility.entities.player.tank.parts.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.TreeMap;

import org.json.simple.JSONObject;

public class PartFactory<O extends Part> {
	
	private static HashMap<Class,PartFactory> factories = new HashMap<Class,PartFactory>();
	
	private Class<O> partClass;
	TreeMap<String, JSONObject> prototypes = new TreeMap<String, JSONObject>(String.CASE_INSENSITIVE_ORDER);
	static{
		new PartFactory<Gun>(Gun.class);
		new PartFactory<Ammo>(Ammo.class);
		new PartFactory<Cooling>(Cooling.class);
		new PartFactory<Armour>(Armour.class);
		new PartFactory<Wheels>(Wheels.class);
	}
	public PartFactory(Class<O> partClass)
    {
        this.partClass = partClass;
        
        factories.put(partClass, this);
    }
	public O getPart(String name)
	{
		JSONObject prototype = prototypes.get(name);
		if(prototype==null)
		{
			System.out.println("Attempted to build Part: \""+name+"\"\nThis part does not exist");
			throw(new NullPointerException());
		}
		return buildPart(prototype);
	}
	public static Part getPart(Class cl,String name)
	{
		return getFactory(cl).getPart(name);
	}
	public void addDefinition(JSONObject prototype)
	{
		String name = (String) prototype.get("name");
		if(!prototypes.containsKey(name))
			prototypes.put(name, prototype);
		else
			System.out.println("Duplicate definition found");
	}
	private O buildPart(JSONObject prototype)
	{
		try {
			O newPart = (O) partClass.getConstructor(JSONObject.class).newInstance(prototype);
			return newPart;					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static PartFactory getFactory(Class cl)
	{
		return factories.get(cl);
	}
}
