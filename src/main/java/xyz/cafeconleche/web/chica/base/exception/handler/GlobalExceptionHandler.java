package xyz.cafeconleche.web.chica.base.exception.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleSQLException(HttpServletRequest request, Exception e) {
		System.err.println("SQLException occured at " + request.getRequestURL());
		
		ModelAndView modelAndView = new ModelAndView("sql-error");
		modelAndView.addObject("exception", e);
	    modelAndView.addObject("url", request.getRequestURL());
		return modelAndView;
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView handleIOException(HttpServletRequest request, Exception e) {
		System.err.println("IOException occured at " + request.getRequestURL());
		// returns 404 error code
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", e);
	    modelAndView.addObject("url", request.getRequestURL());
		return modelAndView;
	}
	
	// returns 404 error code
	
	
	// 404
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView handle404(HttpServletRequest request, Exception e) {
		System.err.println("NoHandlerFoundException occured at " + request.getRequestURL());
		
		ModelAndView modelAndView = new ModelAndView("404");
		modelAndView.addObject("exception", e);
	    modelAndView.addObject("url", request.getRequestURL());
		return modelAndView;
	}
	
//	// todas
//	@ExceptionHandler(Exception.class)
//	public void handleAll(HttpServletRequest request, Exception e) {
//		System.err.println("Exception occured at " + request.getRequestURL());
//	}
	

//	@ExceptionHandler(Exception.class)
//	public ModelAndView handleAll(HttpServletRequest request, Exception e) {
//		System.err.println("Exception occured at " + request.getRequestURL());
//		ModelAndView modelAndView = new ModelAndView("exception");
//		modelAndView.addObject("exception", e);
//	    modelAndView.addObject("url", request.getRequestURL());
//		return modelAndView;
//	}
	

}
