package com.intland.eurocup.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.controller.id.UniqueRequestIdGenerator;
import com.intland.eurocup.controller.model.ModelViewFactory;
import com.intland.eurocup.controller.model.ModelViewFactory.ModelViewType;
import com.intland.eurocup.controller.response.ResponseStorage;
import com.intland.eurocup.jms.adapter.AdapterSenderService;
import com.intland.eurocup.model.Response;
import com.intland.eurocup.model.ResponseStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.converter.TerritoryConverter;

@Controller
public class StatusController {

	private Logger logger = Logger.getLogger(StatusController.class.getSimpleName());

	@Autowired
	private UniqueRequestIdGenerator uniqueRequestIdGenerator;

	@Autowired
	private AdapterSenderService senderService;

	@Autowired
	private ModelViewFactory modelViewFactory;

	@Autowired
	private ResponseStorage responseStorage;
	
	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(Territory.class, new TerritoryConverter());
	}
	
	// save or update user
	@RequestMapping(value = "{territory}/form", method = RequestMethod.POST)
	public ModelAndView voucherSubmit(@PathVariable Territory territory,
			@ModelAttribute("formData") Voucher voucher) {

		// validate territory
		final ModelAndView model = modelViewFactory.getModelView(ModelViewType.STATUS_VIEW);
		
		final Long requestId = uniqueRequestIdGenerator.getNext();
		model.addObject("id", requestId);
		
		voucher.setTerritory(territory);
		senderService.send(requestId, voucher);
		
		runWorkerThread(requestId);
		return model;
	}

	private void runWorkerThread(Long id) {
		Runnable runnable = () -> {
			try {
				Thread.sleep(90000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			responseStorage.save(id, new Response(ResponseStatus.OK, "OK"));
		};
		Thread t = new Thread(runnable);
		t.start();
	}
}