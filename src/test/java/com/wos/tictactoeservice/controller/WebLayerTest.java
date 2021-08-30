package com.wos.tictactoeservice.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


/*
 * Doesn't work: GameController required a bean of type GameService
 */
@WebMvcTest (controllers = GameController.class)
class WebLayerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private final String homePage = "http://localhost:8080/tictactoe/";

	@Test
	void mainPageDefaultMessage() throws Exception {
		this.mockMvc.perform(get(homePage)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	void mainPageParamMessage() throws Exception {
		this.mockMvc.perform(get(homePage)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("123")));
	}
}