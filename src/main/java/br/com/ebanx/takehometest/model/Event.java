package br.com.ebanx.takehometest.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



/**
 * This class represents the event that needs to be processed.
 * 
 * @author Fabio Bertini
 * 
 */
public class Event {

	@NotNull
	private String type;
	@NotNull
	private BigDecimal amount;
	
	private String origin;
	
	private String destination;
	
	public Event() {
		super();
	}
	
	public Event(String type, BigDecimal amount, String destination, String origin) {
		super();
		this.type = type;
		this.amount = amount;
		this.origin = origin;
		this.destination = destination;
	}
	
	public Event(String type, BigDecimal amount, String destination) {
		super();
		this.type = type;
		this.amount = amount;
		this.destination = destination;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	
}
