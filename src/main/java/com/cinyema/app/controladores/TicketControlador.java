package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.TicketServicio;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/ticket")
public class TicketControlador {

	@Autowired
	private TicketServicio servicioTicket;

	@Autowired
	private PeliculaServicio servicioPelicula;

	@Autowired
	private UsuarioServicio servicioUsuario;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		try {
			modelo.addAttribute("listar", "Lista Tickets");
			modelo.addAttribute("tickets", servicioTicket.listar());
			return "vistas/admin/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			return "vistas/admin/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute("ticket", servicioTicket.registrarVacio());
			modelo.addAttribute("peliculas", servicioPelicula.listar());
			modelo.addAttribute("usuarios", servicioUsuario.listar());
			return "vistas/admin/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "vistas/admin/ticket";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Ticket ticket) throws Exception {
		try {
			servicioTicket.registrar(ticket);
			return "redirect:/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute("ticket", ticket);
			modelo.put("error", e.getMessage());
			return "redirect:/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable long idTicket) throws Exception {
		try {
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", servicioTicket.obtenerTicketPorId(idTicket));
			modelo.addAttribute("peliculas", servicioPelicula.listar());
			modelo.addAttribute("usuarios", servicioUsuario.listar());
			return "vistas/admin/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", servicioTicket.obtenerTicketPorId(idTicket));
			return "vistas/admin/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("editar/{id}")
	public String editar(ModelMap modelo, Ticket ticket) throws Exception {
		try {
			servicioTicket.editar(ticket);
			return "redirect:/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", ticket);
			modelo.put("error", e.getMessage());
			return "redirect:/ticket";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long idTicket) {
		try {
			servicioTicket.eliminar(idTicket);
			return "redirect:/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/ticket";
		}
	}
	
	
	//----- COMPRA TICKET 
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/compra/{idPelicula}")
	public String compra(ModelMap modelo, Authentication autenticacion, @PathVariable Long idPelicula) throws Exception {
		try {
			
			modelo.addAttribute("compra", "Compra Ticket");
			modelo.addAttribute("usuario", servicioUsuario.obtenerUsuarioPorNombre(autenticacion.getName()));
			modelo.addAttribute("pelicula", servicioPelicula.obtenerPeliculaPorId(idPelicula));
			modelo.addAttribute("ticket", servicioTicket.registrarVacio());
			return "vistas/ticketCompra";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.addAttribute("compra", "Compra Ticket");
			modelo.addAttribute("ticket", servicioTicket.registrarVacio());
			return "vistas/ticketCompra";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("compra/{usuario}/{pelicula}")
	public String compra(ModelMap modelo, Ticket ticket) throws Exception {
		try {
			servicioTicket.registrar(ticket);
			return "redirect:/index";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("compra", "Compra Ticket");
			modelo.addAttribute("ticket", ticket);
			modelo.put("error", e.getMessage());
			return "redirect:/index";
		}

	}
}
