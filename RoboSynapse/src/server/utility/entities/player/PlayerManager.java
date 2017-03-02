package server.utility.entities.player;

import java.util.ArrayList;
import java.util.TreeMap;

import common.utility.Entity;
import server.utility.common.Game;
import server.utility.events.eventManagers.EventManager;

public class PlayerManager {
	public TreeMap<String,Player> players = new TreeMap<String,Player>();
	public ArrayList<Player> queuedPlayers = new ArrayList<Player>();
	public ArrayList<String> killQueue = new ArrayList<String>();
	
	public void addPlayer(String playerName,String botName)
	{
		addPlayer(new Player(playerName, botName));
	}
	
	public void addPlayer(Player p)
	{
		queuedPlayers.add(p);
	}
	private void addQueuedPlayers()
	{
		for(Player p:queuedPlayers)
		{
			if(!players.containsKey(p.getName()))
			{
				players.put(p.getName(),p);
				p.RegisterProjectileFiredEventListener(Game.getInstance());
				EventManager.fireOnSpawnEvent(p);
			}
		}
		queuedPlayers.clear();
	}
	public void updatePlayers()
	{
		addQueuedPlayers();
		killQueuedPlayers();
		for(String s:players.keySet())
		{
			players.get(s).update();
		}
	}
	
	public void killPlayer(String name)
	{
		if(players.containsKey(name))
			killQueue.add(name);
	}
	private void killQueuedPlayers()
	{
		for(String p:killQueue)
		{
			players.get(p).kill();
			players.remove(p);
		}
		killQueue.clear();
	}
	
	public ArrayList<Entity> getEntities()
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for(String s:players.keySet())
		{
			entities.add(players.get(s).toEntity());
		}
		return entities;
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		for(String s:players.keySet())
		{
			allPlayers.add(players.get(s));
		}
		return allPlayers;
	}
	
	public String getAllPlayerData()
	{
		String result = "--ALL PLAYERS--\n";
		ArrayList<Player> players = getAllPlayers();
		for(Player p : players)
		{
			result += p.getName() + "\t" + p.getPosition().x + "\t" + p.getPosition().y + "\t" + p.getRotation() + "\n";   
		}
		return result;
	}
}
