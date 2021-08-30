package com.wos.tictactoeservice.controller.response;

public class GameNotFoundException extends RuntimeException {

	public GameNotFoundException(String gameId) {
	    super("Could not find game " + gameId);
	}
	
	public GameNotFoundException() {
	    super("Could not find game");
	}
}
