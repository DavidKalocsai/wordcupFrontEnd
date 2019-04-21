package com.intland.eurocup.jms.adapter;

import org.springframework.stereotype.Service;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.common.model.LotResult;
import com.intland.eurocup.model.Response;
import com.intland.eurocup.model.ResponseStatus;
import com.intland.eurocup.model.Voucher;

@Service
public class MessageConverter {
	
	public Response convert(final MessageFromBackend message) {
		Response response;
		final LotResult result = message.getLotResult();
		if (result == LotResult.WINNER || result == LotResult.LOSER) {
			response = new Response(ResponseStatus.OK, result.getDescription());
		} else {
			response = new Response(ResponseStatus.ERROR, result.getDescription());
		}
		return response;
	}
	
	public MessageFromFrontend convert(final Long requestId, final Voucher voucher) {
		final MessageFromFrontend message = new MessageFromFrontend();
		message.setRequestId(requestId);
		message.setEmail(voucher.getEmail());
		message.setVoucher(voucher.getCode());
		message.setTerritory(voucher.getTerritory());
		return message;
	}
	
	
}
