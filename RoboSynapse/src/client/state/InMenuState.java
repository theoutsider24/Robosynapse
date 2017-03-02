
package client.state;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.RoboSynapseClient;
import client.utility.Constants;
import client.utility.FontFactory;
import client.utility.Utility;

public class InMenuState extends BasicGameState implements KeyListener {

	TrueTypeFont messageText;
	TrueTypeFont labelText;

	TrueTypeFont enterText;
	int spawn = 1;

	private static enum MENU_STATE {
		SPAWN_STATE(1), NEXT_STATE(2);

		private final int state;

		private MENU_STATE(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}
	}

	private static enum STRINGS {

		MESSAGE_SPAWN("Type in preferred spawn zone 1-8: "),
		PRESS_ENTER("Press ENTER to continue");

		private final String value;

		private STRINGS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private int state;
	private int currentState;

	public InMenuState(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		currentState = MENU_STATE.SPAWN_STATE.getState();
		messageText = FontFactory.getResource(Constants.COMIC, Constants.EXTRA_SMALL);
		enterText = FontFactory.getResource(Constants.COMIC, Constants.EXTRA_SMALL);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx) throws SlickException {
		if (currentState == MENU_STATE.SPAWN_STATE.getState()) {
			drawSpawnPrompt();
		} else if(currentState == MENU_STATE.NEXT_STATE.getState()) {
			sbg.enterState(ClientState.GAME.getValue());
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	}

	@Override
	public int getID() {
		return state;
	}

	@Override
	public  void keyPressed(int intKey, char charKey) {
		if (currentState == MENU_STATE.SPAWN_STATE.getState()) {
			if (intKey == Keyboard.KEY_RETURN) {
				if (spawn>0) {
				    RoboSynapseClient.startBroadcaster();
					currentState = MENU_STATE.NEXT_STATE.getState();
				}
			} else if (Utility.isInCharRange(charKey, '1', '8')) {
				spawn=Character.getNumericValue(charKey);

				ClientInfo.getInstance().setSpawn(spawn-1);
			}
		}
	}

	private void drawSpawnPrompt() {
		messageText.drawString(10, 10, STRINGS.MESSAGE_SPAWN.getValue() + spawn);
		enterText.drawString(20,50,STRINGS.PRESS_ENTER.getValue());
	}
}