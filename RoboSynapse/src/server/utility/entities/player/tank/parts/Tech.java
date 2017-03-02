/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank.parts;

import org.json.simple.JSONObject;

public abstract class Tech extends Part{
	boolean enabled=true;
	public Tech(JSONObject obj) {
		super(obj);
	}
	public abstract void activate();
	public abstract void deactivate();
}
