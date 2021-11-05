package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cinyema.app.entidades.Actor;
import com.cinyema.app.enumeraciones.Pais;
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
		modelo.addAttribute("listar", "Lista Actores");
		modelo.addAttribute("actores", actorServicio.listar());
		return "vistas/actor";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Actor");
		modelo.addAttribute(actorServicio.registrarVacio());
		return "vistas/actor";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrarActor(ModelMap modelo, Actor actor)
			throws Exception {
		try {
			actorServicio.registrar(actor);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			modelo.addAttribute("registrar", "Registrar Actor");
			actorServicio.registrar(actor);
			return "redirect:/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long idActor) throws Exception {
			modelo.addAttribute("editar", "Editar Actor");
			modelo.addAttribute("actor", actorServicio.obtenerActorPorId(idActor));
			return "vistas/actor";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{id}")
	public String editar(ModelMap modelo, Actor actor) throws Exception {
		try {
			actorServicio.editar(actor);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Actor");
			modelo.addAttribute("error", "Error al ingresar datos");
			actorServicio.editar(actor);
			return "redirect:/actor" ;
		}
		
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			actorServicio.eliminar(id);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/actor";
		}

	}

}
