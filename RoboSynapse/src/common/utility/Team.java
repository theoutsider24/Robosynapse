package common.utility;

import static client.utility.Constants.ARENA_WIDTH;
import static client.utility.Constants.MAP_SQUARE_SIZE;

import java.io.Serializable;


/**
 * This is the main class that is sent from the client to the server. The team's behaviour file will be attached to this object which will
 * be processed by the server.
 * 
 * @author CodexVII
 * 
 */
public class Team implements Serializable {
    private static final long serialVersionUID = 3105484380227225886L;
    private String            playerName;                             // Renamed from teamName to provide consistency
                                                                       // private Behaviour teamBehaviour; //TODO Was a class
                                                                       // CommonBehaviour, needs to be replaced with a copy of Behaviour
                                                                       // from Server
    private int               choosenSpawnZone;
    private int               port;

    public Team(String teamName) {
        this.playerName = teamName;
    }

    public Team(String teamName, int choosenSpawnZone, int port) {
        this.playerName = teamName;
        this.choosenSpawnZone = choosenSpawnZone;
        this.port = port;
    }

    public Team(String teamName, int port) {
        this.playerName = teamName;
        this.port = port;
    }

    // public Team(String teamName, Behaviour teamBehaviour, int port){
    // this.playerName = teamName;
    // this.teamBehaviour = teamBehaviour;
    // this.port = port;
    // }

    public String getTeamName() {
        return playerName;
    }

    // public Behaviour getTeamBehavior() {
    // return teamBehaviour;
    // }
    //
    // public void setTeamBehavior(Behaviour teamBehavior) {
    // this.teamBehaviour = teamBehavior;
    // }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getChoosenSpawnZone() {
        return choosenSpawnZone;
    }

    public void setChoosenSpawnZone(int chossenSpawnZone) {
        this.choosenSpawnZone = chossenSpawnZone;
    }
}
