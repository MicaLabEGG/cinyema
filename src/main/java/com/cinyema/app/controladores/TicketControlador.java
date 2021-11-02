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
	
	@GetMapping("")
	public String listaTicket(ModelMap modelo) {
		
		try {
		    List<Ticket> listTickets = servTic.listarTicket();
		    modelo.addAttribute("listar", "Lista de Tickets");
		    modelo.addAttribute("tickets", listTickets);
		    return "admin/vistas/ticket";
		}catch (Exception e) {
			e.getMessage();
			return "admin/vistas/ticket";
		}
	}
	
	@GetMapping("/registro")
	public String registrarTicket(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Ticket");
		return "admin/vistas/ticket";
	}
	
	@PostMapping("/registro")
	public String registrarTicket(ModelMap modelo, @RequestParam("pelicula") Pelicula pelicula, @RequestParam("usuario") Usuario usuario, @RequestParam("fecha") String fecha, @RequestParam("lugar") String lugar, @RequestParam("precio") Double precio )throws Exception {
		try {
			Ticket ticket = servTic.crearTicket(pelicula, usuario, fecha, lugar,precio);
			modelo.put("ticket", ticket);
			return "redirect:/ticket";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			return "redirect:/ticket";
		}
	}
	
	@GetMapping("/modificar/{id}")
	public String modificarTicket(ModelMap modelo, @PathVariable Long id) throws Exception {
		try {
		     Ticket ticket = servTic.buscarxId(id);
		     modelo.addAttribute("editar", "Editar Ticket");
		     modelo.addAttribute("ticket", ticket);
		     return "admin/vistas/ticket";
		}catch(Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "falta algun dato");
			return "admin/vistas/ticket";
		}
	}
	
	@PostMapping("modificar/{id}")
	public String modificarTicket(ModelMap modelo, @PathVariable Long id, @RequestParam Pelicula pelicula, @RequestParam Usuario usuario, @RequestParam String fecha, @RequestParam String lugar, @RequestParam Double precio) throws Exception {
		try {
		     servTic.modificarTicket(id, pelicula, usuario, fecha, lugar, precio);
		     return "redirect:/ticket";
		}catch(Exception e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
			 modelo.addAttribute("error", e.getMessage());
		}
		return "redirect:/ticket";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarrTicket(@PathVariable Long id) {
		try {
		     servTic.eliminarTicket(id);		
		     return "redirect:/ticket";
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/ticket";
		}
	}
}
	
	
