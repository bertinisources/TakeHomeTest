package br.com.ebanx.takehometest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ebanx.takehometest.enumeration.EventTypeEnum;
import br.com.ebanx.takehometest.model.Event;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class FinanceApiUnitTest {

	@Autowired
    private MockMvc mockMvc;

	@Test
	@Order(1)
	public void contextLoad() {
		assertNotNull(mockMvc);
	}
	
	@Test
	@Order(2)
	public void getBalanceNoExistAccount() throws Exception {
		
		this.mockMvc.perform(get("/balance").param("account_id", "1234")).andExpect(status().isNotFound());
	}
	
	/**
	 * Create account with initial balance
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(3)
	public void createAccountWithInitialBalance() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/event")
				.content(asJsonString(new Event(EventTypeEnum.DEPOSIT.getValue(), new BigDecimal("10.00"), "100")))
				.contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.destination").exists());
		
	}
	
	/**
	 * Deposit into existing account
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(4)
	public void depositIntoExistingAccount() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/event")
				.content(asJsonString(new Event(EventTypeEnum.DEPOSIT.getValue(), new BigDecimal("10.00"), "100")))
				.contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.destination").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.balance").value(20)
			    );
		
	}
	
	/**
	 * Get balance for existing account
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(5)
	public void getBalanceForExistAccount() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/balance")
					.param("account_id", "100")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100))
					.andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(20)
					);
	}
	
	/**
	 * Withdraw from non-existing account
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(6)
	public void withdrawFromNonExistingAccount() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/event")
				.content(asJsonString(new Event(EventTypeEnum.WITHDRAW.getValue(), new BigDecimal("10.00"), null, "200")))
				.contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound())
			    ;	
	}
	
	/**
	 * Withdraw from existing account
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(7)
	public void withdrawFromExistingAccount() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/event")
				.content(asJsonString(new Event(EventTypeEnum.WITHDRAW.getValue(), new BigDecimal("5.00"), null, "100")))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.origin").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.origin.balance").value(15))
			    ;	
	}
	
	/**
	 * Transfer from existing account
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(8)
	public void transferFromExistingAccount() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/event")
				.content(asJsonString(new Event(EventTypeEnum.TRANSFER.getValue(), new BigDecimal("15.00"), "300", "100")))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.origin").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.origin.balance").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.destination").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.destination.balance").value(15))

				;	
	}
	
	
	/**
	 * Transfer from non-existing account
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(9)
	public void transferFromNonExistingAccount() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/event")
				.content(asJsonString(new Event(EventTypeEnum.TRANSFER.getValue(), new BigDecimal("15.00"), "300", "200")))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				;	
	}
	

	
	/**
	 * Write Object as String Json.
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
