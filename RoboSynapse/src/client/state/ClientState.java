package client.state;

public enum ClientState {
	
	LOGIN		(1), 
	MENU		(2), 
	LOAD_GAME	(3), 
	GAME		(4), 
	GAME_OVER	(5);

	private final int value;

	private ClientState(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
