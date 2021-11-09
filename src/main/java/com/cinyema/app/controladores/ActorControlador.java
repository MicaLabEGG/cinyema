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
import com.cinyema.app.servicios.ActorServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/actor")
public class ActorControlador {

	@Autowired
	private ActorServicio actorServicio;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		try {
		     modelo.addAttribute("listar", "Lista Actores");
		     modelo.addAttribute("actores", actorServicio.listar());
		     return "vistas/admin/actor";
		}catch (Exception e) {
             e.printStackTrace();
             modelo.addAttribute("listar", "Lista Actores");
             modelo.put("error", e.getMessage());
             return "vistas/admin/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
		     modelo.addAttribute("registrar", "Registrar Actor");
		     modelo.addAttribute(actorServicio.registrarVacio());
		     return "vistas/admin/actor";
		}catch (Exception e) {
			 e.printStackTrace();
			 modelo.addAttribute("registrar", "Registrar Actor");
			 modelo.put("error", e.getMessage());
			 return "vistas/admin/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Actor actor)
			throws Exception {
		try {
			actorServicio.registrar(actor);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Actor");
			modelo.addAttribute("actor", actor);
			modelo.put("error", e.getMessage());
			return "redirect:/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{idActor}")
	public String editar(ModelMap modelo, @PathVariable Long idActor) throws Exception {
		try {	
		    modelo.addAttribute("editar", "Editar Actor");
			modelo.addAttribute("actor", actorServicio.obtenerPorId(idActor));
			return "vistas/admin/actor";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Actor");
			modelo.put("error", e.getMessage());
			return "vistas/admin/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{idActor}")
	public String editar(ModelMap modelo, Actor actor) throws Exception {
		try {
			actorServicio.editar(actor);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Actor");
			modelo.addAttribute("actor", actor);
			modelo.put("error", e.getMessage());
			return "redirect:/actor" ;
		}
		
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{idActor}")
	public String eliminar(ModelMap modelo, @PathVariable Long idActor) {
		try {
			actorServicio.eliminar(idActor);
			return "redirect:/actor";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "redirect:/actor";
		}
	}

}
