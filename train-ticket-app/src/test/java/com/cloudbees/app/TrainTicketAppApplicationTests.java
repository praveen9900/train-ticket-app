package com.cloudbees.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cloudbees.app.controller.TicketController;
import com.cloudbees.app.entity.Ticket;
import com.cloudbees.app.service.TicketServiceImpl;

@WebMvcTest(TicketController.class)
public class TrainTicketAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TicketServiceImpl ticketService;

	@Test
	public void testPurchaseTicket() throws Exception {
		Ticket ticket = new Ticket();
		ticket.setFromLocation("London");
		ticket.setToLocation("France");
		ticket.setPrice(20.0);
		ticket.setSeatSection("A");

		when(ticketService.purchaseTicket(any(Ticket.class))).thenReturn(ticket);

		mockMvc.perform(post("/api/purchase").contentType("application/json").content(
				"{ \"fromLocation\": \"London\", \"toLocation\": \"France\", \"price\": 20.0, \"seatSection\": \"A\" }"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.fromLocation").value("London"))
				.andExpect(jsonPath("$.toLocation").value("France")).andExpect(jsonPath("$.price").value(20.0))
				.andExpect(jsonPath("$.seatSection").value("A"));
	}

	@Test
	public void testViewReceipt() throws Exception {
		Ticket ticket = new Ticket();
		ticket.setEmail("test@example.com");
		ticket.setFromLocation("London");
		ticket.setToLocation("France");
		ticket.setPrice(20.0);
		ticket.setSeatSection("A");

		when(ticketService.viewReceipt("test@example.com")).thenReturn(ticket);

		mockMvc.perform(get("/api/receipt/test@example.com")).andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("test@example.com"))
				.andExpect(jsonPath("$.fromLocation").value("London"))
				.andExpect(jsonPath("$.toLocation").value("France")).andExpect(jsonPath("$.price").value(20.0))
				.andExpect(jsonPath("$.seatSection").value("A"));
	}
 
	@Test
	public void testViewUsersBySection() throws Exception {
		Ticket ticket1 = new Ticket();
		ticket1.setSeatSection("A");
		Ticket ticket2 = new Ticket();
		ticket2.setSeatSection("A");

		when(ticketService.viewUsersBySection("A")).thenReturn(Arrays.asList(ticket1, ticket2));

		mockMvc.perform(get("/api/usersBySection/A")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].seatSection").value("A")).andExpect(jsonPath("$[1].seatSection").value("A"));
	}
 
	@Test
	public void testRemoveUser() throws Exception {
		mockMvc.perform(delete("/api/remove/test@example.com")).andExpect(status().isOk())
				.andExpect(content().string("Deleted Successfully"));
	}

	@Test
	public void testModifySeat() throws Exception {
		mockMvc.perform(put("/api/modifySeat").param("email", "test@example.com").param("newSeat", "A"))
				.andExpect(status().isOk()).andExpect(content().string("Ticket Updated"));
	}
}
 