package com.cinyema.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.cinyema.app.entidades.Ticket;

public interface TicketRepositorio extends JpaRepository<Ticket, Long> {

	@Query("SELECT t FROM Ticket t WHERE t.pelicula_id_pelicula = :idPelicula")
	public List<Ticket> listarTicketsxPelicula(@Param("idPelicula") Long idPelicula);
	
	
}
