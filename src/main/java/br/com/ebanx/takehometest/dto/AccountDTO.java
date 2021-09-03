package br.com.ebanx.takehometest.dto;

import java.math.BigDecimal;

import br.com.ebanx.takehometest.model.Account;



public class AccountDTO {
	
	private String id;
	
	private Integer balance;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static AccountDTO convertToDTO(Account account) {
		AccountDTO dto = new AccountDTO();
		dto.setId(account.getId().toString());
		dto.setBalance(account.getBalance().intValue());
		
		return dto;
	}
	
}
