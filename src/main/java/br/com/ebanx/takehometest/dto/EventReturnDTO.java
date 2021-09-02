package br.com.ebanx.takehometest.dto;

public class EventReturnDTO {


	
	private AccountDTO origin;
	
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
