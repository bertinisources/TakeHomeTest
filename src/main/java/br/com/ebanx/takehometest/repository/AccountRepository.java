package br.com.ebanx.takehometest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ebanx.takehometest.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
}
