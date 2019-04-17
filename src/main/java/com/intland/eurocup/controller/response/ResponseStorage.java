package com.intland.eurocup.controller.response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.intland.eurocup.model.Response;
import com.intland.eurocup.model.ResponseStatus;

@Component
public class ResponseStorage {
	private static final Response INIT_RESPONSE = new Response();
	private static final Response ERROR_RESPONSE = new Response(ResponseStatus.ERROR, "Request id not found!");

	final Map<Long, Response> responses = new ConcurrentHashMap<>();

	public void registerRequestId(final Long requestId) {
		responses.put(requestId, INIT_RESPONSE);
	}

	public void saveResponseIfRequestIdRegistered(final Long requestId, final Response response) {
		if (responses.get(requestId) == INIT_RESPONSE) {
			responses.put(requestId, response);
		}
	}

	public Response getResponse(final Long id) {
		final Response response = responses.getOrDefault(id, ERROR_RESPONSE);
		removeResponseIfValid(id, response);
		return response;
	}

	private void removeResponseIfValid(final Long id, final Response response) {
		if (isResponseArrived(response)) {
			responses.remove(id);
		}
	}

	private boolean isResponseArrived(final Response response) {
		return response != ERROR_RESPONSE && response.getStatus() != ResponseStatus.NO;
	}
}
