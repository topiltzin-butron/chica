package xyz.cafeconleche.web.chica.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ChicaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2799911533782548609L;

	public ChicaException(String message) {
		super(message);
	}

}
