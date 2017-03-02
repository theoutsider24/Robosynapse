package client.state;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.utility.Constants;
import client.utility.FontFactory;
import client.utility.Utility;

public class InLoginState extends BasicGameState implements KeyListener {

	TrueTypeFont messageText;
	TrueTypeFont labelText;
	String username = "";
	String password = "";

	private int state;
	private int currentState;

	private static enum LOGIN_STATE {
		TEAM_NAME(1), 
		PASSWORD(2), 
		NEXT_STATE(3);

		private final int state;

		private LOGIN_STATE(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}
	}

	private static enum STRINGS {
		TEAM_NAME("Team name is: "), 
		PASSWORD("Password is: "), 
		MESSAGE_NAME("Type in your team name and hit enter"),
		MESSAGE_PASSWORD("Type in your password and hit enter");
		
		private final String value;

		private STRINGS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public InLoginState(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame gs) throws SlickException {
		currentState = LOGIN_STATE.TEAM_NAME.getState();
		messageText = FontFactory.getResource(Constants.COMIC, Constants.EXTRA_SMALL);
		labelText = FontFactory.getResource(Constants.COMIC, Constants.SMALL);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx) throws SlickException {
		if (currentState == LOGIN_STATE.TEAM_NAME.getState()) {
			drawTeamPrompt();
		} else if(currentState == LOGIN_STATE.PASSWORD.getState()) {
			drawPassPrompt();
		} else if (currentState == LOGIN_STATE.NEXT_STATE.getState()) {
			sbg.enterState(ClientState.MENU.getValue());
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
		if (currentState == LOGIN_STATE.TEAM_NAME.getState()) {
			if(intKey == Keyboard.KEY_BACK) {
				username = Utility.deleteLastChar(username);
			} else if (intKey == Keyboard.KEY_RETURN) {
				if (!username.isEmpty()) {
					currentState = LOGIN_STATE.PASSWORD.getState();
				}
			} else if (Utility.isAlpha(charKey)) {
				username+=charKey;
			}
		} else if(currentState == LOGIN_STATE.PASSWORD.getState()) {
				if(intKey == Keyboard.KEY_BACK) {
					password = Utility.deleteLastChar(password);
				} else if (intKey == Keyboard.KEY_RETURN) {
					if(!username.isEmpty()) {
						currentState = LOGIN_STATE.NEXT_STATE.getState();
					}
				} else if (Utility.isAlpha(charKey)) {
					password+=charKey;
				}
			}
		}

	private void drawTeamPrompt() {
		messageText.drawString(10, 10, STRINGS.MESSAGE_NAME.getValue());
		labelText.drawString(50, 250, STRINGS.TEAM_NAME.getValue() + username, Color.red);
	}

	private void drawPassPrompt() {
		messageText.drawString(10, 10, STRINGS.MESSAGE_PASSWORD.getValue());
		labelText.drawString(50, 250, STRINGS.PASSWORD.getValue() + Utility.toPassString(password), Color.red);
	}
}