package br.com.ebanx.takehometest.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ebanx.takehometest.dto.AccountDTO;
import br.com.ebanx.takehometest.dto.EventReturnDTO;
import br.com.ebanx.takehometest.model.Account;
import br.com.ebanx.takehometest.model.Event;
import br.com.ebanx.takehometest.repository.AccountRepository;
import br.com.ebanx.takehometest.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountDTO findById(Long id) {
		Optional<Account> account = accountRepository.findById(id);
		
		if (account.isPresent()) {
			logger.info(account.get());
			return AccountDTO.convertToDTO(account.get());	
		}
		
		return null;	
	}
	
	public EventReturnDTO deposit(Event event) {
		
		//check exist account
		Optional<Account> accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));
		
				
		//check if the account destination not exists, then create a new one.
		if(! accountDestination.isPresent()) {
			Account newAccount = new Account(Long.parseLong(event.getDestination()), new BigDecimal("0.00"));
			
			accountRepository.save(newAccount);
			
			accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));
		}
		
		
		accountDestination.get().setBalance(accountDestination.get().getBalance().add(event.getAmount()));
		
		accountRepository.save(accountDestination.get());
		
		EventReturnDTO eventReturnDTO = EventReturnDTO.convertToDTO(AccountDTO.convertToDTO(accountDestination.get()), null);
		
		return eventReturnDTO;
			
	}
	
	
	
	public EventReturnDTO withdraw(Event event) {
		
		//check exist account origin
		Optional<Account> accountOrigin = accountRepository.findById(Long.parseLong(event.getOrigin()));
		
		
		if(accountOrigin.isPresent()) {
			
			
			accountOrigin.get().setBalance(accountOrigin.get().getBalance().subtract(event.getAmount()));
			
			accountRepository.save(accountOrigin.get());
			
			EventReturnDTO eventReturnDTO = EventReturnDTO.convertToDTO(null, AccountDTO.convertToDTO(accountOrigin.get()));
			
			return eventReturnDTO;
			
		}
					
		return null;
		
	}

	
	public EventReturnDTO transfer(Event event) {
		
		//check exist account destination
		Optional<Account> accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));

		
		//check exist account origin
		Optional<Account> accountOrigin = accountRepository.findById(Long.parseLong(event.getOrigin()));
		
		//check if the account origin exists
		if(accountOrigin.isPresent()) {
			
			//check if the account destination not exists, then create a new one.
			if(!accountDestination.isPresent()) {
				Account newAccount = new Account(Long.parseLong(event.getDestination()), new BigDecimal("0.00"));
				
				accountRepository.save(newAccount);
				
				accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));
			}
			
			
			accountDestination.get().setBalance(accountDestination.get().getBalance().add(event.getAmount()));
			
			accountOrigin.get().setBalance(accountOrigin.get().getBalance().subtract(event.getAmount()));
			
			accountRepository.save(accountDestination.get());
			
			accountRepository.save(accountOrigin.get());
			
			EventReturnDTO eventReturnDTO = EventReturnDTO.convertToDTO(AccountDTO.convertToDTO(accountDestination.get()), AccountDTO.convertToDTO(accountOrigin.get()));
			
			return eventReturnDTO;
			
		}
		
		return null;

	}

}
