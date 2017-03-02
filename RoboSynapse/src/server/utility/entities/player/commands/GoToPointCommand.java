package server.utility.entities.player.commands;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.geom.Vector2f;

import server.utility.common.CommonFunctions;
import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;
import server.utility.map.pathfinder.AStarPathFinder;

public class GoToPointCommand implements ICommand {

    private MovableObject playerTank;
    private boolean actionFinished;
    private double turnsToWait;
    private double turnsWaited;
    private Vector2f point;
    public ArrayList<ICommand>   actions;
    
    
    public GoToPointCommand(MovableObject tank, Vector2f v) {
        this.playerTank = tank;
        this.actionFinished = false;
        this.point=v;
    }
    @Override
    public void init()
    {
    	actions = new ArrayList<ICommand>();
    	ArrayList<Vector2f> path = AStarPathFinder.getPath((int) playerTank.getPosition().x, (int) playerTank.getPosition().y,
				(int) point.x, (int) point.y);
		Collections.reverse(path);
		//if (CommonFunctions.getDistSqr(player.getPosition(), path.get(0)) < 5)
			path.remove(0);
		Vector2f origin = playerTank.getPosition();
		float originAngle = playerTank.getRotation();
		for (Vector2f p : path) {
			actions.add(new TurnToFaceCommand(playerTank,p));
			actions.add(new MoveCommand(playerTank,Math.sqrt(CommonFunctions.getDistSqr(origin, p))));
			origin = p;
		}
		for(int i=0;i<actions.size()-1;i++)
		{
			if(actions.get(i).getClass().equals(MoveCommand.class)&&actions.get(i+1).getClass().equals(MoveCommand.class))
			{
				MoveCommand temp = new MoveCommand((MoveCommand)actions.get(i), (MoveCommand)actions.get(i+1));
				actions.remove(i+1);
				actions.set(i, temp);
			}
			else if(actions.get(i).getClass().equals(TurnCommand.class)&&actions.get(i+1).getClass().equals(MoveCommand.class))
			{
				//MoveCommand temp = new MoveCommand((MoveCommand)actions.get(i), (MoveCommand)actions.get(i+1));
				//actions.remove(i+1);
				//actions.set(i, temp);
				System.out.println("turn");
			}
		}
			
    }
    private ICommand lastActionExecuted;
    @Override
    public void execute() {
        if(actions.size()>0)
    	{
        	if(actions.get(0)!=lastActionExecuted)
    		{
    			lastActionExecuted= actions.get(0); 
    			actions.get(0).init();
    		}
        	
	        actions.get(0).execute();
	        if(actions.get(0).isFinished()) {
	            actions.remove(0);
	        }
    	}
        else
        	 actionFinished = true;
    }

    @Override
    public boolean isFinished() {
        return actionFinished;
    }

}