package client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.state.ClientState;
import client.state.InGameOverState;
import client.state.InGameState;
import client.state.InLoadGameState;
import client.state.InLoginState;
import client.state.InMenuState;
import client.utility.Constants;
import client.utility.TCPBroadcaster;
import client.utility.UDPReceiver;

import common.utility.Team;

public class RoboSynapseClient extends StateBasedGame {

    private static String         GAME_TITLE = "RoboSynapse";
    private static int            listeningPort;
    private static Team           team;
    private static TCPBroadcaster broadcaster;
    private static UDPReceiver    receiver;

    public RoboSynapseClient() {
        super(GAME_TITLE);
        this.addState(new InLoginState(ClientState.LOGIN.getValue()));
        this.addState(new InMenuState(ClientState.MENU.getValue()));
        this.addState(new InGameState(ClientState.GAME.getValue()));
        this.addState(new InGameOverState(ClientState.GAME_OVER.getValue()));
        this.addState(new InLoadGameState(ClientState.LOAD_GAME.getValue()));
        // network setup
        listeningPort = Constants.SERVER_LISTENING_PORT;
        receiver = UDPReceiver.getReceiver();
        receiver.start();
        broadcaster = TCPBroadcaster.getBroadcaster();

    }

    public static void main(String[] args) throws InterruptedException {
        AppGameContainer appGameContainer;
        try {
            appGameContainer = new AppGameContainer(new RoboSynapseClient());
            appGameContainer.setDisplayMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, false);
            appGameContainer.setVSync(true);
            appGameContainer.setTargetFrameRate(60);
            appGameContainer.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        // Add all required states this.addState(new InMenuState(ClientState.MENU.getValue()));

        this.getState(ClientState.LOGIN.getValue()).init(gc, this);
        this.getState(ClientState.MENU.getValue()).init(gc, this);
        this.getState(ClientState.GAME.getValue()).init(gc, this);
        this.getState(ClientState.GAME_OVER.getValue()).init(gc, this);
        this.getState(ClientState.LOAD_GAME.getValue()).init(gc, this);
    }

    private static void exit(GameContainer gc) {
        broadcaster.terminate();
        receiver.disconnect();
        gc.exit();
    }

    public static void startBroadcaster() {
        broadcaster.start();
    }
    
    public static Team getTeam() {
    	return team;
    }
}
