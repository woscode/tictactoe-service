package com.wos.tictactoeservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "players")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Player {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;
    
    @NotEmpty
    @Size(min = 3, max = 32)
    @Column
    private String name;
    
    @Column(columnDefinition = "integer default 0") 
    @PositiveOrZero
    private Integer wins = 0;
    
    @Column(columnDefinition = "integer default 0") 
    @PositiveOrZero
    private Integer defeats = 0;
    
    @Column(columnDefinition = "integer default 0") 
    @PositiveOrZero
    private Integer draws = 0;
    
    public Player() {}
    
    public Player(String name) { 
    	this.name = name; 
    }
	
    /**
     * Reset the number of points (wins,defeats,draws)
     */
    public void resetStatistics() {
        this.wins = 0;
        this.defeats = 0;
        this.draws = 0;
    }
    	
    public void win() {
    	this.wins++;
    }
    
    public void defeat() {
		this.defeats++;
	}
    
    public void draw() {
		this.draws++;
	}
}
