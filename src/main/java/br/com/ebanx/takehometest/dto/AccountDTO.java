package br.com.ebanx.takehometest.dto;

import java.math.BigDecimal;

import br.com.ebanx.takehometest.model.Account;



public class AccountDTO {
	
	private Long id;
	
	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static AccountDTO convertToDTO(Account account) {
		AccountDTO dto = new AccountDTO();
		dto.setId(account.getId());
		dto.setBalance(account.getBalance());
		
		return dto;
	}
	
}
