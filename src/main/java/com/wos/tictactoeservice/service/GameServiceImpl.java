package com.wos.tictactoeservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wos.tictactoeservice.controller.response.GameNotAcceptableException;
import com.wos.tictactoeservice.controller.response.GameNotFoundException;
import com.wos.tictactoeservice.controller.response.GamePlayerAlreadyJoinedException;
import com.wos.tictactoeservice.controller.response.GamePlayerNotJoinedException;
import com.wos.tictactoeservice.controller.response.PlayerUnauthorizedException;
import com.wos.tictactoeservice.dto.GameDTO;
import com.wos.tictactoeservice.dto.PlayerDTO;
import com.wos.tictactoeservice.model.Game;
import com.wos.tictactoeservice.model.GameState;
import com.wos.tictactoeservice.model.Player;
import com.wos.tictactoeservice.repository.GameRepository;


@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerService playerService;
	
	
	@Override
	public Game getGameById(String gameId) {
		
		Optional<Game> gameOptional = gameRepository.findById(gameId);
		
		if (gameOptional.isEmpty())
			throw new GameNotFoundException(gameId);
		
		return gameOptional.get();
	}
	
	
	@Override
	public GameDTO getById(String gameId) {
		
		return new GameDTO(getGameById(gameId));
	}
	
	@Override
	public Game getGameByPlayer(String gameId, PlayerDTO playerDTO) {
		
		Game game = getGameById(gameId);
		Player player = playerService.getPlayerById(playerDTO.getId());
		
		if ( !(Objects.equals(game.getPlayerX(), player) || Objects.equals(game.getPlayerO(), player)) )
			throw new PlayerUnauthorizedException(playerDTO.getId());
		
		return game;
	}
	
	
	@Override
	public GameDTO getByPlayer(String gameId, PlayerDTO playerDTO) {
		
		return new GameDTO(getGameByPlayer(gameId, playerDTO));
	}

	
	@Override
	public GameDTO move(String gameId, PlayerDTO playerDTO, Integer position) {
		
		Game game = getGameById(gameId);
		Player player = playerService.getPlayerById(playerDTO.getId());
		
		if (game.move(position, player))
			gameRepository.save(game);
		else
			throw new GameNotAcceptableException(gameId);
		
		return new GameDTO(game);
	}

	
	@Override
	public GameDTO joinPlayer(String gameId, PlayerDTO playerDTO) {
		
		Game game = getGameById(gameId);
		Player player = playerService.getPlayerById(playerDTO.getId());
		
		if (!game.getGameState().isWaitingPlayer())
			throw new GamePlayerNotJoinedException(gameId, player.getId());
		
		if (Objects.equals(game.getPlayerX(), player) || Objects.equals(game.getPlayerO(), player) )
			throw new GamePlayerAlreadyJoinedException(gameId, player.getId());
		
		if (game.getGameState().isUnknown()) {
			
			game.setPlayerX(player);
			game.setGameState(GameState.WAITING_PLAYER_O);
		}
		else if (game.getGameState().isWaitingPlayerO()) {	
			
			game.setPlayerO(player);
			game.setGameState(GameState.IN_PROGRESS);
		}
		else {
			game.setPlayerX(player);	
			game.setGameState(GameState.IN_PROGRESS);
		}
		
		gameRepository.save(game);

		return new GameDTO(game);
	}

	
	@Override
	public GameDTO createByPlayer(PlayerDTO playerDTO) {
		
		Game newGame = new Game();
		Player player = playerService.getPlayerById(playerDTO.getId());
		
		newGame.setPlayerX(player);
		newGame.setGameState(GameState.WAITING_PLAYER_O);
		
		gameRepository.save(newGame);
		
		return new GameDTO(newGame);
	}

	
	@Override
	public List<GameDTO> getAll() {
		
		if (gameRepository.count() == 0)
			throw new GameNotFoundException();
		
		List<GameDTO> gameDTOs = new ArrayList<>();
		gameRepository.findAll().forEach(g -> gameDTOs.add(new GameDTO(g)));
		
		return gameDTOs;
	}

	
	@Override
	public List<GameDTO> getAllAvailable() {

		List<GameDTO> gameDTOs = new ArrayList<>();
		
		gameRepository.findAllByGameState(GameState.WAITING_PLAYER_O).forEach(g -> gameDTOs.add(new GameDTO(g)));
		gameRepository.findAllByGameState(GameState.WAITING_PLAYER_X).forEach(g -> gameDTOs.add(new GameDTO(g)));
		
		return gameDTOs;
	}

	
	@Override
	public GameDTO getAvailableByGameState(GameState gameState) {

		return new GameDTO(gameRepository.findByGameState(gameState));
	}

	
	@Override
	public GameDTO getAvailableByPlayer(PlayerDTO playerDTO) {
		
		return getAvailableByGameState(GameState.WAITING_PLAYER_O);
	}
}
