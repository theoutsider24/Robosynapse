package server.utility.entities.npc;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import server.utility.common.Game;
import server.utility.entities.player.Player;
import server.utility.entities.player.PlayerManager;
import server.utility.events.ProjectileFiredEventListener;

//This class will be responsible for moving the 
public class SecuritySystem {
    private int bot_count = 0;
    private ProjectileFiredEventListener listener;
    
    public SecuritySystem(ProjectileFiredEventListener listener) {
        this.listener = listener;
        init();        
    }
    
    private void init() {
        //addSecurityBot(new Vector2f(0, 0), 80f);
        addSecurityBot(new Vector2f(600, 300), 270f);
	}  
    
    public void addSecurityBot(Vector2f position, float rotation)
    {
    	Game.getInstance().getPlayerManager().addPlayer(new SecurityBot("blankEnemy", position, rotation, 2, 2, 100,150));
    }
}
