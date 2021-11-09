package com.cinyema.app.servicios;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.repositorios.PeliculaRepositorio;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio {
	
	@Autowired
	private TicketRepositorio repositorioTicket;
	
	@Autowired
	private PeliculaRepositorio repositorioPelicula;
	
	// metodo hecho para compra de tiket
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void setearFechaParaComprar(Long id, String fecha) throws Exception {
		try {
			Ticket ticket = this.obtenerTicketPorId(id);
			ticket.setFecha(fecha);
			repositorioTicket.save(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR AL SETEAR LA FECHA, ESTOY EN EL SERVICIO!");
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void registrar(Ticket ticket) throws Exception {
		validar(ticket);
		repositorioTicket.save(ticket);
	}
	
	@Transactional
	public Ticket registrarVacio() {
		return new Ticket();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long id) {
		repositorioTicket.deleteById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void editar(Ticket ticket) throws Exception {
		validar(ticket);
		repositorioTicket.save(ticket);
	}
	
	@Transactional(readOnly = true)
	public List<Ticket> listar() {
		List<Ticket> listaTickets = repositorioTicket.findAll();
		return listaTickets;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket obtenerTicketPorId(Long id)throws Exception {
		Optional<Ticket> result = repositorioTicket.findById(id);
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Ticket ticket = result.get();
		return ticket;
	    }
	}
	
	public List<Ticket> listarTicketxPelicula(Pelicula pelicula) throws Exception{
		Optional<Pelicula> result = repositorioPelicula.findById(pelicula.getIdPelicula());
		if(result.isEmpty()) {
			throw new Exception("No se encontró la película");
		}else {
			List<Ticket> listaTickets = repositorioTicket.listarTicketsxPelicula(pelicula.getIdPelicula());
			
			return listaTickets;
		}		
	}
	
	public String contarTicketxPelicula(Pelicula pelicula) throws Exception{
		Optional<Pelicula> result = repositorioPelicula.findById(pelicula.getIdPelicula());
		if(result.isEmpty()) {
			throw new Exception("No se encontró la película");
		}else {
			List<Ticket> listaTickets = repositorioTicket.listarTicketsxPelicula(pelicula.getIdPelicula());
			String numeroTickets = Integer.toString(listaTickets.size());
			return numeroTickets;
		}
	}
	
	public Long totalTicket() throws Exception{
		return repositorioTicket.count();
	}
	
	private void validar(Ticket ticket) throws Error {

        if (ticket.getPelicula() == null ) {
            throw new Error("No se encuentra a que película pertenece el ticket");
        }
        if (ticket.getUsuario() == null ) {
            throw new Error("No se encuentra a que usuario pertenece el ticket");
        }

        if (ticket.getFecha() == null ) {
            throw new Error("Debe indicar la fecha");
        }

        if (ticket.getLugar() == null || ticket.getLugar().trim().isBlank()) {
            throw new Error("Debe ingresar el lugar");
        }

        if (ticket.getPrecio() == null ) {
            throw new Error("El campo 'precio' no puede estar vacío");
        }
        
    }		
}