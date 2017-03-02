package client.state;


import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.utility.Game;
import client.utility.UDPReceiver;

public class InGameState extends BasicGameState {

	private final String RESOURCE_DIR = "res";
	
	private boolean initialized;
	private int state;
	Game game;
	UDPReceiver receiver;

	public InGameState(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		game = new Game();
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		receiver = UDPReceiver.getReceiver();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	    gc.setAlwaysRender(true);
	    game.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int millSinceLastUpdate) throws SlickException {
		game.updateEntitiesList(receiver.getEntities());
		game.update(millSinceLastUpdate);
	}	

	@Override
	public int getID() {
		return state;
	}
}