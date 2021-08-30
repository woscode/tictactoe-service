package com.wos.tictactoeservice.service;

import java.util.List;

import com.wos.tictactoeservice.dto.PlayerDTO;
import com.wos.tictactoeservice.dto.PlayerStatsDTO;
import com.wos.tictactoeservice.model.Player;


public interface PlayerService {
	
	Player getPlayerById(String playerId);
	
	PlayerDTO getById(String playerId);
    PlayerDTO newPlayer(String name);
    
    List<PlayerStatsDTO> getStatistics();
}
