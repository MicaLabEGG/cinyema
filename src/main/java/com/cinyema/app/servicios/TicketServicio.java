package com.cinyema.app.servicios;


import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.repositorios.PeliculaRepositorio;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio implements ServicioBase<Ticket> {
	
	@Autowired
	private TicketRepositorio repositorioTicket;
	
	@Autowired
	private PeliculaRepositorio repositorioPelicula;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket registrar(Ticket ticket) throws Error, Exception {
		validar(ticket);
		//if(validarFechaCompra(ticket) == true) {
			ticket.getAsiento().setLibre(false);
			return repositorioTicket.save(ticket);
		//}else {
			//throw new Error("No puede comprar un ticket con fecha anterior al dia de hoy");
		//}
	}
	
	public void enviarMailCompra(Ticket ticket, String siteURL) throws UnsupportedEncodingException, MessagingException{
		String tema = "[BOLETO DE CINYEMA]";
		String remitente = "Equipo de Cinyema";
		String contenido = "<p>Querido "+ticket.getUsuario().getNombre()+"</p>";
		contenido += "<p>Has comprado un boleto para el cine</p>";
		contenido += "<p><br>Resumen de tu compra:</p>";
		contenido += "<p>Película: "+ticket.getFuncion().getPelicula().getTitulo()+"</p>";
		contenido += "<p>Sala: "+ticket.getFuncion().getSala().getNombreSala()+"</p>";
		contenido += "<p>Fecha: "+ticket.getFuncion().getFecha()+"</p>";
		contenido += "<p>Horario: "+ticket.getFuncion().getHorario()+"</p>";
		contenido += "<p>Asiento: "+ticket.getAsiento().getNumeroDeAsiento()+"</p>";
		contenido += "<p>Precio: "+ticket.getFuncion().getSala().getCine().getPrecio()+"</p>";
		
		contenido += "<p><br>Gracias por tu compra! Espero que disfrutes la película! <br>Equipo de Cinyema</p>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("cinyema@gmail.com", remitente);
		helper.setTo(ticket.getUsuario().getMail());
		helper.setSubject(tema);
		helper.setText(contenido, true);
		
		mailSender.send(message);
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
	    if(!result.isPresent()) {
	    	throw new Exception("No se encontro");
	    }else {
		Ticket ticket = result.get();
		return ticket;
	    }
	}
	
//	public Boolean validarFechaCompra(Ticket ticket) throws Exception{
//		Date d1 = new Date();  
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date = sdf.format(d1);
//		Date date1 = sdf.parse(date);
//		Date date2 = sdf.parse(ticket.getFecha());
//		if(date1.before(date2)) {
//			return true;
//		}else {
//			return false;
//		}
//	}
	
//	public List<Ticket> listarTicketxPelicula(Pelicula pelicula) throws Exception{
//		Optional<Pelicula> result = repositorioPelicula.findById(pelicula.getIdPelicula());
//		if(!result.isPresent()) {
//			throw new Exception("No se encontró la película");
//		}else {
//			List<Ticket> listaTickets = repositorioTicket.listarTicketsxPelicula(pelicula.getIdPelicula());
//			
//			return listaTickets;
//		}		
//	}
	
	
//	public String contarTicketxPelicula(Pelicula pelicula) throws Exception{
//		Optional<Pelicula> result = repositorioPelicula.findById(pelicula.getIdPelicula());
//		if(!result.isPresent()) {
//			throw new Exception("No se encontró la película");
//		}else {
//			List<Ticket> listaTickets = repositorioTicket.listarTicketsxPelicula(pelicula.getIdPelicula());
//			String numeroTickets = Integer.toString(listaTickets.size());
//			return numeroTickets;
//		}
//	}
	
	public Long totalTicket() throws Exception{
		return repositorioTicket.count();
	}
	
	private void validar(Ticket ticket) throws Error {

		if (ticket.getIdTicket()  == null ) {
            throw new Error("No se encuentra Id del Ticket");
        }
		
		if (ticket.getUsuario() == null ) {
            throw new Error("No se encuentra a que usuario pertenece el ticket");
        }
		
		if (ticket.getFuncion() == null ) {
            throw new Error("No se encuentra la funcion en donde pertenece el ticket");
        }
		
		if (ticket.getAsiento() == null ) {
            throw new Error("El asiento no aparece");
        }
		
//        if (ticket.getPelicula() == null ) {
//            throw new Error("No se encuentra a que película pertenece el ticket");
//        }

//        if (ticket.getFecha() == null ) {
//            throw new Error("Debe indicar la fecha");
//        }
        
//        if (ticket.getPrecio() == null ) {
//            throw new Error("El campo 'precio' no puede estar vacío");
//        }
        
    }		
}