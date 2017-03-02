/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.entities.player.tank;

import org.newdawn.slick.geom.Vector2f;
import server.utility.entities.MovableObject;
import static server.utility.common.Constants.*;

public class Bullet extends MovableObject{
	Vector2f direction;
    int   owner;
    int strength;

    public Bullet(String name, int owner, int strength, Vector2f pos, float rotation) {
        super(name);
        setSize(4);
        setRotation(rotation);
        setVectorDirectionFromRotation();
        setPosition(pos);
        this.owner = owner;
        this.strength = strength;
    }

    @Override
    public void update() {

        move(new Vector2f((float)(direction.x * (BASESPEED + 1)), (float)(direction.y * (BASESPEED + 1))));
    }

    private void setVectorDirectionFromRotation() {
        double rads = Math.toRadians(getRotation());
        direction = new Vector2f((float) Math.sin(rads), (float) -Math.cos(rads));
    }

    public int getOwner() {
        return owner;
    }

    public int getStrength() {
        return strength;
    }
}
