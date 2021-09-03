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


@RestController()
@RequestMapping("/event")
public class EventController {

	private static final Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> event(@RequestBody @Valid Event event, UriComponentsBuilder uriBuilder) throws URISyntaxException {
	
		if(event != null ) { 
			if(EventTypeEnum.DEPOSIT.getValue().equalsIgnoreCase(event.getType())) {
				
				logger.info("DEPOSIT");
				EventReturnDTO eventReturnDTO = accountService.deposit(event);
				
				return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				
			}
			else if(EventTypeEnum.WITHDRAW.getValue().equalsIgnoreCase(event.getType())) {
				logger.info("WITHDRAW");
				
				EventReturnDTO eventReturnDTO = accountService.withdraw(event);
				
				if(eventReturnDTO != null ) {
					
					return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
			}
			else if(EventTypeEnum.TRANSFER.getValue().equalsIgnoreCase(event.getType())) {
				logger.info("TRANSFER");
				
				EventReturnDTO eventReturnDTO = accountService.transfer(event);
				
				
				if(eventReturnDTO != null ) {
					
					return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);

			}
			
		}
		
		return ResponseEntity.noContent().build();
	
	}
	
	
}
