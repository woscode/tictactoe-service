package com.wos.tictactoeservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wos.tictactoeservice.dto.GameDTO;
import com.wos.tictactoeservice.dto.PlayerDTO;
import com.wos.tictactoeservice.dto.PlayerStatsDTO;
import com.wos.tictactoeservice.service.GameService;
import com.wos.tictactoeservice.service.PlayerService;


@RestController
@RequestMapping("/tictactoe")
public class GameController {
	
	private final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	

	@GetMapping()
	public String mainPage(@RequestParam(required = false) String playerID) {
		
		logger.info("GET Main Page");
		
		return playerID;
	}
	
	
	@PostMapping("/registration")
	public PlayerDTO registration(@RequestBody(required = true) @Valid PlayerDTO player) {
		
		logger.info("New Player Registration");
		
		PlayerDTO newPlayer = playerService.newPlayer(player.getName());
		
		logger.info("New Player Registered: name = {}, id = {}", newPlayer.getName(), newPlayer.getId());
		
		return newPlayer;
	}
	

	@GetMapping("/{id}")
	public String play(@PathVariable("id") String gameID, @RequestBody(required = true) String playerID) {
		
		logger.info("GET game {} by player {}", gameID, playerID);
		
		PlayerDTO player = playerService.getById(playerID);
		
		return gameService.getByPlayer(gameID, player).getId();
	}
	
	
	@PatchMapping("/{id}")
	public String connectPlayerToGame(@PathVariable("id") String gameID, 
									  @RequestBody(required = true) PlayerDTO player) {
		
		logger.info("Attempt by player {} to join the game {}", player.getId(), gameID);
		
		GameDTO gameDTO = gameService.joinPlayer(gameID, player);
		
		logger.info("Player {} joined the game {}", player.getId(), gameID);
		
		return gameDTO.getId();
	}
	
	
	@PatchMapping("/{id}/move")
	public String move(@PathVariable("id") String gameID, 
					   @RequestParam(required = true) Integer position, 
					   @RequestBody(required = true) PlayerDTO player) {
		
		GameDTO gameDTO = gameService.move(gameID, player, position);
				
		logger.info("Player {} moves to position {} in game {}", player.getId(), position, gameID);
		
		return gameDTO.getId();
	}
	

	@PostMapping("/new")
	@ResponseStatus(HttpStatus.CREATED)
	public String newGame(@RequestBody(required = true) PlayerDTO player) {
		
		logger.info("Create new game");
		
		GameDTO gameDTO = gameService.createByPlayer(player);	
		
		logger.info("Player {} create new game {}", player.getId(), gameDTO.getId());
		
		return gameDTO.getId();
	}
	

	@GetMapping("/find")
	public GameDTO findAvailableGame(@RequestParam(required = true) String playerID) {
		
		logger.info("Find available game for player {}", playerID);
		
		PlayerDTO player = playerService.getById(playerID);
		
		return gameService.getAvailableByPlayer(player);
	}
	

	@GetMapping("/{id}/board")
	public String board(@PathVariable("id") String gameID) {
		
		logger.info("Get a board from game {}", gameID);
		
		return gameService.getById(gameID).getBoard().toString();
	}
	

	@GetMapping("/{id}/state")
	public String gameState(@PathVariable("id") String gameID, @RequestParam(required = true) String playerID) {

		logger.info("Get state of the game {} by player {}", gameID, playerID);
		
		PlayerDTO player = playerService.getById(playerID);
		
		return gameService.getByPlayer(gameID, player).getGameState().toString();
	}
	
	
	@GetMapping("/game/statistics")
	public List<GameDTO> getGameStatistics() {
		
		logger.info("Get game statistics");
		
		return gameService.getAll();
	}
	
	
	@GetMapping("/player/statistics")
	public List<PlayerStatsDTO> getPlayerStatistics() {
		
		logger.info("Get player statistics");
		
		return playerService.getStatistics();
	}
}
