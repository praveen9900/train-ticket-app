package com.cloudbees.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudbees.app.entity.Ticket;
import com.cloudbees.app.service.TicketServiceImpl;

@RestController
@RequestMapping("/api")
public class TicketController {

	@Autowired
	private TicketServiceImpl ticketService;

	@PostMapping("/purchase")
	public ResponseEntity<Ticket> purchaseTicket(@RequestBody Ticket ticket) {
		Ticket purchasedTicket = ticketService.purchaseTicket(ticket);
		return new ResponseEntity<>(purchasedTicket, HttpStatus.CREATED);
	}

	@GetMapping("/receipt/{email}")
	public ResponseEntity<Ticket> viewReceipt(@PathVariable String email) {
		Ticket ticket = ticketService.viewReceipt(email);
		return new ResponseEntity<>(ticket, HttpStatus.OK);

	}

	@GetMapping("/usersBySection/{section}")
	public ResponseEntity<List<Ticket>> viewUsersBySection(@PathVariable String section) {
		List<Ticket> users = ticketService.viewUsersBySection(section);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@DeleteMapping("/remove/{email}")
	public ResponseEntity<String> removeUser(@PathVariable String email) {
		ticketService.removeUser(email);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);

	}

	@PutMapping("/modifySeat")
	public ResponseEntity<String> modifySeat(@RequestParam String email, @RequestParam String newSeat) {
		ticketService.modifySeat(email, newSeat);
		return new ResponseEntity<>("Ticket Updated", HttpStatus.OK);
	}

}
