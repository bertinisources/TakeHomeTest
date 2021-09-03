package br.com.ebanx.takehometest.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebanx.takehometest.dto.AccountDTO;
import br.com.ebanx.takehometest.service.AccountService;
/**
 * This class is responsable for check the balance from an exist account
 * 
 * @author Fabio Bertini
 * 
 */
@RestController()
@RequestMapping("/balance")
public class BalanceController {

	private static final Logger logger = Logger.getLogger(BalanceController.class);
	
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * Method responsable for get balance from an account.
	 * 
	 * @param account_id - The number of account.
	 * 
	 * @return ResponseEntity with a <code>Integer</code> with the balance from an account or <code>0</code> if non-account exists and the HTTP status.
	 * 
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 
	 */
	@GetMapping()
	public ResponseEntity<Object> getBalance(@RequestParam("account_id") Long id) {
	
		logger.info(String.format("Get Balance from account: %s" , id));
		
		if(id != null ) { 
			AccountDTO account = accountService.findById(id);
			
			if (account != null ) {
				
				logger.info(String.format("account: %s , balance: %s" , id, account.getBalance()));
				
				return ResponseEntity.ok(account.getBalance());
			}
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
	
	}
	
}
