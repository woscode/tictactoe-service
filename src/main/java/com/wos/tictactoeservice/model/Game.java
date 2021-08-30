package com.wos.tictactoeservice.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game {

	@Transient
	@Getter (AccessLevel.NONE)
    final Logger logger = LoggerFactory.getLogger(Game.class);

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column (length = 16)
    private GameState gameState = GameState.UNKNOWN;
   
    @Enumerated(EnumType.STRING)
    @Column (length = 1)
    private Mark whoMove = Mark.X;
    
    @Embedded
    private Board board = new Board();

    @ManyToOne
    @JoinColumn(name = "player_X_id")
    private Player playerX;
    
    @ManyToOne
    @JoinColumn(name = "player_O_id")
    private Player playerO;  
    
    /**
    * Сheck if the move is acceptable
    * @param position [0,...9)
    * @param player
    */
    public boolean checkMove(int position, Player player) {
    	
    	if (gameState.isInProgress() 
    		&& (whoMove.isMoveX() && Objects.equals(playerX, player) || whoMove.isMoveO() && Objects.equals(playerO, player))
    		&& board.isEmpty(position) )
		    	return true;

    	logger.info("Cannot move on this place");
    	return false ;
    }
    
    /**
     * <p> Make a move to {@code position} by {@code player} </p>
     * @param position [0,...9)
     * @param player
     * @return {@code true} if the move is made
     */
    public boolean move(int position, Player player) {

    	if (!checkMove(position, player)) 
    		return false;
		
        logger.info("Player move on {}", position);
    	
    	board.set(position, whoMove.isMoveX() ? Mark.X : Mark.O);
        
        if (board.isWinCombination()) {
        	
            if (whoMove.isMoveX()) {
            	logger.info("Player {} win", Mark.X);
            	
            	playerX.win();
            	playerO.defeat();
            	setGameState(GameState.PLAYER_X_WINS);
            } else {
            	logger.info("Player {} win", Mark.O);
            	
            	playerO.win();
            	playerX.defeat();
                setGameState(GameState.PLAYER_O_WINS);
            }

        } else if (board.isFull()) {
            logger.info("Draw");
            
            playerX.draw();
            playerO.draw();
            setGameState(GameState.DRAW);
        }
      
        setWhoMove(whoMove.isMoveX() ? Mark.O : Mark.X);
        
    	return true;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return "Game[id=" + getId() + ", state=" + getGameState() + "]";
    }
}
