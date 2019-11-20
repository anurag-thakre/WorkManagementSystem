package com.ffma.workmanagement.handler;

import java.security.InvalidParameterException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ffma.workmanagement.model.ResponseMsg;

import javassist.NotFoundException;

@RestControllerAdvice
public class TaskControllerAdvice {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	  public ResponseMsg handleNotFoundException(NotFoundException ex) {
	    ResponseMsg responseMsg = new ResponseMsg(ex.getMessage());
	    return responseMsg;
	  }
	
	@ExceptionHandler(InvalidParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	  public ResponseMsg handleNotFoundException(InvalidParameterException ex) {
	    ResponseMsg responseMsg = new ResponseMsg(ex.getMessage());
	    return responseMsg;
	  }

}
