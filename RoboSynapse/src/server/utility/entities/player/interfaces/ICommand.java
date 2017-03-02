package server.utility.entities.player.interfaces;

public interface ICommand {
	public void init();
	
    public void execute();
    
    public boolean isFinished();
}