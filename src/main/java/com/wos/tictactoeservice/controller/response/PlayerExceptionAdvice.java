package com.wos.tictactoeservice.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PlayerExceptionAdvice {

	  @ResponseBody
	  @ExceptionHandler(PlayerNotFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String playerNotFoundHandler(PlayerNotFoundException ex) {
		  return ex.getMessage();
	  }
	  
	  @ResponseBody
	  @ExceptionHandler(PlayerUnauthorizedException.class)
	  @ResponseStatus(HttpStatus.UNAUTHORIZED)
	  String playerUnauthorizedHandler(PlayerUnauthorizedException ex) {
		  return ex.getMessage();
	  }
}