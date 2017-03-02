/*********************************************************************************************************************************
 * Copyright (c) 2010 - 2030 by ACI Worldwide Inc. 6060 Coventry Drive, Omaha, Nebraska, 68022-6482, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of ACI Worldwide Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered
 * with ACI Worldwide Inc.
 ********************************************************************************************************************************/
//Written by: Matthew Coyle
//package server.utility.map.pathfinder;
//import static server.utility.Constants.MAP_SQUARE_SIZE;
package server.utility.map.pathfinder;

import static server.utility.common.Constants.*;

import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.geom.Vector2f;

import server.utility.common.CommonFunctions;
import server.utility.common.Game;
import server.utility.entities.player.tank.Bot;
import server.utility.map.matthew.Grid;


//import server.utility.map.Map;

public class AStarPathFinder {
	private static Cell[][] cells;
	private static int[][] mazeWalls;
	
	/*public static final int WINDOW_WIDTH = 2000;
	public static final int WINDOW_HEIGHT = 1125;
	public static final int MAP_SQUARE_SIZE = 50;*/
	
	private static ArrayList<Cell> openList;
	private static ArrayList<Cell> closedList;
	
	public static void init(/*Map map*/)
	{
		 int mapWidth = WINDOW_WIDTH / MAP_SQUARE_SIZE;
	        int mapHeight = WINDOW_HEIGHT / MAP_SQUARE_SIZE;
	        mazeWalls = new int[mapWidth][mapHeight];
	        mazeWalls[0] =  new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	        mazeWalls[1] =  new int[]{0,2,2,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,2,0,0,0,0,0,0,0,0,0,0,2,2,0};
	        mazeWalls[2] =  new int[]{0,2,2,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,2,0,0,0,0,0,0,0,0,0,0,2,2,0};    
	        mazeWalls[3] =  new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0};
	        mazeWalls[4] =  new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0};
	        mazeWalls[5] =  new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
	        mazeWalls[6] =  new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
	        mazeWalls[7] =  new int[]{0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,0};
	        mazeWalls[8] =  new int[]{0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,0};
	        mazeWalls[9] =  new int[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0};
	        mazeWalls[10] = new int[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0};
	        mazeWalls[11] = new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0};
	        mazeWalls[12] = new int[]{0,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0};
	        mazeWalls[13] = new int[]{0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0};
	        mazeWalls[14] = new int[]{0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0};
	        mazeWalls[15] = new int[]{0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0};
	        mazeWalls[16] = new int[]{0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0};
	        mazeWalls[17] = new int[]{0,0,0,0,0,0,0,1,1,0,0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0};
	        mazeWalls[18] = new int[]{0,0,0,0,0,0,0,1,1,0,0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0};
	        mazeWalls[19] = new int[]{0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,2,2,0,0,0,0,0,0,1,1,0,0,1,1,2,2,0};
	        mazeWalls[20] = new int[]{0,2,2,1,1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,2,2,0,0,0,0,0,0,1,1,0,0,1,1,2,2,0};
	        mazeWalls[21] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	        
        int gridWidth = mapWidth*Grid.SIZE_FACTOR;
        int gridHeight = mapHeight*Grid.SIZE_FACTOR;
		
		cells = new Cell[gridWidth][gridHeight];
		
		for(int i=0;i<mapWidth;i++)
			for(int j=0;j<mapHeight;j++)
			{
				for(int x=0;x<Grid.SIZE_FACTOR;x++)
				{
					for(int y=0;y<Grid.SIZE_FACTOR;y++)
					{
						int xCoord = (i*Grid.SIZE_FACTOR)+x;
						int yCoord = (j*Grid.SIZE_FACTOR)+y;
						cells[xCoord][yCoord]=new Cell(xCoord,yCoord,mazeWalls[i][j]);
					}
				}
			}
		ArrayList<Cell> cellsToClose=new ArrayList<Cell>();
		for(int i=0;i<cells.length;i++)
			for(int j=0;j<cells[0].length;j++)
			{
				if(!cells[i][j].isOpen())
				{
					for(int x=-1;x<=1;x++)
						for(int y=-1;y<=1;y++)
						{
							if((x!=0&y!=0)&&cellExists(i+x,j+y)&&cells[i+x][j+y].isOpen())
							{
								if(!cellsToClose.contains(cells[i+x][j+y]))
									cellsToClose.add(cells[i+x][j+y]);
							}
						}
				}
			}
		for(Cell c:cellsToClose)
		{
			c.cost+=2;
		}
				
				
		
		for(int i=0;i<cells.length;i++)
			for(int j=0;j<cells[0].length;j++)
			{
				if(cellExists(i-1,j)&&cells[i-1][j].isOpen())cells[i][j].addNeighour(cells[i-1][j]);
				if(cellExists(i,j-1)&&cells[i][j-1].isOpen())cells[i][j].addNeighour(cells[i][j-1]);
				if(cellExists(i+1,j)&&cells[i+1][j].isOpen())cells[i][j].addNeighour(cells[i+1][j]);
				if(cellExists(i,j+1)&&cells[i][j+1].isOpen())cells[i][j].addNeighour(cells[i][j+1]);
			}
	}
	
	public static boolean cellExists(int x,int y)
	{
		return x>=0&&y>=0&&x<cells.length&&y<cells[0].length;
	}
	
	
	private static Vector2f normaliseVector(Vector2f v)
	{
		float len = (float) Math.sqrt(((v.x*v.x)+(v.y*v.y)));
		Vector2f result = new Vector2f(v.x/len,v.y/len);
		return result;
	}
	public static boolean[][] getVisionMask(Bot bot, int range)
    {
		int coneHalfAngle=40;
		
    	boolean[][] mask = new boolean[cells.length][cells[0].length];
    	Vector2f pos = bot.getPosition();
    	Vector2f botDir =  new Vector2f((float)Math.cos(Math.toRadians(bot.getRotation()-90)),(float)Math.sin(Math.toRadians(bot.getRotation()-90)));
    	Vector2f v1 = normaliseVector(botDir);
    	
    	
    	
    	
    	//Cell origin=getCellFromPos((int)pos.x, (int)pos.y);
    	Cell origin=getCellFromPos((int)pos.x, (int)pos.y);
    	mask[origin.x][origin.y]=true;
    	
    	int cellRange=(int) ((range/Grid.GRID_SQUARE_SIZE)+1);
    	for(int i=origin.x-cellRange;i<origin.x+cellRange;i++)
    		for(int j=origin.y-cellRange;j<origin.y+cellRange;j++)
    			try{
    				if(CommonFunctions.isInRange(pos, cells[i][j].getCenter(), 75))
    				{
    					mask[i][j]=true;
    				}
    				else if(CommonFunctions.isInRange(pos, cells[i][j].getCenter(), range))
	    			{
    					Vector2f center = new Vector2f(cells[i][j].getCenter());
    					Vector2f v2 = new Vector2f(center.x-pos.x,center.y-pos.y);	
    					v2 = normaliseVector(v2);
    			    	double dotProd = (v1.x*v2.x) + (v1.y*v2.y);
    			    	if(Math.toDegrees(Math.acos(dotProd))<coneHalfAngle)
    			    	{
    			    		if(hasLineOfSight(origin.x,origin.y,i,j))
    	    					mask[i][j]=true;
    			    	}
	    			}
    			}catch(ArrayIndexOutOfBoundsException ex){continue;}
    	
    	return mask;
    }
	
	public static ArrayList<Vector2f> getPath(int x1, int y1, int x2, int y2)//these values are cell coords
	{

		openList=new ArrayList<Cell>();
		closedList=new ArrayList<Cell>();
		ArrayList<Vector2f> path = new ArrayList<Vector2f>();
		ArrayList<Cell> cellPath = new ArrayList<Cell>();
		
		Cell startCell=getCellFromPos(x1, y1);
		Cell goalCell=getCellFromPos(x2, y2);
		
		if(!isOpen(startCell.x,startCell.y)) {System.out.println("Start cell is closed"); return path;}
		if(!isOpen(goalCell.x,goalCell.y)) {System.out.println("Goal cell is closed"); return path;}
		
		openList.add(startCell);
		
		while(!openList.contains(goalCell))
		{
			Cell currentCell = openList.get(0);
			for(Cell c: currentCell.getNeighbours())
			{
				if(openList.contains(c))
				{
					if(c.costSoFar+1>currentCell.costSoFar)
					{
						openCell(c,currentCell);
					}
				}
				else if(closedList.contains(c))
				{
					if(c.costSoFar+1>currentCell.costSoFar)
					{
						closedList.remove(c);
						openCell(c,currentCell);
					}
				}
				else
				{
					openCell(c,currentCell);
				}
			}		
			closedList.add(currentCell);
			openList.remove(currentCell);
		}
		
		Cell c = goalCell;
		while(c!=startCell)
		{
			cellPath.add(c.parent);
			c=c.parent;
		}

		//smoothPath(cellPath);
		
		
		for(Cell temp:cellPath)
		{
			path.add(temp.getCenter());
		}
		path.add(0,new Vector2f(x2,y2));		
		
		return path;
	}
	private static void smoothPath(ArrayList<Cell> cellPath)
	{		
		for(int i=0;i<cellPath.size();i++)
		{
			for(int j=i+2;j<cellPath.size();j++)
			{
				if(hasLineOfSight(cellPath.get(i).x,cellPath.get(i).y,cellPath.get(j).x,cellPath.get(j).y))
				{
					int nodesToRemove=(j-i)-1;
					for(int x=0;x<nodesToRemove;x++)
						cellPath.remove(i+1);					
					j-=nodesToRemove;
				}
			}
		}
	}
	public static void openCell(Cell c, Cell parent)
	{
		c.costSoFar=parent.costSoFar+c.cost;
		c.hVal = c.costSoFar+getManhattanDistance(c,parent);
		c.parent=parent;
		if(!openList.contains(c))openList.add(c);
	}
	public static int getManhattanDistance(Cell c1,Cell c2)
	{
		return Math.abs(c1.x-c2.x)+Math.abs(c1.y-c2.y);
	}
	
	public static Cell getCellFromPos(int x, int y)
	{
		int[] pos = getCellIndexFromPos(x,y);
		return cells[pos[0]][pos[1]];
	}
	public static Cell getCellFromPos(Vector2f v)
	{
		int[] pos = getCellIndexFromPos(v);
		return cells[pos[0]][pos[1]];
	}
	public static int[] getCellIndexFromPos(int x, int y)
	{
		int i=(int) ((float)x/(float)Grid.GRID_SQUARE_SIZE);
		int j=(int) ((float)y/(float)Grid.GRID_SQUARE_SIZE);
		return new int[]{i,j};
	}
	public static int[] getCellIndexFromPos(Vector2f v)
	{
		int i=(int) ((float)v.x/(float)Grid.GRID_SQUARE_SIZE);
		int j=(int) ((float)v.y/(float)Grid.GRID_SQUARE_SIZE);
		return new int[]{i,j};
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
	public static ArrayList<int[]> getBresenhamLine(int x1,int y1,int x2,int y2)
	{
		ArrayList<int[]> points = new ArrayList<int[]>();
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		int sx = (x1 < x2) ? 1 : -1;
		int sy = (y1 < y2) ? 1 : -1;

		int err = dx - dy;

		while (true) {
			points.add(new int[]{x1,y1});
		  //  getCell(x1, y1).close();

		    if (x1 == x2 && y1 == y2) {
		        break;
		    }

		    int e2 = 2 * err;

		    if (e2 > -dy) {
		        err = err - dy;
		        x1 = x1 + sx;
		    }

		    if (e2 < dx) {
		        err = err + dx;
		        y1 = y1 + sy;
		    }
		}
		return points;
	}
	
	public static boolean hasLineOfSight(int x1, int y1, int x2, int y2)
	{
		boolean los=true;
		
		ArrayList<int[]> pts = getBresenhamLine(x1, y1, x2, y2);
		for(int i=0;i<pts.size()&&los;i++)
		{
			int p[] = pts.get(i);
			if(!isOpen(p[0],p[1]))
			{
					los=false;
			}
			if(i>0&&!Cell.hasDiagonalConnection(getCell(p), getCell(pts.get(i-1))))
				los=false;
		}		
		return los;
	}
	
}
