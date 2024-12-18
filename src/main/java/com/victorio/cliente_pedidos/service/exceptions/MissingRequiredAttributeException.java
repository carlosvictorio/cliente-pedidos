package com.victorio.cliente_pedidos.service.exceptions;

public class MissingRequiredAttributeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public MissingRequiredAttributeException() {
		super("Preencha todos campos obrigatórios");
	}
	
	public MissingRequiredAttributeException(String msg) {
		super(msg);
	}

}
