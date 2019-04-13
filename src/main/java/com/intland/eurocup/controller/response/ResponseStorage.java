package com.intland.eurocup.controller.response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.BackendJmsMesage;
import com.intland.eurocup.common.model.DrawResult;
import com.intland.eurocup.model.Response;
import com.intland.eurocup.model.ResponseStatus;

@Component
public class ResponseStorage {
	private static final Response INIT_RESPONSE = new Response();
	private static final Response ERROR_RESPONSE = new Response(ResponseStatus.ERROR, "Request id not found!");
	
	final Map<Long, Response> responses = new ConcurrentHashMap<>(); //TODO long

	public void registerRequestId(final Long id) {
		responses.put(id, INIT_RESPONSE);
	}

	public void saveResponseIfIdExists(final BackendJmsMesage jmsResponse) {
		if (responses.get(jmsResponse.getRequestId()) == INIT_RESPONSE) {
			responses.put(jmsResponse.getRequestId(), convert(jmsResponse));
		}
	}
	
	public Response getResponse(final Long id) {
		final Response response = responses.getOrDefault(id, ERROR_RESPONSE);
		removeResponseIfArrived(id, response);
		return response;
	}
	
	private void removeResponseIfArrived(final Long id, final Response response) {
		if (isResponseArrived(response)) {
			responses.remove(id);
		}
	}

	private boolean isResponseArrived(final Response response) {
		return response != null && response.getStatus() != null && response.getStatus() != ResponseStatus.NO && response != ERROR_RESPONSE;
	}
	
	private Response convert(final BackendJmsMesage jmsResponse) {
		final DrawResult drawResult = jmsResponse.getDrawResult();
		ResponseStatus responseStatus = ResponseStatus.OK;
		final String message = drawResult.getDescription();
		if (drawResult == DrawResult.ERROR) {
			responseStatus = ResponseStatus.ERROR;
		}
		return new Response(responseStatus, message);
	}
}
