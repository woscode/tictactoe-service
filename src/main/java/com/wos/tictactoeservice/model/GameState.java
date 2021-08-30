package com.wos.tictactoeservice.model;

public enum GameState {
	
	UNKNOWN("Unknown"),
	WAITING_PLAYER_X("Waiting Player X"), 
	WAITING_PLAYER_O("Waiting Player O"),
	IN_PROGRESS("In Progress"),
	PLAYER_X_WINS("Player X wins"),
	PLAYER_O_WINS("Player O wins"),
	DRAW("Draw");
	
	private String state;
	
	GameState (String state) {
		this.state = state; 
	}
	
	GameState () { 
		this("Unknown");
	}
	
	@Override
	public String toString() { return state; }

	public boolean isFinished() {
		return this == PLAYER_X_WINS || this == PLAYER_O_WINS || this == DRAW;
	}
	
	public boolean isInProgress() {
		return this == IN_PROGRESS;
	}
	
	public boolean isUnknown() {
		return this == UNKNOWN;
	}
	
	public boolean isWaitingPlayerX() {
		return this == WAITING_PLAYER_X;
	}
	
	public boolean isWaitingPlayerO() {
		return this == WAITING_PLAYER_O;
	}
	
	public boolean isWaitingPlayer() {
		return this == UNKNOWN || this == WAITING_PLAYER_X || this == WAITING_PLAYER_O;
	}
	
}
