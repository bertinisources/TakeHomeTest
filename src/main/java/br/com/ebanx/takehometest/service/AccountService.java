package br.com.ebanx.takehometest.service;

import br.com.ebanx.takehometest.dto.AccountDTO;
import br.com.ebanx.takehometest.dto.EventReturnDTO;
import br.com.ebanx.takehometest.model.Event;

public interface AccountService {

	AccountDTO findById(Long id);
	
	EventReturnDTO deposit(Event event);

	EventReturnDTO withdraw(Event event);

	EventReturnDTO transfer(Event event);

	void reset();
}
