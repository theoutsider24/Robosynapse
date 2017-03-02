/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/

package server.utility.map.matthew;

import static server.utility.common.Constants.MAP_SQUARE_SIZE;
import static server.utility.common.Constants.WINDOW_HEIGHT;
import static server.utility.common.Constants.WINDOW_WIDTH;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import server.utility.map.pathfinder.Cell;

public class Grid {
	public static int SIZE_FACTOR=2;
	public static float GRID_SQUARE_SIZE=(float)MAP_SQUARE_SIZE/(float)SIZE_FACTOR;
	
	
	ArrayList<Vector2f> spawnPoints;
	
	private static Cell[][] cells;
	
	public Grid(int[][] map)
	{
        cells = new Cell[map.length][map.length];
        
        
        for(int i=0;i<map.length;i++)
        {
			for(int j=0;j<map.length;j++)
			{
				cells[i][j]= new Cell(i,j,map[i][j]);
			}
        }
	}
	
	public static boolean cellExists(int x,int y)
	{
		return x>=0&&y>=0&&x<cells.length&&y<cells.length;
	}
	
	public static boolean isOpen(int x,int y)
	{
		return isOpen(cells[x][y]);
	}
	public static boolean isOpen(Cell c)
	{
		return c.isOpen();
	}
	public static Cell getCell(int x,int y)
	{
		return cells[x][y];
	}
	public static Cell getCell(int[] p)
	{
		return getCell(p[0],p[1]);
	}
	public static Cell getCellFromPos(int x, int y)
	{
		int i=(int) ((float)x/(float)GRID_SQUARE_SIZE);
		int j=(int) ((float)y/(float)GRID_SQUARE_SIZE);
		return cells[i][j];
	}
}
