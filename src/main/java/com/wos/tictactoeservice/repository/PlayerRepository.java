package com.wos.tictactoeservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wos.tictactoeservice.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String> {

}