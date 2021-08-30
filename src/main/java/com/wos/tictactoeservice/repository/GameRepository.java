package com.wos.tictactoeservice.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wos.tictactoeservice.model.Game;
import com.wos.tictactoeservice.model.GameState;

@Repository
public interface GameRepository extends CrudRepository<Game, String> {

	Game findByGameState(GameState gameState);
	List<Game> findAllByGameState(GameState gameState);
	
}