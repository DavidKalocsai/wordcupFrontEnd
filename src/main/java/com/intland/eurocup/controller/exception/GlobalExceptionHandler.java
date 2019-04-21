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

import lombok.extern.log4j.Log4j;

@Log4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ErrorModelViewFactory modelViewFactory;
	
	
	@ExceptionHandler(value = UnkownTerritoryException.class)
	public ModelAndView unknownTerritoryHandler(HttpServletRequest req, Exception e) throws Exception {
		logException(e);
		return modelViewFactory.getModelView(ErrorModelViewType.UNKNOWN_TERRITORY);
	}
	
	@ExceptionHandler(value = UnsupportedModelViewTypeException.class)
	public ModelAndView unsupportedModelViewTypeHandler(HttpServletRequest req, Exception e) throws Exception {
		logException(e);		
		return modelViewFactory.getModelView(ErrorModelViewType.UNSUPPORTED_VIEW);
	}
	
	@ExceptionHandler(value = Throwable.class)
	public ModelAndView defaultThrowableHandler(HttpServletRequest req, Exception e) throws Exception {
		logException(e);
		return modelViewFactory.getModelView(ErrorModelViewType.OTHER);
	}
	
	private void logException(final Exception e) {
		log.error("Exception handler: " + e);
	}
}