package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinyema.app.entidades.Actor;
import com.cinyema.app.entidades.Funcion;
import com.cinyema.app.servicios.ActorServicio;
import com.cinyema.app.servicios.FuncionServicio;
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.SalaServicio;
import com.cinyema.app.servicios.TicketServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/funcion")
public class FuncionControlador {
	
	@Autowired
	private FuncionServicio funcionServicio;
	
	@Autowired
	private SalaServicio salaServicio;
	
	@Autowired
	private PeliculaServicio peliculaServicio;
	
	@Autowired
	private TicketServicio ticketServicio;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		try {
		     modelo.addAttribute("listar", "Lista Funciones");
		     modelo.addAttribute("funciones", funcionServicio.listar());
		     return "vistas/admin/funcion";
		}catch (Exception e) {
             e.printStackTrace();
             modelo.addAttribute("listar", "Lista Funciones");
             modelo.put("error", e.getMessage());
             return "vistas/admin/funcion";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
		     modelo.addAttribute("registrar", "Registrar Funcion");
		     modelo.addAttribute("funcion", funcionServicio.registrarVacio());
		     modelo.addAttribute("salas", salaServicio.listar());
		     modelo.addAttribute("peliculas", peliculaServicio.listar());
		     modelo.addAttribute("tickets", ticketServicio.listar());
		     return "vistas/admin/funcion";
		}catch (Exception e) {
			 e.printStackTrace();
			 modelo.addAttribute("registrar", "Registrar Funcion");
			 modelo.put("error", e.getMessage());
			 return "vistas/admin/funcion";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Funcion funcion)
			throws Exception {
		try {
			funcionServicio.registrar(funcion);
			return "redirect:/funcion";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Funcion");
			modelo.addAttribute("funcion", funcion);
			modelo.put("error", e.getMessage());
			return "redirect:/funcion";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{idFuncion}")
	public String editar(ModelMap modelo, @PathVariable Long idFuncion) throws Exception {
		try {	
		    modelo.addAttribute("editar", "Editar Funcion");
			modelo.addAttribute("funcion", funcionServicio.obtenerPorId(idFuncion));
			return "vistas/admin/funcion";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Funcion");
			modelo.put("error", e.getMessage());
			return "vistas/admin/funcion";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{idFuncion}")
	public String editar(ModelMap modelo, Funcion funcion) {
		try {
			funcionServicio.editar(funcion);
			return "redirect:/funcion";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Funcion");
			modelo.addAttribute("actor", funcion);
			modelo.put("error", e.getMessage());
			return "redirect:/funcion" ;
		}
		
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{idFuncion}")
	public String eliminar(ModelMap modelo, @PathVariable Long idFuncion) {
		try {
			funcionServicio.eliminar(idFuncion);
			return "redirect:/funcion";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "redirect:/funcion";
		}
	}

}