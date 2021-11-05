package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Ticket;

@Repository
public interface TicketRepositorio extends JpaRepository<Ticket, Long> {

}
