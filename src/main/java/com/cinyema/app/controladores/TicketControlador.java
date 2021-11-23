package com.cinyema.app.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinyema.app.Utility;
import com.cinyema.app.entidades.Funcion;
import com.cinyema.app.entidades.Sala;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.servicios.AsientoServicio;
import com.cinyema.app.servicios.FuncionServicio;
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.SalaServicio;
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
	
	@Autowired
	private AsientoServicio servicioAsiento;

	@Autowired
	private SalaServicio salaServicio;
	
	@Autowired
	private FuncionServicio funcionServicio;

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
			modelo.addAttribute("asientos", servicioAsiento.listar());
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
	@GetMapping("/editar/{idTicket}")
	public String editar(ModelMap modelo, @PathVariable Long idTicket) throws Exception {
		try {
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", servicioTicket.obtenerPorId(idTicket));
			modelo.addAttribute("peliculas", servicioPelicula.listar());
			modelo.addAttribute("usuarios", servicioUsuario.listar());
			return "vistas/admin/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", servicioTicket.obtenerPorId(idTicket));
			return "vistas/admin/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("editar/{idTicket}")
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
	@GetMapping("/eliminar/{idTicket}")
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
	
	@GetMapping("/compra/{idPelicula}")
	public String compra(ModelMap modelo, Authentication autenticacion, @PathVariable Long idPelicula) throws Exception {
		try {
			//modelo.addAttribute("compra", "Compra Ticket");
			modelo.addAttribute("usuario", servicioUsuario.obtenerUsuarioPorNombre(autenticacion.getName()));
			modelo.addAttribute("pelicula", servicioPelicula.obtenerPorId(idPelicula));
//			List<Sala> salas = servicioPelicula.obtenerSalaPorFuncionIdPelicula(idPelicula);
//			modelo.addAttribute("salas", salas);
			modelo.addAttribute("fechas", salaServicio.obtenerFuncionesPorPeliculaId(idPelicula));
//			for (Sala sala1 : salas) {
//				modelo.addAttribute("asientos", salaServicio.obtenerAsientosLibres(sala1));
//			}
//			Ticket ticket = servicioTicket.registrarVacio();
//			ticket.setUsuario(servicioUsuario.obtenerUsuarioPorNombre(autenticacion.getName()));
////			ticket.setPelicula(servicioPelicula.obtenerPorId(idPelicula));
//			modelo.addAttribute("ticket", ticket);
			return "vistas/ticketCompraFecha";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			//modelo.addAttribute("compra", "Compra Ticket");
			modelo.addAttribute("ticket", servicioTicket.registrarVacio());
			return "vistas/ticketCompraFecha";
		}
	}
	
	@GetMapping("/compra/{idPelicula}/{fecha}")
	public String compraFecha(ModelMap modelo, Authentication autenticacion, @PathVariable Long idPelicula,@PathVariable String fecha) throws Exception {
		try {
			modelo.addAttribute("usuario", servicioUsuario.obtenerUsuarioPorNombre(autenticacion.getName()));
			modelo.addAttribute("pelicula", servicioPelicula.obtenerPorId(idPelicula));
			modelo.put("fecha", fecha);
			modelo.addAttribute("funciones", salaServicio.obtenerFuncionesPorPeliculaIdAndFecha(idPelicula, fecha));
			return "vistas/ticketCompraHorario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "vistas/ticketCompraHorario";
		}
	}
	
	@GetMapping("/compra/{idPelicula}/{fecha}/{horario}")
	public String compraHora(ModelMap modelo, Authentication autenticacion, @PathVariable Long idPelicula,@PathVariable String fecha,@PathVariable String horario) throws Exception {
		try {
			System.err.println(horario + "hola");
			modelo.addAttribute("usuario", servicioUsuario.obtenerUsuarioPorNombre(autenticacion.getName()));
			modelo.addAttribute("pelicula", servicioPelicula.obtenerPorId(idPelicula));
			Funcion funcion = salaServicio.obtenerFuncionesPorPeliculaIdAndFechaAndHorario(idPelicula, fecha, horario);
			modelo.addAttribute("funcion", funcion);
			System.err.println(funcion.toString());
			Sala sala = servicioPelicula.obtenerSalaPorFuncion(funcion.getIdFuncion());
			modelo.addAttribute("sala", sala);
			modelo.addAttribute("asientos", servicioAsiento.listar(funcion.getIdFuncion()));
			Ticket ticket = servicioTicket.registrarVacio();
			ticket.setUsuario(servicioUsuario.obtenerUsuarioPorNombre(autenticacion.getName()));
			ticket.setFuncion(funcion);
			//ticket.setPelicula(servicioPelicula.obtenerPorId(idPelicula));
			System.err.println(ticket.toString());
			modelo.addAttribute("ticket", ticket);
			return "vistas/ticketCompra";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "vistas/ticketCompra";
		}
	}

	@PostMapping("/compra/{idPelicula}/{fecha}/{horario}")
	public String compra(ModelMap modelo, Ticket ticket, HttpServletRequest request) throws Exception {
		try {
			servicioTicket.registrar(ticket);
			String siteURL = Utility.getSiteURL(request);
			servicioTicket.enviarMailCompra(ticket, siteURL);		
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			//modelo.addAttribute("compra", "Compra Ticket");
			modelo.addAttribute("ticket", ticket);
			modelo.put("error", e.getMessage());
			return "vistas/ticketCompra";
		}

	}
}
