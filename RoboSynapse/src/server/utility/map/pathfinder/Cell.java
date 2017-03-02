/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

//package server.utility.map.pathfinder;
package server.utility.map.pathfinder;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import server.utility.map.matthew.Grid;
import static server.utility.common.Constants.*;

public class Cell {
	boolean open = false;
	int x=0;
	int y=0;
	ArrayList<Cell> neighbours;
	
	int hVal=-1;
	Cell parent=null;
	int costSoFar=0;
	public int cost=1;
	
	public Cell(int x, int y, int val)
	{
		neighbours = new ArrayList<Cell>();
		this.x=x;
		this.y=y;
		if(val==1)
			close();
		else
			open();
		
		if(x==0||y==0)
			close();
	}
	public Vector2f getCenter()
	{
		return new Vector2f((x+0.5f)*Grid.GRID_SQUARE_SIZE,(y+0.5f)*Grid.GRID_SQUARE_SIZE);
	}
	public void addNeighour(Cell c)
	{
		neighbours.add(c);
	}
	
	public ArrayList<Cell> getNeighbours()
	{
		return neighbours;
	}
	public void close()
	{
		cost=Integer.MAX_VALUE;
		open=false;
	}
	public void open()
	{
		open=true;
		cost=1;
	}
	public boolean isOpen()
	{
		return open;
	}
	public static boolean hasDiagonalConnection(Cell c1,Cell c2)
	{
		if(c1.neighbours.contains(c2))
			return true;
		for(Cell c:c1.getNeighbours())
		{
			if(c.neighbours.contains(c2))
				return true;
		}
		return false;
	}
}
