package io.dowlathbasha.restjpamysql.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handlAllExceptions(Exception ex, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse( new Date(),
				                                      ex.getMessage(),
				                                      webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)

	public final ResponseEntity<Object> handleAllExceptions(UserNotFoundException userEx, WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), userEx.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

	}
}
	
