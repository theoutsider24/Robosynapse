package server.utility.events;

import org.lwjgl.util.vector.Vector2f;

public class EnemyScannedEvent{
	String enemyType;
	Vector2f pos;
	double direction;
//	NPC enemy;
	public EnemyScannedEvent(String enemyType, Vector2f pos, double direction)
	{
		this.enemyType=enemyType;
		this.pos=pos;
		this.direction=direction;
//		this.enemy=enemy;
	}	
	
	public String getEnemyType()
	{
		return enemyType;
	}
//	public NPC getEnemy()
//	{
//		return enemy;
//	}
	
//	public double getDistance()
//	{
//		return CommonFunctions.getDistSqr(Game.myBot.getPosition(), pos);
//	}
	
//	public double getAngle()
//	{
//		return CommonFunctions.getAngle(Game.myBot.getPosition(), pos)-Game.myBot.getDirection();
//	}
}
