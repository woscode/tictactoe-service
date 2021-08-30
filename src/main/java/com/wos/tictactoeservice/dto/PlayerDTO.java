package com.wos.tictactoeservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.wos.tictactoeservice.model.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
	
    private String id;
	
	@NotEmpty
	@Size (min = 3, max = 32, message = "Name length must be between 3 and 32")
    private String name;
    
	
    public PlayerDTO() { }
     
    
    public PlayerDTO(Player playerEntity) {
    	
    	this.id = playerEntity.getId(); 
    	this.name = playerEntity.getId();
    }
}
