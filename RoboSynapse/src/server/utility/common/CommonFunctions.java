package server.utility.common;


import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class CommonFunctions {
	public static float correctAngle(float a2)
	{
		while(a2<0)
			a2+=360;
		while(a2>=360)
			a2-=360;
		return a2;
	}
	public static boolean isAngleInRange(float a1, float a2,float a3)
	{
		
		a1 = correctAngle(a1);
		a2 = correctAngle(a2);
		a3 = correctAngle(a3);
		if(a2<a3)//left to right
		{
		//	System.out.println("first");
			return a1>a2&&a1<a3;
		}
		else
		{
			if(a1>180)
				a3+=360;
			else
				a2-=360;
			//System.out.println("second");
			return a1>a2&&a1<a3;
		}
	}
	
	
	public static boolean isInRange(Vector2f p1,Vector2f p2, int dist)
	{
		int xDiff = (int) (p1.x-p2.x);
		int yDiff = (int) (p1.y-p2.y);
		//if()
		int distSqr = (xDiff*xDiff) + (yDiff*yDiff);
		return distSqr<(dist*dist);
	}
	public static float getDistSqr(Vector2f p1,Vector2f p2)
	{
		float xDiff =  (p1.x-p2.x);
		float yDiff =  (p1.y-p2.y);
		float distSqr = (xDiff*xDiff) + (yDiff*yDiff);
		return distSqr;
	}
	public static float getAngle(Vector2f p1,Vector2f p2)
	{
		float xDiff =  (p1.x-p2.x);
		float yDiff =  (p1.y-p2.y);
		float angle = (float) (Math.toDegrees(Math.atan2(yDiff, xDiff))-90);
		return CommonFunctions.correctAngle(angle);
	}
}
