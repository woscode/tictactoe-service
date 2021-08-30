package com.wos.tictactoeservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wos.tictactoeservice.controller.response.PlayerNotFoundException;
import com.wos.tictactoeservice.dto.PlayerDTO;
import com.wos.tictactoeservice.dto.PlayerStatsDTO;
import com.wos.tictactoeservice.model.Player;
import com.wos.tictactoeservice.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public PlayerDTO newPlayer(String name) {
		
		PlayerDTO newPlayerDTO = new PlayerDTO();
		newPlayerDTO.setName(name);
		newPlayerDTO.setId(playerRepository.save(new Player(name)).getId());
		
		return newPlayerDTO;
	}
	

	@Override
	public PlayerDTO getById(String playerId) {
		
		return new PlayerDTO(getPlayerById(playerId));
	}
	
	
	@Override
	public Player getPlayerById(String playerId) {
		
		Optional<Player> playerOptional = playerRepository.findById(playerId);
		
		if (playerOptional.isEmpty())
			throw new PlayerNotFoundException(playerId);
		
		return playerOptional.get();
	}


	@Override
	public List<PlayerStatsDTO> getStatistics() {
		
		if (playerRepository.count() == 0)
			throw new PlayerNotFoundException();
		
		List<PlayerStatsDTO> playerStatsDTOs = new ArrayList<>();
		playerRepository.findAll().forEach(p -> playerStatsDTOs.add(new PlayerStatsDTO(p)));
		
		return playerStatsDTOs;
	}
}
