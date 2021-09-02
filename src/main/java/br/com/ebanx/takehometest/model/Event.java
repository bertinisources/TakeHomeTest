package br.com.ebanx.takehometest.model;

import java.math.BigDecimal;


public class Event {

	//@NotNull @NotEmpty
	private String type;
	//@NotNull @NotEmpty
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
	
	
	public String getEventTypeEnum() {
		return type;
	}
	public void setEventTypeEnum(String type) {
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
