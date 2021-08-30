package com.wos.tictactoeservice.model;

public enum Mark {
	
	E (" "),
	X ("x"),
	O ("o");
	
	private String symbol;
	
	Mark (String symbol) {
		this.symbol = symbol; 
	}
	
	Mark () { 
		this(" ");
	}
	
	public boolean isEmpty() {
		return this == E;
	}
	
	public boolean isMoveX() {
		return this == X;
	}
	
	public boolean isMoveO() {
		return this == O;
	}
	
	@Override
	public String toString() { return symbol; }
}
