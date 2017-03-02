package server.utility.entities.player.commands;

import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;

public class PauseCommand implements ICommand {

    private MovableObject playerTank;
    private boolean actionFinished;
    private double turnsToWait;
    private double turnsWaited;
    public PauseCommand(MovableObject playerTank, double turnsToWait) {
        this.playerTank = playerTank;
        this.turnsToWait = turnsToWait;
        this.turnsWaited = 0;
        this.actionFinished = false;
    }

    @Override
    public void execute() {
        turnsWaited++;
        if (turnsWaited > turnsToWait) {
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