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

/**
 * This Class is responsable for handle the transations from an account.
 * 
 * @author Fabio Bertini
 * 
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * Method that find the account by id.
	 * 
	 * @param id - The number of account.
	 * 
	 * @author Fabio Bertini
	 */
	public AccountDTO findById(Long id) {
		Optional<Account> account = accountRepository.findById(id);
		
		if (account.isPresent()) {
			
			logger.info(String.format(" class %s - method findById account: %s ", this.getClass().toString(), account.get().getId()));
			return AccountDTO.convertToDTO(account.get());	
		}
		
		return null;	
	}
	
	/**
	 * Method that responsable for DEPOSIT
	 * 
	 * @param event - where: 
	 * 
	 * 	type - type of transaction (DEPOSIT)
	 *  amount - the amount that need to consider in the transaction
	 *  destination - the account that received the amount.
	 *  
	 * @return <code>EventReturnDTO</code> with result of the transaction 
	 * 
	 * 
	 * @author Fabio Bertini
	 */
	public EventReturnDTO deposit(Event event) {
		
		logger.info(String.format("class: %s - method deposit  account: %s , amount: %s", this.getClass().toString(), event.getDestination(), event.getAmount().intValue()));
		
		//check exist account
		Optional<Account> accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));
		
				
		//check if the account destination not exists, then create a new one.
		if(! accountDestination.isPresent()) {
			logger.info(String.format("account: %s non-exist and should be created... ", event.getDestination()));
			Account newAccount = new Account(Long.parseLong(event.getDestination()), new BigDecimal("0.00"));
			
			accountRepository.save(newAccount);
			
			accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));
			logger.info(String.format("account: %s was created with successfully... ", event.getDestination()));
		}
		
		
		accountDestination.get().setBalance(accountDestination.get().getBalance().add(event.getAmount()));
		
		accountRepository.save(accountDestination.get());
		
		EventReturnDTO eventReturnDTO = EventReturnDTO.convertToDTO(AccountDTO.convertToDTO(accountDestination.get()), null);
		
		logger.info(String.format("The transaction was executed with sucessfull -  account: %s , amount: %s", event.getDestination(), event.getAmount().intValue()));
		
		return eventReturnDTO;
			
	}
	
	
	/**
	 * Method that responsable for WITHDRAW
	 * 
	 * @param event - where: 
	 * 
	 * 	type - type of transaction (WITHDRAW)
	 *  amount - the amount that need to consider in the transaction
	 *  origin -  the account that was responsable for provide the amount 
	 *  
	 *  
	 * @return <code>EventReturnDTO</code> with result of the transaction 
	 * 
	 * 
	 * @author Fabio Bertini
	 */
	public EventReturnDTO withdraw(Event event) {
		
		logger.info(String.format("class: %s - method withdraw  account: %s , amount: %s", this.getClass().toString(), event.getOrigin(), event.getAmount().intValue()));
		
		//check exist account origin
		Optional<Account> accountOrigin = accountRepository.findById(Long.parseLong(event.getOrigin()));
		
		
		if(accountOrigin.isPresent()) {
			
			
			accountOrigin.get().setBalance(accountOrigin.get().getBalance().subtract(event.getAmount()));
			
			accountRepository.save(accountOrigin.get());
			
			EventReturnDTO eventReturnDTO = EventReturnDTO.convertToDTO(null, AccountDTO.convertToDTO(accountOrigin.get()));
			
			logger.info(String.format("The transaction was executed with sucessfull -  account: %s , amount: %s", event.getOrigin(), event.getAmount().intValue()));
			
			return eventReturnDTO;
			
		}
		logger.info(String.format("account: %s non-exist ", event.getOrigin()));
		return null;
		
	}

	/**
	 * Method that responsable for TRANSFER
	 * 
	 * @param event - where: 
	 * 
	 * 	type - type of transaction (TRANSFER)
	 *  amount - the amount that need to consider in the transaction
	 *  origin -  the account that was responsable for provide the amount 
	 *  
	 *  
	 * @return <code>EventReturnDTO</code> with result of the transaction 
	 * 
	 * 
	 * @author Fabio Bertini
	 */
	public EventReturnDTO transfer(Event event) {
		
		logger.info(String.format("class: %s - method transfer account_origin: %s, account_destination: %s , amount: %s", this.getClass().toString(), event.getOrigin(), event.getDestination(), event.getAmount().intValue()));
		
		//check exist account destination
		Optional<Account> accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));

		
		//check exist account origin
		Optional<Account> accountOrigin = accountRepository.findById(Long.parseLong(event.getOrigin()));
		
		//check if the account origin exists
		if(accountOrigin.isPresent()) {
			
			//check if the account destination not exists, then create a new one.
			if(!accountDestination.isPresent()) {
				logger.info(String.format("account: %s non-exist and should be created... ", event.getDestination()));
				
				Account newAccount = new Account(Long.parseLong(event.getDestination()), new BigDecimal("0.00"));
				
				accountRepository.save(newAccount);
				
				accountDestination = accountRepository.findById(Long.parseLong(event.getDestination()));
			}
			
			
			accountDestination.get().setBalance(accountDestination.get().getBalance().add(event.getAmount()));
			
			accountOrigin.get().setBalance(accountOrigin.get().getBalance().subtract(event.getAmount()));
			
			accountRepository.save(accountDestination.get());
			
			accountRepository.save(accountOrigin.get());
			
			EventReturnDTO eventReturnDTO = EventReturnDTO.convertToDTO(AccountDTO.convertToDTO(accountDestination.get()), AccountDTO.convertToDTO(accountOrigin.get()));
			
			logger.info(String.format("The transaction was executed with sucessfull -  account_origin: %s, account_destination: %s , amount: %s", event.getOrigin(), event.getDestination(), event.getAmount().intValue()));
			
			return eventReturnDTO;
			
		}
		
		logger.info(String.format("account_origin: %s non-exist ", event.getOrigin()));
		
		return null;

	}
	
	/**
	 * Method that responsable for reset the database
	 * 
	 * @author Fabio Bertini
	 */
	public void reset() {
		logger.info(String.format("class: %s - method reset the database...", this.getClass().toString()));
		
		accountRepository.deleteAll();
		
		logger.info(String.format("class: %s - method reset the database was executed with sucessfull...", this.getClass().toString()));
	}


}
