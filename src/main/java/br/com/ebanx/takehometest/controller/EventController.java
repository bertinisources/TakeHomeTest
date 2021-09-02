package br.com.ebanx.takehometest.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<EventReturnDTO> event(@RequestBody Event event, UriComponentsBuilder uriBuilder) throws URISyntaxException {
	
		if(event != null ) { 
			if(EventTypeEnum.DEPOSIT.getValue().equalsIgnoreCase(event.getEventTypeEnum())) {
				
				logger.info("DEPOSIT");
				EventReturnDTO eventReturnDTO = accountService.deposit(event);
				
				//URI uri = uriBuilder.path("/event/{Id}"+event.getDestination()).buildAndExpand(event.getDestination()).toUri();
				return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				
			}
			else if(EventTypeEnum.WITHDRAW.getValue().equalsIgnoreCase(event.getEventTypeEnum())) {
				logger.info("WITHDRAW");
				
				EventReturnDTO eventReturnDTO = accountService.withdraw(event);
				
				if(eventReturnDTO != null ) {
					
					//URI uri = uriBuilder.path("/event/{Id}"+event.getDestination()).buildAndExpand(event.getDestination()).toUri();
					return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				}
				
				return ResponseEntity.notFound().build();
			}
			else if(EventTypeEnum.TRANSFER.getValue().equalsIgnoreCase(event.getEventTypeEnum())) {
				logger.info("TRANSFER");
				
				EventReturnDTO eventReturnDTO = accountService.transfer(event);
				
				
				if(eventReturnDTO != null ) {
					
					//URI uri = uriBuilder.path("/event/{Id}"+event.getDestination()).buildAndExpand(event.getDestination()).toUri();
					return ResponseEntity.created(new URI("")).body(eventReturnDTO);
				}
				
				return ResponseEntity.notFound().build();

			}
			
		}
		
		return ResponseEntity.noContent().build();
	
	}
	
	
}
