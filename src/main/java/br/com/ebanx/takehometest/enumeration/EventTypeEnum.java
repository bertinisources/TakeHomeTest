package br.com.ebanx.takehometest.enumeration;


public enum EventTypeEnum {

	DEPOSIT("deposit"), WITHDRAW("withdraw"), TRANSFER("transfer");
	
	private String value;
	
	private EventTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static EventTypeEnum getEnum(String value) {
		
		for(EventTypeEnum t : values()) {
			if(value.equals(t.getValue())) {
				return t;
			}
		}
		
		throw new RuntimeException("Type not found.");
	}
}
