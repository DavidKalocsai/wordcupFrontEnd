package com.intland.eurocup.controller.response;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Response;
import com.intland.eurocup.model.ResponseStatus;
import com.intland.eurocup.service.date.DateService;

@Component
public class ResponseStorage {
	private static final int REPSONSE_TIMEOUT_MIN = 5;
	private static final int CLEANER_TASK_SCHEDULE = 5 * 60 * 1000;
	private static final Response ERROR_RESPONSE = new Response(ResponseStatus.ERROR, "Request id not found!");

	private final Map<Long, Response> responses = new ConcurrentHashMap<>();

	@Autowired
	private DateService dateService;

	public void registerRequestId(final Long requestId) {
		responses.put(requestId, new Response());
	}

	public void save(final Long requestId, final Response response) {
		if (responses.get(requestId) != null) {
			responses.put(requestId, response);
		}
	}

	public Response getResponse(final Long id) {
		final Response response = responses.getOrDefault(id, ERROR_RESPONSE);
		removeResponseOnArrival(id, response);
		return response;
	}

	private void removeResponseOnArrival(final Long id, final Response response) {
		if (isResponseArrived(response)) {
			responses.remove(id);
		}
	}

	private boolean isResponseArrived(final Response response) {
		return response != ERROR_RESPONSE && response.getStatus() != ResponseStatus.NO;
	}

	@Service
	private class RepsonseStorageCleaner {
		@Scheduled(fixedDelay = CLEANER_TASK_SCHEDULE)
		public void scheduleFixedDelayTask() {
			removeNextOld();
		}

		private void removeNextOld() {
			final DateTime timeoutedDateTime = dateService.getNow().minusMinutes(REPSONSE_TIMEOUT_MIN);
			System.out.println("Now: " + dateService.getNow() + " Cleaning: " + ResponseStorage.this.responses + " Timeout:" + timeoutedDateTime);
			iterate();
		}

		private void iterate() {
			final Iterator<Response> iterator = ResponseStorage.this.responses.values().iterator(); 			
			while(iterator.hasNext()){ 
				final Response response = iterator.next(); 
				if(isReponseTimout(response)){ 
					iterator.remove(); 
				} 
			}
		}

		private boolean isReponseTimout(Response response) {
			final DateTime timeoutedDateTime = dateService.getNow().minusMinutes(REPSONSE_TIMEOUT_MIN);
			return response.getCreatedDate().isBefore(timeoutedDateTime);
		}
	}
}
