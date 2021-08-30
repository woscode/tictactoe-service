package com.wos.tictactoeservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Embeddable
public class Board {
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Mark.class)	
	@Column (name = "board", columnDefinition = "CHAR(9)")
	private List<Mark> field;
	
	@Transient
	private static final int [][] WIN_COMBINATION = {
		{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, 
		{1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
	};
	
	public Board() {
		field = new ArrayList<>(9);
		for (short i = 0; i < 9; i++)
			field.add(Mark.E);
	}

	/**
	 * Сlear all squares on the board
	 */
	public void clear() {
		for (short i = 0; i < 9; i++)
			field.set(i, Mark.E);
	}

	/**
	 * Check if the {@code board} is full
	 * @return {@code true} if the {@code board} is full
	 */
	public boolean isFull() {
		for (short i = 0; i < 9; i++)
			if (field.get(i).isEmpty())
				return false;
		return true;
	}

	/**
	 * Check if there is a winning combination on the {@code board}
	 * @return  if there is a winning combination on the board
	 */
	public boolean isWinCombination() {
		for (short i = 0; i < WIN_COMBINATION.length; i++)
			if (!field.get(WIN_COMBINATION[i][0]).isEmpty() &&
				Objects.equals(field.get(WIN_COMBINATION[i][0]), field.get(WIN_COMBINATION[i][1])) &&
				Objects.equals(field.get(WIN_COMBINATION[i][1]), field.get(WIN_COMBINATION[i][2])))
				return true;
		return false;
	}

	/**
	 * Get all fields from the {@code board}
	 * @return playing {@code field}
	 */
	public List<Mark> get() {
		return field;
	}
	
	/**
	 * Get {@code field} by {@code index}
	 * @param i (0 {@literal <}= {@code i} {@literal <} 9)
	 * @return square by {@code i}
	 * @throws IllegalAccessException if {@code i} {@literal <} 0 or {@code i} {@literal >}= 9
	 */
	public Mark get(int i) throws IllegalAccessException{
		if (isIllegalAcces(i))
			throw new IllegalAccessException();
		return field.get(i);
	}

	/**
	 * Set a {@code mark} on a {@code field} by {@code index}
	 * @param i (0 {@literal <}= {@code i} {@literal <} 9)
	 * @param mark (X, O, EMPTY)
	 * @return {@code true} if set the {@code mark}
	 */
	public boolean set(int i, Mark mark) {
		if (isIllegalAcces(i))
			return false;
		field.set(i, mark);
		return true;
	}

	/**
	 * Set a {@code mark} on a {@code field} by {@code index} if the {@code field} is empty
	 * @param i (0 {@literal <}= {@code i} {@literal <} 9)
	 * @param mark (X, O, EMPTY)
	 * @return {@code true} if set the {@code mark}
	 */
	public boolean setIfEmpty(int i, Mark mark) {
		if (!isEmpty(i))
			return false;
		field.set(i, mark);
		return true;
	}
	
	/**
	 * Returns false if this field contains X or O .
	 * @param i
	 * @return {@code false} if this field {@code EMPTY} or i {@literal <} 0 or  {@code i} {@literal >} 8)
	 */
	public boolean isEmpty(int i) {
		return !isIllegalAcces(i) && field.get(i).isEmpty();
	}

	/**
	 * Get a random available field (marked blank)
	 * @return index of the field
	 */
	public int getRandomAvailable() {

		java.util.List<Integer> available = new java.util.ArrayList<>();

		for (int i = 0; i < 9; i++)
			if (field.get(i).isEmpty())
				available.add(i);

		if (available.isEmpty())
			return 0;

		int randomPlace = new java.util.Random().nextInt(available.size());
		return available.get(randomPlace);
	}
	
	/**
	 * Check if the index is valid
	 * @param i
	 * @return {@code true} if the index is valid
	 */
	private boolean isIllegalAcces(int i) {
		return i < 0 || i >= 9;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
	    for (short i = 0; i < 9; i++)
	    	result.append(field.get(i));
		return result.toString();
	}
}
