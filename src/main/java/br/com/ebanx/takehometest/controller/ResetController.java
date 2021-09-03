package br.com.ebanx.takehometest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebanx.takehometest.service.AccountService;

@RestController
@RequestMapping("/reset")
public class ResetController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<String> reset(){
		
		accountService.reset();
		
		return ResponseEntity.status(HttpStatus.OK)
	            .body("OK");
	}
}
