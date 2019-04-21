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

/**
 * Thread safe storage of responses. It stores only those responses, which previously registers on submit in the storage.
 * Until response not arrived it is signaled with ResponseStatus.NO.
 * When response is requested that has arrived, it is removed and returned. 
 * @author David
 *
 */
@Component
public class ResponseStorage {
	private static final int REPSONSE_TIMEOUT_MIN = 5;
	private static final Response ERROR_RESPONSE = new Response(ResponseStatus.ERROR, "Request id not found!");

	private final Map<Long, Response> responses = new ConcurrentHashMap<>();

	@Autowired
	private DateService dateService;

	/**
	 * Register a request id, before it is sent to the back end. Only those responses will be saved which request id is previously saved into map.
	 * @param requestId id to register.
	 */
	public void registerRequestId(final Long requestId) {
		responses.put(requestId, new Response());
	}

	/**
	 * Save response in storage if request id is found it storage.
	 * @param requestId id of the request. 
	 * @param response {@link Response}.
	 */
	public void save(final Long requestId, final Response response) {
		if (responses.get(requestId) != null) {
			responses.put(requestId, response);
		}
	}

	/**
	 * Get {@link Response} based on Id. If response arrived, before returning remove it from map.
	 * @param id identifies response.
	 * @return {@link Response}.
	 */
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

	/**
	 * ResponseStorageCleaner is responsible to clean left overs from response storage.
	 *
	 */
	@Service
	private class RepsonseStorageCleaner {
		private static final int CLEANER_TASK_SCHEDULE = 5 * 60 * 1000;
		
		/**
		 * Schedule task to clean left overs from response storage. 
		 */
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
