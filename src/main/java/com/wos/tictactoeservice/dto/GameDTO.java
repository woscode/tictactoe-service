package com.wos.tictactoeservice.dto;

import javax.validation.constraints.NotEmpty;

import com.wos.tictactoeservice.model.Board;
import com.wos.tictactoeservice.model.Game;
import com.wos.tictactoeservice.model.GameState;
import com.wos.tictactoeservice.model.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
	
	@NotEmpty
    private String id;
    
    private GameState gameState;
    private Board board;
    
    private Player playerX;
    private Player playerO;
    
    public GameDTO() { }
    
    public GameDTO(Game gameEntity) {
    	
    	this.id = gameEntity.getId();
    	this.gameState = gameEntity.getGameState();
    	this.board = gameEntity.getBoard();
    	this.playerX = gameEntity.getPlayerX();
    	this.playerO = gameEntity.getPlayerO();
    	
    }
}
