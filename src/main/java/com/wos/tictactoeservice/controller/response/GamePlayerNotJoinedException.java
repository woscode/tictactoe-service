package com.wos.tictactoeservice.controller.response;

public class GamePlayerNotJoinedException extends RuntimeException {

	public GamePlayerNotJoinedException(String gameId, String playerId) {
	    super("Player " + playerId + " did not join game " + gameId);
	}

}
