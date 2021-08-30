package com.wos.tictactoeservice.controller.response;

public class PlayerUnauthorizedException extends RuntimeException {

   public PlayerUnauthorizedException(String playerId) {
        super("Player " + playerId + "is not authorized ");
    }
}
