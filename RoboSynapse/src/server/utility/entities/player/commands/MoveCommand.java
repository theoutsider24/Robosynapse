package server.utility.entities.player.commands;

import org.newdawn.slick.geom.Vector2f;

import server.utility.common.Constants;
import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;

public class MoveCommand implements ICommand {
	private MovableObject playerTank;
	private boolean actionFinished;
	private double distanceToTravel;
	private double distanceTravelled;
	private boolean reverse;
    
    public MoveCommand(MovableObject playerTank, Double distanceToTravel) {
        this.playerTank = playerTank;
        this.distanceToTravel = distanceToTravel;
        this.distanceTravelled = (double) 0;
        this.actionFinished = false;
        if (this.distanceToTravel < 0) {
        	reverse = true;
        } else {
        	reverse = false;
        }
    }
    public MoveCommand(MoveCommand c1,MoveCommand c2) {    	
		this(c1.playerTank==c2.playerTank?c1.playerTank:null,c1.distanceToTravel+c2.distanceToTravel);        
    }
    
    @Override
    public void execute() {
        // move teamTank in a straight line      
        float rotationInRadians = (float) Math.toRadians(playerTank.getRotation());
        double distanceLeft = distanceToTravel-distanceTravelled;
        double desiredSpeed = playerTank.getSpeed()>distanceLeft? distanceLeft:playerTank.getSpeed();
        
        Vector2f rotationAsVector = new Vector2f((float)(desiredSpeed * Math.sin(rotationInRadians)),
                (float)(desiredSpeed * -Math.cos(rotationInRadians)));
        double moveDist = Constants.getDistance(new Vector2f(0,0), rotationAsVector);
        if (reverse) {
        	playerTank.move(rotationAsVector.negate());
        	playerTank.setUndo(new ForceMoveUndo(playerTank, rotationAsVector.negate()));
        } else {
        	playerTank.move(rotationAsVector);
        	playerTank.setUndo(new ForceMoveUndo(playerTank, rotationAsVector));
        }
        distanceTravelled += moveDist;       
        if (Math.abs(distanceToTravel) <= distanceTravelled) {
              actionFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return actionFinished;
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
