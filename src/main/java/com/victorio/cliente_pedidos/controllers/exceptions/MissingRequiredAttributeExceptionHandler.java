package com.victorio.cliente_pedidos.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.victorio.cliente_pedidos.service.exceptions.MissingRequiredAttributeException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MissingRequiredAttributeExceptionHandler {
	
	@ExceptionHandler(MissingRequiredAttributeException.class)
	public ResponseEntity<StandardError> missingRequiredAttribute(MissingRequiredAttributeException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), "Bad Request", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
