package br.com.ebanx.takehometest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ebanx.takehometest.model.Account;

/**
 * This interface was responsable to delegate the Spring and JPA to handle the database.
 * 
 * @author Fabio Bertini
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
	
}
