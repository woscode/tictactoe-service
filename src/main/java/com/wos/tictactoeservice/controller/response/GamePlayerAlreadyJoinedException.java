package com.wos.tictactoeservice.controller.response;

public class GamePlayerAlreadyJoinedException extends RuntimeException {

	public GamePlayerAlreadyJoinedException(String gameId, String playerId) {
	    super("Player " + playerId + " did not join game " + gameId);
	}
}