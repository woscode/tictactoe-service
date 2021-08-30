package com.wos.tictactoeservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.wos.tictactoeservice.model.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatsDTO {
	
	@NotEmpty
    private String id;
	
	@Size (min = 3, max = 32, message = "Name length must be between 3 and 32")
    private String name;
	
	@PositiveOrZero
    private Integer wins = 0;
    
    @PositiveOrZero
    private Integer defeats = 0;
    
    @PositiveOrZero
    private Integer draws = 0;
    
    public PlayerStatsDTO() { }
    
    public PlayerStatsDTO(Player playerEntity) {
    	
    	this.id = playerEntity.getId(); 
    	this.name = playerEntity.getId();
    	this.wins = playerEntity.getWins();
    	this.defeats = playerEntity.getDefeats();
    	this.draws = playerEntity.getDraws();
    }
}
