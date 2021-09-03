package br.com.ebanx.takehometest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class EventReturnDTO {


	@JsonInclude(value = Include.NON_NULL)
	private AccountDTO origin;
	
	@JsonInclude(value = Include.NON_NULL)
	private AccountDTO destination;

	
	public AccountDTO getOrigin() {
		return origin;
	}

	public void setOrigin(AccountDTO origin) {
		this.origin = origin;
	}

	public AccountDTO getDestination() {
		return destination;
	}

	public void setDestination(AccountDTO destination) {
		this.destination = destination;
	}
	
	public static EventReturnDTO convertToDTO(AccountDTO destination, AccountDTO origin) {
		EventReturnDTO eventReturnDTO = new EventReturnDTO();
		
		eventReturnDTO.setOrigin(origin);
		eventReturnDTO.setDestination(destination);
		
		return eventReturnDTO;
	}
	
		
	
}
