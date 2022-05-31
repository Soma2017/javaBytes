package com.tweetapp.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tweetapp.util.Envelope;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@Generated
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// error handle for @Valid

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Envelope<String> envelope = new Envelope<>();
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		envelope.setData(errors.get(0));
		envelope.setStatusCode(status.value());
		envelope.setHttpStatus(status);
		return new ResponseEntity<>(envelope, headers, status);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Envelope<String>> TweetAppException(TweetAppException exception) {

		Envelope<String> env = new Envelope<>(exception.getStatusCode(), exception.getStatus(), exception.getData());
		log.debug("TweetAppException StatusCode{} Message{}", exception.getStatus(), exception.getData());
		return new ResponseEntity<>(env, HttpStatus.valueOf(exception.getStatusCode()));

	}

}