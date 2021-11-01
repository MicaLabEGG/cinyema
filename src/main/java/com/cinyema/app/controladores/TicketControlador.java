package com.cinyema.app.controladores;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.servicios.TicketServicio;


@Controller
@RequestMapping("/ticket")
public class TicketControlador {
	
	@Autowired
	private TicketServicio servTic;

	@GetMapping("/{id}")
	public String vistaticket(ModelMap modelo, @PathVariable Long id) {
		Ticket tic = servTic.buscarxId(id);
		modelo.put("ticket", tic);
		
		return "ticket.html";		
	}
	
	@GetMapping("/{id}/modificar")
	public String modificarTicket(ModelMap modelo, @PathVariable Long id) throws Exception {
		try {
		     Ticket ticket = servTic.buscarxId(id);
		     modelo.addAttribute("ticket",ticket);
		     return "modificarTicket";
		}catch(Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "falta algun dato");
			return "modificarTicket";
		}
	}
	
	@PostMapping("/{id}/modificar")
	public String modificarTicket(ModelMap modelo, @PathVariable Long id, @RequestParam Pelicula pelicula, @RequestParam Usuario usuario, @RequestParam Date fecha, @RequestParam String lugar, @RequestParam Double precio) throws Exception {
		try {
		     servTic.modificarTicket(id, pelicula, usuario, fecha, lugar, precio);
		     modelo.put("exito", "Modificación exitosa!");
		     return "redirect:/ticket/{id}";
		}catch(Exception e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
			 modelo.addAttribute("error", e.getMessage());
		}
		return "redirect:/ticket/{id}";
	}
	
	@GetMapping("/{id}/eliminar")
	public String eliminarrTicket(@PathVariable Long id) {
		
		servTic.eliminarTicket(id);		
		return "redirect:/autores";
	}
	
	@GetMapping("")
	public String listaTicket(ModelMap modelo) {
		
		List<Ticket> listTickets = servTic.listarTicket();

		modelo.addAttribute("tickets", listTickets);

		return "listaTickets.html";
	}
}
