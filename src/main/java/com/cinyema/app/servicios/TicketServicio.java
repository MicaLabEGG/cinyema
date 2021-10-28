package com.cinyema.app.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio {
	
	@Autowired
	private TicketRepositorio repTic;
	
	
	public void crearTicket(Pelicula pel, Usuario usu, Date fecha, String lugar, Double precio) {
		
		Ticket tic = new Ticket();
		
		tic.setPelicula(pel);
		tic.setUsuario(usu);
		tic.setFecha(fecha);
		tic.setLugar(lugar);
		tic.setPrecio(precio);
		
		repTic.save(tic);
	}
	
	public void eliminarTicket(Long id) {
		
		repTic.deleteById(id);
	}
	
	public void modificarTicket(Long id, Pelicula pel, Usuario usu, Date fecha, String lugar, Double precio) {
		
		Ticket tic = repTic.findById(id).get();
		
		tic.setPelicula(pel);
		tic.setUsuario(usu);
		tic.setFecha(fecha);
		tic.setLugar(lugar);
		tic.setPrecio(precio);
		
		repTic.save(tic);
	}
	
	public List<Ticket> listarTicket() {
		List<Ticket> listaTickets = repTic.findAll();
		return listaTickets;
	}
	
	public Ticket buscarxId(Long id) {
		Ticket tic = repTic.getById(id);
		return tic;
	}
	
}