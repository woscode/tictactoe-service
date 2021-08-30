package com.wos.tictactoeservice.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GameExceptionAdvice {

	  @ResponseBody
	  @ExceptionHandler(GameNotFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String gameNotFoundHandler(GameNotFoundException ex) {
		  return ex.getMessage();
	  }
	  
	  @ResponseBody
	  @ExceptionHandler(GameNotAcceptableException.class)
	  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	  String gameNotAcceptableHandler(GameNotAcceptableException ex) {
		  return ex.getMessage();
	  }
	  
	  @ResponseBody
	  @ExceptionHandler(GamePlayerNotJoinedException.class)
	  @ResponseStatus(HttpStatus.NOT_MODIFIED)
	  String gamePlayerNotJoinedHandler(GamePlayerNotJoinedException ex) {
		  return ex.getMessage();
	  }
	  
	  @ResponseBody
	  @ExceptionHandler(GamePlayerAlreadyJoinedException.class)
	  @ResponseStatus(HttpStatus.CONFLICT)
	  String gamePlayerAlreadyJoinedHandler(GamePlayerAlreadyJoinedException ex) {
		  return ex.getMessage();
	  }
}