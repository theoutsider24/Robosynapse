package server.utility.entities.player.commands;

import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;

public class FireCommand implements ICommand {

    private MovableObject playerTank;
    
    public FireCommand(MovableObject playerTank) {
        this.playerTank = playerTank;
    }
    @Override
    public void execute() {
        playerTank.fire();
    }

    @Override
    public boolean isFinished() {
        // isFinished gets called after execute and firing only takes a tick so returning true is fine
        return true;
    }
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}