package com.wos.tictactoeservice.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GameTest {
	
	private static Game game;
	private static Player playerX;
	private static Player playerO;
	
	@BeforeAll
	static void init() {
		
		game = new Game();
		
		playerX = new Player("Adam");
		playerO = new Player("Alice");
		
		playerX.setId("0");
		playerO.setId("1");
		
		game.setPlayerX(playerX);
		game.setPlayerO(playerO);
		
		game.setGameState(GameState.IN_PROGRESS);
	}

	@Test
	void initTest() {
		
		assertNotNull(game);
		
		assertNotNull(game.getPlayerX());
		assertNotNull(game.getPlayerO());
	}
	
	@Test
	void moveTest() {
		
		assertFalse(game.checkMove(-1, playerX));
		assertFalse(game.checkMove(9, playerX));
		
		assertTrue(game.move(0, playerX));
		
		assertFalse(game.checkMove(0, playerX));
		assertFalse(game.checkMove(0, playerO));
		
		assertTrue(game.move(8, playerO));
		assertFalse(game.checkMove(8, playerX));
		assertFalse(game.checkMove(8, playerO));
		
		game.setGameState(GameState.WAITING_PLAYER_X);
		
		assertFalse(game.checkMove(1, playerX));
		assertFalse(game.checkMove(1, playerO));
		
		game.setGameState(GameState.WAITING_PLAYER_O);
		
		assertFalse(game.checkMove(1, playerX));
		assertFalse(game.checkMove(1, playerO));
	}
	
	@Test
	void moveTest2() {
		
		Player otherPlayer = new Player();
		
		assertFalse(game.checkMove(4, otherPlayer));
		assertFalse(game.checkMove(-1, otherPlayer));
		assertFalse(game.checkMove(9, otherPlayer));
	}
}
