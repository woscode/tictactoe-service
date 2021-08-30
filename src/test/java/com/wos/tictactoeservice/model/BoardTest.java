package com.wos.tictactoeservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	private static Board board;
	
	@BeforeAll
	static void init() {
		board = new Board();
	}
	
	@Test
	void initTest() {
		board = new Board();
		assertTrue(board.get().containsAll(Collections.nCopies(9, Mark.E)));
	}
	
	@Test
	void toStringTest() {
		
		board.clear();
		assertEquals(' ', board.toString().charAt(0));
		
		board.set(0, Mark.X);
		assertEquals('x', board.toString().charAt(0));
		
		board.set(0, Mark.O);
		assertEquals('o', board.toString().charAt(0));
		
		board.set(8, Mark.X);
		assertEquals('x', board.toString().charAt(8));
	}
	
	@Test
	void fillTest() {
		
		board.clear();
		assertTrue(board.get().containsAll(Collections.nCopies(9, Mark.E)));
		assertFalse(board.isFull());
		
		for (short i = 0; i < 9; i++)
			board.set(i, Mark.X);
		assertTrue(board.get().containsAll(Collections.nCopies(9, Mark.X)));
		assertTrue(board.isFull());
		
		for (short i = 0; i < 9; i++)
			board.set(i, Mark.O);
		assertTrue(board.get().containsAll(Collections.nCopies(9, Mark.O)));
		assertTrue(board.isFull());
	}
	
	@Test
	void setTest() {
		
		board.clear();
		assertTrue(board.set(0, Mark.X));
		assertTrue(board.set(8, Mark.O));
		
		assertFalse(board.set(-1, Mark.X));
		assertFalse(board.set(9, Mark.O));
		
		assertTrue(board.setIfEmpty(1, Mark.X));
		assertTrue(board.setIfEmpty(7, Mark.O));
		
		assertFalse(board.setIfEmpty(1, Mark.X));
		assertFalse(board.setIfEmpty(7, Mark.O));
		
		assertFalse(board.setIfEmpty(-1, Mark.X));
		assertFalse(board.setIfEmpty(9, Mark.O));
	}
}
