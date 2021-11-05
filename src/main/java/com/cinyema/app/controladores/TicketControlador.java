package com.cinyema.app.controladores;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.TicketServicio;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
//para saber si inicio sesion(esta logueado) no tiene que ver con Roles - PreAhutorize
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
	public String listaTicket(ModelMap modelo) {

		try {
			List<Ticket> listTickets = servicioTicket.listar();
			modelo.addAttribute("listar", "Lista de Tickets");
			modelo.addAttribute("tickets", listTickets);
			return "admin/vistas/ticket";
		} catch (Exception e) {
			e.getMessage();
			return "admin/vistas/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrarTicket(ModelMap modelo) {
		try {
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute("ticket", servicioTicket.crearTicketVac());
			modelo.addAttribute("peliculas", servicioPelicula.listarPeliculas());
			modelo.addAttribute("usuarios", servicioUsuario.buscarUsuarios());
			return "admin/vistas/ticket";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "admin/vistas/ticket";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrarTicket(ModelMap modelo, Ticket ticket) throws Exception {
		try {
			servicioTicket.crear(ticket);
			return "redirect:/ticket";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute(ticket);
			return "redirect:/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String modificarTicket(ModelMap modelo) throws Exception {
		try {
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute("ticket", servicioTicket.crearTicketVac());
			modelo.addAttribute("peliculas", servicioPelicula.listarPeliculas());
			modelo.addAttribute("usuarios", servicioUsuario.buscarUsuarios());
			return "admin/vistas/ticket";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", e.getMessage());
			return "admin/vistas/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("editar/{id}")
	public String modificarTicket(ModelMap modelo, Ticket ticket, @PathVariable Long id,
			@RequestParam Pelicula pelicula, @RequestParam Usuario usuario, @RequestParam String fecha,
			@RequestParam String lugar, @RequestParam Double precio) throws Exception {
		try {
			servicioTicket.modificar1(id, pelicula, usuario, fecha, lugar, precio);
			return "redirect:/ticket";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			modelo.addAttribute("registrar", "Registrar Ticketr");
			modelo.addAttribute(ticket);
			return "redirect:/ticket";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminarrTicket(@PathVariable Long idTicket) {
		try {
			servicioTicket.eliminarPorId(idTicket);
			return "redirect:/ticket";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/ticket";
		}
	}
}
