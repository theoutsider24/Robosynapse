/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank.parts;

import org.json.simple.JSONObject;






import server.utility.common.JsonReader;
import static server.utility.common.Constants.*;

public class Gun extends Part{
	int damage;
	int reloadSpeed;
	int accuracy;
	int range;	
	
	public Gun(JSONObject obj)
	{
		super(obj);
		
		setAccuracy(JsonReader.getInt(obj,"accuracy",DEFAULT_ACCURACY));
		setDamage(JsonReader.getInt(obj,"damage",DEFAULT_DAMAGE));			
		setRange(JsonReader.getInt(obj,"range",DEFAULT_RANGE));
		setReloadSpeed(JsonReader.getInt(obj,"reloadSpeed",DEFAULT_RELOAD_SPEED));
	}
	
	@Override
	public String toString(){
		String result=super.toString();
		
		result+="Damage: "+damage +"\n";
		result+="Reload Speed: "+reloadSpeed +"\n";
		result+="Accuracy: "+accuracy +"\n";
		result+="Range: "+range +"\n";		
		
		return result;
	}
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getReloadSpeed() {
		return reloadSpeed;
	}
	public void setReloadSpeed(int reloadSpeed) {
		this.reloadSpeed = reloadSpeed;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}	
}
