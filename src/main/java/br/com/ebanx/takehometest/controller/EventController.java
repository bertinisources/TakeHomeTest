package br.com.ebanx.takehometest.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ebanx.takehometest.dto.EventReturnDTO;
import br.com.ebanx.takehometest.enumeration.EventTypeEnum;
import br.com.ebanx.takehometest.model.Event;
import br.com.ebanx.takehometest.service.AccountService;

/**
 * This class is responsable for handle the transations from an account.
 * 
 * @author Fabio Bertini
 * 
 */
@RestController()
@RequestMapping("/event")
public class EventController {

	private static final Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * Method that responsable for get balance from an account.
	 * 
	 * @param event where:
	 *  
	 *  type - type of transaction (DEPOSIT, WITHDRAW, TRANSFER)
	 *  amount - the amount that need to consider in the transaction
	 *  origin -  the account that was responsable for provide the amount 
	 *  destination - the account that received the amount.
	 * 
	 * @return ResponseEntity with a <code>Object</code> and the HTTP status. 
	 * The <code>Object</code> could be <code>EventReturnDTO</code> if everything occurred with successfully
	 * The <code>Object</code> could be <code>Integer</code> if the transaction occurred with exception.
	 * 
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 
	 */
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> event(@RequestBody @Valid Event event, UriComponentsBuilder uriBuilder) throws URISyntaxException {
	
		if(event != null ) { 
			if(EventTypeEnum.DEPOSIT.getValue().equalsIgnoreCase(event.getType())) {
				
				logger.info(String.format("DEPOSIT account: %s , amount: %s", event.getDestination(), event.getAmount().intValue()));
				EventReturnDTO eventReturnDTO = accountService.deposit(event);
				
				return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				
			}
			else if(EventTypeEnum.WITHDRAW.getValue().equalsIgnoreCase(event.getType())) {
				logger.info(String.format("WITHDRAW account: %s , amount: %s", event.getOrigin(), event.getAmount().intValue()));
				
				EventReturnDTO eventReturnDTO = accountService.withdraw(event);
				
				if(eventReturnDTO != null ) {
					
					return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
			}
			else if(EventTypeEnum.TRANSFER.getValue().equalsIgnoreCase(event.getType())) {
				logger.info(String.format("TRANSFER account_origin: %s , amount: %s, account_destination: %s", event.getOrigin(), event.getAmount().intValue(), event.getDestination()));
				
				EventReturnDTO eventReturnDTO = accountService.transfer(event);
				
				if(eventReturnDTO != null ) {
					
					return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);

			}
			
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
	
	}
	
	
}
