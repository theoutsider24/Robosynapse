package server.utility.entities.player.commands;

import org.newdawn.slick.geom.Vector2f;

import server.utility.entities.MovableObject;
import server.utility.entities.player.interfaces.ICommand;

public class ForceMoveUndo implements ICommand {

	private MovableObject playerTank;
	private Vector2f moveToUndo;
	float rotateToUndo;
	public ForceMoveUndo(MovableObject playerTank, Vector2f moveToUndo) {
		this.playerTank = playerTank;
		this.moveToUndo = moveToUndo;
	}
	
	public ForceMoveUndo(MovableObject playerTank, float rotateToUndo) {
		this.playerTank = playerTank;
		this.rotateToUndo = rotateToUndo;
	}
	@Override
	public void execute() {
		if (moveToUndo != null) {
			playerTank.move(moveToUndo.negate());
		} else {
			playerTank.rotate(-rotateToUndo);
		}
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
