package com.cloudbees.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudbees.app.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	List<Ticket> findBySeatSection(String section);

	Ticket findByEmail(String email);

}
