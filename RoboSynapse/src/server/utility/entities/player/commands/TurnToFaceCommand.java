package server.utility.entities.player.commands;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.geom.Vector2f;

import server.utility.common.CommonFunctions;
import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;
import server.utility.map.WayPoint;
import server.utility.map.pathfinder.AStarPathFinder;

public class TurnToFaceCommand implements ICommand {

    private MovableObject playerTank;
    private boolean actionFinished;
    private double turnsToWait;
    private double turnsWaited;
    private Vector2f point;
    private float desiredAngle;
    
    private TurnCommand turnCommand;
    
    
    public TurnToFaceCommand(MovableObject tank, Vector2f v) {
        this.playerTank = tank;
        this.actionFinished = false;
        this.point=v;
    }
    
    public TurnToFaceCommand(MovableObject tank, float angle) {
        this.playerTank = tank;
        this.actionFinished = false;
        this.desiredAngle=angle;
    }
    
    @Override
    public void init()
    {

		System.out.println("init");
    	if(point==null)
    		turnCommand = new TurnCommand(playerTank, WayPoint.getRequiredRotation(playerTank, desiredAngle));
    	else
    	{
    		Vector2f origin = playerTank.getPosition();
			float originAngle = playerTank.getRotation();
			float angle = CommonFunctions.getAngle(origin, point);
			float turnAngle = angle - originAngle;
			if (Math.abs(turnAngle) > 180) {
				if (turnAngle < 0)
					turnAngle += 360;
				else
					turnAngle -= 360;
			}
			turnCommand =new TurnCommand(playerTank,turnAngle);
		}
    }
    
    @Override
    public void execute() {
        if(!turnCommand.isFinished())
        	turnCommand.execute();
        else
        	actionFinished=true;
    }

    @Override
    public boolean isFinished() {
        return actionFinished;
    }

}