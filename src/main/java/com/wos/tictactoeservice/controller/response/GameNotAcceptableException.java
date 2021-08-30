package com.wos.tictactoeservice.controller.response;

public class GameNotAcceptableException extends RuntimeException{
	
	public GameNotAcceptableException(String gameId) {
	    super("Not Acceptable for game " + gameId);
	}
}
