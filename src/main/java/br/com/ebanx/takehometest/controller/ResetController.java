package br.com.ebanx.takehometest.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebanx.takehometest.service.AccountService;
/**
 * This class is responsable for reset the database.
 * 
 * @author Fabio Bertini
 * 
 */
@RestController
@RequestMapping("/reset")
public class ResetController {

	private static final Logger logger = Logger.getLogger(ResetController.class);
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * Method that responsable for reset the database
	 * 
	 * @return OK and HTTP status. 
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 
	 */
	@PostMapping
	public ResponseEntity<String> reset(){
		logger.info(String.format("reset the database... "));
		
		accountService.reset();
		
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}
}
