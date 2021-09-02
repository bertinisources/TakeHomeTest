package br.com.ebanx.takehometest.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebanx.takehometest.dto.AccountDTO;
import br.com.ebanx.takehometest.model.Account;
import br.com.ebanx.takehometest.repository.AccountRepository;
import br.com.ebanx.takehometest.service.AccountService;

@RestController()
@RequestMapping("/balance")
public class BalanceController {

	private static final Logger logger = Logger.getLogger(BalanceController.class);
	
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping()
	public ResponseEntity<AccountDTO> getBalance(@RequestParam("account_id") Long id) {
	
		if(id != null ) { 
			AccountDTO account = accountService.findById(id);
			
			if (account != null ) {
				logger.info(account);
				return ResponseEntity.ok(account);
			}
		}
		
		return ResponseEntity.notFound().build();
	
	}
	
}
