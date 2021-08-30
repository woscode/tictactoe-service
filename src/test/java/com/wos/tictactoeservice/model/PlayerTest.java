package com.wos.tictactoeservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	private static Player player;
	
	@BeforeAll
	static void init() {
		player = new Player();
		player.setId("0");
		player.setName("Adam");
	}
	
	@Test
	void initTest() {
		
		assertEquals("0", player.getId());
		assertEquals("Adam", player.getName());
		assertEquals(0, player.getWins());
		assertEquals(0, player.getDefeats());
		assertEquals(0, player.getDraws());
	}
}
