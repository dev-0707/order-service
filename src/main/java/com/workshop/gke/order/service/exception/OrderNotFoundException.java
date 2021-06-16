package com.workshop.gke.order.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1074046729608435984L;

	public OrderNotFoundException(Long id) {
		super(String.format("Order '%s' not found", id));
	}
}