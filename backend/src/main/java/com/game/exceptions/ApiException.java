package com.game.exceptions;

public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected final int code;

	public ApiException(int code, String msg) {
		super(msg);
		this.code = code;
	}
}
