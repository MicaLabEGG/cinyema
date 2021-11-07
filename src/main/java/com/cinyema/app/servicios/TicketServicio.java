package com.cinyema.app.servicios;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio implements ServicioBase<Ticket> {
	
	@Autowired
	private TicketRepositorio repositorioTicket;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket registrar(Ticket ticket) throws Exception {
		validar(ticket);
		return repositorioTicket.save(ticket);
	}
	
	@Transactional
	public Ticket registrarVacio() {
		return new Ticket();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long id) {
		repositorioTicket.deleteById(id);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket editar(Ticket ticket) throws Exception {
		validar(ticket);
		return repositorioTicket.save(ticket);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Ticket> listar() {
		List<Ticket> listaTickets = repositorioTicket.findAll();
		return listaTickets;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket obtenerPorId(Long id) throws Exception {
		Optional<Ticket> result = repositorioTicket.findById(id);
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Ticket ticket = result.get();
		return ticket;
	    }
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