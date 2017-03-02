package server.utility.entities.player.commands;


import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;

public class TurnCommand implements ICommand { 
    private MovableObject playerTank;
    private boolean actionFinished;
    private float degreesToTurn;
    private float degreesTurned;
    private boolean reverse;
    
    public TurnCommand(MovableObject playerTank, float degreesToTurn) {
        this.playerTank = playerTank;
        this.degreesToTurn = degreesToTurn;
        this.degreesTurned = (float) 0;
        this.actionFinished = false;
        if (this.degreesToTurn < 0) {
        	reverse = true;
        } else {
        	reverse = false;
        }
        if(this.degreesToTurn==0)
        {
        	actionFinished=true;
        }
    }
   
    @Override
    public void execute() {
    	if(!actionFinished)
    	{
    		float rotationLeft = degreesToTurn-degreesTurned;
    		float desiredGrip = (float) (playerTank.getGrip()>Math.abs(rotationLeft)? rotationLeft:playerTank.getGrip());
	    	if ( reverse) {
	    		playerTank.rotate(-desiredGrip);
	    		playerTank.setUndo(new ForceMoveUndo(playerTank, -desiredGrip));
	    	} else {
	    		playerTank.rotate(desiredGrip); // Tank tyre type should effect turning
	    		playerTank.setUndo(new ForceMoveUndo(playerTank, desiredGrip));
	    	}
    	}
        degreesTurned += playerTank.getGrip();
        if (Math.abs(degreesToTurn) <= degreesTurned) {
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
