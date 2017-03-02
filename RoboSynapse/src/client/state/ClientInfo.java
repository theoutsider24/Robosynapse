package client.state;

public class ClientInfo {

	private static ClientInfo info;
	private String teamName;
	private String teamPassword;
	private int spawn;
	
	public static synchronized ClientInfo getInstance() {
		if (info == null) {
			info = new ClientInfo();
		}
		return info;
	}
	
	private ClientInfo() {
		
	}
	
	protected String getTeamName() {
		return teamName;
	}
	
	protected String getTeamPassword() {
		return teamPassword;
	}
	
	protected int getSpawn() {
		return spawn;
	}
	
	protected void setSpawn(int spawn) {
		this.spawn = spawn;
	}

	protected void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	protected void setTeamPassword(String teamPassword) {
		this.teamPassword = teamPassword;
	}
}
