package com.cloudbees.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudbees.app.entity.Ticket;
import com.cloudbees.app.exception.InvalidInputException;
import com.cloudbees.app.exception.ResourceNotFoundException;
import com.cloudbees.app.exception.TicketNotFoundException;
import com.cloudbees.app.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public Ticket purchaseTicket(Ticket ticket) {
		ticket.setFromLocation("London");
		ticket.setToLocation("France");
		ticket.setPrice(20.0);
		ticket.setSeatSection(allocateSeat());
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket viewReceipt(String email) {
		Ticket ticket = ticketRepository.findByEmail(email);
		if (ticket != null) {
			return ticket;
		} else {
			throw new TicketNotFoundException();
		}
	}

	public List<Ticket> viewUsersBySection(String section) {

		List<Ticket> tickets = ticketRepository.findBySeatSection(section);
		if (tickets != null && (!tickets.isEmpty())) {
			return tickets;
		} else {
			throw new TicketNotFoundException();
		}
	}

	@Override
	public void removeUser(String email) {
		Ticket ticket = ticketRepository.findByEmail(email);
		if (ticket != null) {
			ticketRepository.delete(ticket);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@Override
	public void modifySeat(String email, String newSeat) {
		Ticket ticket = ticketRepository.findByEmail(email);
		if (ticket != null) {
			if (newSeat.equalsIgnoreCase("A") || newSeat.equalsIgnoreCase("B")) {
				ticket.setSeatSection(newSeat);
				ticketRepository.save(ticket);
			} else {
				throw new InvalidInputException();
			}
		} else {
			throw new ResourceNotFoundException();
		}
	}

	private String allocateSeat() {
		return ticketRepository.count() % 2 == 0 ? "A" : "B";
	}

}
