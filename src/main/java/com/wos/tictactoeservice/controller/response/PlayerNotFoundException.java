package com.wos.tictactoeservice.controller.response;

public class PlayerNotFoundException extends RuntimeException {

	public PlayerNotFoundException() {
		super("Could not find player");
	}
	
	public PlayerNotFoundException(String playerId) {
	    super("Could not find player " + playerId);
	}
}
