package com.wos.tictactoeservice.service;

import java.util.List;

import com.wos.tictactoeservice.dto.GameDTO;
import com.wos.tictactoeservice.dto.PlayerDTO;
import com.wos.tictactoeservice.model.Game;
import com.wos.tictactoeservice.model.GameState;

public interface GameService {

	Game getGameById(String gameId);
	Game getGameByPlayer(String gameId, PlayerDTO playerDTO);
	
	GameDTO getById(String gameId);
	GameDTO getByPlayer(String gameId, PlayerDTO playerDTO);
	GameDTO createByPlayer(PlayerDTO playerDTO);
	GameDTO move(String gameId, PlayerDTO playerDTO, Integer position);
	GameDTO joinPlayer(String gameId, PlayerDTO playerDTO);
	
	List<GameDTO> getAll();
	List<GameDTO> getAllAvailable();
	GameDTO getAvailableByGameState(GameState gameState);
	
	GameDTO getAvailableByPlayer(PlayerDTO playerDTO);
}
