package com.cinyema.app.servicios;


import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio {
	
	@Autowired
	private TicketRepositorio repTic;
	
	@Transactional
	public void crearTicket(Ticket ticket) throws Exception {
		
		//String fecha1 = ticket.getFecha();
		//Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(ticket.getFecha());
		
		validar(ticket);
		
		//Ticket tic = new Ticket();
		
		//tic.setPelicula(pel);
		//tic.setUsuario(usu);
		//tic.setFecha(fecha);
		//tic.setLugar(lugar);
		//tic.setPrecio(precio);
		
		repTic.save(ticket);
	}
	
	@Transactional
	public Ticket crearTicketVac() {
		Ticket t = new Ticket();
		
		return t;
	}
	
	@Transactional
	public void eliminarTicket(Long id) {
		
		repTic.deleteById(id);
	}
	
	@Transactional
	public void modificarTicket(Ticket ticket) throws Exception {
		
		//Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
		
		validar(ticket);
		
//		Ticket tic = buscarxId(id);
//		
//		tic.setPelicula(pel);
//		tic.setUsuario(usu);
//		tic.setFecha(fecha);
//		tic.setLugar(lugar);
//		tic.setPrecio(precio);
		
		repTic.save(ticket);
	}
	
	@Transactional
	public List<Ticket> listarTicket() {
		List<Ticket> listaTickets = repTic.findAll();
		return listaTickets;
	}
	
	@Transactional
	public Ticket buscarxId(Long id)throws Exception {
		Optional<Ticket> result = repTic.findById(id);
	       
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