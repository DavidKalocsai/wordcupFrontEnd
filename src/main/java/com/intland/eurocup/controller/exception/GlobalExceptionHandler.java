package com.intland.eurocup.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.intland.eurocup.controller.model.ErrorModelViewFactory;
import com.intland.eurocup.controller.model.ErrorModelViewFactory.ErrorModelViewType;
import com.intland.eurocup.service.converter.exception.UnkownTerritoryException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	private ErrorModelViewFactory modelViewFactory;
	
	
	@ExceptionHandler(value = UnkownTerritoryException.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		
		logger.error("UnkownTerritoryException catcehd: [URL] : {}", req.getRequestURL(), e);
		
		return modelViewFactory.getModelView(ErrorModelViewType.UNKNOWN_TERRITORY);
	}
	
	@ExceptionHandler(value = Throwable.class)
	public ModelAndView defaultThrowableHandler(HttpServletRequest req, Exception e) throws Exception {
		
		logger.error("Exception catcehd: [URL] : {}", req.getRequestURL(), e);
		
		return modelViewFactory.getModelView(ErrorModelViewType.OTHER);
	}
}