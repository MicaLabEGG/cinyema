package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cinyema.app.entidades.Ticket;

public interface TicketRepositorio extends JpaRepository<Ticket, Long> {

}
