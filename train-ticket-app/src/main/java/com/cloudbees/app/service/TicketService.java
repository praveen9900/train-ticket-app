package com.cloudbees.app.service;

import java.util.List;

import com.cloudbees.app.entity.Ticket;

public interface TicketService {

	Ticket purchaseTicket(Ticket ticket);

	Ticket viewReceipt(String email);

	List<Ticket> viewUsersBySection(String section);

	void removeUser(String email);

	void modifySeat(String email, String newSeat);

}
