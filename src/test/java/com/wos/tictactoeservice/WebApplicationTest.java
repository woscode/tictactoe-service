package com.wos.tictactoeservice;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wos.tictactoeservice.dto.GameDTO;
import com.wos.tictactoeservice.dto.PlayerDTO;
import com.wos.tictactoeservice.model.GameState;
import com.wos.tictactoeservice.service.GameService;
import com.wos.tictactoeservice.service.PlayerService;

@SpringBootTest
@AutoConfigureMockMvc
class WebApplicationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private GameService gameService;
	
	private final String homePage = "http://localhost:8080/tictactoe/";

	
	@Test
	void mainPageDefaultMessage() throws Exception {
		
		this.mockMvc.perform(get(homePage)).andDo(print()).andExpect(status().isOk());
	}
	
	
	@Test
	void mainPageParamMessage() throws Exception {
		
		String playerID = "1";
		this.mockMvc.perform(get(homePage+"?playerID="+playerID)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(playerID)));
	}
	
	
	@Test
	void gameByIdResponseStatusTest() throws Exception {
		
		String gameID = "0";
		String playerID = "1";
		this.mockMvc.perform(get(homePage+gameID+"/?playerID="+playerID)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	
	@Test
	void newPlayerRegistrationTest( ) throws Exception {
		
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setName("Adam");
		
		this.mockMvc.perform(post(homePage+"registration/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerDTO).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isOk());
	}
	
	
	@Test
	void newGameTest( ) throws Exception {
		
		PlayerDTO playerDTO = registerNewPlayer("Adam");

		this.mockMvc.perform(post(homePage+"new/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerDTO).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isCreated());
	}
	
	
	@Test
	void joinPlayerToGameTest( ) throws Exception {
		
		PlayerDTO playerXdto = registerNewPlayer("Adam");	
		PlayerDTO playerOdto = registerNewPlayer("Alice");
				
		GameDTO gameDTO = createNewGame(playerXdto);
		
		/*
		 * PlayerO joined the game
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerOdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isOk());

		PlayerDTO playerOtherDto = registerNewPlayer("Someone");
		
		/*
		 * All players are already in the game
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerOtherDto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotModified());
	}
	
	@Test
	void gameStateTest() throws Exception {
		
		PlayerDTO playerXdto = registerNewPlayer("Adam");	
		PlayerDTO playerOdto = registerNewPlayer("Alice");
		PlayerDTO playerOtherDto = registerNewPlayer("Someone");
		
		GameDTO gameDTO = createNewGame(playerXdto);
		
		/*
		 * Check game state by playerX
		 * "Waiting Player O"
		 */
		this.mockMvc.perform(get(homePage+gameDTO.getId()+"/state/?playerID="+playerXdto.getId()))
					.andExpect(status().isOk());
		assertEquals(GameState.WAITING_PLAYER_O.toString(), getGameStateByPlayer(gameDTO, playerXdto));
		
		/* PlayerO joined the game */
		connectPlayerToGame(gameDTO, playerOdto);
		
		/*
		 * Check game state by playerO
		 * "In Progress"
		 */
		this.mockMvc.perform(get(homePage+gameDTO.getId()+"/state/?playerID="+playerXdto.getId()))
					.andDo(print()).andExpect(status().isOk());
		assertEquals(GameState.IN_PROGRESS.toString(), getGameStateByPlayer(gameDTO, playerOdto));
		
		/*
		 * Check game state by someone (is not acceptable)
		 */
		this.mockMvc.perform(get(homePage+gameDTO.getId()+"/state/?playerID="+playerOtherDto.getId()))
					.andDo(print()).andExpect(status().isUnauthorized());
		
		/*
		 * Check game state by someone (game does not exist)
		 */
		this.mockMvc.perform(get(homePage+"0000"+"/state/?playerID="+playerOtherDto.getId()))
					.andDo(print()).andExpect(status().isNotFound());
		
		/*
		 * Check game state by someone (player does not exist)
		 */
		this.mockMvc.perform(get(homePage+gameDTO.getId()+"/state/?playerID="+getNoneExistentPlayer().getId()))
					.andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	void moveTest( ) throws Exception {
		
		PlayerDTO playerXdto = registerNewPlayer("Adam");	
		PlayerDTO playerOdto = registerNewPlayer("Alice");
		PlayerDTO playerOtherDto = registerNewPlayer("Someone");
				
		GameDTO gameDTO = createNewGame(playerXdto);
		gameDTO = connectPlayerToGame(gameDTO, playerOdto);
		
		Integer position = 0;
		Integer unacceptablePosition = -1;
		
		/*
		 * Game doesn't exist at this ID
		 */
		this.mockMvc.perform(patch(homePage+"0000"+"/move/?position="+position).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerXdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotFound());
		
		/*
		 * Player doesn't exist at this ID
		 */
		this.mockMvc.perform(patch(homePage+"0000"+"/move/?position="+position).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(getNoneExistentPlayer()).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotFound());
		
		/*
		 * This player is not in the game
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+unacceptablePosition).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerOtherDto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotAcceptable());
		
		/*
		 * Attempt by playerX to move to a unacceptable position
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+unacceptablePosition).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerXdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotAcceptable());
		
		/*
		 * Attempt by playerO to make a move (is not acceptable)
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+position).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerOdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotAcceptable());
		
		/*
		 * PlayerX makes a move
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+position).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerXdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isOk());
		
		position = 1;
		/*
		 * PlayerO makes a move
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+position).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerOdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isOk());
		
		/*
		 * Attempt by playerX to move to a unacceptable position (is not acceptable)
		 */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+position).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerXdto).getBytes(StandardCharsets.UTF_8)))
				.andDo(print()).andExpect(status().isNotAcceptable());
	}

	
	@Test
	void gamePlayTest( ) throws Exception {
		
		PlayerDTO playerXdto = registerNewPlayer("Adam");	
		PlayerDTO playerOdto = registerNewPlayer("Alice");
				
		GameDTO gameDTO = createNewGame(playerXdto);
		
		
		
		gameDTO = connectPlayerToGame(gameDTO, playerOdto);
		Integer postition = 0;
		
		for (; postition < 6; postition += 2) {
			
			/* PlayerX makes a move */
			this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+postition).contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(playerXdto).getBytes(StandardCharsets.UTF_8)))
					.andExpect(status().isOk());
			
			/* PlayerO makes a move */
			this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+(postition+1)).contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(playerOdto).getBytes(StandardCharsets.UTF_8)))
					.andExpect(status().isOk());
		}
		
		/* PlayerX makes a move and win*/
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+postition).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerXdto).getBytes(StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
		
		/* Attempt by playerO to make a move (is not acceptable) */
		this.mockMvc.perform(patch(homePage+gameDTO.getId()+"/move/?position="+(postition+1)).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerOdto).getBytes(StandardCharsets.UTF_8)))
				.andExpect(status().isNotAcceptable());
		
		/*
		 * Check game state by playerX
		 * "Player X win"
		 */
		this.mockMvc.perform(get(homePage+gameDTO.getId()+"/state/?playerID="+playerXdto.getId()))
					.andExpect(status().isOk());
		assertEquals(GameState.PLAYER_X_WINS.toString(), getGameStateByPlayer(gameDTO, playerXdto));
		
		/*
		 * Check game state by playerO
		 * "Player X win"
		 */
		this.mockMvc.perform(get(homePage+gameDTO.getId()+"/state/?playerID="+playerOdto.getId()))
					.andExpect(status().isOk());
		assertEquals(GameState.PLAYER_X_WINS.toString(), getGameStateByPlayer(gameDTO, playerOdto));
	}
	
	
	PlayerDTO registerNewPlayer(String name) throws Exception {

//		Mockito.when(playerService.newPlayer(Mockito.anyString())).thenReturn(playerDTO);

		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setName(name);
		
	 	String responseMockMvc = this.mockMvc.perform(post(homePage+"registration/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playerDTO).getBytes(StandardCharsets.UTF_8)))
				.andReturn().getResponse().getContentAsString();
	 	
	 	return mapper.readValue(responseMockMvc, PlayerDTO.class);
	}
	
	
	GameDTO createNewGame(PlayerDTO player) throws Exception {
	
//		Mockito.when(gameService.createByPlayer(Mockito.any(PlayerDTO.class))).thenReturn(gameDTO);

		String responseGameIdMockMvc = this.mockMvc.perform(post(homePage+"new/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(player).getBytes(StandardCharsets.UTF_8)))
				.andReturn().getResponse().getContentAsString();
		
		return gameService.getById(responseGameIdMockMvc);
	}
	
	
	GameDTO connectPlayerToGame(GameDTO game, PlayerDTO player) throws Exception {
		
		String responseGameIdMockMvc = this.mockMvc.perform(patch(homePage+game.getId()+"/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(player).getBytes(StandardCharsets.UTF_8)))
				.andReturn().getResponse().getContentAsString();
		
		return gameService.getById(responseGameIdMockMvc);
	}
	
	PlayerDTO getNoneExistentPlayer() {
		
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setId("badid3d44bc44fb1a7b78df264badid"); 
		playerDTO.setName("Bob");
		
		return playerDTO;
	}
	
	String getGameStateByPlayer(GameDTO game, PlayerDTO player) throws Exception {
		
		String responseGameStateMockMvc = 
				this.mockMvc.perform(get(homePage+game.getId()+"/state/?playerID="+player.getId()))
							.andReturn().getResponse().getContentAsString();

		return responseGameStateMockMvc;
		
	}
}