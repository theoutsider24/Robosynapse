/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank.parts;

import server.utility.common.JsonReader;
import static server.utility.common.Constants.*;

import org.json.simple.JSONObject;

public abstract class Part implements Cloneable{
	String name;
	int noise;
	int weight;
	int heat;
	int health;
	
	
	public Part(JSONObject obj)
	{
		setNoise(JsonReader.getInt(obj,"noise",DEFAULT_NOISE));
		setWeight(JsonReader.getInt(obj,"weight",DEFAULT_WEIGHT));
		setName(JsonReader.getString(obj, "name", DEFAULT_NAME));
		setHeat(JsonReader.getInt(obj,"heat",DEFAULT_HEAT));	
		setHealth(JsonReader.getInt(obj,"health",DEFAULT_HEAT));	
	}
	
	@Override
	public String toString(){
		String result="";
		result+="Name: "+name +"\n";
		result+="Noise: "+noise +"\n";
		result+="Weight: "+weight +"\n";	
		result+="Heat: "+heat +"\n";
		result+="Health: "+health +"\n";
		
		return result;
	}
	public abstract void update();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNoise() {
		return noise;
	}
	public void setNoise(int noise) {
		this.noise = noise;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
}
